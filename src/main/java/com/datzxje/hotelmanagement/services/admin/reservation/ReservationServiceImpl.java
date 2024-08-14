package com.datzxje.hotelmanagement.services.admin.reservation;

import com.datzxje.hotelmanagement.dto.ReservationResponseDto;
import com.datzxje.hotelmanagement.entity.Reservation;
import com.datzxje.hotelmanagement.entity.Room;
import com.datzxje.hotelmanagement.enums.ReservationStatus;
import com.datzxje.hotelmanagement.repository.ReservationRepository;
import com.datzxje.hotelmanagement.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    private final RoomRepository roomRepository;

    public static final int SEARCH_RESULT_PER_PAGE = 4;

    public ReservationResponseDto getAllReservations(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE);

        Page<Reservation> reservationPage = reservationRepository.findAll(pageable);

        ReservationResponseDto reservationResponseDto = new ReservationResponseDto();

        reservationResponseDto.setReservationDtoList(reservationPage.stream().map(Reservation::getReservationDto)
                .collect(Collectors.toList()));

        reservationResponseDto.setPageNumber(reservationPage.getPageable().getPageNumber());
        reservationResponseDto.setTotalPages(reservationResponseDto.getTotalPages());

        return reservationResponseDto;
    }

    public boolean changeReservationStatus(Long id, String status) {
        Optional<Reservation> optional = reservationRepository.findById(id);
        if (optional.isPresent()) {
            Reservation existedReservation = optional.get();

            if(Objects.equals(status, "Approve")) {
                existedReservation.setReservationStatus(ReservationStatus.APPROVED);
            } else {
                existedReservation.setReservationStatus(ReservationStatus.REJECTED);
            }

            reservationRepository.save(existedReservation);

            Room existingRoom = existedReservation.getRoom();
            existingRoom.setAvailable(false);

            roomRepository.save(existingRoom);

            return true;
        }
        return false;
    }
}
