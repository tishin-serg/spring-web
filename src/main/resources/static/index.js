(function () {
    var app = angular
        .module('market-front', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'welcome/welcome.html',
                controller: 'welcomeController'
            })
            .when('/store', {
                templateUrl: 'store/store.html',
                controller: 'storeController'
            })
            .when('/cart', {
                templateUrl: 'cart/cart.html',
                controller: 'cartController'
            })
            .when('/order', {
                templateUrl: 'order/order.html',
                controller: 'orderController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }

    function run($rootScope, $http, $localStorage) {
        if ($localStorage.springWebUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
        }
    }
})();

angular.module('market-front').controller('indexController', function ($scope, $rootScope, $http, $localStorage, $location) {
    const contextPath = 'http://localhost:8189/app/api/v1';
    $scope.isVisibleRegistrationForm = false;

    $scope.tryToAuth = function () {
        $http.post(contextPath + '/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.springWebUser = { username: $scope.user.username, token: response.data.token };
                    $scope.user.username = null;
                    $scope.user.password = null;
                    $location.path('/');
                }
            }, function errorCallback(response) {
            });
    };

    // $scope.register = function () {
    //     $http.post(contextPath + '/registration', $scope.userNew)
    //         .then(function successCallback(response) {
    //             setTimeout(
    //                 () => {
    //                     $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
    //                     $localStorage.springWebUser = { username: $scope.userNew.username, token: response.data.token };
    //                     $scope.userNew.username = null;
    //                     $scope.userNew.password = null;
    //                 },
    //                 4 * 1000
    //             );
    //         }, function errorCallback(response) {
    //             var arr = response.data;
    //             alert(JSON.stringify(response.data));

    //         });
    // };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        $scope.user = null;
        $location.path('/');
    };

    $scope.clearUser = function () {
        delete $localStorage.springWebUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $rootScope.isUserLoggedIn = function () {
        if ($localStorage.springWebUser) {
            return true;
        } else {
            return false;
        }
    };

    // $scope.showRegistrationForm = function () {
    //     console.log($scope.isVisibleRegistrationForm);
    //     if (!$scope.isVisibleRegistrationForm) {
    //         $scope.isVisibleRegistrationForm = true;
    //     } else {
    //         $scope.isVisibleRegistrationForm = false;
    //     }
    // };


    //
    //    $scope.removeFromCart = function (productId) {
    //        $http.delete(contextPath + '/products' + '/cart/' + productId)
    //            .then(function (response) {
    //                $scope.loadCart();
    //            });
    //    };
});