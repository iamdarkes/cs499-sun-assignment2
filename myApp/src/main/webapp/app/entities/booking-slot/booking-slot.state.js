(function() {
    'use strict';

    angular
        .module('assignment2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('booking-slot', {
            parent: 'entity',
            url: '/booking-slot?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'BookingSlots'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/booking-slot/booking-slots.html',
                    controller: 'BookingSlotController',
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
        .state('booking-slot-detail', {
            parent: 'booking-slot',
            url: '/booking-slot/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'BookingSlot'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/booking-slot/booking-slot-detail.html',
                    controller: 'BookingSlotDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'BookingSlot', function($stateParams, BookingSlot) {
                    return BookingSlot.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'booking-slot',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('booking-slot-detail.edit', {
            parent: 'booking-slot-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/booking-slot/booking-slot-dialog.html',
                    controller: 'BookingSlotDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BookingSlot', function(BookingSlot) {
                            return BookingSlot.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('booking-slot.new', {
            parent: 'booking-slot',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/booking-slot/booking-slot-dialog.html',
                    controller: 'BookingSlotDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                description: null,
                                fromDate: null,
                                toDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('booking-slot', null, { reload: 'booking-slot' });
                }, function() {
                    $state.go('booking-slot');
                });
            }]
        })
        .state('booking-slot.edit', {
            parent: 'booking-slot',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/booking-slot/booking-slot-dialog.html',
                    controller: 'BookingSlotDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BookingSlot', function(BookingSlot) {
                            return BookingSlot.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('booking-slot', null, { reload: 'booking-slot' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('booking-slot.delete', {
            parent: 'booking-slot',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/booking-slot/booking-slot-delete-dialog.html',
                    controller: 'BookingSlotDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BookingSlot', function(BookingSlot) {
                            return BookingSlot.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('booking-slot', null, { reload: 'booking-slot' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
