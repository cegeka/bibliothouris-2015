(function() {
    angular
        .module("Bibliothouris")
        .controller("ListBooksCtrl", ListBooksCtrl);

    function ListBooksCtrl(restService, $location, $scope) {
        var vm = this;

        vm.noBooks = false;
        vm.searchFilters = ["Title", "ISBN"];
        vm.filter = vm.searchFilters[0];
        vm.filterValue = "";
        vm.maxSize = 5;
        vm.itemsPerPage = 10;
        vm.currentPage = 1;
        vm.populatedFilterValues = null;

        vm.onSelectFilter = onSelectFilter;
        vm.showBook = showBook;
        vm.pageChanged = pageChanged;
        vm.populateFilterValues = populateFilterValues;
        vm.enableTooltip = enableTooltip;
        vm.getTooltipContent = getTooltipContent;

        activate();

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

        function activate() {
            $scope.$watch('vm.filter', function() {
                populateFilterValues();
            });

            restService
                .getBooks()
                .then(function(data){
                    vm.books = data.books;
                    vm.totalItems = data.booksCount;

                    if (!$location.search().start) {
                        $location.search('start', 0);
                        $location.search('end', vm.itemsPerPage);
                    }

                    vm.currentPage = ($location.search().start / vm.itemsPerPage) + 1;
                }, function(){
                    vm.noBooks = true;
                });
        }

        function onSelectFilter() {
            for (var key in $location.search()) {
                if (key != "start" && key != "end")
                    $location.search(key, null);
            }
            $location.search(vm.filter.toLowerCase(), vm.filterValue);

            vm.currentPage = 1;

            pageChanged();
        }

        function showBook(bookId) {
            for (var key in $location.search()) {
                $location.search(key, null);
            }

            $location.path($location.path() + "/" + bookId);
        }

        function pageChanged() {
            start = vm.itemsPerPage * (vm.currentPage - 1);
            end = start + vm.itemsPerPage;

            $location.search('start', start);
            $location.search('end', end);
        }

        function enableTooltip(param, book) {
            book.tooltip = param;
        }

        function getTooltipContent(book) {
            return book.authors.map(function(author){
                return author.firstName + " " + author.lastName;
            }).join(", ");
        }
    }
})();

