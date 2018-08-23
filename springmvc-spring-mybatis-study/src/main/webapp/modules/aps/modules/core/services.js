angular.module('app.services', [])

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
//				var url = "../customer/customerAction!getCurrentUser.action";
//				$http.get(url).success(function(resultData){
//					if(resultData.result.flag=='Y'){
//						var userInfo = resultData.result.data;
//						window.localStorage.setItem(userKey, JSON.stringify(userInfo));
//						deferred.resolve(userInfo); 
//					}
//				});
                }
                return deferred.promise;
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
                    if (evt.lengthComputable) {
                        var percentComplete = Math.round(evt.loaded * 100 / evt.total);
                        var progress = document.getElementById('progressBar');
                        progress.style.width = percentComplete + '%';
                        progress.innerHTML = percentComplete + '%';
                    } else {
                        document.getElementById('progressNumber').innerHTML = 'unable to compute';
                    }

                }, false);

                oXHR.addEventListener('load', function (evt) {
                    deferred.resolve(evt.target.response);
                }, false);

                oXHR.addEventListener('error', function (evt) {
                    deferred.resolve(evt.target.response);
                }, false);

                oXHR.addEventListener('abort', function (evt) {
                    deferred.resolve(evt.target.response);
                }, false);
                oXHR.open('POST', "../file/fileAction!uploadFile.action?url=" + uploadPath);
                oXHR.send(formData);
                return deferred.promise;
            },
            getFile: function (fileUrl, fileName) {

                var filePath = fileUrl + fileName;
                var url = "../file/fileAction!downloadFile.action?url=" + filePath;

                var imageType = "bmp,jpg,png,tiff,gif,pcx,tga,exif,fpx,svg,psd,cdr,pcd,dxf,ufo,eps,ai,raw,wmf";
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

