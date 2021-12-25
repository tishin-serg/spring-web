angular.module('app', ['ngStorage']).controller('indexController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/app/api/v1';
    $scope.isVisibleRegistrationForm = false;

    if ($localStorage.springWebUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
    }

    $scope.loadProducts = function () {
        $http({
            url: contextPath + '/products',
            method: 'GET',
            params: {
                tittle_part: $scope.filter ? $scope.filter.tittle_part : null,
                min_cost: $scope.filter ? $scope.filter.min_cost : null,
                max_cost: $scope.filter ? $scope.filter.max_cost : null
            }
        }).then(function (response) {
            $scope.ProductsPage = response.data;
        });
    };

    $scope.deleteProduct = function (productId) {
        $http.delete(contextPath + '/products/' + productId)
            .then(function (response) {
                $scope.loadProducts();
            });
    };

    $scope.tryToAuth = function () {
        $http.post(contextPath + '/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.springWebUser = { username: $scope.user.username, token: response.data.token };
                    $scope.user.username = null;
                    $scope.user.password = null;
                }
            }, function errorCallback(response) {
            });
    };

    $scope.register = function () {
        $http.post(contextPath + '/registration', $scope.userNew)
            .then(function successCallback(response) {
                alert(response.data.username + ' was created');
                // $scope.user2 = { username: $scope.userNew.username, password: $scope.userNew.password, };
                // console.log($scope.user2);
                // setTimeout(
                //     () => {
                //         $scope.tryToAuthAfterRegistration();
                //     },
                //     4 * 1000
                // );
            }, function errorCallback(response) {
                alert(response.data.message);
            });
    };

    // $scope.tryToAuthAfterRegistration = function () {
    //     $http.post(contextPath + '/auth', $scope.user2)
    //         .then(function successCallback(response) {
    //             console.log($scope.user2);
    //             alert(response.data);
    //         }, function errorCallback(response) {
    //             alert(response.data);
    //         });
    // };

    // $scope.tryToAuthAfterRegistration = function () {
    //     $http({
    //         url: contextPath + '/auth',
    //         method: 'POST',
    //         params: {
    //             username: $scope.userNew.username,
    //             password: $scope.userNew.password
    //         }
    //     }).then(function (response) {
    //         alert(response.data);
    //     });
    // };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        if ($scope.user.username) {
            $scope.user.username = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }
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

    $scope.showCurrentUserInfo = function () {
        $http.get('http://localhost:8189/app/api/v1/profile')
            .then(function successCallback(response) {
                alert('MY NAME IS: ' + response.data.username);
            }, function errorCallback(response) {
                alert('UNAUTHORIZED');
            });
    };

    $scope.showRegistrationForm = function () {
        console.log($scope.isVisibleRegistrationForm);
        if (!$scope.isVisibleRegistrationForm) {
            $scope.isVisibleRegistrationForm = true;
        } else {
            $scope.isVisibleRegistrationForm = false;
        }
    };

    //    $scope.loadCart = function () {
    //        $http.get(contextPath + '/products' + '/cart')
    //            .then(function (response) {
    //                $scope.CartList = response.data;
    //                console.log($scope.CartList);
    //            });
    //    };
    //
    //    $scope.addToCart = function (productId) {
    //        $http.get(contextPath + '/products' + '/cart/' + productId)
    //            .then(function (response) {
    //                $scope.loadCart();
    //            });
    //    };
    //
    //    $scope.removeFromCart = function (productId) {
    //        $http.delete(contextPath + '/products' + '/cart/' + productId)
    //            .then(function (response) {
    //                $scope.loadCart();
    //            });
    //    };

    $scope.loadProducts();
    //    $scope.loadCart();
});