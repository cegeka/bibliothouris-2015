(function () {
    angular
        .module("Bibliothouris")
        .controller("BorrowedBooksCtrl", BorrowedBooksCtrl);

    function BorrowedBooksCtrl(restService, $location) {
        var vm = this;

        vm.maxSize = 1000;
        vm.itemsPerPage = 1000;

        vm.borrowedBooks = {};
        vm.noBorrows = false;
        vm.pageChanged = pageChanged;
        vm.addTitleToSort = addTitleToSort;
        vm.enableTooltip = enableTooltip;
        vm.sort = ['title','isbn','date'];

        activate();

        function activate() {
            if (!($location.search().start) && !($location.search().end)) {
                $location.search('start', 0);
                $location.search('end', vm.itemsPerPage);
                $location.search('sort', vm.sort.join(","));
                $location.search('order', ['asc','asc','desc'].join(","));
            }

            var searchUrl = "?start=" + $location.search().start +
                "&" + "end=" + $location.search().end +
                "&" + "sort=" + $location.search().sort +
                "&" + "order=" + $location.search().order
            restService
                .countBorrowedBooks()
                .then(function (data) {
                    vm.numberOfItems = data;
                });

            restService
                .getGlobalBorrowedBooks(searchUrl)
                .then(function (data) {
                    console.log(data);
                    vm.borrowedBooks = data;
                    vm.currentPage = ($location.search().start / vm.itemsPerPage) + 1;
                }, function () {
                    vm.noBorrows = true;
                });
        }

        function pageChanged() {
            start = vm.itemsPerPage * (vm.currentPage - 1);
            end = start + vm.itemsPerPage;

            $location.search('start', start);
            $location.search('end', end);
        }

        function addTitleToSort(field) {
            var orderString = $location.search().order.split(",");
            var sortString = $location.search().sort.split(",");
            if(orderString[vm.sort.indexOf(field)]=="asc"){
                orderString[vm.sort.indexOf(field)]="desc";
            } else {
                orderString[vm.sort.indexOf(field)]="asc";
            }
            $location.search("order", orderString.join(","));
        }

        function moveInArray(array, fromIndex) {
            var element = array[fromIndex];
            array.splice(fromIndex, 1);
            array.splice(0, 0, element);
        }

        function enableTooltip(member) {
            vm.tooltip = member;
        }

    }
})();