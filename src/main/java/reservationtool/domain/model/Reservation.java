package reservationtool.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
public class Reservation implements Serializable {
    private Integer reservationId;

    private LocalTime startTime;

    private LocalTime endTime;

    private Integer roomId;

    private LocalDate reservedDate;

    private String roomName;

    private User user;

    public boolean overlap(Reservation target) {
        if (!Objects.equals(roomId, target.getRoomId())) {
            return false;
        }
        if (startTime.equals(target.startTime) && endTime.equals(target.endTime)) {
            return true;
        }
        return target.endTime.isAfter(startTime) && endTime.isAfter(target.startTime);
    }
}