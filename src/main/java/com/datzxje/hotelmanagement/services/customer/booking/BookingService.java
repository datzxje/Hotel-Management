package com.datzxje.hotelmanagement.services.customer.booking;

import com.datzxje.hotelmanagement.dto.ReservationDto;

public interface BookingService {
    boolean postReservation(ReservationDto reservationDto);
}
