'use strict';

describe('Controller Tests', function() {

    describe('AvailabilitySlot Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAvailabilitySlot, MockParkingSpace;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAvailabilitySlot = jasmine.createSpy('MockAvailabilitySlot');
            MockParkingSpace = jasmine.createSpy('MockParkingSpace');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'AvailabilitySlot': MockAvailabilitySlot,
                'ParkingSpace': MockParkingSpace
            };
            createController = function() {
                $injector.get('$controller')("AvailabilitySlotDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'assignment2App:availabilitySlotUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
