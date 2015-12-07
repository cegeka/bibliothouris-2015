(function() {
    angular
        .module("Bibliothouris")
        .controller("ListBookDetailsCtrl", ListBookDetailsCtrl);

    function ListBookDetailsCtrl(restService, $routeParams) {
        var vm = this;

        vm.book = {};

        activate();

        function activate() {
            restService
                .getBookDetails($routeParams.bookId)
                .then(function(data){
                    vm.book = data;
                    if (vm.book.cover == null)
                        vm.book.cover = "../icons/default_book.png";
                });
        }
    }
})();