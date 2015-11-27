(function() {
    angular
        .module("Bibliothouris")
        .factory("restService", restService);

    function restService($http) {
        var service = {
            addBook: addBook,
            getBooks: getBooks,
            getAuthors: getAuthors
        };

        return service;

        function addBook(book) {
            return $http.post("/api/books", book)
                .then(function(response){
                    return response.data;
                });
        }

        function getBooks() {
            return $http.get("/api/books")
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