(function() {
    'use strict';

    angular
        .module('assignment2App')
        .controller('AvailabilitySlotDeleteController',AvailabilitySlotDeleteController);

    AvailabilitySlotDeleteController.$inject = ['$uibModalInstance', 'entity', 'AvailabilitySlot'];

    function AvailabilitySlotDeleteController($uibModalInstance, entity, AvailabilitySlot) {
        var vm = this;

        vm.availabilitySlot = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AvailabilitySlot.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
