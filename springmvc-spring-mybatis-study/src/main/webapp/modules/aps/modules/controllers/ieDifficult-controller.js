angular.module('ieDifficult-controller', [])

    .controller('ieDifficultCtrl', ['$scope', '$state', '$http', 'HttpService', '$modal', '$filter', function ($scope, $state, $http, HttpService, $modal, $filter) {

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

        $scope.$watch('addData.prodtype+addData.operationId', function () {
            if ($scope.addData && $scope.addData.prodtype && $scope.addData.operationId) {
                $scope.getDifficultLevel($scope.addData.prodtype, $scope.addData.operationId)
            }
        })

        $scope.getDifficultLevel = function (prodtype, operationId) {
            var url = "../aps/apsDifficultAction!getOperationDifficultByCond.action";
            var condData = {
                prodtype: prodtype,
                operationId: operationId
            }
            var params = {paramJson: angular.toJson(condData)};
            $http({
                method: 'POST',
                url: url,
                params: params
            }).success(function (resultData) {
                if (resultData.result.flag == 'Y') {
                    $scope.operationDifficultLevel = resultData.result.data;
                } else {
                    alert(resultData.result.data);
                }
            })
        }

        $scope.selectDifficultLevel = function (level) {
            for (var i = 0; i < $scope.operationDifficultLevel.length; i++) {
                if (level == $scope.operationDifficultLevel[i].difficultLevel) {
                    $scope.addData.difficultNum = $scope.operationDifficultLevel[i].difficultNum
                    break;
                }
            }
        }

        $scope.clearAddData = function () {
            $scope.addData = {
                pid: '',
                prodtype: '',
                operationId: '',
                difficultLevel: '',
                difficultNum: ''
            }
        }

        $scope.searchData = {
            prodtype: '',
            operationId: '',
            pageNo: 1,
            onePageTotal: 10,
        }

        $scope.searchIeDifficult = function () {
            $scope.searchData.pageNo = 1; //页面始终从第一页开始
            $scope.getIeDifficult();
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
                $scope.getIeDifficult();
            }
        });

        $scope.addIeDifficult = function () {
            $scope.clearAddData();
            $('#myModal').modal('show');
        }
        $scope.saveIeDifficult = function () {
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
            var url = "../aps/apsDifficultAction!saveIeDifficult.action";
            var params = {paramJson: angular.toJson($scope.addData)};
            $http({
                method: 'POST',
                url: url,
                params: params
            }).success(function (resultData) {
                    if (resultData.result.flag == 'Y') {
                        $('#myModal').modal('hide');
                        alert('保存成功');
                        $scope.getIeDifficult();
                    } else {
                        alert(resultData.result.data);
                    }
                }
            )
        }

        $scope.editIeDifficult = function (pid) {
            $scope.clearAddData();
            var url = "../aps/apsDifficultAction!getIeDifficultById.action?pid=" + pid;
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

        $scope.getIeDifficult = function () {
            var url = "../aps/apsDifficultAction!getIeDifficultByPage.action";
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


        $scope.doDeleteIeDifficult = function (one) {
            $scope.delId = one.pid;
            $scope.confirm(null, '确定删除当前数据吗?', $scope.confirmDelete, null, 'sm');
        }

        $scope.confirmDelete = function () {
            var url = "../aps/apsDifficultAction!doDeleteIeDifficult.action?pid=" + $scope.delId;
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

    .controller('operationDifficultCtrl', ['$scope', '$state', '$http', 'HttpService', '$modal', '$filter', function ($scope, $state, $http, HttpService, $modal, $filter) {

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
                difficultLevel: '',
                difficultNum: ''
            }
        }

        $scope.searchData = {
            prodtype: '',
            operationId: '',
            pageNo: 1,
            onePageTotal: 10,
        }

        $scope.searchOperationDifficult = function () {
            $scope.searchData.pageNo = 1; //页面始终从第一页开始
            $scope.getOperationDifficult();
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
                $scope.getOperationDifficult();
            }
        });

        $scope.addOperationDifficult = function () {
            $scope.clearAddData();
            $('#myModal').modal('show');
        }
        $scope.saveOperationDifficult = function () {
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
            var url = "../aps/apsDifficultAction!saveOperationDifficult.action";
            var params = {paramJson: angular.toJson($scope.addData)};
            $http({
                method: 'POST',
                url: url,
                params: params
            }).success(function (resultData) {
                    if (resultData.result.flag == 'Y') {
                        $('#myModal').modal('hide');
                        alert('保存成功');
                        $scope.getOperationDifficult();
                    } else {
                        alert(resultData.result.data);
                    }
                }
            )
        }

        $scope.editOperationDifficult = function (pid) {
            $scope.clearAddData();
            var url = "../aps/apsDifficultAction!getOperationDifficultById.action?pid=" + pid;
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

        $scope.getOperationDifficult = function () {
            var url = "../aps/apsDifficultAction!getOperationDifficultByCond.action";
            var params = {paramJson: angular.toJson($scope.searchData)};
            $http({
                method: 'POST',
                url: url,
                params: params
            }).success(function (resultData) {
                if (resultData.result.flag == 'Y') {
                    $scope.dataList = resultData.result.data;
                } else {
                    alert(resultData.result.data);
                }
            })


        }


        $scope.doDeleteOperationDifficult = function (one) {
            $scope.delId = one.pid;
            $scope.confirm(null, '确定删除当前数据吗?', $scope.confirmDelete, null, 'sm');
        }

        $scope.confirmDelete = function () {
            var url = "../aps/apsDifficultAction!doDeleteOperationDifficult.action?pid=" + $scope.delId;
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