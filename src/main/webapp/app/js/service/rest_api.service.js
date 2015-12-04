(function() {
    angular
        .module("Bibliothouris")
        .factory("restService", restService);

    function restService($http) {
        var service = {
            addBook: addBook,
            getBooks: getBooks,
            countBooks: countBooks,
            getAuthors: getAuthors,
            getBookDetails: getBookDetails
        };

        return service;

        function addBook(book) {
            console.log(book);
            return $http.post("/api/books", book)
                .then(function(response){
                    return response.data;
                });
        }

        function getBooks(start, end) {
            return $http.get("/api/books?start=" + start + "&end=" + end)
                .then(function(response){
                    return response.data;
                });
        }

        function countBooks() {
            return $http.get("/api/books/size")
                .then(function(response){
                    return response.data;
                });
        }
        
        function getAuthors() {
            return $http.get("/api/authors")
                .then(function(response){
                    return response.data;
                });
        }

        function getBookDetails(bookId) {
            return $http.get("/api/books/" + bookId)
                .then(function(response){
                    return response.data;
                });
        }
    }
})();