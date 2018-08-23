/**
 * Created by wyb on 2018/6/27.
 */

angular.module('product-controller', [])

    .controller('productCtrl', ['$scope', 'HttpService','ConfirmService', function ($scope, HttpService,ConfirmService) {
        $scope.init = function(){

            $scope.configValueList = [{id:1,name:'标配'},{id:2,name:'选配'},{id:3,name:'无'}];

        };

        $scope.getIndustry = function(){
            var url = "../rad/wordbook/getWordbookByCond.do?type=1";
            HttpService.get(url).then(function(result){
                $scope.industryList = JSON.parse(result);
            });
        };

        $scope.getIndustry();

        $scope.getType = function(){
            var url = "../rad/wordbook/getWordbookByCond.do?type=3";
            HttpService.get(url).then(function(result){
                $scope.typeList = JSON.parse(result);
            });
        };

        $scope.getType();

        $scope.searchData = {
            typeId: null,
            industry: null,
            model: '',
            onePageNum: 10,
            pageNo: 1
        };

        $scope.getProduct = function(){
            var url = "../rad/product/getProductByPage.do";
            HttpService.get(url,$scope.searchData).then(function(result){
                var resultData = JSON.parse(result);
                $scope.dataList = resultData.dataList;
                $scope.dataCount = resultData.dataCount;
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
        };

        $scope.addProduct = function(){
            $scope.addData = {

            };
            $scope.typeId = null;
            $scope.detailList = new Array();
            $('#myModal').modal('show');
        };

        $scope.$watch('typeId',function(){
            if($scope.addData && $scope.typeId){
                if($scope.addData.id && $scope.addData.typeId==$scope.typeId){
                    $scope.getProductConfig();
                }else{
                    $scope.getTypeConfig();
                }

            }

        });

        $scope.getTypeConfig = function(typeId){
            var url = "../rad/product/getProductConfig.do?typeId="+$scope.typeId;
            HttpService.get(url).then(function(result){
                $scope.detailList = JSON.parse(result);
            });
        };

        $scope.getProductConfig = function(){
            var url = "../rad/product/getProductById.do?id="+$scope.addData.id;
            HttpService.get(url)
                .then(function (result){
                    var resultData = JSON.parse(result);
                    if(angular.isObject(resultData)){
                        $scope.detailList = resultData.detailList;
                    }
                });
        };




        $scope.editProduct = function(id){
            var url = "../rad/product/getProductById.do?id="+id;
            HttpService.get(url)
                .then(function (result){
                    var resultData = JSON.parse(result);
                    if(angular.isObject(resultData)){
                        $scope.addData = resultData.product;
                        $scope.typeId = $scope.addData.typeId;
                        $scope.detailList = resultData.detailList;
                        $('#myModal').modal('show');
                    }else{
                        ConfirmService.alert("该信息不存在");
                    }
                });
        };

        $scope.saveProduct = function(){

            if (!(angular.isObject($scope.addData) && angular.isArray($scope.detailList))) {
                ConfirmService.alert("参数错误");
                return;
            }
            $scope.addData.typeId = $scope.typeId;

            if(!$scope.addData.typeId){
                ConfirmService.alert("请选择类型");
                return;
            }
            if(!$scope.addData.model){
                ConfirmService.alert("请填写型号");
                return;
            }
            if(!$scope.addData.industry) {
                ConfirmService.alert("请选择行业");
                return;
            }

            for(var i=0;i<$scope.detailList.length;i++){
                if(!$scope.detailList[i].configId || !$scope.detailList[i].configValue && $scope.detailList[i].configValue!=0){
                    ConfirmService.alert("参数配置不允许为空");
                    return;
                }
            }

            $scope.addData.detailList = $scope.detailList;
            var url = "../rad/product/saveProduct.do";
            HttpService.post(url,$scope.addData).then(function(result){
                ConfirmService.message(result);
                $('#myModal').modal('hide');
                $scope.getProduct();
            })
        };

        $scope.doDeleteProduct = function(id){
            $scope.delId = id;
            ConfirmService.confirm("删除提示","确定删除该记录吗？",$scope.confirmDelete,null);
        };

        $scope.confirmDelete = function(){
            var url = "../rad/product/doDeleteProduct.do?id="+$scope.delId;
            HttpService.get(url).then(function(result){
                ConfirmService.message(result);

            })
        };

        $scope.init();


    }])

;

