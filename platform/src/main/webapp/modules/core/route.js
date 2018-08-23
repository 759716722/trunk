angular.module('app.route', [])
    .config(['$stateProvider', '$urlRouterProvider',
        function ($stateProvider, $urlRouterProvider) {

            $urlRouterProvider.otherwise("/");
            $stateProvider

                .state('dataWorkbook', {
                    title: '数据字典',
                    controller: 'dataWorkbookCtrl',
                    url: '/dataWorkbook',
                    templateUrl: 'modules/htmls/dataWorkbook.html'
                })

                .state('equParam', {
                    title: '设备信息',
                    controller: 'equParamCtrl',
                    url: '/equParam',
                    templateUrl: 'modules/htmls/equParam.html'
                })

                .state('iePerson', {
                    title: '人员信息',
                    controller: 'iePersonCtrl',
                    url: '/iePerson',
                    templateUrl: 'modules/htmls/iePerson.html'
                })

                .state('passRate', {
                    title: '直通率信息',
                    controller: 'passRateCtrl',
                    url: '/passRate',
                    templateUrl: 'modules/htmls/passRate.html'
                })

                .state('ieDifficult', {
                    title: '难度系数信息',
                    controller: 'ieDifficultCtrl',
                    url: '/ieDifficult',
                    templateUrl: 'modules/htmls/ieDifficult.html'
                })

                .state('operationDifficult', {
                    title: '难度系数定义',
                    controller: 'operationDifficultCtrl',
                    url: '/operationDifficult',
                    templateUrl: 'modules/htmls/operationDifficult.html'
                })

                .state('lineCapacity', {
                    title: '线体产能',
                    controller: 'lineCapacityCtrl',
                    url: '/lineCapacity',
                    templateUrl: 'modules/htmls/lineCapacity.html'
                })

                .state('prodtypeCapacity', {
                    title: '型号产能',
                    controller: 'prodtypeCapacityCtrl',
                    url: '/prodtypeCapacity',
                    templateUrl: 'modules/htmls/prodtypeCapacity.html'
                })


        }
    ])
;