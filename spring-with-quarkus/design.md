# venue
- id
- name

# hall
- id
- name
- venue_id
- seatCapacity
- seatType
- seatNumber
- isActivated

# ticket
- id
- hall_name
- seatNumber
- seatType
- code


# schedule
- id
- name
- startDate
- endDate
- Organizer_id

# Rental
- id
- name
- schedule_id
- Organizer_id
- hall_id

# Reservation
- id
- name
- hall_id
- ticket_id
- schedule_id


