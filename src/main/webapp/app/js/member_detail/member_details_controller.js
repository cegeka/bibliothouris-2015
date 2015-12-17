(function() {
    angular
        .module("Bibliothouris")
        .controller("MemberDetailCtrl", MemberDetailCtrl);

    function MemberDetailCtrl(restService, $routeParams, $location, $scope, $route) {
        var vm = this;

        vm.member = {};
        vm.noMember = false;
        vm.noBorrowedBooks = false;
        vm.maxSize = 5;
        vm.itemsPerPage = 5;
        vm.availableItemsPerPage = 10;
        vm.borrowedAndNotReturnedBooks = [];
        vm.noAvailableBooks = false;
        vm.searchFilters = ["Title", "ISBN"];
        vm.filter = vm.searchFilters[0];
        vm.filterValue = "";
        vm.populatedFilterValues = null;
        vm.baseMemberBorrowUrl = $location.url();


        vm.pageChanged = pageChanged;
        vm.borrowBook = borrowBook;
        vm.availableBooksPageChanged = availableBooksPageChanged;
        vm.populateFilterValues = populateFilterValues;
        vm.onSelectFilter = onSelectFilter;

        activate();

        function activate() {
            restService
                .getMemberDetail($routeParams.memberId)
                .then(function(data){
                    vm.member = data;
                }, function(){
                    vm.noMember = true;
                });

            restService
                .getMemberBorrowedHistory($routeParams.memberId, vm.itemsPerPage)
                .then(function(data){
                    vm.borrowedBooks = data;

                    vm.borrowedBooks.forEach(function(book){
                        if(!book.endLendDate)
                            vm.borrowedAndNotReturnedBooks.push(book);
                    })
                }, function() {
                    vm.noBorrowedBooks = true;
                });

            restService
                .countBorrowedHistoryItems($routeParams.memberId)
                .then(function(data){
                    vm.totalItems = data;
                    vm.currentPage = ($location.search().start / vm.itemsPerPage) + 1;
                }, function() {
                    vm.noBorrowedBooks = true;
                });

            restService
                .getAvailableBooks(vm.availableItemsPerPage)
                .then(function(data){
                    vm.availableBooks = data.books;
                    vm.totalAvailableItems = data.booksCount;
                    vm.currentAvailablePage = ($location.search().aStart / vm.availableItemsPerPage) + 1;
                }, function() {
                    vm.noAvailableBooks = true;
                });

            $scope.$watch('vm.filter', function() {
                populateFilterValues();
            });

        }

        function populateFilterValues() {
            if (vm.filter == "Title")
                restService
                    .getBookTitles()
                    .then(function(data){
                        vm.populatedFilterValues = data;
                    });
            else if (vm.filter == "ISBN")
                restService
                    .getBookIsbnCodes()
                    .then(function(data){
                        vm.populatedFilterValues = data;
                    });
        }

        function onSelectFilter() {
            console.log(vm.searchFilters);
            vm.searchFilters.forEach(function(key) {
                $location.search(key.toLowerCase(), null);
            });
            $location.search(vm.filter.toLowerCase(), vm.filterValue);

            vm.currentAvailablePage = 1;

            availableBooksPageChanged();
        }

        function pageChanged() {
            start = vm.itemsPerPage * (vm.currentPage - 1);
            end = start + vm.itemsPerPage;

            if (start != $location.search().start && end != $location.search().end) {
                $location.search('start', start);
                $location.search('end', end);
            }
        }

        function availableBooksPageChanged() {
            aStart = vm.availableItemsPerPage * (vm.currentAvailablePage - 1);
            aEnd = aStart + vm.availableItemsPerPage;

            if (aStart != $location.search().aStart && aEnd != $location.search().aEnd) {
                $location.search('aStart', aStart);
                $location.search('aEnd', aEnd);
            }
        }

        function borrowBook(bookId) {
            vm.historyItem = {
                bookId: bookId,
                memberUuid: vm.member.UUID,
                startDate: moment(Date.now()).format("YYYY-MM-DD")
            };

            restService
                .borrowBook(vm.historyItem)
                .then(function(data){
                    if(data.status == 400) {
                        vm.tooManyBorrowedBooks = true;
                        console.log(data.status);
                    }

                    if(angular.equals($location.search(), {})) {
                        $route.reload();
                    } else {
                        $location.url("/member/" + vm.member.UUID);
                    }

                }, function(data){
                    console.log(data);

                });
        }
    }
})();