/**
 * Created by wyb on 2018/5/16.
 */

angular.module('permission-controller', [])

    .controller('permissionCtrl', ['$scope','$stateParams','HttpService','ConfirmService', function ($scope,$stateParams,HttpService,ConfirmService) {

        var roleId = $stateParams.roleId;

        $scope.searchAll = function(){
            roleId = $scope.searchRoleObject.id;
            if(roleId && roleId!=-1){
                $scope.getRole();
                $scope.getMenu();
                $scope.getUserByRole();
            }
        }

        $scope.init = function(){
            if(roleId && roleId!=-1){
                $scope.getRole();
                $scope.getMenu();
                $scope.getUserByRole();
            }
        }

        $scope.getRole = function(){
            var url = "../sys/role/getRoleById.do?id="+roleId;
            HttpService.get(url).then(function(result){
                var roleData = JSON.parse(result);
                if(angular.isObject(roleData)){
                    $scope.roleData = roleData;
                }else{
                    ConfirmService.alert("角色信息不存在");
                }
            })
        }

        $scope.getUserByRole = function(){
            var url = "../sys/userRole/getUserByRoleId.do?roleId="+roleId;
            HttpService.get(url).then(function(result){
                $scope.userList = JSON.parse(result);
            })
        }


        var zTreeObj;
        var setting = {
            data: {
                key : {
                    title : "title", //鼠标悬停显示的信息
                    name : "name", //网页上显示出节点的名称
                    checked: "isChecked"
                },
                simpleData: {
                    enable: true,
                    idKey: "id", //修改默认的ID为自己的ID
                    pIdKey: "pid",//修改默认父级ID为自己数据的父级ID
                    rootPId: 0     //根节点的ID
                }
            },
            check: {
                enable: true,
                chkStyle: "checkbox",
                chkboxType: { "Y": "ps", "N": "ps" }
            }

        };

        $scope.getMenu = function(){
            var url = "../sys/roleMenu/getMenuByRoleId.do?roleId="+roleId
            HttpService.get(url).then(function(result){
                var resultData = JSON.parse(result);
                var treeNodes = new Array();
                for(var i=0;i<resultData.length;i++){
                    var obj = new Object();
                    obj.id=resultData[i].id;
                    obj.menuType = resultData[i].type;
                    obj.name = resultData[i].name;
                    obj.title = resultData[i].description;
                    obj.pid = resultData[i].parentId;
                    if(resultData[i].type<3){
                        obj.isParent = true;
                    }
                    if(resultData[i].seq){
                        obj.isChecked = true;
                    }
                    treeNodes.push(obj);
                }
                $(document).ready(function () {
                    zTreeObj = $.fn.zTree.init($("#treePermission"), setting,treeNodes);
                    zTreeObj.expandAll(true);
                });
            })
        }

        $scope.saveRoleMenu = function(){
            var url = "../sys/roleMenu/createRoleMenu.do?roleId="+roleId;
            if(!roleId || roleId<1){
                ConfirmService.alert("角色参数无效");
                return;
            }else if(roleId==1){
                ConfirmService.alert("管理员角色不允许操作");
                return;
            }
            var treeObj = $.fn.zTree.getZTreeObj("treePermission");
            var nodes = treeObj.getCheckedNodes(true);
            var dataList = new Array();
            var obj = new Object();
            for(var i=0;i<nodes.length;i++){
                obj.menuId=nodes[i].id;
                obj.roleId = roleId;
                dataList.push(angular.copy(obj));
            }
            HttpService.post(url,dataList).then(function(result){
                ConfirmService.message(result);
            })
        }


        $scope.addUserRole = function(){

            if(!roleId || roleId<1){
                ConfirmService.alert("角色参数无效");
                return;
            }
            if(!$scope.searchUserObject || !$scope.searchUserObject.id){
                ConfirmService.alert("请选择用户");
                return;
            }
            var data={
                userId:$scope.searchUserObject.id,
                roleId:roleId
            }
            var url = "../sys/userRole/createUserRole.do";
            HttpService.post(url,data).then(function(result){
                ConfirmService.message(result);
                $scope.getUserByRole();
            })
        }

        $scope.doDeleteUserRole = function(id){
            $scope.delId = id;
            ConfirmService.confirm("删除提示","确定取消该用户的角色吗？",$scope.confirmDelete,null);
        }
        $scope.confirmDelete = function(){
            var url = "../sys/userRole/doDeleteUserRole.do?id="+$scope.delId;
            HttpService.get(url).then(function(result){
                ConfirmService.message(result);
                $scope.getUserByRole();
            })
        }

        $scope.init();

    }]);

