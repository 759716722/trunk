angular.module('prodtypeCapacity-controller', [])

    .controller('prodtypeCapacityCtrl', ['$scope', '$state', '$http', 'HttpService', '$modal', '$filter', function ($scope, $state, $http, HttpService, $modal, $filter) {

        $scope.init = function () {
            $scope.searchProdtype = "";
            $scope.searchFactoryId = "";
            $scope.getFactoryType();
            $scope.getProdtypeCapacity();
        }

        $scope.getProdtypeCapacity = function () {
            var prodtype = $scope.searchProdtype;
            var factoryId = $scope.searchFactoryId;
            if (!prodtype) {
                prodtype = '';
            }
            if (!factoryId) {
                factoryId = '';
            }
            var url = "../aps/apsCapacityAction!getProdtypeCapacity.action?prodtype=" + prodtype + "&factoryId=" + factoryId;
            $http.get(url).success(function (resultData) {
                if (resultData.result.flag == 'Y') {
                    $scope.prodtypeCapacityList = resultData.result.data;
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