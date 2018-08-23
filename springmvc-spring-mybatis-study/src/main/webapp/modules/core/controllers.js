angular.module('app.controllers', ['sys-controller'])

    .controller('AppCtrl', ['$scope', 'HttpService', 'ConfirmService', function ($scope, HttpService, ConfirmService) {

        $scope.getCurrUser = function(){
            var url = "../sys/user/getCurrUser.do";
            HttpService.get(url).then(function(result){
                $scope.user = JSON.parse(result);
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


    }]);
