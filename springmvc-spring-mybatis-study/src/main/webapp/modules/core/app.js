angular.module('app', ['ui.router', 'app.services', 'app.directives', 'app.controllers'])

    .config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

        $urlRouterProvider.otherwise("/user");

        $stateProvider

            .state('myAccount', {
                title: '个人资料',
                controller: 'sysCtrl',
                url: '/myAccount',
                templateUrl: 'modules/sys/htmls/myAccount.html'
            })
            .state('modifyPsw', {
                title: 'modifyPsw',
                controller: 'modifyPswCtrl',
                url: '/modifyPsw',
                templateUrl: 'modules/sys/htmls/modifyPsw.html'
            })
            .state('user', {
                title: '用户管理',
                controller: 'userCtrl',
                url: '/user',
                templateUrl: 'modules/sys/htmls/user.html'
            })
            .state('role', {
                title: '角色管理',
                controller: 'roleCtrl',
                url: '/role',
                templateUrl: 'modules/sys/htmls/role.html'
            })
            .state('dept', {
                title: '角色管理',
                controller: 'deptCtrl',
                url: '/dept',
                templateUrl: 'modules/sys/htmls/dept.html'
            })
            .state('menu', {
                title: '菜单管理',
                controller: 'menuCtrl',
                url: '/menu',
                templateUrl: 'modules/sys/htmls/menu.html'
            })
            .state('permission', {
                title: '权限管理',
                controller: 'permissionCtrl',
                url: '/permission/:roleId',
                templateUrl: 'modules/sys/htmls/permission.html'
            })



    }])

;