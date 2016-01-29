(function() {
    angular
        .module("Bibliothouris")
        .controller("ListBooksCtrl", ListBooksCtrl);

    function ListBooksCtrl(bookService, $location, $scope, $rootScope) {
        var vm = this;

        vm.noBooks = false;
        vm.searchFilters = ["Title", "ISBN", "FirstName", "LastName"];
        vm.filter = vm.searchFilters[0];
        vm.filterValue = "";
        vm.maxSize = 5;
        vm.itemsPerPage = 10;
        vm.currentPage = 1;
        vm.populatedFilterValues = null;

        vm.onSelectFilter = onSelectFilter;
        vm.showBook = showBook;
        vm.pageChanged = pageChanged;
        vm.populateFilterValues = populateFilterValuesForAutocomplete;
        vm.enableTooltip = enableTooltip;
        vm.getTooltipContent = getTooltipContent;
        vm.addFieldToSort = addFieldToSort;

        activate();

        function activate() {
            loadElementsInPage();

            $rootScope.$on('$routeUpdate', loadElementsInPage);

            $scope.$watch('vm.filter', populateFilterValuesForAutocomplete);
        }

        function loadElementsInPage() {
            vm.orderString = $location.search().order;
            vm.sortString = $location.search().sort;

            if ($location.search().title != null) {
                vm.filter = "Title";
                vm.filterValue = $location.search().title;
                console.log('title');
            } else if ($location.search().isbn != null) {
                vm.filter = "ISBN";
                vm.filterValue = $location.search().isbn;
            } else if ($location.search().firstName != null) {
                vm.filter = "FirstName";
                vm.filterValue = $location.search().firstName;
                console.log('first name');
            } else if ($location.search().lastName != null) {
                vm.filter = "LastName";
                vm.filterValue = $location.search().lastName;
            }

            bookService
                .getBooks(createSearchUrlForBooks())
                .then(function(data){
                    vm.books = data.items;
                    vm.totalItems = data.itemsCount;
                    vm.currentPage = ($location.search().start / vm.itemsPerPage) + 1;
                    vm.noBooks = false;
                }, function(){
                    vm.noBooks = true;
                });
        }

        function populateFilterValuesForAutocomplete() {
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
            else if (vm.filter == "FirstName")
                bookService
                    .getBookAuthorsFirstNames()
                    .then(function(data){
                        vm.populatedFilterValues = data;
                        console.log(data);
                    });
            else if (vm.filter == "LastName")
                bookService
                    .getBookAuthorsLastNames()
                    .then(function(data){
                        vm.populatedFilterValues = data;
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

        function createSearchUrlForBooks() {
            if (!$location.search().start && !$location.search().end) {
                var aux = "?start=0&end=" + vm.itemsPerPage;

                if (vm.filterValue)
                    aux += "&" + vm.filter.toLowerCase() + "=" + vm.filterValue;
                return aux;
            } else {
                return $location.url().substr($location.path().length);
            }
        }

        function addFieldToSort(field) {
            if (vm.sortString != field)
                vm.orderString = "asc";
            else
                vm.orderString = vm.orderString == "asc" ? "desc" : "asc";

            vm.sortString = field;

            $location.search('sort', vm.sortString);
            $location.search('order', vm.orderString);

            vm.currentPage = 1;
            pageChanged();
        }
    }
})();

