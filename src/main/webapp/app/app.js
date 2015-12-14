(function() {
    angular
        .module("Bibliothouris", ["ngRoute", "ngMessages", "ui.bootstrap", "file-model", "angular-loading-bar"])
        .config(function($routeProvider, $httpProvider, cfpLoadingBarProvider){
            cfpLoadingBarProvider.includeSpinner = false;

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
                    controller: "ListBooksCtrl",
                    controllerAs: "vm"
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
                    controller: "AddMemberCtrl",
                    controllerAs: "vm"
                })
                .otherwise({redirectTo:"/status"});

            $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
        });

})();