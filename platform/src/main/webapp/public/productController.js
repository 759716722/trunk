angular.module('productSearch', ['app.services'])

    .controller('productCtrl', ['$scope','HttpService',function ($scope,HttpService) {

        $scope.init = function(){

            var config = {
                allowClear: false,
                language: "zh-CN",
                width: '100%'
            };
            $('#modelSelect2').select2(config);

            $scope.getType();

        };

        $scope.getType = function(){
            var url = "../api/product/getParam.do";
            HttpService.get(url).then(function(result){
                var resultData = JSON.parse(result);
                $scope.industryList = resultData.industryList;
                $scope.configList = resultData.configList;
                $scope.typeList = resultData.typeList;
                $scope.modelList = resultData.modelList;


            });
        };

        $scope.choose = function(one){
            if(1==one.type){ // 选择行业
                for(var i=0;i<$scope.industryList.length;i++){
                    if(one.id==$scope.industryList[i].id){
                        if(one.checked){
                            one.checked = false;
                        }else{
                            one.checked = true;
                        }

                    }else{
                        $scope.industryList[i].checked = false;
                    }
                }
            }else{   // 选择配置
                if(one.checked){
                    one.checked = false;
                }else{
                    one.checked = true;
                }
            }
            $scope.searchProductByConfig();

        }

        $scope.searchProductByConfig = function(){
            layer.open({
                type: 2,
                time: 0.8
            });

            var industryId = '';
            var configIds = [];
            for(var i=0;i<$scope.industryList.length;i++){
                if($scope.industryList[i].checked){
                    industryId = $scope.industryList[i].id;
                }
            }
            for(var i=0;i<$scope.configList.length;i++){
                if($scope.configList[i].checked){
                    configIds.push($scope.configList[i].id);
                }
            }
            var url = "../api/product/getProduct.do?industryId="+industryId+"&configIds="+configIds;
            HttpService.get(url).then(function(result){
                $scope.dataList = JSON.parse(result);
                for(var i=0;i<$scope.dataList.length;i++){
                    var one = $scope.dataList[i];
                    for(var j=0;j<$scope.typeList.length;j++){
                        if(one.typeId == $scope.typeList[j].id){
                            one.typeName = $scope.typeList[j].name;
                            break;
                        }
                    }
                    for(var j=0;j<$scope.industryList.length;j++){
                        if(one.industry == $scope.industryList[j].id){
                            one.industryName = $scope.industryList[j].name;
                            break;
                        }
                    }
                }
            });

        }

        $scope.searchProductByModel = function(){
            var model= $('#modelSelect2').select2("data")[0].id;

            var url = "../api/product/getProduct.do?model="+model+"&configIds="+[];
            HttpService.get(url).then(function(result){
                $scope.dataList = JSON.parse(result);
                for(var i=0;i<$scope.dataList.length;i++){
                    var one = $scope.dataList[i];
                    for(var j=0;j<$scope.typeList.length;j++){
                        if(one.typeId == $scope.typeList[j].id){
                            one.typeName = $scope.typeList[j].name;
                            break;
                        }
                    }
                    for(var j=0;j<$scope.industryList.length;j++){
                        if(one.industry == $scope.industryList[j].id){
                            one.industryName = $scope.industryList[j].name;
                            break;
                        }
                    }
                }
            });

        }

        $scope.getProductDetail = function(id){

            var url = "../api/product/getProductById.do?id="+id;
            HttpService.get(url).then(function(result){
                $scope.detailList = JSON.parse(result);
                for(var i=0;i<$scope.detailList.length;i++){
                    for(var j=0;j<$scope.configList.length;j++){
                        if($scope.detailList[i].configId == $scope.configList[j].id){
                            $scope.detailList[i].configName = $scope.configList[j].name;
                            break;
                        }
                    }
                }
                $('#myModal').modal('show');
            });

        }



        $scope.init();

    }]);
