(function() {
    angular
        .module("Bibliothouris")
        .controller("MemberDetailCtrl", MemberDetailCtrl);

    function MemberDetailCtrl(restService, $routeParams) {
        var vm = this;

        vm.member = {};
        vm.noMember = false;

        activate();

        function activate() {
            restService
                .getMemberDetail($routeParams.memberId)
                .then(function(data){
                    vm.member = data;
                }, function(){
                    vm.noMember = true;
                });
        }
    }
})();