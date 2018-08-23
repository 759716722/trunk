angular.module('iePerson-controller', [])

    .controller('iePersonCtrl', ['$scope', '$state', '$http', 'HttpService', '$modal', '$filter', function ($scope, $state, $http, HttpService, $modal, $filter) {

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


        $scope.clearAddData = function () {

            $scope.addData = {
                pid: '',
                factoryId: '',
                hourseId: '',
                lineId: '',
                operationId: '',
                demand: '',
                actual: '',
                skilled: '',
                perSkilled: '',
                perActual: ''
            }
        }

        $scope.searchData = {
            factoryId: '',
            hourseId: '',
            lineId: '',
            operationId: '',
            pageNo: 1,
            onePageTotal: 10,
        }


        $scope.addIePerson = function () {
            $scope.clearAddData();
            $('#myModal').modal('show');
        }

        $scope.saveIePerson = function () {
            if (!$scope.addData) {
                alert('保存参数有误,请刷新并重新录入');
                return;
            }

            var url = "../aps/apsPersonAction!saveIePerson.action";
            var params = {paramJson: angular.toJson($scope.addData)};
            $http({
                mothed: 'POST',
                url: url,
                params: params
            }).success(function (resultData) {
                if (resultData.result.flag == "Y") {
                    $('#myModal').modal('hide');
                    $scope.getIePerson();
                    alert('保存成功')
                } else {
                    alert(resultData.result.data);
                }
            })

        }

        $scope.searchIePerson = function () {
            $scope.searchData.pageNo = 1; //页面始终从第一页开始
            $scope.getIePerson();
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
                    return;
                }
            } else if ($scope.count) {
                var tempPageNo = parseInt($scope.count / 10)
                if (($scope.count % 10) > 0) {
                    tempPageNo = tempPageNo + 1;
                }
                if ($scope.searchData.pageNo > tempPageNo) {
                    $scope.searchData.pageNo = tempPageNo;
                    return;
                }
            }

            if ($scope.searchData.pageNo > 0) {
                $scope.getIePerson();
            }
        });


        $scope.getIePerson = function () {
            var url = "../aps/apsPersonAction!getIePersonByPage.action";
            var params = {paramJson: angular.toJson($scope.searchData)}
            $http({
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
                }
            })
        }

        $scope.editIePerson = function (pid) {
            var url = "../aps/apsPersonAction!getIePersonById.action?pid=" + pid;
            $http.get(url).success(function (resultData) {
                if (resultData.result.flag == 'Y') {
                    $scope.addData = resultData.result.data;
                    $('#myModal').modal('show');

                } else {
                    alert(resultData.result.data);
                }
            })
        }

        $scope.doDeleteIePerson = function (one) {
            $scope.delId = one.pid;
            $scope.confirm(null, '确定删除当前数据吗?', $scope.confirmDelete, null, 'sm');
        }

        $scope.confirmDelete = function () {
            var url = "../aps/apsPersonAction!doDeleteIePerson.action?pid=" + $scope.delId;
            $http.get(url).success(function (resultData) {
                if (resultData.result.flag == 'Y') {
                    for (var i = 0; i < $scope.dataList.length; i++) {
                        if ($scope.delId == $scope.dataList[i].pid) {
                            $scope.dataList.splice(i, 1);
                            break;
                        }
                    }
                    $scope.count = $scope.count - 1;

                    alert('删除成功');
                } else {
                    alert(resultData.result.data);
                }
            })
        }


        $scope.init();

    }])


;
