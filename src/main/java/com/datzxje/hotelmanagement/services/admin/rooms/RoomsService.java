package com.datzxje.hotelmanagement.services.admin.rooms;

import com.datzxje.hotelmanagement.dto.RoomDto;
import com.datzxje.hotelmanagement.dto.RoomsResponseDto;

public interface RoomsService {

    boolean postRoom(RoomDto roomDto);

    RoomsResponseDto getAllRooms(int pageNumber);

    RoomDto getRoomById(Long roomId);

    boolean updateRoom(Long id, RoomDto roomDto);

    void deleteRoom(Long id);
}
