(function() {
    'use strict';

    angular
        .module('assignment2App')
        .controller('BookingSlotDeleteController',BookingSlotDeleteController);

    BookingSlotDeleteController.$inject = ['$uibModalInstance', 'entity', 'BookingSlot'];

    function BookingSlotDeleteController($uibModalInstance, entity, BookingSlot) {
        var vm = this;

        vm.bookingSlot = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BookingSlot.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
