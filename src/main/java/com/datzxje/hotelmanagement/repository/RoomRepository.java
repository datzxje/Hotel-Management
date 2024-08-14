package com.datzxje.hotelmanagement.repository;

import com.datzxje.hotelmanagement.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Page<Room> findByAvailable(boolean available, Pageable pageable);
}
