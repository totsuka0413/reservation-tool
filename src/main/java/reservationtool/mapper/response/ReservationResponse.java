package reservationtool.mapper.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class ReservationResponse {
    private Integer reservationId;

    private LocalTime startTime;

    private LocalTime endTime;

    private Integer roomId;

    private LocalDate reservedDate;

    private String roomName;

    private String userId;

    private String firstName;

    private String lastName;
}
