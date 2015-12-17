(function () {
    angular
        .module("Bibliothouris")
        .controller("BorrowedBooksCtrl", BorrowedBooksCtrl);

    function BorrowedBooksCtrl(restService, $location) {
        var vm = this;

        vm.maxSize = 10;
        vm.itemsPerPage = 10;

        vm.borrowedBooks = {};
        vm.noBorrows = false;
        vm.pageChanged = pageChanged;
        activate();

        function activate() {
            if (!($location.search().start) && !($location.search().end)) {
                $location.search('start', 0);
                $location.search('end', vm.itemsPerPage);
            }
            var searchUrl = "?start=" + $location.search().start + "&" + "end=" + $location.search().end;
            console.log(searchUrl);
            restService
                .countBorrowedBooks()
                .then(function(data){
                    console.log(data);
                    vm.numberOfItems = data;
                    console.log(vm.numberOfItems);
                });

            restService
                .getGlobalBorrowedBooks(searchUrl)
                .then(function(data){
                    console.log(data);
                    vm.borrowedBooks = data;
                    vm.currentPage = ($location.search().start / vm.itemsPerPage) + 1;
                    console.log(vm.currentPage);
                }, function(){
                   vm.noBorrows = true;
                });
        }

        function pageChanged() {
            start = vm.itemsPerPage * (vm.currentPage - 1);
            end = start + vm.itemsPerPage;

            $location.search('start', start);
            $location.search('end', end);
        }
    }
})();