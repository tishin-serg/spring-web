(function () {
    angular
        .module('market-front', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    const contextPath = 'http://localhost:5555/core/api/v1';
    const contextPathCartService = 'http://localhost:5555/cart/api/v1';

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
                    try {
                        let jwt = $localStorage.springWebUser.token;
                        let payload = JSON.parse(atob(jwt.split('.')[1]));
                        let currentTime = parseInt(new Date().getTime() / 1000);
                        if (currentTime > payload.exp) {
                            console.log("Token is expired!!!");
                            delete $localStorage.springWebUser;
                            $http.defaults.headers.common.Authorization = '';
                        }
                    } catch (e) {
                    }

                    if ($localStorage.springWebUser) {
                        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
                    }
                }
        if (!$localStorage.springWebCartId) {
            $http.get(contextPathCartService + '/carts/generate')
                .then(function successCallback(response) {
                    $localStorage.springWebCartId = response.data.value;
                });
        };
    };
})();

angular.module('market-front').controller('indexController', function ($scope, $rootScope, $http, $localStorage, $location) {
    const contextPath = 'http://localhost:5555/auth/api/v1';
    const contextPathCartService = 'http://localhost:5555/cart/api/v1';
    $scope.isVisibleRegistrationForm = false;

    $scope.tryToAuth = function () {
        $http.post(contextPath + '/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.springWebUser = { username: $scope.user.username, token: response.data.token };
                    $scope.user.username = null;
                    $scope.user.password = null;

                    $http.get(contextPathCartService + '/carts/' + $localStorage.springWebCartId + '/merge')
                        .then(function successCallback(response) {

                        });

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