/**
 * Created by wyb on 2018/1/27.
 */
angular.module('sys-controller', ['user-controller','role-controller','dept-controller','menu-controller','permission-controller'])

    .controller('sysCtrl', ['$scope','HttpService','ConfirmService', function ($scope,HttpService,ConfirmService) {

        $scope.init = function(){
            $scope.sexList = [{'id':1,'name':'男'},{'id':2,'name':'女'}]
            var url = "../sys/user/getUserById.do?id="+$scope.user.id;
            HttpService.get(url)
                .then(function (result){
                    $scope.myAccount = JSON.parse(result);
                });
        }

        document.onkeydown = function (event) {
            if (event.keyCode == 13) {
                var url = "../sys/user/doModifyUserBySelf.do";
                HttpService.post(url,$scope.myAccount)
                    .then(function (result){
                        ConfirmService.message("信息已修改");
                    });
            }
        }

        $scope.init();


    }])

    .controller('modifyPswCtrl', ['$scope','HttpService','ConfirmService', function ($scope,HttpService,ConfirmService) {


        $scope.modifyData = {
            oldPsw:'',
            newPsw:'',
            newPswAgain:''
        }

        $scope.confirmModify = function(){
            if($scope.modifyData){
                if($scope.modifyData.newPsw!=$scope.modifyData.newPswAgain){
                    ConfirmService.alert('两次密码输入不一致');
                    return;
                }
                var url="../sys/user/doModifyPswBySelf.do?oldPassword="+$scope.modifyData.oldPsw+"&password="+$scope.modifyData.newPsw;
                HttpService.get(url)
                    .then(function(result){
                        ConfirmService.message('已修改');
                        $('#modifyPswModal').modal('hide');
                    })
            }
        }

    }])

;
