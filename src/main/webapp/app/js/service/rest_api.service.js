(function() {
    angular
        .module("Bibliothouris")
        .factory("restService", restService);

    function restService($http) {
        var service = {
            addBook: addBook,
            getBooks: getBooks,
            countBooks: countBooks,
            getAuthors: getAuthors
        };

        return service;

        function addBook(book) {
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
    }
})();