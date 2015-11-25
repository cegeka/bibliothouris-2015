(function() {
    angular
        .module("Bibliothouris")
        .controller("AddBookCtrl", AddBookCtrl);

    function AddBookCtrl(restService, $location, $uibModal) {
        var vm = this;
        vm.originalBook = {};
        vm.book = {};

        vm.addAuthor = addAuthor;
        vm.deleteAuthor = deleteAuthor;
        vm.submitForm = submitForm;
        vm.resetForm = resetForm;

        activate();

        function activate() {
            vm.originalBook = {
                authors: [""]
            };
            vm.book = angular.copy(vm.originalBook);
        }

        function addAuthor() {
            vm.book.authors.push("");
        }

        function deleteAuthor($index) {
            vm.book.authors.splice($index, 1);

            if (vm.book.authors.length == 0)
                vm.book.authors.push("");
        }

        function submitForm() {
            restService
                .addBook(vm.book)
                .then(function(data){
                    $location.path("/books/" + data.id);
                });

            $uibModal.open({
                templateUrl: 'templates/informative_modal.html',
                resolve: {
                    message: function () {
                        return "ceva de test";
                    }
                }
            });
        }

        function resetForm() {
            vm.book = angular.copy(vm.originalBook);
        }
    }
})();