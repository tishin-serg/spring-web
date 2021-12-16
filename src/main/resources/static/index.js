angular.module('app', []).controller('indexController', function ($scope, $http) {

    const contextPath = 'http://localhost:8189/app/api/v1';

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

    $scope.loadCart = function () {
        $http.get(contextPath + '/products' + '/cart')
            .then(function (response) {
                $scope.CartList = response.data;
                console.log($scope.CartList);
            });
    };

    $scope.addToCart = function (productId) {
        $http.get(contextPath + '/products' + '/cart/' + productId)
            .then(function (response) {
                $scope.loadCart();
            });
    };

    $scope.removeFromCart = function (productId) {
        $http.delete(contextPath + '/products' + '/cart/' + productId)
            .then(function (response) {
                $scope.loadCart();
            });
    };

    $scope.loadProducts();
    $scope.loadCart();
});