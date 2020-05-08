package reservationtool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import reservationtool.domain.model.ReservableRoom;
import reservationtool.service.RoomService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("rooms")
public class RoomController {

    private final RoomService roomService;

    @RequestMapping(method = RequestMethod.GET)
    public String getReservableRooms(Model model, @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<ReservableRoom> rooms = roomService.getReservableRooms(date != null ? Optional.of(date) : Optional.empty());
        model.addAttribute("rooms", rooms);
        model.addAttribute("date", rooms.get(0).getReservedDate());
        return "room/rooms";
    }
}
