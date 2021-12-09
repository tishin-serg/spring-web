angular.module('app', []).controller('indexController', function ($scope, $http) {

    const contextPath = 'http://localhost:8189/app/api/v1';

    $scope.loadProducts = function () {
        $http({
            url: contextPath + '/catalog',
            method: 'GET',
            params: {
                tittle_part: $scope.filter ? $scope.filter.tittle_part : null,
                min_cost: $scope.filter ? $scope.filter.min_cost : null,
                max_cost: $scope.filter ? $scope.filter.max_cost : null
            }
        }).then(function (response) {
            $scope.ProductsList = response.data.content;
        });
    };

    $scope.deleteProduct = function (productId) {
            $http.delete(contextPath + '/catalog/' + productId)
                .then(function (response) {
                    $scope.loadProducts();
                });
        };

    $scope.changeCost = function (productId, delta) {
        $http({
            url: contextPath + '/catalog/cost',
            method: 'PATCH',
            params: {
                productId: productId,
                delta: delta
            }
        }).then(function (response) {
            $scope.loadProducts();
        });
    }

    $scope.loadProducts();
});