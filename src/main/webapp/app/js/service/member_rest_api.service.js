(function() {
    angular
        .module("Bibliothouris")
        .factory("memberService", memberService);

    function memberService($http, $location) {
        var service = {
            addMember: addMember,
            getMemberDetail: getMemberDetail,
            getMembers: getMembers,
            getMemberBorrowedHistory: getMemberBorrowedHistory,
            countBorrowedHistoryItems: countBorrowedHistoryItems,
        };

        return service;

        function addMember(member) {
            return $http.post("/api/member", member)
                .then(function (response) {
                    return response.data;
                });
        }

        function getMemberDetail(memberId) {
            return $http.get("/api/member/" + memberId)
                .then(function (response) {
                    return response.data;
                });
        }

        function getMembers(itemsPerPage) {
            var searchUrl = "";
            console.log('lalal');
            if (!$location.search().start && !$location.search().end) {
                if (angular.equals($location.search(), {}))
                    searchUrl += "?";
                else
                    searchUrl += "&";
                searchUrl += "start=0&end=" + itemsPerPage;
            }
            return $http.get("/api" + $location.url() + searchUrl)
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

        function countBorrowedHistoryItems(memberId) {
            return $http.get("/api/borrow/" + memberId + "/size")
                .then(function (response) {
                    return response.data;
                });
        }
    }
})();