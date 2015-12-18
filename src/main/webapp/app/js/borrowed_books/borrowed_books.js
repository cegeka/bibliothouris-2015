(function () {
    angular
        .module("Bibliothouris")
        .controller("BorrowedBooksCtrl", BorrowedBooksCtrl);

    function BorrowedBooksCtrl(restService, $location) {
        var vm = this;

        vm.maxSize = 5;
        vm.itemsPerPage = 10;
        vm.viewFilters = ["All borrowed books", "Only overdue books"];
        vm.filter = vm.viewFilters[0];

        vm.borrowedBooks = {};
        vm.noBorrows = false;
        vm.pageChanged = pageChanged;
        vm.addFieldToSort = addFieldToSort;

        activate();

        function activate() {
            if (!($location.search().start) && !($location.search().end)) {
                var searchUrl = "?start=0&end=" + vm.itemsPerPage + "&sort=title&order=asc";
                vm.orderString = "asc";
                vm.sortString = "title";
            } else {
                var searchUrl = "?start=" + $location.search().start +
                    "&" + "end=" + $location.search().end +
                    "&" + "sort=" + $location.search().sort +
                    "&" + "order=" + $location.search().order;
                vm.orderString = $location.search().order;
                vm.sortString = $location.search().sort;
            }

            restService
                .countBorrowedBooks()
                .then(function (data) {
                    vm.numberOfItems = data;
                    vm.currentPage = ($location.search().start / vm.itemsPerPage) + 1;
                });

            restService
                .getGlobalBorrowedBooks(searchUrl)
                .then(function (data) {
                    vm.borrowedBooks = data;
                }, function () {
                    vm.noBorrows = true;
                });
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

            if (!($location.search().start) && !($location.search().end)) {
                $location.search('start', 0);
                $location.search('end', vm.itemsPerPage);
            }

            vm.currentPage = 1;
            pageChanged();
        }
    }
})();