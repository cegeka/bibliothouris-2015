(function() {
    angular
        .module("Bibliothouris")
        .filter('abs', function () {
            return function (val) {
                return Math.abs(val);
            }
        });
})();