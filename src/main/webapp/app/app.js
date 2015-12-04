(function() {
    console.log("app.js");

    angular
        .module("Bibliothouris", ["ngRoute", "ui.bootstrap", "bootstrap.fileField"])
        .config(function($routeProvider, $httpProvider){
            $routeProvider
                .when("/status", {
                    templateUrl: "templates/application_status.html",
                    controller: "ApplicationStatusCtrl",
                    controllerAs: "vm"
                })
                .when("/books/add", {
                    templateUrl: "templates/add_book.html",
                    controller: "AddBookCtrl",
                    controllerAs: "vm"
                })
                .when("/books", {
                    templateUrl: "templates/list_books.html",
                    controller: "ListBooksCtrl"
                })
                .when("/books/:bookId", {
                    templateUrl: "templates/list_book_details.html",
                    controller: "ListBookDetailsCtrl",
                    controllerAs: "vm"
                })
                .when("/login", {
                    templateUrl: "templates/login.html",
                    controller: "LoginCtrl"
                })
                .when("/member/add", {
                    templateUrl: "templates/add_member.html",
                    controller: "AddMemberCtrl"
                })
                .otherwise({redirectTo:"/status"});

            $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
        });

})();