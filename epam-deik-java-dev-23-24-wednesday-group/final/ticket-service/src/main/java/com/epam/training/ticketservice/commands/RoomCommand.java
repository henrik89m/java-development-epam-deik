package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.dto.RoomDto;
import com.epam.training.ticketservice.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class RoomCommand extends PriviligeCommand {

    private final RoomService roomService;

    @ShellMethod(key = "create room", value = "Usage: <room> <number of rows> <number of cols>")
    @ShellMethodAvailability("isAdmin")
    public String createRoom(String roomName, int rows, int cols) {
        try {
            var res = roomService.createRoom(roomName, rows, cols);
            return "Room created successfully";

        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    @ShellMethod(key = "update room", value = "Usage: <room> <number of rows> <number of cols>")
    @ShellMethodAvailability("isAdmin")
    public String updateRoom(String roomName, int rows, int cols) {
        try {
            var res = roomService.updateRoom(roomName, rows, cols);
            return "Room updated successfully";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    @ShellMethod(key = "delete room", value = "Usage: <room>")
    @ShellMethodAvailability("isAdmin")
    public String deleteRoom(String roomName) {
        try {
            var res = roomService.deleteRoom(roomName);
            return "Room deleted successfully";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }
    @ShellMethod(key = "list rooms", value = "List of all rooms")
    public void listRooms() {
        try {
            List<RoomDto> rooms = roomService.listRooms();
            if (rooms.isEmpty()){
                System.out.println("There are no rooms at the moment");
            }else{
                for (RoomDto room : rooms) {
                    System.out.println("Room Name: " + room.getName() +
                            ", Rows: " + room.getRows() +
                            ", Columns: " + room.getCols());
                }
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
