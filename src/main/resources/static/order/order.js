angular.module('market-front').controller('orderController', function ($scope, $location, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/app/api/v1';

    $scope.loadOrders = function () {
            $http.get(contextPath + '/orders')
                .then(function (response) {
                    $scope.Orders = response.data;
                });
        };

    $scope.loadOrders();
});