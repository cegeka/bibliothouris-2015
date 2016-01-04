(function() {
    angular
        .module("Bibliothouris")
        .factory("borrowService", borrowService);

    function borrowService($http) {
        var service = {
            borrowBook: borrowBook,
            returnBook: returnBook,
            getMemberBorrowedHistory: getMemberBorrowedHistory,
            getBookBorrowerDetails: getBookBorrowerDetails,
            getAvailableBooks: getAvailableBooks,
            getGlobalBorrowedBooks: getGlobalBorrowedBooks,
            getOverdueBooks: getOverdueBooks
        };

        return service;

        function borrowBook(book) {
            return $http.post("/api/borrow", book)
                .then(function (response) {
                    return response.data;
                });
        }

        function returnBook(borrowHistoryItemIdTO) {
            return $http.put("/api/borrow", borrowHistoryItemIdTO)
                .then(function (response) {
                    return response.data;
                });
        }

        function getAvailableBooks(searchUrl) {
            return $http.get("/api/books/available" + searchUrl)
                .then(function (response) {
                    return response.data;
                });
        }

        function getMemberBorrowedHistory(memberId, searchUrl) {
            return $http.get("/api/borrow/" + memberId + searchUrl)
                .then(function (response) {
                    return response.data;
                });
        }

        function getGlobalBorrowedBooks(searchUrl) {
            return $http.get("/api/borrow/" + searchUrl)
                .then(function (response) {
                    return response.data;
                });
        }

        function getBookBorrowerDetails(bookId) {
            return $http.get("/api/books/" + bookId + "/borrowedBy")
                .then(function (response) {
                    return response.data;
                });
        }

        function getOverdueBooks(searchUrl) {
            return $http.get("/api/borrow/overdue" + searchUrl)
                .then(function (response) {
                    return response.data;
                });
        }
    }
})();