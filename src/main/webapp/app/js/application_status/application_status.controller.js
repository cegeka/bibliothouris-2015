(function() {
    angular
        .module("Bibliothouris")
        .controller("ApplicationStatusController", ApplicationStatusController);

    function ApplicationStatusController(applicationStatusService) {
        var vm = this;
        vm.status = {};
        activate();
        function activate() {
            applicationStatusService
                .getStatus()
                .then(function(data){
                    vm.status = data;
                });
        };
        console.log("controller");
    }
})();