(function() {
    'use strict';
    angular
        .module('assignment2App')
        .factory('BookingSlot', BookingSlot);

    BookingSlot.$inject = ['$resource', 'DateUtils'];

    function BookingSlot ($resource, DateUtils) {
        var resourceUrl =  'api/booking-slots/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fromDate = DateUtils.convertDateTimeFromServer(data.fromDate);
                        data.toDate = DateUtils.convertDateTimeFromServer(data.toDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
