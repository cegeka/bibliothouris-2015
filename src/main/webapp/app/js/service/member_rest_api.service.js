(function() {
    angular
        .module("Bibliothouris")
        .factory("memberService", memberService);

    function memberService($http) {
        var service = {
            addMember: addMember,
            getMemberDetail: getMemberDetail,
            getMembers: getMembers,
            getMemberBorrowedHistory: getMemberBorrowedHistory,
            countBorrowedHistoryItems: countBorrowedHistoryItems,
            getMembersNames: getMembersNames
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

        function getMembers(searchUrlForMembers) {
            return $http.get("/api/member" + searchUrlForMembers)
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

        function getMembersNames() {
            return $http.get("/api/member/names")
                .then(function (response) {
                    return response.data;
                });
        }
    }
})();