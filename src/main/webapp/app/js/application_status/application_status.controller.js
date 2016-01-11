(function() {
    angular
        .module("Bibliothouris")
        .controller("ApplicationStatusCtrl", ApplicationStatusCtrl);

    function ApplicationStatusCtrl(applicationStatusService, $timeout, $scope) {
        var vm = this;

        var onTimeout = function() {
            getAppStatus();
            timer = $timeout(onTimeout, 3600 * 1000);
        };

        var timer = $timeout(onTimeout, 3600 * 1000);

        getAppStatus();

        $scope.$on("$destroy", function() {
            if (timer) {
                $timeout.cancel(timer);
            }
        });

        function getAppStatus() {
            applicationStatusService
                .getStatus()
                .then(function(data){
                    vm.status = data;
                }, function() {
                    vm.status.upTime = "Server is down!";
                    vm.status.isDatabaseConnected = false;
                });
        }
    }
})();