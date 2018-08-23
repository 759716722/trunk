angular.module('app.services', [])

    .factory('HttpService', ['$http','$q',function ($http,$q) {
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
                                location.href = '../login.html';
                            }else{
                                alert(result.data);
                            }

                        }else if(result.data.flag=='Y'){
                            deferred.resolve(result.data.data);
                        }else{
                            if(result.data.data){
                                alert(result.data.data);
                            }else{
                                alert("返回值异常");
                            }
                        }
                    }
                }).catch(function(result){
                    if(result.status==400){
                        alert("请求失败，请检查参数类型是否匹配。");
                    }else if(result.status==404){
                        alert("该功能异常，请联系开发人员。");
                    }else if(result.status==500){
                        alert(result.statusText);
                    }else{
                        alert(result.message);
                    }
                });
                return deferred.promise;
            },
            post: function (url,data) {
                var deferred = $q.defer();
                $http({
                    method:'POST',
                    url:url,
                    data:JSON.stringify(data)
                }).then(function(result){
                    if(result.status==200 && result.statusText=='OK'){
                        if(!result.data.flag){
                            if(result.data.indexOf('登陆')>0 && result.data.indexOf('sys/user/login.do')>0){
                                alert("回话超时，重新登陆。");
                                location.href = '../login.html';
                            }else{
                                alert(result.data);
                            }

                        }else if(result.data.flag=='Y'){
                            deferred.resolve(result.data.data);
                        }else{
                            if(result.data.data){
                                alert(result.data.data);
                            }else{
                                alert("返回值异常");
                            }
                        }
                    }
                }).catch(function(result){
                    if(result.status==400){
                        alert("请求失败，请检查参数类型是否匹配。");
                    }else if(result.status==404){
                        alert("该功能异常，请联系开发人员。");
                    }else if(result.status==500){
                        alert(result.statusText);
                    }else{
                        alert(result.message);
                    }
                });
                return deferred.promise;
            }
        }

    }])

;

