(function() {
    'use strict';

    angular
        .module('assignment2App')
        .controller('AvailabilitySlotDialogController', AvailabilitySlotDialogController);

    AvailabilitySlotDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'AvailabilitySlot', 'ParkingSpace'];

    function AvailabilitySlotDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, AvailabilitySlot, ParkingSpace) {
        var vm = this;

        vm.availabilitySlot = entity;
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
            if (vm.availabilitySlot.id !== null) {
                AvailabilitySlot.update(vm.availabilitySlot, onSaveSuccess, onSaveError);
            } else {
                AvailabilitySlot.save(vm.availabilitySlot, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('assignment2App:availabilitySlotUpdate', result);
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
