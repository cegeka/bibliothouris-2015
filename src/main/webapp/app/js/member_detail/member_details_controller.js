(function() {
    angular
        .module("Bibliothouris")
        .controller("MemberDetailCtrl", MemberDetailCtrl);

    function MemberDetailCtrl(bookService, memberService, borrowService, notificationService,
                              $routeParams, $location, $scope, $rootScope) {
        var vm = this;

        vm.member = {};
        vm.noMember = false;
        vm.noBorrowedBooks = false;
        vm.maxSize = 5;
        vm.itemsPerPage = 5;
        vm.currentAvailablePage = 1;
        vm.availableItemsPerPage = 10;
        vm.borrowedAndNotReturnedBooks = [];
        vm.overdueBooks = [];
        vm.noAvailableBooks = false;
        vm.searchFilters = ["Title", "ISBN"];
        vm.filter = vm.searchFilters[0];
        vm.filterValue = "";
        vm.populatedFilterValues = null;
        vm.baseMemberBorrowUrl = $location.url();
        vm.isCollapsed = false;
        vm.isBorrowCollapsed = false;

        vm.pageChanged = pageChanged;
        vm.borrowBook = borrowBook;
        vm.returnBook = returnBook;
        vm.availableBooksPageChanged = availableBooksPageChanged;
        vm.populateFilterValues = populateFilterValues;
        vm.onSelectFilter = onSelectFilter;
        vm.enableAuthorTooltip = enableAuthorTooltip;
        vm.enableTitleTooltip = enableTitleTooltip;
        vm.getAuthorTooltipContent = getAuthorTooltipContent;

        activate();

        function activate() {
            loadElementsInPage();

            $rootScope.$on('$routeUpdate', loadElementsInPage);

            $scope.$watch('vm.filter', populateFilterValues);
        }

        function loadElementsInPage() {
            memberService
                .getMemberDetail($routeParams.memberId)
                .then(function(data){
                    vm.member = data;
                }, function(){
                    vm.noMember = true;
                });

            getBorrowedBooks(createSearchUrlForBorrowedBooks());
            getAvailableBooks(createSearchUrlForAvailableBooks());
        }

        function getAvailableBooks(searchUrlForAvailableBooks) {
            borrowService
                .getAvailableBooks(searchUrlForAvailableBooks)
                .then(function(data){
                    vm.availableBooks = data.items;
                    vm.totalAvailableItems = data.itemsCount;
                    vm.noAvailableBooks = false;
                }, function() {
                    vm.noAvailableBooks = true;
                });
        }

        function getBorrowedBooks(searchUrlForBorrowedBooks) {
            memberService
                .getMemberBorrowedHistory($routeParams.memberId, searchUrlForBorrowedBooks)
                .then(function(data){
                    vm.borrowedBooks = data.items;
                    vm.totalItems = data.itemsCount;
                    vm.currentPage = ($location.search().start / vm.itemsPerPage) + 1;

                    vm.borrowedBooks.forEach(function(book){
                        if(!book.endLendDate)
                            vm.borrowedAndNotReturnedBooks.push(book);
                    });
                    vm.noBorrowedBooks = false;
                }, function() {
                    vm.noBorrowedBooks = true;
                });
        }

        function populateFilterValues() {
            if (vm.filter == "Title")
                bookService
                    .getBookTitles()
                    .then(function(data){
                        vm.populatedFilterValues = data;
                    });
            else if (vm.filter == "ISBN")
                bookService
                    .getBookIsbnCodes()
                    .then(function(data){
                        vm.populatedFilterValues = data;
                    });
        }

        function onSelectFilter() {
            vm.currentAvailablePage = 1;
            getAvailableBooks(createSearchUrlForAvailableBooks());
        }

        function pageChanged() {
            start = vm.itemsPerPage * (vm.currentPage - 1);
            end = start + vm.itemsPerPage;

            if ($location.search().start == start && $location.search().end == end) {
                getBorrowedBooks(createSearchUrlForBorrowedBooks());
            } else {
                $location.search('start', start);
                $location.search('end', end);
            }
        }

        function availableBooksPageChanged() {
            getAvailableBooks(createSearchUrlForAvailableBooks());
        }

        function borrowBook(bookId) {
            vm.historyItem = {
                bookId: bookId,
                memberUuid: vm.member.UUID,
                date: moment(Date.now()).format("YYYY-MM-DD")
            };

            borrowService
                .borrowBook(vm.historyItem)
                .then(function(){
                    vm.currentPage = 1;
                    pageChanged();

                    if (vm.availableBooks.length == 1)
                        vm.currentAvailablePage = 1;
                    availableBooksPageChanged();
                }, function(data){
                    if(data.status == 400)
                        notificationService.createNotification(data.data.value, "danger");
                    else
                        notificationService.createNotification("Something wrong happened when you tried to borrow a book!", "danger")
                });
        }

        function returnBook(borrowHistoryItemId) {
            vm.borrowHistoryItemIdTO = {
                value: borrowHistoryItemId
            };

            borrowService
                .returnBook(vm.borrowHistoryItemIdTO)
                .then(function(){
                    vm.currentPage = 1;
                    pageChanged();

                    availableBooksPageChanged();
                });
        }

        function createSearchUrlForBorrowedBooks() {
            if (!$location.search().start && !$location.search().end)
                return "?start=0&end=" + vm.itemsPerPage;
            else
                return "?start=" + $location.search().start + "&" + "end=" + $location.search().end;
        }

        function createSearchUrlForAvailableBooks() {
            aStart = vm.availableItemsPerPage * (vm.currentAvailablePage - 1);
            aEnd = aStart + vm.availableItemsPerPage;
            var searchUrlForAvailableBooks = "?start=" + aStart + "&end=" + aEnd;

            if (vm.filter == "Title")
                searchUrlForAvailableBooks += "&title=" + vm.filterValue;
            else if (vm.filter == "ISBN")
                searchUrlForAvailableBooks += "&isbn=" + vm.filterValue;

            return searchUrlForAvailableBooks;
        }

        function enableAuthorTooltip(param, book) {
            book.authorTooltip = param;
        }

        function getAuthorTooltipContent(book) {
            return book.authors.map(function(author){
                return author.firstName + " " + author.lastName;
            }).join(", ");
        }

        function enableTitleTooltip(param, book) {
            book.titleTooltip = param;
        }
    }
})();