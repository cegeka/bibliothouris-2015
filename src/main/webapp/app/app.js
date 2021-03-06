(function() {
    angular
        .module("Bibliothouris", ["ngRoute", "ngMessages", "ui.bootstrap", "file-model", "angular-loading-bar"])
        .config(function($routeProvider, $httpProvider, cfpLoadingBarProvider){
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
                .when("/books/import", {
                    templateUrl: "templates/import_books.html",
                    controller: "ImportBooksCtrl",
                    controllerAs: "vm",
                    reloadOnSearch: false
                })
                .when("/books", {
                    templateUrl: "templates/list_books.html",
                    controller: "ListBooksCtrl",
                    controllerAs: "vm",
                    reloadOnSearch: false
                })
                .when("/books/:bookId", {
                    templateUrl: "templates/book_details.html",
                    controller: "ListBookDetailsCtrl",
                    controllerAs: "vm"
                })
                .when("/login", {
                    templateUrl: "templates/login.html",
                    controller: "LoginCtrl"
                })
                .when("/member", {
                    templateUrl: "templates/list_members.html",
                    controller: "ListMembersCtrl",
                    controllerAs: "vm",
                    reloadOnSearch: false
                })
                .when("/member/add", {
                    templateUrl: "templates/add_member.html",
                    controller: "AddMemberCtrl",
                    controllerAs: "vm"
                })

                .when("/member/:memberId", {
                    templateUrl: "templates/member_detail.html",
                    controller: "MemberDetailCtrl",
                    controllerAs: "vm",
                    reloadOnSearch: false
                })

                .when("/borrow", {
                    templateUrl: "templates/borrowed_books.html",
                    controller: "BorrowedBooksCtrl",
                    controllerAs: "vm",
                    reloadOnSearch: false
                })
                .otherwise({redirectTo:"/books"});

            cfpLoadingBarProvider.includeSpinner = false;

            $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
        });

})();