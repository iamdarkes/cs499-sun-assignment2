(function() {
    'use strict';
    angular
        .module('assignment2App')
        .factory('AvailabilitySlot', AvailabilitySlot);

    AvailabilitySlot.$inject = ['$resource', 'DateUtils'];

    function AvailabilitySlot ($resource, DateUtils) {
        var resourceUrl =  'api/availability-slots/:id';

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
