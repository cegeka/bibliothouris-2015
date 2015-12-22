(function () {
    angular
        .module("Bibliothouris")
        .controller("BorrowedBooksCtrl", BorrowedBooksCtrl);

    function BorrowedBooksCtrl(borrowService, $location) {
        var vm = this;

        vm.maxSize = 5;
        vm.itemsPerPage = 10;
        vm.viewFilters = ["All borrowed books", "Only overdue books"];

        vm.borrowedBooks = {};
        vm.noBorrows = false;
        vm.pageChanged = pageChanged;
        vm.addFieldToSort = addFieldToSort;
        vm.changeViewData = changeViewData;

        activate();

        function activate() {
            if (!$location.search().view)
                $location.search("view", "all");

            if ($location.search().view == "all") {
                vm.filter = vm.viewFilters[0];
                getAllBorrowedBooks();
            } else {
                vm.filter = vm.viewFilters[1];
                getOverdueBooks();
            }
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

        function changeViewData() {
            var viewMode = $location.search().view;

            for (var key in $location.search())
                $location.search(key, null);

            if (viewMode == "all")
                $location.search("view", "overdue");
            else if (viewMode == "overdue")
                $location.search("view", "all");

            activate();
        }

        function getAllBorrowedBooks() {
            borrowService
                .countBorrowedBooks()
                .then(function (data) {
                    vm.numberOfItems = data;
                    vm.currentPage = ($location.search().start / vm.itemsPerPage) + 1;
                });

            borrowService
                .getGlobalBorrowedBooks(createSearchUrlForBorrowedBooks())
                .then(function (data) {
                    vm.borrowedBooks = data;
                }, function () {
                    vm.noBorrows = true;
                });
        }

        function getOverdueBooks() {
            borrowService
                .getOverdueBooks(createSearchUrlForBorrowedBooks())
                .then(function (data) {
                    vm.borrowedBooks = data.books;
                    vm.numberOfItems = data.booksCount;
                    vm.currentPage = ($location.search().start / vm.itemsPerPage) + 1;
                }, function () {
                    vm.noBorrows = true;
                });
        }

        function createSearchUrlForBorrowedBooks() {
            if (!$location.search().start && !$location.search().end) {
                vm.orderString = "asc";
                vm.sortString = "title";
                return "?start=0&end=" + vm.itemsPerPage + "&sort=title&order=asc";
            } else {
                vm.orderString = $location.search().order;
                vm.sortString = $location.search().sort;
                return $location.url().substr($location.path().length);
            }
        }
    }
})();