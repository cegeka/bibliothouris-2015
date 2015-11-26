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
                    createNotification("Book <strong>" + data.title + "</strong> was added in the library!", "success")
                }, function(data){
                    createNotification("Something wrong happened when you tried to add a new book!", "danger")
                });
        }

        function resetForm() {
            vm.book = angular.copy(vm.originalBook);
        }

        function createNotification(message, type) {
            $.notify(message,{
                placement: {
                    from: "bottom",
                    align: "right"
                },
                type: type,
                delay: 1500,
                animate: {
                    enter: 'animated fadeInUp',
                    exit: 'animated fadeOutDown'
                },
                template: '<div data-notify="container" class="col-xs-11 col-sm-2 alert alert-{0}" role="alert">' +
                '<button type="button" aria-hidden="true" class="close" data-notify="dismiss">×</button>' +
                '<span data-notify="icon"></span> ' +
                '<span data-notify="title">{1}</span> ' +
                '<span data-notify="message">{2}</span>' +
                '<div class="progress" data-notify="progressbar">' +
                '<div class="progress-bar progress-bar-{0}" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;"></div>' +
                '</div>' +
                '<a href="{3}" target="{4}" data-notify="url"></a>' +
                '</div>'
            });
        }
    }
})();