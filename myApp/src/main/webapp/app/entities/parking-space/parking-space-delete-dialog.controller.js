(function() {
    'use strict';

    angular
        .module('assignment2App')
        .controller('ParkingSpaceDeleteController',ParkingSpaceDeleteController);

    ParkingSpaceDeleteController.$inject = ['$uibModalInstance', 'entity', 'ParkingSpace'];

    function ParkingSpaceDeleteController($uibModalInstance, entity, ParkingSpace) {
        var vm = this;

        vm.parkingSpace = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ParkingSpace.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
