(function() {
    angular
        .module("Bibliothouris")
        .controller("ImportBooksCtrl", ImportBooksCtrl);

    function ImportBooksCtrl(bookService, notificationService, $uibModal, $location, $route) {
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
            if ($location.search().title != null) {
                vm.filter = "Title";
                vm.filterValue = $location.search().title;
                vm.searchParametersInUrl = "title=" + vm.filterValue;
            } else if ($location.search().isbn != null) {
                vm.filter = "ISBN";
                vm.filterValue = $location.search().isbn;
                vm.searchParametersInUrl = "isbn=" + vm.filterValue;
            } else if (!$location.search().isbn)
                vm.searchParametersInUrl = "title=" + "Clean Code";

            bookService
                .getImportedBooks("?" + vm.searchParametersInUrl)
                .then(function(data) {
                    console.log(data);
                    vm.books = data;
                }, function(){
                    vm.noBooks = true;
                });

        }

        function onSelectFilter() {
            console.log(vm.filterValue);

            for (var key in $location.search())
                $location.search(key, null);

            $location.search(vm.filter.toLowerCase(), vm.filterValue);
        }

        function importBook(book) {
            if (!book.isbn || !book.authors) {
                $uibModal.open({
                    templateUrl: 'templates/add_import_book_details.html',
                    controller: function (book, $uibModalInstance) {
                        var vm = this;

                        vm.book = book;

                        console.log(vm.book);

                        vm.authors = [""];
                        //vm.type = type;
                        //vm.title = title;
                        //vm.message = message;
                        //
                        //vm.onYes = onYes;
                        //vm.onNo = onNo;
                        //
                        //function onYes() {
                        //    $uibModalInstance.close();
                        //}
                        //
                        //function onNo() {
                        //    $uibModalInstance.dismiss();
                        //}
                    },
                    controllerAs: 'vm',
                    resolve: {
                        book: function () {
                            return book;
                        }
                    }
                });
            } else {
                saveBook(book);
            }
        }

        function saveBook(book) {
            bookService
                .addBook(book)
                .then(function(data){
                    $route.reload();
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