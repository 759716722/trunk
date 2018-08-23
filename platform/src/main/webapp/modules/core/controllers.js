angular.module('app.controllers', ['sys-controller','rad-controller','hr-controller'])

    .controller('AppCtrl', ['$scope', 'HttpService', 'ConfirmService', function ($scope, HttpService, ConfirmService) {


        $scope.getCurrUser = function(){
            var url = "../sys/user/getCurrUserAndMenu.do";
            HttpService.get(url).then(function(result){
                var resultData = JSON.parse(result);resultData
                $scope.user = resultData.user;
                // var menuList = resultData.menuList;
                // var menuDataList = new Array();
                // for(var i=0;i<menuList.length;i++){
                //     if(menuList[i].parentId==0 && menuList[i].type==1){
                //         var obj = new Object();
                //         obj.text = menuList[i].name;
                //         var childList = new Array();
                //         for (var j = 0; j < menuList.length; j++) {
                //             if (menuList[i].id == menuList[j].parentId) {
                //                 var childObj = new Object();
                //                 childObj.text = menuList[j].name;
                //                 childObj.href = "/employee" ;
                //                 childList.push(childObj);
                //             }
                //         }
                //         obj.nodes = childList;
                //         menuDataList.push(obj);
                //     }
                // }
                // console.log(menuDataList);
                // $('#indexMenuTree').treeview({
                //     data: menuDataList,
                //     enableLinks : true,
                //     onNodeSelected:function(event, data) {
                //         console.log(event);
                //         console.log(data);
                //     }
                //
                // });
            });
        }
        $scope.getCurrUser();


        $scope.flag = true;
        $scope.changeNavBar = function () {
            if(screen.width<=600){
                $('#leftContext').collapse('toggle')
            }else{
                if($scope.flag){ //隐藏侧边栏
                    $("#navBar").animate({marginLeft:"0px"},80);
                    $("#leftContext").animate({width:"0%",opacity:"hide"},80);
                    $("#rightContext").animate({width:"100%",opacity:"show"},100);
                    $scope.flag = false;
                }else{
                    $("#navBar").animate({marginLeft:"100px"},100);
                    $("#leftContext").animate({width:"10%",opacity:"show"},100);
                    $("#rightContext").animate({width:"90%",opacity:"show"},80);
                    $scope.flag = true;
                }
            }

        }
        $scope.changeCollapseTitle = function () {
            $('#collapseTitle').collapse('toggle')
        }
        $scope.changeCollapseOne = function () {
            $('#collapseOne').collapse('toggle')
        }
        $scope.changeCollapseTwo = function () {
            $('#collapseTwo').collapse('toggle')
        }
        $scope.changeCollapseThree = function () {
            $('#collapseThree').collapse('toggle')
        }
        $scope.changeCollapseFour = function () {
            $('#collapseFour').collapse('toggle')
        }

        $scope.toProductSearch = function(){
            window.open("../public/productSearch.html");
        }


    }]);
