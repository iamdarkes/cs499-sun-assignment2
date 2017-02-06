(function() {
    'use strict';

    angular
        .module('assignment2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('parking-space', {
            parent: 'entity',
            url: '/parking-space?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ParkingSpaces'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/parking-space/parking-spaces.html',
                    controller: 'ParkingSpaceController',
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
        .state('parking-space-detail', {
            parent: 'parking-space',
            url: '/parking-space/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ParkingSpace'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/parking-space/parking-space-detail.html',
                    controller: 'ParkingSpaceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ParkingSpace', function($stateParams, ParkingSpace) {
                    return ParkingSpace.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'parking-space',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('parking-space-detail.edit', {
            parent: 'parking-space-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/parking-space/parking-space-dialog.html',
                    controller: 'ParkingSpaceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ParkingSpace', function(ParkingSpace) {
                            return ParkingSpace.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('parking-space.new', {
            parent: 'parking-space',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/parking-space/parking-space-dialog.html',
                    controller: 'ParkingSpaceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                expiration: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('parking-space', null, { reload: 'parking-space' });
                }, function() {
                    $state.go('parking-space');
                });
            }]
        })
        .state('parking-space.edit', {
            parent: 'parking-space',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/parking-space/parking-space-dialog.html',
                    controller: 'ParkingSpaceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ParkingSpace', function(ParkingSpace) {
                            return ParkingSpace.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('parking-space', null, { reload: 'parking-space' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('parking-space.delete', {
            parent: 'parking-space',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/parking-space/parking-space-delete-dialog.html',
                    controller: 'ParkingSpaceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ParkingSpace', function(ParkingSpace) {
                            return ParkingSpace.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('parking-space', null, { reload: 'parking-space' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
