package reservationtool.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ReservableRoom implements Serializable {
    private Integer roomId;
    private LocalDate reservedDate;
    private String roomName;
}