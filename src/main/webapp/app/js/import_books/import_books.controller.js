(function() {
    angular
        .module("Bibliothouris")
        .controller("ImportBooksCtrl", ImportBooksCtrl);

    function ImportBooksCtrl(bookService, notificationService, $uibModal, $location, $rootScope) {
        var vm = this;

        vm.noBooks = false;
        vm.searchFilters = ["Title", "ISBN"];
        vm.filter = vm.searchFilters[0];
        vm.filterValue = "";
        vm.books = [];

        vm.getAuthors = getAuthors;
        vm.getCategories = getCategories;
        vm.onSelectFilter = onSelectFilter;
        vm.importBook = importBook;

        activate();

        function activate() {
            loadElementsInPage();

            $rootScope.$on('$routeUpdate', loadElementsInPage);
        }

        function loadElementsInPage() {
            console.log("abc");

            if ($location.search().title != null) {
                vm.filter = "Title";
                vm.filterValue = $location.search().title;
            } else if ($location.search().isbn != null) {
                vm.filter = "ISBN";
                vm.filterValue = $location.search().isbn;
            }

            getImportedBooks();
        }

        function buildSearchUrlForImportedBooks() {
            if ($location.search().title != null)
                return "title=" + vm.filterValue;
            else if ($location.search().isbn != null)
                return "isbn=" + vm.filterValue;
            else
                return "title=" + "Clean Code";
        }

        function getImportedBooks() {
            bookService
                .getImportedBooks("?" + buildSearchUrlForImportedBooks())
                .then(function(data) {
                    vm.books = data;
                    vm.noBooks = false;
                }, function(){
                    vm.noBooks = true;
                });
        }

        function onSelectFilter() {
            for (var key in $location.search())
                $location.search(key, null);

            $location.search(vm.filter.toLowerCase(), vm.filterValue);
        }

        function importBook(book) {
            if (!book.isbn || book.authors.length == 0) {
                $uibModal
                    .open({
                        templateUrl: 'templates/add_import_book_details.html',
                        controller: "AddImportDetailsCtrl",
                        controllerAs: 'vm',
                        resolve: {
                            book: function () {
                                return angular.copy(book);
                            }
                        }
                    })
                    .result.then(function (bookWithDetails) {
                        saveBook(bookWithDetails);
                    });
            } else {
                saveBook(angular.copy(book));
            }
        }

        function saveBook(book) {
            if (book.id)
                book.id = null;

            bookService
                .addBook(book)
                .then(function(data){
                    getImportedBooks();
                    notificationService.createNotification("Book <strong>" + data.title + "</strong> was added in the library!", "success")
                }, function(data){
                    if(data.status == 400)
                        notificationService.createNotification(data.data.value, "danger")
                    else
                        notificationService.createNotification("Something wrong happened when you tried to add a new book!", "danger")
                });
        }

        function getAuthors(book) {
            return book.authors.map(function(author){
                if (!author.firstName)
                    return author.lastName;

                return author.firstName + " " + author.lastName;
            }).join(", ");
        }

        function getCategories(book) {
            return book.categories.map(function(category){
                return category.category;
            }).join(", ");
        }
    }
}
)();