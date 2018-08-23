angular.module('lineCapacity-controller', [])

    .controller('lineCapacityCtrl', ['$scope', '$state', '$http', 'HttpService', '$modal', '$filter', function ($scope, $state, $http, HttpService, $modal, $filter) {

        $scope.init = function () {
            $scope.searchFactoryId = '';
            $scope.getFactoryType();
            $scope.getLineCapacity();
        }

        $scope.getLineCapacity = function () {
            var factoryId = $scope.searchFactoryId;
            var url = "../aps/apsCapacityAction!getLineCapacity.action?factoryId=" + factoryId;
            $http.get(url).success(function (resultData) {
                if (resultData.result.flag == 'Y') {
                    $scope.lineCapacityList = resultData.result.data;
                } else {
                    alert(resultData.result.data);
                }
            })
        }

        $scope.getFactoryType = function () {
            var url = "../aps/apsDataWorkAction!getDataWorkbook.action?type=1";
            $http.get(url).success(function (resultData) {
                if (resultData.result.flag == 'Y') {
                    $scope.factoryTypeList = resultData.result.data;
                } else {
                    alert(resultData.result.data);
                }
            })
        }


        $scope.init();

    }])

;