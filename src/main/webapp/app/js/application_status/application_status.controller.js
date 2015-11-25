(function() {
    angular
        .module("Bibliothouris")
        .controller("ApplicationStatusCtrl", ApplicationStatusCtrl);

    function ApplicationStatusCtrl(applicationStatusService) {
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