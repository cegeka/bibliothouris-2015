(function() {
    angular
        .module("Bibliothouris")
        .controller("ApplicationStatusController", ApplicationStatusController);

    function ApplicationStatusController(applicationStatusService) {
        console.log("controller");

        var vm = this;

        vm.status = {};

        activate();

        function activate() {
            applicationStatusService
                .getStatus()
                .then(function(data){
                    vm.status = data;
                });
        }
    }
})();