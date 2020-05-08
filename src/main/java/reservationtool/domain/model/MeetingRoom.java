package reservationtool.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MeetingRoom {
    private Integer roomId;
    private String roomName;
}
