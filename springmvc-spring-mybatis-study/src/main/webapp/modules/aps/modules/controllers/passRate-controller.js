angular.module('passRate-controller', [])

    .controller('passRateCtrl', ['$scope', '$state', '$http', 'HttpService', '$modal', '$filter', function ($scope, $state, $http, HttpService, $modal, $filter) {

        $scope.init = function () {

            $scope.getOperationType();
        }

        $scope.getOperationType = function () {
            var url = "../aps/apsDataWorkAction!getDataWorkbook.action?type=5";
            $http.get(url).success(function (resultData) {
                if (resultData.result.flag == 'Y') {
                    $scope.operationTypeList = resultData.result.data;
                } else {
                    alert(resultData.result.data);
                }
            })
        }

        $scope.clearAddData = function () {
            $scope.addData = {
                pid: '',
                prodtype: '',
                operationId: '',
                passRate: ''
            }
        }

        $scope.searchData = {
            prodtype: '',
            operationId: '',
            pageNo: 1,
            onePageTotal: 10,
        }

        $scope.searchPassRate = function () {
            $scope.searchData.pageNo = 1; //页面始终从第一页开始
            $scope.getPassRate();
        }

        $scope.$watch("searchData.pageNo", function () {

            if ($scope.searchData.pageNo < 1) {
                $scope.searchData.pageNo = 1;
            }
            if ($scope.count && $scope.searchData.onePageTotal) {
                var tempPageNo = parseInt($scope.count / $scope.searchData.onePageTotal)
                if (($scope.count % $scope.searchData.onePageTotal) > 0) {
                    tempPageNo = tempPageNo + 1;
                }
                if ($scope.searchData.pageNo > tempPageNo) {
                    $scope.searchData.pageNo = tempPageNo;
                }
            } else if ($scope.count) {
                var tempPageNo = parseInt($scope.count / 10)
                if (($scope.count % 10) > 0) {
                    tempPageNo = tempPageNo + 1;
                }
                if ($scope.searchData.pageNo > tempPageNo) {
                    $scope.searchData.pageNo = tempPageNo;
                }
            }

            if ($scope.searchData.pageNo > 0) {
                $scope.getPassRate();
            }
        });

        $scope.addPassRate = function () {
            $scope.clearAddData();
            $('#myModal').modal('show');
        }
        $scope.savePassRate = function () {
            if (!$scope.addData) {
                alert('保存参数有误,请刷新并重新录入');
                return;
            }
            if (!$scope.addData.prodtype) {
                alert('请填写产品型号');
                return;
            }
            if (!$scope.addData.operationId) {
                alert('请选择工序');
                return;
            }
            var url = "../aps/apsPassRateAction!savePassRate.action";
            var params = {paramJson: angular.toJson($scope.addData)};
            $http({
                method: 'POST',
                url: url,
                params: params
            }).success(function (resultData) {
                    if (resultData.result.flag == 'Y') {
                        $('#myModal').modal('hide');
                        alert('保存成功');
                        $scope.getPassRate();
                    } else {
                        alert(resultData.result.data);
                    }
                }
            )
        }

        $scope.editPassRate = function (pid) {
            $scope.clearAddData();
            var url = "../aps/apsPassRateAction!getPassRateById.action?pid=" + pid;
            $http.get(url).success(
                function (resultData) {
                    if (resultData.result.flag == 'Y') {
                        $scope.addData = resultData.result.data;
                        $('#myModal').modal('show');

                    } else {
                        alert(resultData.result.data);
                    }
                })

        }

        $scope.getPassRate = function () {
            var url = "../aps/apsPassRateAction!getPassRateByPage.action";
            var params = {paramJson: angular.toJson($scope.searchData)};
            $http({
                method: 'POST',
                url: url,
                params: params
            }).success(function (resultData) {
                if (resultData.result.flag == 'Y') {
                    $scope.count = resultData.result.data.count;
                    $scope.dataList = resultData.result.data.list;
                } else {
                    alert(resultData.result.data);
                }
            })


        }


        $scope.doDeletePassRate = function (one) {
            $scope.delId = one.pid;
            $scope.confirm(null, '确定删除当前数据吗?', $scope.confirmDelete, null, 'sm');
        }

        $scope.confirmDelete = function () {
            var url = "../aps/apsPassRateAction!doDeletePassRate.action?pid=" + $scope.delId;
            $http.get(url).success(
                function (resultData) {
                    if (resultData.result.flag == 'Y') {
                        for (var i = 0; i < $scope.dataList.length; i++) {
                            if ($scope.delId == $scope.dataList[i].pid) {
                                $scope.dataList.splice(i, 1);
                                break;
                            }
                        }
                        $scope.count = $scope.count - 1;
                        alert("删除成功");
                    }
                })
        }

        $scope.init();

    }])

;