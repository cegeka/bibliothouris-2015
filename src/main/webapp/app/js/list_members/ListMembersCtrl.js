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
        vm.onSelectFilter = onSelectFilter;

        activate();

        function activate() {
            loadElementsInPage();

            $rootScope.$on('$routeUpdate', loadElementsInPage);
        }

        function loadElementsInPage() {
            vm.orderString = $location.search().order;
            vm.sortString = $location.search().sort;
            vm.filterValue = $location.search().name;

            memberService
                .getMembersNames()
                .then(function(data){
                    vm.populatedFilterValues = data.map(function(name){
                        return name.firstName + " " + name.lastName;
                    });
                });

            memberService
                .getMembers(createSearchUrlForMembers())
                .then(function(data) {
                    vm.members = data.items;
                    vm.totalItems = data.itemsCount;
                    vm.currentPage = ($location.search().start / vm.itemsPerPage) + 1;
                    vm.noMembers = false;
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
        }

        function addFieldToSort(field) {
            if (vm.sortString != field)
                vm.orderString = "asc";
            else
                vm.orderString = vm.orderString == "asc" ? "desc" : "asc";

            vm.sortString = field;

            $location.search('sort', vm.sortString);
            $location.search('order', vm.orderString);

            vm.currentPage = 1;
            pageChanged();
        }

        function onSelectFilter() {
            for (var key in $location.search()) {
                if (key != "start" && key != "end")
                    $location.search(key, null);
            }
            $location.search("name", vm.filterValue);

            vm.currentPage = 1;
            pageChanged();
        }

        function createSearchUrlForMembers() {
            if (!$location.search().start && !$location.search().end) {
                var aux = "?start=0&end=" + vm.itemsPerPage;

                if (vm.filterValue)
                    aux += "&name=" + vm.filterValue;
                if($location.search().sort && $location.search().order)
                    aux += "&sort=" + vm.sortString + "&order=" + vm.orderString;

                return aux;
            } else {
                return $location.url().substr($location.path().length);
            }
        }
    }
}
)();