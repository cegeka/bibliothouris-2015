(function() {
    console.log("app.js");

    angular
        .module("Bibliothouris", ["ngRoute"])
        .config(function($routeProvider){
            $routeProvider
                .when("/status", {
                    templateUrl: "templates/application_status.html",
                    controller: "ApplicationStatusController",
                    controllerAs: "vm"
                })
                .otherwise({redirectTo:"/status"});
        });
})();