var booksApp = angular.module("Bibliothouris");
booksApp.controller("ListBooksCtrl", ListBooksCtrl);
function ListBooksCtrl($scope, restService, $location){

    $scope.bigTotalItems = 100;
    $scope.start = 0;
    $scope.end = 5;
    $scope.noBooks = true;

    restService.getBooks($scope.start, $scope.end).then(function(data){
        $scope.books = data;
        console.log($scope.books);
        $scope.noBooks = false;
    });

    $scope.showBook = function(bookId) {
        $location.path($location.url() + "/" + bookId);
    };

    restService.countBooks().then(function(data){
        $scope.totalItems = data;
        console.log($scope.totalItems);
        $scope.noBooks = false;
    });

    $scope.setPage = function (pageNo) {
        $scope.currentPage = pageNo;
    };

    $scope.pageChanged = function() {
        start2 = ($scope.bigCurrentPage - 1) * $scope.maxSize;
        console.log(start2);
        end2 = start2 + $scope.maxSize;
        console.log(end2);
        restService.getBooks(start2, end2).then(function(data){
            $scope.books = data;
            console.log($scope.books);
            $scope.noBooks = false;
        });
    };

    $scope.itemsPerPage = 5;
    $scope.maxSize = 5;
    $scope.bigCurrentPage = 1;
}

