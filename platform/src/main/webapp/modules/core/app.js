angular.module('app', ['ui.router', 'app.services', 'app.directives', 'app.controllers'])

    .config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

        // $urlRouterProvider.otherwise("/user");

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

            .state('wordbook', {
                title: '字典管理',
                controller: 'wordbookCtrl',
                url: '/wordbook',
                templateUrl: 'modules/rad/htmls/wordbook.html'
            })

            .state('typeConfig', {
                title: '机型配置',
                controller: 'typeConfigCtrl',
                url: '/typeConfig',
                templateUrl: 'modules/rad/htmls/typeConfig.html'
            })

            .state('product', {
                title: '产品管理',
                controller: 'productCtrl',
                url: '/product',
                templateUrl: 'modules/rad/htmls/product.html'
            })

            .state('employee', {
                title: '员工信息',
                controller: 'employeeCtrl',
                url: '/employee',
                templateUrl: 'modules/hr/htmls/employee.html?v=00'
            })
            .state('salary', {
                title: '薪资信息',
                controller: 'salaryCtrl',
                url: '/salary',
                templateUrl: 'modules/hr/htmls/salary.html'
            })



    }])

;