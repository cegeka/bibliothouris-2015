(function() {
    angular
        .module("Bibliothouris")
        .controller("MemberDetailCtrl", MemberDetailCtrl);

    function MemberDetailCtrl(restService, $routeParams, $location) {
        var vm = this;

        vm.member = {};
        vm.noMember = false;
        vm.noBorrowedBooks = false;
        vm.maxSize = 5;
        vm.itemsPerPage = 5;
        vm.borrowedAndNotReturnedBooks = [];

        vm.pageChanged = pageChanged;

        activate();

        function activate() {
            restService
                .getMemberDetail($routeParams.memberId)
                .then(function(data){
                    vm.member = data;
                }, function(){
                    vm.noMember = true;
                });

            restService
                .getMemberBorrowedHistory($routeParams.memberId, vm.itemsPerPage)
                .then(function(data){
                    vm.borrowedBooks = data;

                    vm.borrowedBooks.forEach(function(book){
                        if(!book.endLendDate)
                            vm.borrowedAndNotReturnedBooks.push(book);
                    })
                }, function() {
                    vm.noBorrowedBooks = true;
                });

            restService
                .countBorrowedHistoryItems($routeParams.memberId)
                .then(function(data){
                    vm.totalItems = data;
                    vm.currentPage = ($location.search().start / vm.itemsPerPage) + 1;
                }, function() {
                    vm.noBorrowedBooks = true;
                });
        }

        function pageChanged() {
            start = vm.itemsPerPage * (vm.currentPage - 1);
            end = start + vm.itemsPerPage;

            if (start != $location.search().start && end != $location.search().end) {
                $location.search('start', start);
                $location.search('end', end);
            }
        }
    }
})();