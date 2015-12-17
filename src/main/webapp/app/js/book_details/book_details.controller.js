(function() {
    angular
        .module("Bibliothouris")
        .controller("ListBookDetailsCtrl", ListBookDetailsCtrl);

    function ListBookDetailsCtrl(restService, $routeParams) {
        var vm = this;

        vm.book = {};
        vm.noBook = false;
        vm.isBookBorrowedborrowerDetails = {};

        activate();

        function activate() {
            restService
                .getBookDetails($routeParams.bookId)
                .then(function(data){
                    vm.book = data;
                    if (vm.book.cover == null)
                        vm.book.cover = "../icons/default_book.png";
                }, function(){
                    vm.noBook = true;
                });

            restService
                .getBookBorrowerDetails($routeParams.bookId)
                .then(function(data){
                    vm.borrowerDetails = data;
                    console.log(data);
                });
        }
    }
})();