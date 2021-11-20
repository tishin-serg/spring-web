/*  запускаем приложение angular, которым будет управлять indexController,
подключаем в него модули $scope (аналог моделей в джаве) и $http, чтобы работать с запросами.
*/
angular.module('app', []).controller('indexController', function ($scope, $http) {
    // Прописываем путь до бэкенда нашего приложения
    const contextPath = 'http://localhost:8189/app';

    // Настраиваем функцию по загрузке товаров
    // Посылаем запрос по адресу
    // Когда дождёмся ответ, то кладём ответ в $scope.ProductsList
    $scope.loadProducts = function () {
        $http.get(contextPath + '/catalog')
            .then(function (response) {
                $scope.ProductsList = response.data;
            });
    };

/*
    $scope.deleteProduct = function (productId) {
            $http.get(contextPath + '/catalog/delete_product/' + productId)
                .then(function (response) {
                    $scope.loadProducts();
                });
        } */

    $scope.deleteProduct = function (productId) {
                    $http({
                         url: contextPath + '/catalog/delete_product',
                         method: 'GET',
                         params: {
                             productId: productId
                             }
                       }).then(function (response) {
                            $scope.loadProducts();
                        });
                }

    $scope.changeCost = function (productId, delta) {
                    $http({
                         url: contextPath + '/catalog/change_cost',
                         method: 'GET',
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