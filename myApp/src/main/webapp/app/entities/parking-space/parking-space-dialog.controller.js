(function() {
    'use strict';

    angular
        .module('assignment2App')
        .controller('ParkingSpaceDialogController', ParkingSpaceDialogController);

    ParkingSpaceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ParkingSpace', 'AvailabilitySlot', 'BookingSlot'];

    function ParkingSpaceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ParkingSpace, AvailabilitySlot, BookingSlot) {
        var vm = this;

        vm.parkingSpace = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.availabilityslots = AvailabilitySlot.query();
        vm.bookingslots = BookingSlot.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.parkingSpace.id !== null) {
                ParkingSpace.update(vm.parkingSpace, onSaveSuccess, onSaveError);
            } else {
                ParkingSpace.save(vm.parkingSpace, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('assignment2App:parkingSpaceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.expiration = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
