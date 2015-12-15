(function() {
    angular
        .module("Bibliothouris")
        .factory("restService", restService);

    function restService($http, $location) {
        var service = {
            addBook: addBook,
            addMember: addMember,
            getBooks: getBooks,
            countBooks: countBooks,
            getAuthors: getAuthors,
            getBookCategories: getBookCategories,
            getBookDetails: getBookDetails,
            getBookTitles: getBookTitles,
            getBookIsbnCodes: getBookIsbnCodes,
            getMemberDetail: getMemberDetail
        };

        return service;

        function addBook(book) {
            return $http.post("/api/books", book)
                .then(function(response){
                    return response.data;
                });
        }

        function addMember(member) {
            return $http.post("/api/member", member)
                .then(function(response){
                    return response.data;
                });
        }

        function getBooks() {
            return $http.get("/api" + $location.url())
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

        function getBookCategories() {
            return $http.get("/api/categories")
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

        function getBookTitles() {
            return $http.get("/api/books/titles")
                .then(function(response){
                    return response.data;
                });
        }

        function getBookIsbnCodes() {
            return $http.get("/api/books/isbnCodes")
                .then(function(response){
                    return response.data;
                });
        }

        function getMemberDetail(memberId) {
            return $http.get("/api/member/" + memberId)
                .then(function(response){
                    console.log(response.data);
                    return response.data;
                });
        }
    }
})();