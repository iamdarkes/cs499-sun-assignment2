(function() {
    'use strict';

    angular
        .module('assignment2App')
        .controller('BookingSlotDialogController', BookingSlotDialogController);

    BookingSlotDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'BookingSlot', 'ParkingSpace'];

    function BookingSlotDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, BookingSlot, ParkingSpace) {
        var vm = this;

        vm.bookingSlot = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.parkingspaces = ParkingSpace.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.bookingSlot.id !== null) {
                BookingSlot.update(vm.bookingSlot, onSaveSuccess, onSaveError);
            } else {
                BookingSlot.save(vm.bookingSlot, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('assignment2App:bookingSlotUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fromDate = false;
        vm.datePickerOpenStatus.toDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
