package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.dto.ScreeningDto;
import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    Optional<Screening> findByMovieTitleAndRoomNameAndStartTime(String title, String roomName, LocalDateTime startTime);

    boolean existsByMovieTitleAndRoomNameAndStartTime(String title, String roomName, LocalDateTime startTime);

    List<Screening> findAllByRoomName(String roomName);

    @Transactional
    Optional<Screening> deleteScreeningByMovieAndRoomNameAndStartTime(String movie, String roomName, LocalDateTime startTime);
}
