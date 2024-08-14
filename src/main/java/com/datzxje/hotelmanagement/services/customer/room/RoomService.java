package com.datzxje.hotelmanagement.services.customer.room;

import com.datzxje.hotelmanagement.dto.RoomsResponseDto;

public interface RoomService {

    RoomsResponseDto getAvailableRooms(int pageNumber);
}
