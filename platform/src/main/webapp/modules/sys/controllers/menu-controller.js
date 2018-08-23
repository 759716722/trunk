/**
 * Created by wyb on 2018/5/16.
 */

angular.module('menu-controller', [])

    .controller('menuCtrl', ['$scope', 'HttpService','ConfirmService', function ($scope, HttpService,ConfirmService) {
        var zTreeObj;
        // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
        var setting = {
            data: {
                key : {
                    title : "title", //鼠标悬停显示的信息
                    name : "name" //网页上显示出节点的名称
                },
                simpleData: {
                    enable: true,
                    idKey: "id", //修改默认的ID为自己的ID
                    pIdKey: "pid",//修改默认父级ID为自己数据的父级ID
                    rootPId: 0     //根节点的ID
                }
            },
            async: {
                enable: true,
                url: "../sys/menu/getMenuByPid.do",
                type: "get",
                autoParam: ["id=pid"],
                dataFilter: filter
            },
            view: {
                addHoverDom: addHoverDom,
                removeHoverDom: removeHoverDom,
                showIcon: true,
                showTitle: true
            },
            callback: {
                onClick: clickNode
            }
        };

        var treeNodes = [
            {"id":0, "pid":0,"type":0,"name":"根目录","isParent":true}
        ];
        $(document).ready(function () {
            zTreeObj = $.fn.zTree.init($("#treeMenu"), setting,treeNodes);
        });

        function filter(treeId, parentNode, childNodes) {
            if(childNodes.flag=='Y'){
                var result = JSON.parse(childNodes.data);
                $scope.$apply(function(){
                    $scope.parentData = parentNode.theParentData;
                    $scope.dataList = result;
                })
                var resultData = new Array();
                for(var i=0;i<result.length;i++){
                    var obj = new Object();
                    obj.id=result[i].id;
                    obj.menuType = result[i].type;
                    obj.name = result[i].name;
                    obj.title = result[i].description;
                    obj.pid = result[i].parentId;
                    if(result[i].type<3){
                        obj.isParent = true;
                    }
                    obj.theParentData = result[i];
                    resultData.push(obj);
                }

                return resultData;
            }else{
                ConfirmService.alert(childNodes);
            }
        }

        function clickNode(event, treeId, treeNode) {
            var treeObj = $.fn.zTree.getZTreeObj("treeMenu");
            treeObj.reAsyncChildNodes(treeNode, "refresh");
        }

        function addHoverDom(treeId, treeNode) {

            var aObj = $("#" + treeNode.tId + "_a");
            if ($("#editBtn_"+treeNode.id).length>0) return;
            if ($("#removeBtn_"+treeNode.id).length>0) return;

            if(treeNode.id==0 || treeNode.level==0){
                var editStr ="<span type='button' class='button add' id='editBtn_" + treeNode.id
                    + "' title='添加子节点'></span>";
            }else if(treeNode.menuType==3){
                var editStr ="<span type='button' class='button remove' id='removeBtn_" + treeNode.id
                    + "' title='删除该节点'></span>";
            }else{
                var editStr ="<span type='button' class='button add' id='editBtn_" + treeNode.id
                    + "' title='添加子节点'></span>"
                    +"<span type='button' class='button remove' id='removeBtn_" + treeNode.id
                    + "' title='删除该节点'></span>";
            }
            aObj.append(editStr);
            var editBtn = $("#editBtn_"+treeNode.id);
            var removeBtn = $("#removeBtn_"+treeNode.id);
            if (editBtn) editBtn.bind("click", function(){
                $scope.addMenuList(treeNode.id);
            });
            if (removeBtn) removeBtn.bind("click",function(){
                $scope.delId = treeNode.id;
                var promptMsg = "确定删除该节点吗？";
                if(treeNode.type<3){
                    promptMsg = "确定删除该节点及所有子节点吗？"
                }
                ConfirmService.confirm("删除提示",promptMsg,$scope.confirmDelete,null);
            });
        }

        function removeHoverDom(treeId, treeNode) {
            $("#editBtn_"+treeNode.id).unbind().remove();
            $("#removeBtn_"+treeNode.id).unbind().remove();
        }

        $scope.addMenuList = function(pid){
            $scope.addDataPid = pid;
            $scope.addDataType = "";
            $scope.addDataList = new Array();
            $scope.$apply(function(){
                $scope.addList();
            });
            $('#myAddModal').modal('show');
        }

        $scope.addList = function(){
            var oneObj = {
                type : '',
                name : '',
                url : '',
                description:'',
                parentId : $scope.addDataPid
            }
            $scope.addDataList.push(oneObj);
        }

        $scope.removeList = function($$hashKey){
            for(var i=0;i<$scope.addDataList.length;i++){
                if($$hashKey==$scope.addDataList[i].$$hashKey){
                    $scope.addDataList.splice(i,1);
                }
            }
        }

        $scope.createMenuList = function(){
            var url = "../sys/menu/createMenu.do";
            if(!$scope.addDataPid && $scope.addDataPid!=0){
                ConfirmService.alert("父节点参数不能为空");
                return;
            }
            if(!$scope.addDataType){
                ConfirmService.alert("类型不能为空");
                return;
            }
            if(angular.isArray($scope.addDataList)){
                for(var i=0;i<$scope.addDataList.length;i++){
                    $scope.addDataList[i].type = $scope.addDataType;
                    $scope.addDataList[i].parentId = $scope.addDataPid;
                    if(!$scope.addDataList[i].name){
                        ConfirmService.alert("名称不能为空");
                        return;
                    }
                    if(!$scope.addDataList[i].url){
                        ConfirmService.alert("url不能为空");
                        return;
                    }
                }
            }else{
                ConfirmService.alert("参数错误");
                return;
            }
            HttpService.post(url,$scope.addDataList).then(function(result){
                ConfirmService.message(result);

                var treeObj = $.fn.zTree.getZTreeObj("treeMenu");
                var nodes = treeObj.getSelectedNodes();
                if (nodes.length>0) {
                    treeObj.reAsyncChildNodes(nodes[0], "refresh");
                }

                $('#myAddModal').modal('hide');
            })
        }

        $scope.confirmDelete = function(){
            var url = "../sys/menu/doDeleteMenu.do?id="+$scope.delId;
            HttpService.get(url).then(function(result){
                ConfirmService.message(result);

                var treeObj = $.fn.zTree.getZTreeObj("treeMenu");
                var nodes = treeObj.getSelectedNodes();
                if (nodes.length>0) {
                    var parentNode = nodes[0].getParentNode();
                    treeObj.reAsyncChildNodes(parentNode, "refresh");
                }
            })
        }


    }]);

