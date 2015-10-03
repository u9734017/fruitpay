'use strict';
angular.module('app',[
	'ui.router',
	'checkout',
	'login'
]); 
 
appRouter.$inject = ['$stateProvider','$urlRouterProvider'];
angular.module('app')
	.config(appRouter);
 
function appRouter($stateProvider, $urlRouterProvider){
      $urlRouterProvider.otherwise("/index")

      $stateProvider
        .state('index', {
            url: "/index",
            templateUrl: 'layout/shell.html'
        })
		.state('index.checkout', {
            url: "/checkout",
            templateUrl: 'checkout/checkout.html',
            controller:'checkoutController'
        })
		.state('index.login', {
            url: "/login",
            templateUrl: 'login/login.html',
            controller:'loginController'
        })

}