var booksApp = angular.module("Bibliothouris");
booksApp.controller("ListBooksCtrl", ListBooksCtrl);
function ListBooksCtrl($scope){
    $scope.books = [{
            id: "123",
            title: "Clean Code",
            authors: "Robert C. Martin"
        },
        {
            id: "123",
            title: "Clean Code",
            authors: "Robert C. Martin"
        },
        {
            id: "123",
            title: "Clean Code",
            authors: "Robert C. Martin"
        },
    ]
}

