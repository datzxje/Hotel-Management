package com.datzxje.hotelmanagement.services.customer.booking;

import com.datzxje.hotelmanagement.dto.ReservationDto;
import com.datzxje.hotelmanagement.dto.ReservationResponseDto;
import com.datzxje.hotelmanagement.entity.Reservation;
import com.datzxje.hotelmanagement.entity.Room;
import com.datzxje.hotelmanagement.entity.User;
import com.datzxje.hotelmanagement.enums.ReservationStatus;
import com.datzxje.hotelmanagement.repository.ReservationRepository;
import com.datzxje.hotelmanagement.repository.RoomRepository;
import com.datzxje.hotelmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final UserRepository userRepository;

    private final RoomRepository roomRepository;

    private final ReservationRepository reservationRepository;

    public static final int SEARCH_RESULT_PER_PAGE = 4;

    public boolean postReservation(ReservationDto reservationDto) {
        Optional<User> user = userRepository.findById(reservationDto.getUserId());
        Optional<Room> room = roomRepository.findById(reservationDto.getRoomId());

        if(room.isPresent() && user.isPresent()) {
            Reservation reservation = new Reservation();

            reservation.setUser(user.get());
            reservation.setRoom(room.get());
            reservation.setCheckInDate(reservationDto.getCheckInDate());
            reservation.setCheckOutDate(reservationDto.getCheckOutDate());
            reservation.setReservationStatus(ReservationStatus.PENDING);

            Long days = ChronoUnit.DAYS.between(reservation.getCheckInDate(), reservation.getCheckOutDate());
            reservation.setPrice(days * room.get().getPrice());

            reservationRepository.save(reservation);
            return true;
        }
        return false;
    }

    public ReservationResponseDto getAllReservationByUserId(Long userId, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE);

        Page<Reservation> reservationPage = reservationRepository.findAllByUserId(pageable, userId);

        ReservationResponseDto reservationResponseDto = new ReservationResponseDto();

        reservationResponseDto.setReservationDtoList(reservationPage.stream().map(Reservation::getReservationDto)
                .collect(Collectors.toList()));

        reservationResponseDto.setPageNumber(reservationPage.getPageable().getPageNumber());
        reservationResponseDto.setTotalPages(reservationResponseDto.getTotalPages());

        return reservationResponseDto;
    }
}
