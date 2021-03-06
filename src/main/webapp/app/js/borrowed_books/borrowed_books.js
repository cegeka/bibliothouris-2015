(function () {
    angular
        .module("Bibliothouris")
        .controller("BorrowedBooksCtrl", BorrowedBooksCtrl);

    function BorrowedBooksCtrl(borrowService, $location, $rootScope) {
        var vm = this;

        vm.maxSize = 5;
        vm.itemsPerPage = 10;
        vm.viewFilters = ["All borrowed books", "Only overdue books"];

        vm.borrowedBooks = {};
        vm.noBorrowedBooks = false;
        vm.pageChanged = pageChanged;
        vm.addFieldToSort = addFieldToSort;
        vm.changeViewData = changeViewData;
        vm.enableAuthorTooltip = enableAuthorTooltip;
        vm.getAuthorTooltipContent = getAuthorTooltipContent;

        activate();

        function activate() {
            loadElementsInPage();

            $rootScope.$on('$routeUpdate', loadElementsInPage);
        }

        function loadElementsInPage() {
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
                .getGlobalBorrowedBooks(createSearchUrlForBorrowedBooks())
                .then(function (data) {
                    vm.borrowedBooks = data.items;
                    vm.numberOfItems = data.itemsCount;
                    vm.currentPage = ($location.search().start / vm.itemsPerPage) + 1;
                    vm.noBorrowedBooks = false;
                }, function () {
                    vm.noBorrowedBooks = true;
                });
        }

        function getOverdueBooks() {
            borrowService
                .getOverdueBooks(createSearchUrlForBorrowedBooks())
                .then(function (data) {
                    vm.borrowedBooks = data.items;
                    vm.numberOfItems = data.itemsCount;
                    vm.currentPage = ($location.search().start / vm.itemsPerPage) + 1;
                }, function () {
                    vm.noBorrowedBooks = true;
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

        function enableAuthorTooltip(param, book) {
            book.authorTooltip = param;
        }

        function getAuthorTooltipContent(book) {
            return book.authors.map(function(author){
                return author.firstName + " " + author.lastName;
            }).join(", ");
        }
    }
})();