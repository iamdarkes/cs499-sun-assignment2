(function() {
    'use strict';

    angular
        .module('assignment2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('availability-slot', {
            parent: 'entity',
            url: '/availability-slot?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'AvailabilitySlots'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/availability-slot/availability-slots.html',
                    controller: 'AvailabilitySlotController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('availability-slot-detail', {
            parent: 'availability-slot',
            url: '/availability-slot/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'AvailabilitySlot'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/availability-slot/availability-slot-detail.html',
                    controller: 'AvailabilitySlotDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'AvailabilitySlot', function($stateParams, AvailabilitySlot) {
                    return AvailabilitySlot.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'availability-slot',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('availability-slot-detail.edit', {
            parent: 'availability-slot-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/availability-slot/availability-slot-dialog.html',
                    controller: 'AvailabilitySlotDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AvailabilitySlot', function(AvailabilitySlot) {
                            return AvailabilitySlot.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('availability-slot.new', {
            parent: 'availability-slot',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/availability-slot/availability-slot-dialog.html',
                    controller: 'AvailabilitySlotDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                description: null,
                                availabilityReason: null,
                                fromDate: null,
                                toDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('availability-slot', null, { reload: 'availability-slot' });
                }, function() {
                    $state.go('availability-slot');
                });
            }]
        })
        .state('availability-slot.edit', {
            parent: 'availability-slot',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/availability-slot/availability-slot-dialog.html',
                    controller: 'AvailabilitySlotDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AvailabilitySlot', function(AvailabilitySlot) {
                            return AvailabilitySlot.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('availability-slot', null, { reload: 'availability-slot' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('availability-slot.delete', {
            parent: 'availability-slot',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/availability-slot/availability-slot-delete-dialog.html',
                    controller: 'AvailabilitySlotDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AvailabilitySlot', function(AvailabilitySlot) {
                            return AvailabilitySlot.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('availability-slot', null, { reload: 'availability-slot' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
