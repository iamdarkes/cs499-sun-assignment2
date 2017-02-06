(function() {
    'use strict';

    angular
        .module('assignment2App')
        .controller('BookingSlotDetailController', BookingSlotDetailController);

    BookingSlotDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'BookingSlot', 'ParkingSpace'];

    function BookingSlotDetailController($scope, $rootScope, $stateParams, previousState, entity, BookingSlot, ParkingSpace) {
        var vm = this;

        vm.bookingSlot = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('assignment2App:bookingSlotUpdate', function(event, result) {
            vm.bookingSlot = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
