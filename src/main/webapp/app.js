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
                .when("/books/add", {
                    templateUrl: "templates/add_book.html",
                    controller: "AddBookCtrl"
                })
                .when("/books", {
                    templateUrl: "templates/list_books.html",
                    controller: "ListBooksCtrl"
                })
                .when("/login", {
                    templateUrl: "templates/login.html",
                    controller: "LoginCtrl"
                })
                .otherwise({redirectTo:"/status"});
        });
})();