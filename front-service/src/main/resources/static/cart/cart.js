angular.module('market-front').controller('cartController', function ($scope, $location, $http, $localStorage) {
    const contextPathCartService = 'http://localhost:5555/cart/api/v1';
    const contextPathOrderService = 'http://localhost:5555/core/api/v1';

    $scope.loadCart = function () {
        $http.get(contextPathCartService + '/carts/' + $localStorage.springWebCartId)
            .then(function (response) {
                $scope.cart = response.data;
            });
    };

    $scope.clearCart = function () {
        $http.get(contextPathCartService + '/carts/' + $localStorage.springWebCartId + '/clear')
            .then(function (response) {
                $scope.loadCart();
            });
    };

    $scope.checkOut = function () {
        $http.post(contextPathOrderService + '/orders', $scope.orderDto)
            .then(function successCallback(response) {
                $scope.clearCart();
                $scope.orderDto = null;
            }, function errorCallback(response) {
                var arr = response.data;
                alert(JSON.stringify(response.data));
                // $scope.loadCart();
                $scope.orderDto = null;
            });
    };

    $scope.disabledCheckOut = function () {
        alert("Для оформления заказа необходимо войти в учетную запись");
    }

    $scope.loadCart();

});