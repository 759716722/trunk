angular.module('app.services', [])

    .factory('HttpService', ['$http','$q','ConfirmService', function ($http,$q,ConfirmService) {
        return {
            get: function (url,params) {
                var deferred = $q.defer();
                if(!params){
                    params = null;
                }
                $http({
                    method:'GET',
                    url:url,
                    params:params
                }).then(function(result){
                    if(result.status==200 && result.statusText=='OK'){
                        if(!result.data.flag){
                            if(result.data.indexOf('登陆')>0 && result.data.indexOf('sys/user/login.do')>0){
                                alert("回话超时，重新登陆。");
                                location.href = 'login.html';
                            }else{
                                ConfirmService.alert(result.data);
                            }

                        }else if(result.data.flag=='Y'){
                            deferred.resolve(result.data.data);
                        }else{
                            if(result.data.data){
                                ConfirmService.alert(result.data.data);
                            }else{
                                ConfirmService.alert("返回值异常");
                            }
                        }
                    }
                }).catch(function(result){
                    if(result.status==400){
                        ConfirmService.alert("请求失败，请检查参数类型是否匹配。");
                    }else if(result.status==404){
                        ConfirmService.alert("该功能异常，请联系开发人员。");
                    }else if(result.status==500){
                        ConfirmService.alert(result.statusText);
                    }else{
                        ConfirmService.alert(result.message);
                    }
                });
                return deferred.promise;
            },
            post: function (url,data) {
                var deferred = $q.defer();
                $http({
                    method:'POST',
                    url:url,
                    data:data
                }).then(function(result){
                    if(result.status==200 && result.statusText=='OK'){
                        if(!result.data.flag){
                            if(result.data.indexOf('登陆')>0 && result.data.indexOf('sys/user/login.do')>0){
                                alert("回话超时，重新登陆。");
                                location.href = 'login.html';
                            }else{
                                ConfirmService.alert(result.data);
                            }

                        }else if(result.data.flag=='Y'){
                            deferred.resolve(result.data.data);
                        }else{
                            if(result.data.data){
                                ConfirmService.alert(result.data.data);
                            }else{
                                ConfirmService.alert("返回值异常");
                            }
                        }
                    }
                }).catch(function(result){
                    if(result.status==400){
                        ConfirmService.alert("请求失败，请检查参数类型是否匹配。");
                    }else if(result.status==404){
                        ConfirmService.alert("该功能异常，请联系开发人员。");
                    }else if(result.status==500){
                        ConfirmService.alert(result.statusText);
                    }else{
                        ConfirmService.alert(result.message);
                    }
                });
                return deferred.promise;
            }
        }

    }])

    .factory('UserService', ['$http', '$q', function ($http, $q) {
        var userKey = 'User';
        window.localStorage.setItem(userKey, '');
        return {
            getUser: function () {
                var deferred = $q.defer();
                var user = window.localStorage.getItem(userKey);
                if (user) {
                    deferred.resolve(JSON.parse(user));
                } else {
				var url = "../sys/getCurrUser.do";
				$http.get(url).success(function(result){
					if(result.flag=='Y'){
						var userInfo = result.data;
						window.localStorage.setItem(userKey, JSON.stringify(userInfo));
						deferred.resolve(userInfo);
					}
				});
                }
                return deferred.promise;
            }
        }

    }])

    .factory('ConfirmService', [function () {

        return {
            confirm: function (title, content, yesFunction, noFunction) {
                layer.confirm(content, {
                    title: title,
                    btn: ['确定', '取消'] //按钮
                }, function (index) {
                    yesFunction();
                    layer.close(index);

                }, function () {
                    if (angular.isFunction(noFunction)) {
                        noFunction();
                    }
                });
            },
            alert: function(content){
                layer.open({
                    type: 0,  //0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
                    closeBtn: 2, //0 不显示关闭按钮
                    anim: 1,
                    shadeClose: true, //开启遮罩关闭
                    content: content
                });

                /*layer.open({
                    type: 1,
                    area: '20%',
                    closeBtn: 2, //0 不显示关闭按钮
                    content: '<div style="padding: 20px 20px;">'+content+'</div>',
                    shadeClose: true, //开启遮罩关闭
                    btn: '确定'
                });*/

            },
            message: function(content){
                layer.msg(content, {
                    offset:'t',
                    shift:0,
                    time:1200
                });
            },
            loading: function(time){
                if(!time){
                    time=5;
                }
                layer.open({
                    type: 3,
                    time: time*1000
                });
            }
        }
    }])

    .factory('UploadFileService', ['$http', '$q', function ($http, $q) {
        var uploadPath = '';
        var service = {
            uploadFile: function (path) {
                $("#files").val('');
                document.getElementById('Lists').innerHTML = "";
                var progress = document.getElementById('progressBar');
                progress.style.width = '0%';
                progress.innerHTML = "";

                if (window.File && window.FileList && window.FileReader && window.Blob) {
                    document.getElementById('files').addEventListener('change', service.fileSelect, false);
                    uploadPath = path;
                    $('#selectFileModal').modal('show');
                } else {
                    alert('您的浏览器不支持File Api');
                }
            },
            fileSelect: function (e) {
                e = e || window.event;
                var files = e.target.files;  //FileList Objects
                var output = [];
                for (var i = 0, f; f = files[i]; i++) {
                    var fileSize = 0;
                    if (f.size > 1024 * 1024) {
                        fileSize = (Math.round(f.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
                        output.push('<li style="font-size:14px;color: blue;"><strong>' + f.name + '</strong> ' + fileSize + '</li>');
                    } else {
                        fileSize = (Math.round(f.size * 100 / 1024) / 100).toString() + 'KB';
                        output.push('<li style="font-size:14px;color: blue;"><strong>' + f.name + '</strong> ' + fileSize + '</li>');
                    }
                }
                document.getElementById('Lists').innerHTML = '<ul>' + output.join('') + '</ul>';
            },
            startUpload: function () {
                var deferred = $q.defer();

                var fileList = document.getElementById("files").files;
                if (!(fileList && fileList.length > 0)) {
                    alert("请选择需上传的文件!");
                    return;
                }
                if (!uploadPath) {
                    alert('上传路径错误，请联系开发人员');
                    return;
                }
                //客户问题的附件为PDF格式
                if (uploadPath.indexOf('problem') >= 0) {
                    for (var i = 0; i < fileList.length; i++) {
                        var fileName = fileList[i].name;
                        var fileType = fileName.substr(fileName.indexOf(".") + 1)
                        if ('PDF,XLSX'.indexOf(fileType.toUpperCase()) < 0) {
                            alert('请上传pdf/xlsx/xls格式的文件');
                            $("#files").val('');
                            document.getElementById('Lists').innerHTML = "";
                            return;
                        }
                    }

                }
                var formData = new FormData(document.getElementById('fileUploadForm'));

                var oXHR = new XMLHttpRequest();
                oXHR.upload.addEventListener("progress", function (evt) {
                    console.log("progress");
                    console.log(evt);
                    if (evt.lengthComputable) {
                        var percentComplete = Math.round(evt.loaded * 100 / evt.total);
                        console.log(percentComplete);
                        var progress = document.getElementById('progressBar');
                        progress.style.width = percentComplete + '%';
                        progress.innerHTML = percentComplete + '%';
                    } else {
                        document.getElementById('progressNumber').innerHTML = 'unable to compute';
                    }

                }, false);

                oXHR.addEventListener('load', function (evt) {
                    console.log("load");
                    console.log(evt);
                    deferred.resolve(evt.target.response);
                }, false);

                oXHR.addEventListener('error', function (evt) {
                    console.log("error");
                    console.log(evt);
                    deferred.resolve(evt.target.response);
                }, false);

                oXHR.addEventListener('abort', function (evt) {
                    console.log("abort");
                    console.log(evt);
                    deferred.resolve(evt.target.response);
                }, false);
                oXHR.open('POST', "../file/uploadFile.do?url=" + uploadPath);
                oXHR.send(formData);
                return deferred.promise;
            },
            getFile: function (fileUrl, fileName) {

                var filePath = fileUrl + fileName;
                var url = "../file/downloadFile.do?url=" + filePath;

                var imageType = "bmp,jpg,png,tiff,gif,pcx,tga,exif,fpx,svg,psd,cdr,pcd,dxf,ufo,eps,ai,raw,wmf,txt";
                var fileType = fileName.substr(fileName.indexOf(".") + 1);
                if (imageType.indexOf(fileType.toLowerCase()) >= 0) {
                    url = url + "&isOnLine=1";
                    window.open(url);
                } else {
                    var form = $("<form>");//定义一个form表单
                    form.attr("style", "display:none");
                    form.attr("target", "");
                    form.attr("method", "post");//请求类型
                    form.attr("action", url);//请求地址
                    $("body").append(form);//将表单放置在web中
                    form.submit();//表单提交
                }
            },
            doDeleteFile: function (fileNameList, delFileName) {
                if (fileNameList && fileNameList instanceof Array) {
                    for (var i = 0; i < fileNameList.length; i++) {
                        if (delFileName == fileNameList[i]) {
                            fileNameList.splice(i, 1);
                            break;
                        }
                    }
                    var newFileName = '';
                    for (var j = 0; j < fileNameList.length; j++) {
                        newFileName = newFileName + fileNameList[j];
                        if (j + 1 < fileNameList.length) {
                            newFileName = newFileName + ",";
                        }
                    }
                    return newFileName;
                }

            }

        }

        return service;

    }])


    .run(['$rootScope', '$location', function ($rootScope, $location) {
        $rootScope.$on('$stateChangeSuccess', function (evt, current, previous) {
            $rootScope.title = current.title;
        });
    }])

;

