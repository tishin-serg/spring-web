angular.module('market-front').controller('storeController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/app/api/v1';

    $scope.loadProducts = function () {
        $http({
            url: contextPath + '/products',
            method: 'GET',
            params: {
                category: $scope.filter ? $scope.filter.category : null,
                tittle_part: $scope.filter ? $scope.filter.tittle_part : null,
                min_cost: $scope.filter ? $scope.filter.min_cost : null,
                max_cost: $scope.filter ? $scope.filter.max_cost : null
            }
        }).then(function (response) {
            $scope.ProductsPage = response.data;
        });
    };

    $scope.loadProductsByCategories = function (category) {
        $http({
            url: contextPath + '/products/category?category=' + category,
            method: 'GET'
        }).then(function (response) {
            $scope.ProductsPage = response.data;
        });
    };

    $scope.addToCart = function (productId) {
        $http.get(contextPath + '/carts/' + $localStorage.springWebCartId + '/add/' + productId)
            .then(function (response) {

            });
    };

    $scope.loadCategories = function () {
        $http.get(contextPath + '/categories')
            .then(function (response) {
                $scope.CategoryList = response.data;
            });
    }

    // $scope.deleteProduct = function (productId) {
    //     $http.delete(contextPath + '/products/' + productId)
    //         .then(function (response) {
    //             $scope.loadProducts();
    //         });
    // };

    $scope.loadProducts();
    $scope.loadCategories();
});