(function() {
    angular
        .module("Bibliothouris")
        .controller("AddMemberCtrl", AddMemberCtrl);

    function AddMemberCtrl(restService,$location) {

        var vm = this;

        vm.member = {};
        vm.originalMember = {};

        vm.resetForm = function(memberForm) {
            vm.member = angular.copy(vm.originalMember);
            memberForm.$setPristine();
        }

        vm.showFeedback = function(data){
            return !data.required;
        }

        vm.submitForm = function (memberForm) {
            if(vm.member.birthDate){
                vm.member.birthDate = moment(new Date(vm.member.birthDate)).format("YYYY-MM-DD");
            }
            vm.member.memberSince = moment(new Date()).format("YYYY-MM-DD");
            if(memberForm.$valid){
                    restService
                        .addMember(vm.member)
                        .then(function(data){
                            console.log(data);
                            $location.path("/member/" + data.UUID);
                            createNotification("Member <strong>" + data.firstName + " " + data.lastName + "</strong> was added!", "success")
                        }, function(data){
                            createNotification("Something wrong happened when you tried to add a new member!", "danger")
                        });
            }
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
    }
})();