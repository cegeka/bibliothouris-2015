(function () {
    angular
        .module("Login")
        .controller("LoginCtrl", LoginCtrl);

    function LoginCtrl($scope, $location) {

        $scope.loginFailed = function () {
            if (($location.search()).error) {
                return "has-error";
            }
        }

        $scope.logoutSuccess = function () {
            return ($location.search()).logout;
        }
    }
})();