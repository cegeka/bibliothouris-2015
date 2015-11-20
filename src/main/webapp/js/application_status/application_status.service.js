(function() {
    angular
        .module("Bibliothouris")
        .factory("applicationStatusService", applicationStatusService);

    function applicationStatusService($http) {
        var service = {
            getStatus: getStatus
        };

        return service;

        function getStatus() {
            return $http.get("/api/status")
                .then(function(response){
                    return response.data;
                });
        }
    }
})();