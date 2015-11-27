(function () {
    angular
        .module("Login")
        .controller("LoginCtrl", LoginCtrl);

    function LoginCtrl($scope, $location) {
        var vm = this;

        vm.ifLogout = false;
        console.log("in login/logout controller");

        var logoutval = $location.search();

        console.log(logoutval);

        if(($location.search()).logout){
            console.log("OMG LOGOUT VAL IS TRUE");
        }
    }
})();