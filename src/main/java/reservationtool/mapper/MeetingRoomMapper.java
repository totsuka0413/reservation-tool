package reservationtool.mapper;

import org.apache.ibatis.annotations.Mapper;
import reservationtool.domain.model.MeetingRoom;

@Mapper
public interface MeetingRoomMapper {
    MeetingRoom getMeetionRoom(Integer roomId);
}
