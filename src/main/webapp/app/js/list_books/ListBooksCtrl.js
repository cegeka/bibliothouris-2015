(function() {
    angular
        .module("Bibliothouris")
        .controller("ListBooksCtrl", ListBooksCtrl);

    function ListBooksCtrl(restService, $location) {
        var vm = this;

        vm.noBooks = false;
        vm.searchFilters = ["Title", "ISBN"];
        vm.filter = vm.searchFilters[0];
        vm.filterValue = "";
        vm.maxSize = 5;
        vm.itemsPerPage = 10;
        vm.currentPage = 1;

        vm.onSelectFilter = onSelectFilter;
        vm.showBook = showBook;
        vm.pageChanged = pageChanged;

        activate();

        function activate() {
            restService
                .getBookTitles()
                .then(function(data){
                    vm.titles = data;
                });

            restService
                .getBooks()
                .then(function(data){
                    vm.books = data.books;
                    vm.totalItems = data.booksCount;

                    if (!$location.search().start)
                        vm.currentPage = 1;
                    else
                        vm.currentPage = ($location.search().start / vm.itemsPerPage) + 1;
                }, function(){
                    vm.noBooks = true;
                });
        }

        function onSelectFilter() {
            $location.search(vm.filter.toLowerCase(), vm.filterValue);

            vm.currentPage = 1;

            pageChanged();
        }

        function showBook(bookId) {
            $location.path($location.url() + "/" + bookId);
        }

        function pageChanged() {
            start = vm.itemsPerPage * (vm.currentPage - 1);
            end = start + vm.itemsPerPage;

            $location.search('start', start);
            $location.search('end', end);
        }
    }
})();

