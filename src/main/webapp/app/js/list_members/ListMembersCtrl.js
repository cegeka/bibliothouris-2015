(function() {
    angular
        .module("Bibliothouris")
        .controller("ListMembersCtrl", ListMembersCtrl);

    function ListMembersCtrl(memberService, $location, $rootScope) {
        var vm = this;

        vm.itemsPerPage = 10;
        vm.currentPage = 1;
        vm.noMembers = false;
        vm.maxSize = 5;

        vm.pageChanged = pageChanged;
        vm.showMember = showMember;
        vm.addFieldToSort = addFieldToSort;
        vm.createSearchUrlForMembers = createSearchUrlForMembers;

        activate();

        function activate() {
            loadElementsInPage();

            $rootScope.$on('$routeUpdate', loadElementsInPage);
        }

        function loadElementsInPage() {
            memberService
                .getMembers(createSearchUrlForMembers())
                .then(function(data) {
                    vm.members = data.items;
                    vm.totalItems = data.itemsCount;
                    vm.currentPage = ($location.search().start / vm.itemsPerPage) + 1;
                }, function(){
                    vm.noMembers = true;
                });
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

            $location.search('start', start);
            $location.search('end', end);
            $location.search('sort', vm.sortString);
            $location.search('order', vm.orderString);
        }

        function addFieldToSort(field) {
            if (vm.sortString != field)
                vm.orderString = "asc";
            else
                vm.orderString = vm.orderString == "asc" ? "desc" : "asc";

            vm.sortString = field;

            vm.currentPage = 1;
            pageChanged();
        }

        function createSearchUrlForMembers() {
            if (!$location.search().start && !$location.search().end) {
                vm.orderString = "desc";
                vm.sortString = "memberSince";
                return "?start=0&end=" + vm.itemsPerPage + "&sort=memberSince&order=desc";
            } else {
                vm.orderString = $location.search().order;
                vm.sortString = $location.search().sort;
                return $location.url().substr($location.path().length);
            }
        }
    }
}
)();