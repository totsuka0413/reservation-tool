package reservationtool.mapper;

import org.apache.ibatis.annotations.Mapper;
import reservationtool.domain.model.ReservableRoom;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ReservableRoomMapper {
    List<ReservableRoom> findByReservableRoomByDate(LocalDate reserveDate);
}
