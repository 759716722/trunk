angular.module('equParam-controller', [])

    .controller('equParamCtrl', ['$scope', '$state', '$http', 'UserService', '$modal', '$filter', function ($scope, $state, $http, UserService, $modal, $filter) {
        var user = '';
        $scope.addPersonFlag = false;
        UserService.getUser().then(function (data) {
            user = data;
            if (!user) {
                alert('用户数据异常');
                return;
            }
            if ('SYSTEM_ADMIN' == user.id) {
                $scope.addPersonFlag = true;
            }
        });

        $scope.init = function () {
            $scope.getFactoryType();
            $scope.getLineType();
            $scope.getOperationType();
        }

        $scope.$watch('searchData.factoryId', function () {
            if ($scope.searchData && $scope.searchData.factoryId) {
                $scope.getHourseType($scope.searchData.factoryId);
            }
        })
        $scope.$watch('searchData.hourseId', function () {
            if ($scope.searchData && $scope.searchData.hourseId) {
                $scope.getResourceType($scope.searchData.hourseId);
            }
        })
        $scope.$watch('addData.factoryId', function () {
            if ($scope.addData && $scope.addData.factoryId) {
                $scope.getHourseType($scope.addData.factoryId);
            }
        })
        $scope.$watch('addData.hourseId', function () {
            if ($scope.addData && $scope.addData.hourseId) {
                $scope.getResourceType($scope.addData.hourseId);
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

        $scope.getResourceType = function (parentId) {
            var url = "../aps/apsDataWorkAction!getDataWorkbook.action?type=3&parentId=" + parentId;
            $http.get(url).success(function (resultData) {
                if (resultData.result.flag == 'Y') {
                    $scope.resourceTypeList = resultData.result.data;
                } else {
                    alert(resultData.result.data);
                }
            })
        }

        $scope.getLineType = function () {
            var url = "../aps/apsDataWorkAction!getDataWorkbook.action?type=4";
            $http.get(url).success(function (resultData) {
                if (resultData.result.flag == 'Y') {
                    $scope.lineTypeList = resultData.result.data;
                } else {
                    alert(resultData.result.data);
                }
            })
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


        $scope.openDate = function ($event, attr) {
            $event.preventDefault();
            $event.stopPropagation();
            $scope.addData[attr] = true;
        };

        $scope.clearAddData = function () {
            $scope.addData = {
                pid: '',
                factoryId: '',
                hourseId: '',
                lineId: '',
                operationId: '',
                resourceId: '',
                standardCt: '',
                rate: '',
                startRate: '',
                yield: '',
                hours: '',
                allowance: '',
                changeHours: '',
                standardCapacity: ''
            }
        }

        $scope.searchData = {
            factoryId: '',
            hourseId: '',
            lineId: '',
            operationId: '',
            resourceId: '',
            pageNo: 1,
            onePageTotal: 10
        }

        $scope.searchEqu = function () {
            $scope.searchData.pageNo = 1; //页面始终从第一页开始
            $scope.getEqu();
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
                $scope.getEqu();
            }
        });

        $scope.addEqu = function () {
            $scope.clearAddData();
            $('#myModal').modal('show');
        }
        $scope.saveEqu = function () {
            if (!$scope.addData) {
                alert('保存参数有误,请刷新并重新录入');
                return;
            }
            if (!$scope.addData.factoryId) {
                alert('请选择厂区');
                return;
            }
            if (!$scope.addData.hourseId) {
                alert('请选择车间');
                return;
            }
            if (!$scope.addData.resourceId) {
                alert('请选择设备');
                return;
            }
            if (!$scope.addData.lineId) {
                alert('请选择线体');
                return;
            }
            if (!$scope.addData.operationId) {
                alert('请选择工序');
                return;
            }
            if (!$scope.addData.standardCt) {
                alert('请填写标准C/T');
                return;
            }
            if (!$scope.addData.rate) {
                alert('请填写速率');
                return;
            }
            if (!$scope.addData.startRate) {
                alert('请填写时间开动率');
                return;
            }
            if (!$scope.addData.yield) {
                alert('请填写良率');
                return;
            }
            if (!$scope.addData.hours) {
                alert('请填写时数');
                return;
            }
            if (!$scope.addData.allowance) {
                alert('请填写宽放率');
                return;
            }
            if (!$scope.addData.changeHours) {
                alert('请填写换线工时');
                return;
            }
            if (!$scope.addData.standardCapacity) {
                alert('请填写标准产能');
                return;
            }
            var url = "../aps/apsEquParamAction!saveEquParam.action";
            var params = {paramJson: angular.toJson($scope.addData)};
            $http
            ({
                method: 'POST',
                url: url,
                params: params
            }).success(function (resultData) {
                    if (resultData.result.flag == 'Y') {
                        $('#myModal').modal('hide');
                        $scope.getEqu();
                        alert('保存成功');
                    } else {
                        alert(resultData.result.data);
                    }
                }
            )
        }

        $scope.editEqu = function (pid) {
            $scope.clearAddData();
            var url = "../aps/apsEquParamAction!getEquParamById.action?pid=" + pid;
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

        $scope.getEqu = function () {
            var url = "../aps/apsEquParamAction!getEquParamByPage.action";
            var params = {paramJson: angular.toJson($scope.searchData)};
            $http
            ({
                method: 'POST',
                url: url,
                params: params
            }).success(function (resultData) {
                if (resultData.result.flag == 'Y') {
                    $scope.count = resultData.result.data.count;
                    $scope.dataList = resultData.result.data.list;
                    var pageNoSum = parseInt($scope.count / $scope.searchData.onePageTotal)
                    if (($scope.count % $scope.searchData.onePageTotal) > 0) {
                        pageNoSum = pageNoSum + 1;
                    }
                    $scope.pageNoSum = pageNoSum;
                } else {
                    alert(resultData.result.data);
                }
            })


        }


        $scope.doDeleteEqu = function (one) {
            $scope.delId = one.pid;
            $scope.confirm(null, '确定删除当前数据吗?', $scope.confirmDelete, null, 'sm');
        }

        $scope.confirmDelete = function () {
            var url = "../aps/apsEquParamAction!doDeleteEquParam.action?pid=" + $scope.delId;
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