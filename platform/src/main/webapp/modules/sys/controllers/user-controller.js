/**
 * Created by wyb on 2018/1/28.
 */

angular.module('user-controller', [])

    .controller('userCtrl', ['$scope','$http','HttpService','ConfirmService',function ($scope,$http,HttpService,ConfirmService) {
        $scope.init = function () {
            $scope.sexList = [{'id':1,'name':'男'},{'id':2,'name':'女'}]
            $scope.stateList = [{'id':1,'name':'启用'},{'id':0,'name':'禁用'}]
            $scope.nowTime = new Date().getTime();
            $scope.getDeptList();
        }
        $scope.getDeptList = function () {
            var url = "../sys/dept/getAllDept.do";
            HttpService.get(url).then(function (result) {
                    $scope.deptList = JSON.parse(result);
                });
        }

        $scope.searchData = {
            name: '',
            loginName: '',
            state: '',
            onePageNum: 10,
            pageNo: 1
        }

        $scope.getUser = function () {
            var url = "../sys/user/getUserByPage.do";
            HttpService.get(url,$scope.searchData)
                .then(function (result) {
                    var resultData = JSON.parse(result);
                    $scope.dataList = resultData.dataList;
                    $scope.dataCount = resultData.dataCount;
                });
        }

        $scope.addUser = function () {
            $scope.addData = {
                loginName: '',
                name: '',
                password: '123456',
                salt: '',
                sex: '1',
                birthday: '',
                phone: '',
                email: '',
                dept_id: '',
                state: '1'
             }
            $('#myModal').modal('show');
        }


        $scope.editUser = function (id) {
            var url = "../sys/user/getUserById.do?id="+id;
            HttpService.get(url)
                .then(function (result){
                    var userData = JSON.parse(result);
                    if(angular.isObject(userData)){
                        $scope.addData = userData
                        $('#myModal').modal('show');
                    }else{
                        ConfirmService.alert("用户信息不存在");
                        return;
                    }
                });
        }

        $scope.saveUser = function () {
            if (!angular.isObject($scope.addData)) {
                ConfirmService.alert("参数错误");
                return;
            }
            if($scope.addData.id){
                var url = "../sys/user/doModifyUser.do";
            }else{
                var url = "../sys/user/createUser.do";
            }
            HttpService.post(url, $scope.addData)
                .then(function (result) {
                    ConfirmService.message(result);
                    $scope.getUser();
                    $('#myModal').modal('hide');
                });
        }

        $scope.doModifyState = function(one){
            var data = {
                id : one.id,
                state : 1
            }
            if(one.state==0){
                data.state=1;
            }else if(one.state==1){
                data.state=0;
            }
            var url ="../sys/user/doModifyUser.do";
            HttpService.post(url,data).then(function(result){
                ConfirmService.message(result);
                one.state=data.state;
            })
        }

        $scope.doDeleteUser = function(id){
            $scope.delId = id;
            ConfirmService.confirm("删除提示","确定删除该用户吗？",$scope.confirmDeleteUser,null);
        }
        $scope.confirmDeleteUser = function () {
            var url = "../sys/user/doDeleteUser.do?id="+$scope.delId;
            HttpService.get(url).then(function (result) {
                ConfirmService.message("用户已删除");
                $scope.getUser();
            });
        }

        $scope.init();

    }]);
