# cs499-sun-assignment2
Jhipster Application

Entity Relationships

There are three entities 
  - Parking Space
  - Availability Slot
  - Booking Slot

The fields of Parking Space are:
  - name, a String
  - description, a String
  - expiration, a LocalDate
  
The fields of Availability Slot are:
  - description, a String
  - availability reason, an enum
  - start date, ZonedDateTime
  - end date, ZonedDateTime
 
The fields of Booking Slot are:
  - description, a String
  - start date, ZonedDateTime
  - end date, ZonedDateTime
  
The relationships are:
  - oneToMany for Parking Spaces to Booking Slots
  - oneToMany for Parking Spaces to Availability Slots

The application has parking spaces and takes reservations for the available and books the space.
It also checks sets the available parking spaces among the same group.
