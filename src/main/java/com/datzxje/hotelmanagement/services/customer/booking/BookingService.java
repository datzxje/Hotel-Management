package com.datzxje.hotelmanagement.services.customer.booking;

import com.datzxje.hotelmanagement.dto.ReservationDto;
import com.datzxje.hotelmanagement.dto.ReservationResponseDto;

public interface BookingService {

    boolean postReservation(ReservationDto reservationDto);

    ReservationResponseDto getAllReservationByUserId(Long userId, int pageNumber);
}
