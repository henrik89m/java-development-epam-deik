package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.dto.ScreeningDto;
import com.epam.training.ticketservice.service.ScreeningService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ScreeningCommand extends PriviligeCommand{

    private final ScreeningService screeningService;

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "create screening", value = "Usage: <movie> <room> <start>")
    public String createScreening(String movie, String roomName, String startTimeStr) {
        try {
            LocalDateTime startTime = LocalDateTime.parse(startTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            var res = screeningService.createScreening(movie, roomName, startTime);
            return "Screening created successfully";
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }
    }
    @ShellMethod(key = "delete screening", value = "Usage: <movie> <roomName> <startTime>")
    @ShellMethodAvailability("isAdmin")
    public String deleteScreening(String movie, String roomName, String startTimeStr){
        try{
            LocalDateTime startTime = LocalDateTime.parse(startTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            var res = screeningService.deleteScreening(movie, roomName, startTime);
            return "Screening deleted successfully";
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e);
        }

    }

    @ShellMethod(key = "list screenings", value = "List of all screenings")
    public void listScreening() {
        try {
            List<ScreeningDto> screenings = screeningService.listScreening();
            if (screenings.isEmpty()){
                System.out.println("There are no screenings at the moment");
            }else{
                for (ScreeningDto screening : screenings) {
                    System.out.println("Movie: " + screening.getMovie() +
                            ", Room Name: " + screening.getRoomName() +
                            ", Start Time: " + screening.getStartTime());
                }
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
