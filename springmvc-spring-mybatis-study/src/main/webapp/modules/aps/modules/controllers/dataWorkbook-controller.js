angular.module('dataWorkbook-controller', ['equParam-controller', 'iePerson-controller', 'passRate-controller', 'ieDifficult-controller', 'lineCapacity-controller', 'prodtypeCapacity-controller'])

    .controller('dataWorkbookCtrl', ['$scope', '$state', '$http', 'HttpService', '$modal', '$filter', function ($scope, $state, $http, HttpService, $modal, $filter) {

        $scope.init = function () {
            $scope.dataWorkbookList = [
                {isid: '1', name: '厂区'}, {isid: '2', name: '车间'}, {isid: '3', name: '设备'},
                {isid: '4', name: '线体'}, {isid: '5', name: '工序'}
            ]
            $scope.getFactoryType()
        }


        $scope.clearAddData = function () {
            $scope.addData = {
                pid: '',
                type: '',
                resourceNo: '',
                name: '',
                description: '',
                parentId: ''
            }
        }

        $scope.$watch('searchFactoryId', function () {
            if ($scope.searchFactoryId) {
                $scope.getHourseType($scope.searchFactoryId);
            }
        })
        $scope.$watch('addData.factoryId', function () {
            if ($scope.addData && $scope.addData.factoryId) {
                $scope.getHourseType($scope.addData.factoryId);
            }
        })

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

        $scope.getHourseType = function (parentId) {
            var url = "../aps/apsDataWorkAction!getDataWorkbook.action?type=2&parentId=" + parentId;
            $http.get(url).success(function (resultData) {
                if (resultData.result.flag == 'Y') {
                    $scope.hourseTypeList = resultData.result.data;
                } else {
                    alert(resultData.result.data);
                }
            })
        }

        $scope.addDataWorkbook = function (type) {
            $scope.addType = type;
            $scope.clearAddData();
            $('#myModal').modal('show');
        }
        $scope.saveDataWorkbook = function () {
            if (!$scope.addData) {
                alert('保存参数有误,请刷新并重新录入');
                return;
            }
            if (!($scope.addData.pid || $scope.addType)) {
                alert('参数有误');
                return;
            } else if (!$scope.addData.pid) {
                $scope.addData.type = $scope.addType;
            }
            if ($scope.addType == 2) {
                if (!$scope.addData.factoryId) {
                    alert('请选择所属厂区');
                    return;
                } else {
                    $scope.addData.parentId = $scope.addData.factoryId;
                }
            }
            if ($scope.addType == 3) {
                if (!$scope.addData.hourseId) {
                    alert('请选择所属车间');
                    return;
                } else {
                    $scope.addData.parentId = $scope.addData.hourseId;
                }
            }
            if (!$scope.addData.name) {
                alert('请填写名称');
                return;
            }
            var url = "../aps/apsDataWorkAction!saveDataWorkbook.action";
            var params = {paramJson: angular.toJson($scope.addData)};
            $http
            ({
                method: 'POST',
                url: url,
                params: params
            }).success(function (resultData) {
                    if (resultData.result.flag == 'Y') {

                        $scope.searchDataWorkbook($scope.addData.type)
                        $('#myModal').modal('hide');
                        alert('保存成功');
                    } else {
                        alert(resultData.result.data);
                    }
                }
            )
        }

        $scope.editDataWorkbook = function (one, type) {
            $scope.addType = type;
            $scope.clearAddData();
            var url = "../aps/apsDataWorkAction!getDataWorkbookById.action?pid=" + one.pid;
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

        $scope.searchDataWorkbook = function (type) {
            var parentId = '';
            if (type == 2) {
                if ($scope.searchFactoryId) {
                    parentId = $scope.searchFactoryId;
                }
            } else if (type == 3) {
                if ($scope.searchHourseId) {
                    parentId = $scope.searchHourseId;
                }
            }
            var url = "../aps/apsDataWorkAction!getDataWorkbook.action?type=" + type + "&parentId=" + parentId;
            $http.get(url).success(function (resultData) {
                if (resultData.result.flag == 'Y') {
                    var dataList = resultData.result.data;
                    if (type == 1) {
                        $scope.factoryDataList = dataList;
                    } else if (type == 2) {
                        $scope.hourseDataList = dataList;
                    } else if (type == 3) {
                        $scope.resourceDataList = dataList;
                    } else if (type == 4) {
                        $scope.lineDataList = dataList;
                    } else if (type == 5) {
                        $scope.operationDataList = dataList;
                    }

                } else {
                    alert(resultData.result.data);
                }
            })
        }


        $scope.doDeleteDataWorkbook = function (one, type) {
            $scope.delId = one.pid;
            $scope.delType = type;
            $scope.confirm(null, '确定删除当前数据吗?', $scope.confirmDelete, null, 'sm');
        }

        $scope.confirmDelete = function () {
            var url = "../aps/apsDataWorkAction!doDeleteDataWorkbook.action?pid=" + $scope.delId;
            $http.get(url).success(
                function (resultData) {
                    if (resultData.result.flag == 'Y') {
                        var dataList = new Array();
                        if ($scope.delType == 1) {
                            dataList = $scope.factoryDataList;
                        } else if ($scope.delType == 2) {
                            dataList = $scope.hourseDataList;
                        } else if ($scope.delType == 3) {
                            dataList = $scope.resourceDataList;
                        } else if ($scope.delType == 4) {
                            dataList = $scope.lineDataList;
                        } else if ($scope.delType == 5) {
                            dataList = $scope.operationDataList;
                        }
                        for (var i = 0; i < dataList.length; i++) {
                            if ($scope.delId == dataList[i].pid) {
                                dataList.splice(i, 1);
                                break;
                            }
                        }
                        alert("删除成功");
                    }
                })
        }

        $scope.init();

    }])

;