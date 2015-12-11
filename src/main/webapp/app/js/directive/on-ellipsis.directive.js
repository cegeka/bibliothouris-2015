(function(){
    angular
        .module("Bibliothouris")
        .directive('onEllipsis', function($parse) {
            return {
                restrict: 'A',
                link: function (scope, element, attrs) {
                    var fn = $parse(attrs["onEllipsis"]);

                    scope.$watch(function() {
                                    return hasOverflow($(element[0]))
                                },
                                function(newValue) {
                                    fn(scope, {param: newValue});
                                });
                }
            };

            function hasOverflow(element) {
                var result = false;

                var $c = element
                    .clone()
                    .css({display: 'inline', width: 'auto', visibility: 'hidden'})
                    .appendTo('body');

                if( $c.width() > element.width() ) {
                    result = true;
                }

                $c.remove();

                return result;
            }
        })
})();