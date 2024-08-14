package com.datzxje.hotelmanagement.services.admin.reservation;

import com.datzxje.hotelmanagement.dto.ReservationResponseDto;

public interface ReservationService {

    ReservationResponseDto getAllReservations(int pageNumber);
}
