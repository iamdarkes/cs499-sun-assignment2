(function() {
    'use strict';

    angular
        .module('assignment2App')
        .controller('AvailabilitySlotDetailController', AvailabilitySlotDetailController);

    AvailabilitySlotDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'AvailabilitySlot', 'ParkingSpace'];

    function AvailabilitySlotDetailController($scope, $rootScope, $stateParams, previousState, entity, AvailabilitySlot, ParkingSpace) {
        var vm = this;

        vm.availabilitySlot = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('assignment2App:availabilitySlotUpdate', function(event, result) {
            vm.availabilitySlot = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
