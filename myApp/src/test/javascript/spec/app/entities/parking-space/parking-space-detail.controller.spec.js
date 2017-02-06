'use strict';

describe('Controller Tests', function() {

    describe('ParkingSpace Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockParkingSpace, MockAvailabilitySlot, MockBookingSlot;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockParkingSpace = jasmine.createSpy('MockParkingSpace');
            MockAvailabilitySlot = jasmine.createSpy('MockAvailabilitySlot');
            MockBookingSlot = jasmine.createSpy('MockBookingSlot');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ParkingSpace': MockParkingSpace,
                'AvailabilitySlot': MockAvailabilitySlot,
                'BookingSlot': MockBookingSlot
            };
            createController = function() {
                $injector.get('$controller')("ParkingSpaceDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'assignment2App:parkingSpaceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
