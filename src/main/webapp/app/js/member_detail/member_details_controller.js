(function() {
    angular
        .module("Bibliothouris")
        .controller("MemberDetailCtrl", MemberDetailCtrl);

    function MemberDetailCtrl(restService, $routeParams) {
        var vm = this;

        vm.member = {};

        activate();

        function activate() {
            restService
                .getMemberDetails($routeParams.memberId)
                .then(function(data){
                    vm.member = data;
                });
        }
    }
})();