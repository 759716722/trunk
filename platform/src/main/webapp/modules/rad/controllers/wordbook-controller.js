/**
 * Created by wyb on 2018/5/16.
 */

angular.module('wordbook-controller', [])

    .controller('wordbookCtrl', ['$scope', 'HttpService','ConfirmService', function ($scope, HttpService,ConfirmService) {
        $scope.init = function(){
            $scope.typeList = [{id:1,name:'行业'},{id:2,name:'配置'},{id:3,name:'机器类型'}];
            $scope.categoryList = [{id:1,name:'工具类'},{id:2,name:'附件类'}];

        }
        $scope.searchData = {
            type: null,
            name: '',
            onePageNum: 10,
            pageNo: 1
        }

        $scope.getWordbook = function(){
            if (!angular.isObject($scope.searchData)) {
                ConfirmService.alert("参数错误");
                return;
            }
            var url = "../rad/wordbook/getWordbookByPage.do";
            HttpService.get(url,$scope.searchData).then(function(result){
                var resultData = JSON.parse(result);
                $scope.dataList = resultData.dataList;
                $scope.dataCount = resultData.dataCount;
            });
        }

        $scope.addWordbook = function(){
            $scope.addData = {

            }
            $('#myModal').modal('show');
        }

        $scope.editWordbook = function(id){
            var url = "../rad/wordbook/getWordbookById.do?id="+id;
            HttpService.get(url)
                .then(function (result){
                    var wordbookData = JSON.parse(result);
                    if(angular.isObject(wordbookData)){
                        $scope.addData = wordbookData
                        $('#myModal').modal('show');
                    }else{
                        ConfirmService.alert("该信息不存在");
                    }
                });
        }

        $scope.saveWordbook = function(){
            if (!angular.isObject($scope.addData)) {
                ConfirmService.alert("参数错误");
                return;
            }
            if(!$scope.addData.type){
                ConfirmService.alert("请选择类型");
                return;
            }
            if(!$scope.addData.name){
                ConfirmService.alert("请填写名称");
                return;
            }

            var url = "../rad/wordbook/saveWordbook.do";
            HttpService.post(url,$scope.addData).then(function(result){
                ConfirmService.message(result);
                $scope.getWordbook();
                $('#myModal').modal('hide');
            })
        }

        $scope.doDeleteWordbook = function(id){
            $scope.delId = id;
            ConfirmService.confirm("删除提示","确定删除该记录吗？",$scope.confirmDelete,null);
        }

        $scope.confirmDelete = function(){
            var url = "../rad/wordbook/doDeleteWordbook.do?id="+$scope.delId;
            HttpService.get(url).then(function(result){
                ConfirmService.message(result);
                $scope.getWordbook();

            })
        }


        $scope.setSort = function(){
            $scope.sortFlag = true;
        }
        $scope.sortNum = 0;

        $scope.setSortNum = function(id){
            $scope.sortNum++;
            for(var i=0;i<$scope.configList.length;i++){
                if(id==$scope.configList[i].id){
                    $scope.configList[i].sortNum=$scope.sortNum;
                }
            }
        }
        $scope.setUpSort = function(one,seq){
            one.seq=seq;
        }

        $scope.setDownSort = function(one,seq){
            one.seq=seq+1;
        }


        $scope.init();


    }]);

