var booksApp = angular.module("Bibliothouris");
booksApp.controller("ListBooksCtrl", ListBooksCtrl);
function ListBooksCtrl($scope, restService){
   /* $scope.books = [{
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
    ]*/

    restService.getBooks().then(function(data){
        $scope.books = data;
        console.log($scope.books);
    })



}

