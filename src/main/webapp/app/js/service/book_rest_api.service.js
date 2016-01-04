(function() {
    angular
        .module("Bibliothouris")
        .factory("bookService", bookService);

    function bookService($http) {
        var service = {
            addBook: addBook,
            getBooks: getBooks,
            getAuthors: getAuthors,
            getBookCategories: getBookCategories,
            getBookDetails: getBookDetails,
            getBookTitles: getBookTitles,
            getBookIsbnCodes: getBookIsbnCodes
        };

        return service;

        function addBook(book) {
            return $http.post("/api/books", book)
                .then(function (response) {
                    return response.data;
                });
        }

        function getBooks(searchUrl) {
            return $http.get("/api/books" + searchUrl)
                .then(function (response) {
                    return response.data;
                });
        }

        function getAuthors() {
            return $http.get("/api/authors")
                .then(function (response) {
                    return response.data;
                });
        }

        function getBookCategories() {
            return $http.get("/api/categories")
                .then(function (response) {
                    return response.data;
                });
        }

        function getBookDetails(bookId) {
            return $http.get("/api/books/" + bookId)
                .then(function (response) {
                    return response.data;
                });
        }

        function getBookTitles() {
            return $http.get("/api/books/titles")
                .then(function (response) {
                    return response.data;
                });
        }

        function getBookIsbnCodes() {
            return $http.get("/api/books/isbnCodes")
                .then(function (response) {
                    return response.data;
                });
        }
    }
})();