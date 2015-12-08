(function(){
    angular
        .module("Bibliothouris")
        .directive('fileInput', function() {
            return {
                restrict: 'A',
                link: function (scope, element, attrs) {
                    $(element).fileinput({
                        'showUpload': false,
                        'previewFileType': 'any'
                    });
                }
            }
        })
})();