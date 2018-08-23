angular.module('app.directives', [])

    /*日期选择器指令，只能以元素的方式使用，使用方式如下:
    * <laydate class="form-control" ng-model="searchData.theDate">{{searchData.theDate}}</laydate>
    * {{searchData.theDate}} 一定要有这个，不然显示的数据会在一些特殊的情况下出现问题，这个待会解释。
    * @ngModel 绑定的数据
    * @dateType 选择器的类型 可以不使用，默认 date
    * 参数类型 ：datetime 日期时间选择器 year 年选择器 month 年月选择器 date 日期选择器 time 时间选择器
    * @format  自定义格式  可以不使用，默认 yyyy-MM-dd
    * 参数类型 ：参见 http://www.layui.com/doc/modules/laydate.html#format
    * */
    .directive('laydate',[function(){
        return{
            restrict: 'E',
            scope: {
                ngModel: '=',
                dateType: '@',
                format: '@'
            },
            link: function(scope,el,attrs){
                // 默认值
                var type = 'date';
                var format = 'yyyy-MM-dd';
                if(scope.dateType){
                    type = scope.dateType;
                }
                if(scope.format){
                    format = scope.format;
                }
                laydate.render({
                    elem: el[0],
                    type: type,
                    format: format,
                    calendar: true,     //显示节日
                    /*mark: {             //自定义标注重要日子
                        '0-12-31': '跨年', //每年的日期
                        '0-0-10': '工资' //每月某天
                    },*/
                    done: function(data){
                        scope.$apply(function(){
                            scope.ngModel = data;
                        })
                    }
                });
                // 这里$watch监听不到值变化的原因是
                // layate操作的是Html元素的方式是在调用的时候在页面上创建新的 HTML 元素（即选择日期框）,
                // 它是把选取到的数据绑定在了该元素上，可通过$('#test').val()获取值，但是不能实现ng-model的数据绑定。
                // 所以我们用选择完毕后的回调方法done进行赋值done: function(data){scope.ngModel = data;},
                // 但是这个时候用$watch监听ngModel是不触发的，不论是在自定义指令还是在控制器里都监听不到值。
                // 因此如果需要监听，可以使用$apply触发脏检查。done: function(data){scope.$apply(function(){scope.ngModel = data;})}
                /*scope.$watch('ngModel', function(newVal, oldVal) {
                    console.log(newVal);
                    console.log(oldVal);
                });*/

            }
        }
    }])

    /*
     * 分页指令,使用方式如下：
     * <my-pagination count-data="{{dataCount}}" one-page-total="10" curr-page="currPage" get-date-func="getRole()" ></my-pagination>
     * @count-data 数据总数
     * @one-page-total 每页展示记录数
     * @get-date-func 查询函数
     * @curr-page 当前页
     * */
    .directive('myPagination',[function(){
        return{
            restrict: 'EA',
            template:'<ul class="pagination pagination-sm" style="margin-top: 0px;margin-bottom: 0px;">' +
            '<li><a>第{{currPage}}/{{pageCount}}页</a></li>' +
            '<li ng-click="prevPage()"><a>&laquo;</a></li>' +
            '<li ng-repeat="x in pageList track by $index" class="{{x==currPage?\'active\':\'\'}}"><a ng-click="changePage(x)">{{x}}</a></li>' +
            '<li ng-click="nextPage()"><a>&raquo;</a></li> ' +
            '</ul>',
            scope: {
                currPage: '=',
                // 使用 = 双向绑定时，scope为了使双向数据绑定起作用，必须可以将新值写回到用表达式定义的路径中。
                // 就是说必须在使用自定义指令的时候 必须有 curr-page="xxxx",否则会出现如下错误。
                // Error: [$compile:nonassign] http://errors.angularjs.org/1.2.20/$compile/nonassign?p0=undefined&p1=myPagination
                countData: '@',  // 这里不能用 data开头命名 不然scope里没这个参数,不知道为啥
                onePageTotal: '@',
                getDateFunc:'&'
                // @ 数据单向绑定 = 数据双向绑定 & 绑定函数
            },
            link: function(scope,el,attrs){
                // 初始化
                var pageLength = 5;
                scope.pageList = [];
                scope.currPage = 1;
                if(!scope.countData){
                    scope.getDateFunc();
                }
                scope.prevPage = function(){
                    if(scope.currPage && scope.currPage>2){
                        scope.currPage--;
                    }else{
                        scope.currPage=1;
                    }
                    scope.getDateFunc();
                }
                scope.nextPage = function(){
                    if(scope.currPage){
                        scope.currPage++;
                    }else{
                        scope.currPage=1;
                    }
                    scope.getDateFunc();
                }

                scope.changePage = function(page){
                    scope.currPage = page;
                    scope.getDateFunc();

                }

                scope.$watch('countData+currPage',function(){
                    /*alert('参数错误[自定义指令myPagination中countData,onePageTotal,currPage参数必须有效（不能为空）,请检查如下参数]\n'+
                    'countData:'+scope.countData+'\nonePageTotal:'+scope.onePageTotal+'\ncurrPage:'+scope.onePageTotal);*/
                    if(scope.countData && scope.onePageTotal && scope.currPage){
                        var pageCount =  Math.ceil(scope.countData/scope.onePageTotal);
                        var offset = Math.ceil(pageLength/2);
                        if(pageCount==0){
                            scope.currPage = 1;
                        }else if(scope.currPage>pageCount){
                            scope.currPage = pageCount;
                        }
                        if(scope.currPage<=offset){
                            scope.pageList = [];
                            var tempPage = pageCount>pageLength?pageLength:pageCount==0?1:pageCount;
                            for(var i=1;i<tempPage+1;i++){
                                scope.pageList.push(i);
                            }

                        }else if(scope.currPage>(pageCount-pageLength+offset)){
                            scope.pageList = [];
                            var tempPage = pageCount-pageLength;
                            for(var i=tempPage;i<pageCount;i++){
                                scope.pageList.push(i+1);
                            }

                        }else{
                            scope.pageList = [];
                            for(var i=offset;i>0;i--){
                                scope.pageList.push(scope.currPage-i+1);
                            }
                            for(var i=1;i<offset;i++){
                                scope.pageList.push(scope.currPage+i);
                            }
                        }
                        scope.pageCount = pageCount==0?1:pageCount;

                    }
                })


            }
        }
    }])

    .directive('select2', ['$timeout', function ($timeout) {
        return {
            restrict: 'A',			//E(元素),A(属性),C(类),M(注释)
            scope: {
                ngModel: '='
            },
            link: function (scope, el, attrs) {
                var config = {
                    allowClear: false,
                    language: "zh-CN",
                    width: '100%'
                }
                // 生成select
                if (el.is('select')) {
                    // 初始化
                    $(el).select2(config);
                }

                scope.$watch('ngModel', function(newVal, oldVal) {
                    scope.timer = $timeout(function () {
                        el.val(newVal).trigger("change.select2");
                    }, 0);
                }, true);
                scope.$on("$destroy", function () {
                    if (scope.timer) {
                        $timeout.cancel(scope.timer);
                    }
                });

            }
        };
    }])
    /*
     * 用户专用指令  只能以attribute方式使用
     *
     * */
    .directive('select2User', ['$timeout', function ($timeout) {
        return {
            restrict: 'A',			//E(元素),A(属性),C(类),M(注释)
            scope: {
                selectObject: '='
            },
            link: function (scope, el, attrs) {
                var queryResultDisplay = function (result, params) {
                    var list = new Array();
                    if (result.flag == 'Y') {
                        var roleList = JSON.parse(result.data);
                        for (var i = 0; i < roleList.length; i++) {
                            var obj = new Object();
                            obj.id = roleList[i].id;
                            obj.text = roleList[i].name+" "+roleList[i].loginName+" "+roleList[i].phone;
                            obj.data = roleList[i];
                            list.push(obj);
                        }
                    }
                    /*params.page = params.page || 1;*/
                    return {
                        results: list
                        /*pagination: {
                         more: (params.page * 30) < data.total_count
                         }*/
                    };
                }
                var config = {
                    ajax: {
                        url: "../sys/user/getUserByOrCond.do",
                        type: 'GET',
                        dataType: 'json',
                        delay: 250,
                        data: function (params) {
                            return {
                                searchParam: params.term // 查询参数
                                /*page: params.page*/
                            };
                        },
                        processResults: queryResultDisplay,
                        cache: true
                    },
                    escapeMarkup: function (markup) {
                        return markup;
                    },
                    minimumInputLength: 3,
                    language: "zh-CN",
                    maximumSelectionLength: 2,
                    allowClear:true,
                    placeholder: "请选择",
                    width: "100%",
                    tags: false
                };
                if (el.is('select')) {
                    $(el).select2(config);
                }
                el.on("select2:select", function (evt) {
                    if(attrs.multiple){
                        scope.selectObject = $(el).select2("data");
                    }else{
                        scope.selectObject = $(el).select2("data")[0];
                    }
                    scope.$apply(function(){
                        scope.selectObject;
                    })
                });

            }

        };
    }])

    /*
     * 角色专用指令  只能以attribute方式使用
     *
     * */
    .directive('select2Role', ['$timeout', function ($timeout) {
        return {
            restrict: 'A',			//E(元素),A(属性),C(类),M(注释)
            scope: {
                selectObject: '='
            },
            link: function (scope, el, attrs) {
                var queryResultDisplay = function (result, params) {
                    var list = new Array();
                    if (result.flag == 'Y') {
                        var roleList = JSON.parse(result.data);
                        for (var i = 0; i < roleList.length; i++) {
                            var obj = new Object();
                            obj.id = roleList[i].id;
                            obj.text = roleList[i].name;
                            obj.data = roleList[i];
                            list.push(obj);
                        }
                    }
                    /*params.page = params.page || 1;*/
                    return {
                        results: list
                        /*pagination: {
                         more: (params.page * 30) < data.total_count
                         }*/
                    };
                }
                var config = {
                    ajax: {
                        url: "../sys/role/getRoleByCond.do",
                        type: 'GET',
                        dataType: 'json',
                        delay: 250,
                        data: function (params) {
                            return {
                                name: params.term // 查询参数
                                /*page: params.page*/
                            };
                        },
                        processResults: queryResultDisplay,
                        cache: true
                    },
                    escapeMarkup: function (markup) {
                        return markup;
                    },
                    minimumInputLength: 3,
                    language: "zh-CN",
                    maximumSelectionLength: 2,
                    allowClear:true,
                    placeholder: "请选择",
                    width: "100%",
                    tags: false
                };
                if (el.is('select')) {
                    $(el).select2(config);
                }
                el.on("select2:select", function (evt) {
                    if(attrs.multiple){
                        scope.selectObject = $(el).select2("data");
                    }else{
                        scope.selectObject = $(el).select2("data")[0];
                    }
                    scope.$apply(function(){
                        scope.selectObject;
                    })
                });

            }

        };
    }])

;