(function() {
    angular
        .module("Bibliothouris")
        .factory("restService", restService);

    function restService($http, $location) {
        var service = {
            addBook: addBook,
            addMember: addMember,
            getBooks: getBooks,
            countBorrowedHistoryItems: countBorrowedHistoryItems,
            getAuthors: getAuthors,
            getBookCategories: getBookCategories,
            getBookDetails: getBookDetails,
            getBookTitles: getBookTitles,
            getBookIsbnCodes: getBookIsbnCodes,
            getMemberDetail: getMemberDetail,
            getMemberBorrowedHistory: getMemberBorrowedHistory,
            getGlobalBorrowedBooks: getGlobalBorrowedBooks,
            countBorrowedBooks : countBorrowedBooks,
            getBookBorrowerDetails: getBookBorrowerDetails
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

        function getBooks(itemsPerPage) {
            var searchUrl = "";

            if (!$location.search().start && !$location.search().end) {
                if (angular.equals($location.search(), {}))
                    searchUrl +="?";
                else
                    searchUrl += "&";
                searchUrl += "start=0&end=" + itemsPerPage;
            }

            return $http.get("/api" + $location.url() + searchUrl)
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
                    return response.data;
                });
        }

        function getMemberBorrowedHistory(memberId, itemsPerPage) {
            var searchUrl = "?start=0&end=" + itemsPerPage;
            if ($location.search().start && $location.search().end)
                searchUrl = "?start=" + $location.search().start + "&" + "end=" + $location.search().end;
            return $http.get("/api/borrow/" + memberId + searchUrl)
                .then(function(response){
                    return response.data;
                });
        }

        function countBorrowedHistoryItems(memberId) {
            return $http.get("/api/borrow/" + memberId + "/size")
                .then(function(response){
                    return response.data;
                });
        }

        function countBorrowedBooks() {
            return $http.get("/api/borrow/size")
                .then(function(response){
                    return response.data;
                });
        }

        function getGlobalBorrowedBooks(searchUrl) {
            return $http.get("/api/borrow/" + searchUrl)
                .then(function(response){
                    return response.data;
                });
        }

        function getBookBorrowerDetails(bookId) {
            return $http.get("/api/books/" + bookId + "/borrowedBy")
                .then(function(response){
                    return response.data;
                });
        }
    }
})();