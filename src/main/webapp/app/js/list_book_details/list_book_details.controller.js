(function() {
    angular
        .module("Bibliothouris")
        .controller("ListBookDetailsCtrl", ListBookDetailsCtrl);

    function ListBookDetailsCtrl(restService) {
        var vm = this;

        activate();

        function activate() {
        }
    }
})();