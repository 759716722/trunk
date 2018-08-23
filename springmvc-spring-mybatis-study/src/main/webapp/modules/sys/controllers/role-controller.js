/**
 * Created by wyb on 2018/1/27.
 */
angular.module('role-controller', [])

    .controller('roleCtrl', ['$scope','HttpService','ConfirmService',function ($scope, HttpService,ConfirmService) {

        $scope.searchData = {
            name : '',
            onePageNum:10,
            pageNo:1
        }

        $scope.getRole = function(){
            var url ="../sys/role/getRoleByPage.do";
            HttpService.get(url,$scope.searchData).then(function(result){
                var resultData = JSON.parse(result);
                $scope.dataList = resultData.dataList;
                $scope.dataCount = resultData.dataCount;
            });
        }

        $scope.addRole = function(){
            $scope.addData = {
                name : '',
                description : '',
                remark : ''
            }
            $('#myModal').modal('show');
        }

        $scope.saveRole = function(){
            if(!angular.isObject($scope.addData)){
                ConfirmService.alert("参数错误,请刷新后重新操作。");
                return;
            }
            var url = "../sys/role/saveRole.do";
            HttpService.post(url,$scope.addData).then(function(result){
                ConfirmService.message(result);
                $scope.getRole();
                $('#myModal').modal('hide');
            });
        }

        $scope.editRole = function(id){
            var url = "../sys/role/getRoleById.do?id="+id;
            HttpService.get(url).then(function(result){
                var roleData = JSON.parse(result);
                if(angular.isObject(roleData)){
                    $scope.addData = roleData
                    $('#myModal').modal('show');
                }else{
                    ConfirmService.alert("角色信息不存在");
                }
            })
        }

        $scope.doDeleteRole = function(id){
            $scope.delId = id;
            ConfirmService.confirm("删除提示","确定删除该角色吗？",$scope.confirmDelete,null);
        }

        $scope.confirmDelete = function () {
            var url = "../sys/role/doDeleteRole.do?id="+$scope.delId;
            HttpService.get(url).then(function(result){
                ConfirmService.message("角色已删除");
                $scope.getRole();
            });
        }

    }]);
