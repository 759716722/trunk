/**
 * Created by wyb on 2018/6/27.
 */
angular.module('hr-controller', [])

    .controller('employeeCtrl', ['$scope','HttpService','ConfirmService', function ($scope,HttpService,ConfirmService) {

        $scope.init = function(){

            $scope.sexList = [{id:1,name:'男'},{id:2,name:'女'}];
            $scope.stateList = [{id:1,name:'在职'},{id:0,name:'离职'}];
            $scope.educationList = [{id:1,name:'初中及以下'},{id:2,name:'高中'},{id:3,name:'中专'},{id:4,name:'大专'},{id:5,name:'本科'},{id:6,name:'硕士'},{id:7,name:'博士'}];
            $scope.monthList = [1,2,3,4,5,6,7,8,9,10,11,12];

        };

        $scope.searchData = {
            name: null,
            state: null,
            contractMonth:null,
            onePageNum: 10,
            pageNo: 1
        };

        $scope.searchEmployee = function(){

            var url = "../hr/employee/getEmployeeByPage.do";
            HttpService.get(url,$scope.searchData).then(function(result){
                var resultData = JSON.parse(result);
                $scope.dataList = resultData.dataList;
                for(var i=0;i<$scope.dataList.length;i++){
                    var phone = $scope.dataList[i].phone;
                    if(phone.length>11){
                        // bootstrap table 数字不换行
                        $scope.dataList[i].phone= phone.substring(0,11)+" "+phone.substring(11);
                    }

                }
                $scope.dataCount = resultData.dataCount;

            });
        };

        $scope.addEmployee = function(){
            $scope.addData = {
                state:1
            };
            $('#myModal').modal('show');
        };

        $scope.editEmployee = function(id){
            var url = "../hr/employee/getEmployeeById.do?id="+id;
            HttpService.get(url).then(function(result){
                var employeeData = JSON.parse(result);
                if(angular.isObject(employeeData)){
                    $scope.addData = employeeData;
                    $('#myModal').modal('show');
                }else{
                    ConfirmService.alert("该员工信息不存在");
                    return;
                }

            });
        };

        $scope.saveEmployee = function(){
            if(!angular.isObject($scope.addData)){
                ConfirmService.alert("保存信息为空");
                return;
            }
            if(!$scope.addData.name || !$scope.addData.idCard ||!$scope.addData.state ){
                ConfirmService.alert("关键参数不能为空");
                return;
            }
            if($scope.addData.id){
                var url = "../hr/employee/doModifyEmployee.do";
            }else{
                var url = "../hr/employee/createEmployee.do";
            }
            HttpService.post(url,$scope.addData).then(function(result){
                $('#myModal').modal('hide');
                ConfirmService.message(result);
                $scope.searchEmployee();
            });
        };

        $scope.doModifyState = function(one){
            if(one.state==1){
                $scope.addData = one;
                $scope.addData.leaveDate = new Date();;
                $('#myLeaveDateModal').modal('show');
            }

        }
        $scope.confirmModifyState = function(){
            if(!$scope.addData.leaveDate){
                ConfirmService.alert("请选择离职日期");
                return;
            }
            var url = "../hr/employee/doModifyEmployee.do";
            HttpService.post(url,$scope.addData).then(function(result){
                $('#myLeaveDateModal').modal('hide');
                ConfirmService.message(result);
                $scope.searchEmployee();
            });

        }

        $scope.doDeleteEmployee = function(id){
            $scope.delId = id;
            ConfirmService.confirm("删除提示","确定删除该记录吗？删除后数据不可恢复！",$scope.confirmDeleteUser,null);
        }
        $scope.confirmDeleteUser = function () {
            var url = "../hr/employee/doDeleteEmployee.do?id="+$scope.delId;
            HttpService.get(url).then(function (result) {
                ConfirmService.message(result);
                $scope.searchEmployee();
            });
        }

        $scope.getContract = function(id){
            var url = "../hr/employee/getContract.do?employeeId="+id;
            HttpService.get(url).then(function (result) {
                $scope.contractList = JSON.parse(result);
                $('#myContractListModal').modal('show');

            });
        }

        $scope.editContract = function(employeeId){
            $('#myContractModal').modal('show');
            $scope.contractData = {
                employeeId : employeeId,
                startDate : new Date(),
                endDate : null
            }
        }

        $scope.saveContract = function(){
            if(!angular.isObject($scope.contractData)){
                ConfirmService.message("系统异常，请重新操作。");
                return;
            }
            if(!$scope.contractData.startDate){
                ConfirmService.message("开始日期不能为空");
                return;
            }
            if(!$scope.contractData.endDate){
                ConfirmService.message("结束日期不能为空");
                return;
            }
            var url = "../hr/employee/saveContract.do";
            HttpService.post(url,$scope.contractData).then(function (result) {
                $('#myContractModal').modal('hide');
                ConfirmService.message(result);
            });

        }

        $scope.init();

    }])

    .controller('salaryCtrl', ['$scope','HttpService','ConfirmService','UploadFileService', function ($scope,HttpService,ConfirmService,UploadFileService) {

        $scope.init = function(){

            $scope.sexList = [{id:1,name:'男'},{id:2,name:'女'}];
            $scope.stateList = [{id:1,name:'在职'},{id:0,name:'离职'}];
            $scope.educationList = [{id:1,name:'初中及以下'},{id:2,name:'高中'},{id:3,name:'中专'},{id:4,name:'大专'},{id:5,name:'本科'},{id:6,name:'硕士'},{id:7,name:'博士'}];
            $scope.monthList = [1,2,3,4,5,6,7,8,9,10,11,12];

        };


        $scope.selectFile = function(fileColumn){
            // $scope.uploadFileColumn = fileColumn;
            $scope.uploadPath = "base/";
            UploadFileService.uploadFile($scope.uploadPath);
        }

        $scope.startUpload = function(){

            UploadFileService.startUpload().then(function(data){
                var resultData = JSON.parse(data);
                console.log(resultData);
                if(resultData.result.flag == 'Y'){
                    var successFileName = resultData.result.data;
                    // if($scope.uploadFileColumn){
                    //     $scope.addData[$scope.uploadFileColumn] = successFileName;
                    // }else{
                    //     alert('参数错误');
                    //     return;
                    // }
                    alert('上传成功');
                    $('#selectFileModal').modal('hide');
                }
            });

        }

        $scope.getFile = function(one,fileName){
            UploadFileService.getFile("base/","帐号信息.txt");
        }

        $scope.doDeleteFile = function(fileNameList,delfileName,tempColumn){
            var newFileName = UploadFileService.doDeleteFile(fileNameList,delfileName);
            $scope.addData[tempColumn] = newFileName;
        }



        $scope.init();

    }])

;
