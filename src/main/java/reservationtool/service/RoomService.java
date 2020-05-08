package reservationtool.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reservationtool.domain.model.MeetingRoom;
import reservationtool.domain.model.ReservableRoom;
import reservationtool.mapper.MeetingRoomMapper;
import reservationtool.mapper.ReservableRoomMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final ReservableRoomMapper reservableRoomMapper;
    private final MeetingRoomMapper meetingRoomMapper;

    public List<ReservableRoom> getReservableRooms(Optional<LocalDate> date) {
        LocalDate targetDate = LocalDate.now();
        if (date.isPresent()) {
            targetDate = date.get();
        }
        return reservableRoomMapper.findByReservableRoomByDate(targetDate);
    }

    public MeetingRoom getReservableRoom(Integer roomId) {
        return meetingRoomMapper.getMeetionRoom(roomId);
    }
}
