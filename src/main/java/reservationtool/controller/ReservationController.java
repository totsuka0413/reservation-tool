package reservationtool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reservationtool.domain.model.MeetingRoom;
import reservationtool.domain.model.Reservation;
import reservationtool.dto.ReservableRoomId;
import reservationtool.dto.ReservationForm;
import reservationtool.exception.AlreadyReservedException;
import reservationtool.service.ReservationService;
import reservationtool.service.RoomService;
import reservationtool.service.user.ReservationUserDetails;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequiredArgsConstructor
@RequestMapping("reservations")
public class ReservationController {

    private final ReservationService reservationService;

    private final RoomService roomService;

    @ModelAttribute
    ReservationForm setUpForm() {
        return ReservationForm.builder()
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(10, 0))
                .build();
    }

    @GetMapping
    public String reserveForm(Model model, @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @RequestParam(value = "roomId", required = false) Integer roomId) {

        MeetingRoom room = roomService.getReservableRoom(roomId);

        ReservableRoomId params = ReservableRoomId.builder()
                .reservedDate(date)
                .roomId(roomId)
                .build();
        List<Reservation> reservations = reservationService.getReservations(params);
        List<LocalTime> timeList =
                Stream.iterate(LocalTime.of(0, 0), t -> t.plusMinutes(30))
                        .limit(24 * 2)
                        .collect(Collectors.toList());

        model.addAttribute("reservations", reservations);
        model.addAttribute("roomName", room.getRoomName());
        model.addAttribute("date", date);
        model.addAttribute("roomId", roomId);
        model.addAttribute("timeList", timeList);
        return "reservation/reserveForm";
    }

    @RequestMapping(path = "/{date}/{roomId}", method = RequestMethod.POST)
    public String reserve(@Validated ReservationForm form,
                          BindingResult bindingResult,
                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date,
                          @PathVariable("roomId") Integer roomId,
                          @AuthenticationPrincipal ReservationUserDetails userDetails,
                          Model model) {
        Reservation request = Reservation.builder()
                .startTime(form.getStartTime())
                .endTime(form.getEndTime())
                .roomId(roomId)
                .reservedDate(date)
                .user(userDetails.getUser())
                .build();
        try {
            reservationService.reserve(request);
        } catch (AlreadyReservedException e) {
            model.addAttribute("error", e.getMessage());
            return reserveForm(model, request.getReservedDate(), request.getRoomId());
        }

        return "redirect:/reservations?date=" + date + "&roomId=" + roomId;
    }

    @RequestMapping(path = "/{date}/{roomId}", method = RequestMethod.POST, params = "cancel")
    public String cancel(@Validated ReservationForm form,
                         @RequestParam("reservationId") Integer reservationId,
                         @PathVariable("roomId") Integer roomId,
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date,
                         Model model) {
        reservationService.cancel(reservationId);

        return "redirect:/reservations?date=" + date + "&roomId=" + roomId;
    }
}
