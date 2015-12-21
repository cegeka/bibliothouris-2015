(function() {
    angular
        .module("Bibliothouris")
        .controller("AddBookCtrl", AddBookCtrl);

    function AddBookCtrl($scope, restService, $location) {
        var vm = this;
        vm.oneMB = Math.pow(2, 20);
        vm.originalBook = {};
        vm.book = {};
        vm.authors = {};
        vm.isbn = {
            prefix: "",
            registrationGroupElement: "",
            registrantElement: "",
            publicationElement: "",
            checkDigit: ""
        };

        vm.isCoverSizeValid = isCoverSizeValid;
        vm.isCoverTypeValid = isCoverTypeValid;
        vm.isCoverValid = isCoverValid;
        vm.addAuthor = addAuthor;
        vm.addCategory = addCategory;
        vm.deleteAuthor = deleteAuthor;
        vm.submitForm = submitForm;
        vm.resetForm = resetForm;

        vm.onSelect = function (item, index) {
            vm.book.authors[index].firstName = item.firstName;
            vm.book.authors[index].lastName = item.lastName;
        };
        vm.validateFilledIn = '';       // TODO: it seems to be unused! To be checked!
        activate();

        function activate() {
            vm.originalBook = {
                authors: [""],
                categories: [],
                isbn: []
            };
            vm.book = angular.copy(vm.originalBook);

            restService
                .getAuthors()
                .then(function(data){
                    vm.authors = data;
                });
            restService
                .getBookCategories()
                .then(function(data){
                    vm.categories = data;
                });

            $('#input-id').on('filecleared', function(event) {
                vm.cover = null;
                if ($scope.$root.$$phase != '$apply' && $scope.$root.$$phase != '$digest') {
                    $scope.$apply();
                }
            });
        }

        function addAuthor() {
            vm.book.authors.push("");
        }

        function addCategory() {
            vm.book.categories.push("");
        }

        function deleteAuthor($index) {
            vm.book.authors.splice($index, 1);

            if (vm.book.authors.length == 0)
                vm.book.authors.push("");
        }

        function submitForm(bookForm) {
            if(vm.book.date){
                vm.book.date = moment(new Date(vm.book.date)).format("YYYY-MM-DD");
            }
            if(bookForm.$valid && isCoverValid()){
                var reader = new FileReader();
                reader.onload = function(){
                    addBook(reader.result);
                };

                if (vm.cover) {
                    reader.readAsDataURL(vm.cover);
                } else {
                    addBook();
                }
            }
        }

        function addBook(cover) {
            vm.book.cover = cover;

            restService
                .addBook(vm.book)
                .then(function(data){
                    $location.path("/books/" + data.id);
                    createNotification("Book <strong>" + data.title + "</strong> was added in the library!", "success")
                }, function(){
                    createNotification("Something wrong happened when you tried to add a new book!", "danger")
                });
        }

        function resetForm(bookForm) {
            bookForm.$setPristine();
            vm.isbn = null;
            vm.book = angular.copy(vm.originalBook);
            $('#input-id').fileinput('clear');
        }

        function isCoverSizeValid() {
            return !(vm.cover && vm.cover.size > vm.oneMB);
        }

        function isCoverTypeValid() {
            return !(vm.cover && vm.cover.type.indexOf('image') != 0);
        }

        function isCoverValid() {
            return isCoverSizeValid() && isCoverTypeValid();
        }

        function createNotification(message, type) {
            $.notify(message,{
                placement: {
                    from: "bottom",
                    align: "right"
                },
                type: type,
                delay: 1500,
                animate: {
                    enter: 'animated fadeInUp',
                    exit: 'animated fadeOutDown'
                },
                template: '<div data-notify="container" class="col-xs-11 col-sm-2 alert alert-{0}" role="alert">' +
                '<button type="button" aria-hidden="true" class="close" data-notify="dismiss">×</button>' +
                '<span data-notify="icon"></span> ' +
                '<span data-notify="title">{1}</span> ' +
                '<span data-notify="message">{2}</span>' +
                '<div class="progress" data-notify="progressbar">' +
                '<div class="progress-bar progress-bar-{0}" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;"></div>' +
                '</div>' +
                '<a href="{3}" target="{4}" data-notify="url"></a>' +
                '</div>'
            });
        }

        vm.showFeedback = function(data){
            return !data.required;
        };

        vm.showIsbnError = function(data){
            return !data.pattern;
        };

        vm.addItemToModel = function(item){
            var index = -1;
            for(var key in vm.book.categories) {
                if(vm.book.categories[key].category == item.category) {
                    index = key;
                    break;
                }
            }

            if(index == -1)
                vm.book.categories.push({category: item.category});
            else
                vm.book.categories.splice(index, 1);
        };

        vm.categoriesSize = function(){
            if(vm.book.categories.length == 0){
                return {
                    empty: true
                }
            }
        };

        vm.isCategorySelected = function(item){
            for(var key in vm.book.categories) {
                if(vm.book.categories[key].category == item.category) {
                    return true;
                }
            } return false;
        };
        vm.onchange = function(){
            vm.book.isbn = vm.isbn.prefix +"-"+ vm.isbn.registrationGroupElement+"-"+vm.isbn.registrantElement +"-"+vm.isbn.publicationElement +"-"+vm.isbn.checkDigit ;
        }

        vm.isbnPattern = "(?=[0-9]{13}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)97[89][- ]?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9]";
    }
})();