(function() {
    angular
        .module("Bibliothouris")
        .controller("ListMembersCtrl", ListMembersCtrl);

    function ListMembersCtrl(memberService, $location) {
        var vm = this;

        vm.itemsPerPage = 10;
        vm.currentPage = 1;
        vm.noMembers = false;
        vm.maxSize = 5;

        vm.pageChanged = pageChanged;
        vm.showMember = showMember;

        activate();

        function activate() {
            memberService
                .getMembers(vm.itemsPerPage)
                .then(function(data) {
                    vm.members = data.members;
                    console.log(data);
                    vm.totalItems = data.membersCount;
                })
        }

        function showMember(memberId) {
            for (var key in $location.search()) {
                $location.search(key, null);
            }

            $location.path($location.path()+ "/" + memberId);
        }

        function pageChanged() {
            start = vm.itemsPerPage * (vm.currentPage - 1);
            end = start + vm.itemsPerPage;

            if (start != $location.search().start && end != $location.search().end) {
                $location.search('start', start);
                $location.search('end', end);
            }
        }
    }
}
)();