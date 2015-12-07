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
                .countBooks()
                .then(function(data){
                    vm.totalItems = data;

                    pageChanged();
                }, function(){
                    vm.noBooks = true;
                });
        }

        function onSelectFilter() {
            console.log(vm.filterValue);
        }

        function showBook(bookId) {
            $location.path($location.url() + "/" + bookId);
        }

        function pageChanged() {
            start = vm.itemsPerPage * (vm.currentPage - 1);
            end = start + vm.itemsPerPage;

            restService
                .getBooks(start, end)
                .then(function(data){
                    vm.books = data;
                }, function(){
                    vm.noBooks = true;
                });
        }
    }
})();

