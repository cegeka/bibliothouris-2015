(function() {
    angular
        .module("Bibliothouris")
        .controller("AddImportDetailsCtrl", AddImportDetailsCtrl);

    function AddImportDetailsCtrl(book, $uibModalInstance) {
        var vm = this;

        vm.book = book;
        vm.authors = [""];
        vm.isbn = {
            prefix: "",
            registrationGroupElement: "",
            registrantElement: "",
            publicationElement: "",
            checkDigit: ""
        };
        vm.isbnPattern = "(?=[0-9]{13}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)97[89][- ]?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9]";

        vm.addAuthor = addAuthor;
        vm.deleteAuthor = deleteAuthor;
        vm.submitForm = submitForm;
        vm.onChangeIsbnParts = onChangeIsbnParts;
        vm.closeModal = closeModal;

        function addAuthor() {
            vm.authors.push("");
        }

        function deleteAuthor($index) {
            vm.authors.splice($index, 1);

            if (vm.authors.length == 0)
                vm.authors.push("");
        }

        function onChangeIsbnParts(){
            vm.isbnText = vm.isbn.prefix +"-"+ vm.isbn.registrationGroupElement+"-"+vm.isbn.registrantElement +"-"+vm.isbn.publicationElement +"-"+vm.isbn.checkDigit ;
        }

        function submitForm(bookForm) {
            if(bookForm.$valid){
                if (vm.book.authors.length == 0)
                    vm.book.authors = vm.authors;
                if (!vm.book.isbn)
                    vm.book.isbn = vm.isbnText;

                $uibModalInstance.close(vm.book);
            }
        }

        function closeModal() {
            $uibModalInstance.dismiss('cancel');
        }
    }
})();