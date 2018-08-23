/**
 * Created by wyb on 2018/6/27.
 */
angular.module('typeConfig-controller', [])

    .controller('typeConfigCtrl', ['$scope', 'HttpService','ConfirmService', function ($scope, HttpService,ConfirmService) {
        $scope.init = function(){
            $scope.categoryList = [{id:1,name:'工具类'},{id:2,name:'附件类'}];
            $scope.searchByCategory = 1;
            $scope.getTypeConfig();
        }


        $scope.getTypeConfig = function(){

            var url = "../rad/product/getTypeConfig.do";
            HttpService.get(url).then(function(result){
                var resultData = JSON.parse(result);
                $scope.typeList = resultData.typeList;
                $scope.configList = resultData.configList;
                $scope.typeConfigList = resultData.typeConfigList;

                for(var i=0;i<$scope.configList.length;i++){
                    var typeConfigList = new Array();
                    for(var j=0;j<$scope.typeList.length;j++){
                        var obj = new Object();
                        obj.configId=$scope.configList[i].id;
                        obj.typeId=$scope.typeList[j].id;
                        obj.checked = false;
                        for(var k=0;k<$scope.typeConfigList.length;k++){
                            if($scope.typeConfigList[k].configId==$scope.configList[i].id && $scope.typeConfigList[k].typeId==$scope.typeList[j].id){
                                obj.checked = true;
                                break;
                            }
                        }
                        typeConfigList.push(obj);
                    }
                    $scope.configList[i].typeConfigList = typeConfigList;
                }
            });

        }

        $scope.saveTypeConfig = function(){

            var configValueList = new Array();
            for(var i=0;i<$scope.configList.length;i++){
                configValueList = configValueList.concat($scope.configList[i].typeConfigList);
            }
            var configValue = new Array();
            for(var i=0;i<configValueList.length;i++){
                if(configValueList[i].checked){
                    configValue.push(configValueList[i])
                }
            }
            var url = "../rad/product/saveTypeConfig.do";
            HttpService.post(url,configValue).then(function(result){
                ConfirmService.message(result);
            })
        }

        $scope.getSortConfig = function(){
            $scope.sortConfigList = new Array();
            if(!$scope.searchByCategory){
                ConfirmService.alert("请选择分类");
            }
            var sortNum = 0;
            for(var i=0;i<$scope.configList.length;i++){
                if($scope.searchByCategory == $scope.configList[i].category){
                    sortNum ++;
                    $scope.configList[i].seq = sortNum
                    $scope.sortConfigList.push($scope.configList[i]);
                }else if(!$scope.configList[i].category){
                    ConfirmService.alert("配置参数【"+$scope.configList[i].name+"】未分类，请在字典管理模块选择分类");
                    $scope.sortConfigList = new Array();
                    return;
                }
            }
        }

        $scope.setSort = function(id,beforeNum,afterNum){
            if(afterNum<1){
                afterNum=1
            }else if(afterNum>=$scope.sortConfigList.length){
                afterNum = $scope.sortConfigList.length;
            }
            for(var i=0;i<$scope.sortConfigList.length;i++){
                if(id==$scope.sortConfigList[i].id){
                    $scope.sortConfigList[i].seq = afterNum;
                }else if($scope.sortConfigList[i].seq==afterNum){
                    $scope.sortConfigList[i].seq = beforeNum;
                }
            }
        }

        $scope.saveSortList = function(){
            if(!angular.isArray($scope.sortConfigList)){
                ConfirmService.alert("请查询相关配置");
                return;
            }
            var dataList = new Array();
            for(var i=0;i<$scope.sortConfigList.length;i++){
                var obj = new Object();
                if(!$scope.sortConfigList[i].id || !$scope.sortConfigList[i].seq){
                    ConfirmService.alert("关键参数为空");
                    return;
                }
                obj.id=$scope.sortConfigList[i].id;
                obj.seq=$scope.sortConfigList[i].seq;
                dataList.push(obj);
            }
            var url = "../rad/wordbook/saveWordbookSort.do";
            HttpService.post(url,dataList).then(function(result){
                ConfirmService.message(result);
            })
        }

        $scope.init();

    }]);

