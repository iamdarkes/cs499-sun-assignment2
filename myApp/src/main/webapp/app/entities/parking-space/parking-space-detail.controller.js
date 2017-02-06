(function() {
    'use strict';

    angular
        .module('assignment2App')
        .controller('ParkingSpaceDetailController', ParkingSpaceDetailController);

    ParkingSpaceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ParkingSpace', 'AvailabilitySlot', 'BookingSlot'];

    function ParkingSpaceDetailController($scope, $rootScope, $stateParams, previousState, entity, ParkingSpace, AvailabilitySlot, BookingSlot) {
        var vm = this;

        vm.parkingSpace = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('assignment2App:parkingSpaceUpdate', function(event, result) {
            vm.parkingSpace = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
