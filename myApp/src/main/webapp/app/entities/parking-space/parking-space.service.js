(function() {
    'use strict';
    angular
        .module('assignment2App')
        .factory('ParkingSpace', ParkingSpace);

    ParkingSpace.$inject = ['$resource', 'DateUtils'];

    function ParkingSpace ($resource, DateUtils) {
        var resourceUrl =  'api/parking-spaces/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.expiration = DateUtils.convertLocalDateFromServer(data.expiration);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.expiration = DateUtils.convertLocalDateToServer(copy.expiration);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.expiration = DateUtils.convertLocalDateToServer(copy.expiration);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
