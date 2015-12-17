(function () {
    angular
        .module("Bibliothouris")
        .controller("BorrowedBooksCtrl", BorrowedBooksCtrl);

    function BorrowedBooksCtrl(restService, $location) {
        var vm = this;

        vm.maxSize = 5;
        vm.itemsPerPage = 10;

        vm.borrowedBooks = {};
        vm.noBorrows = false;
        vm.pageChanged = pageChanged;
        vm.addTitleToSort = addTitleToSort;
        vm.enableTooltip = enableTooltip;

        activate();

        function activate() {
            if (!($location.search().start) && !($location.search().end)) {
                $location.search('start', 0);
                $location.search('end', vm.itemsPerPage);
                $location.search('sort', 'title');
                $location.search('order', "asc");
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
            var orderString = $location.search().order;
            if(orderString=="asc"){
                orderString="desc";
            } else {
                orderString="asc";
            }
            $location.search("order", orderString);
            $location.search("sort", field);
        }

        function enableTooltip(member) {
            vm.tooltip = member;
        }

    }
})();