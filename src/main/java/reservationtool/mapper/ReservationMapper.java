package reservationtool.mapper;

import org.apache.ibatis.annotations.Mapper;
import reservationtool.domain.model.Reservation;
import reservationtool.dto.ReservableRoomId;
import reservationtool.mapper.response.ReservationResponse;

import java.util.List;

@Mapper
public interface ReservationMapper {
    List<ReservationResponse> findByReservationsReservableRoomId(ReservableRoomId params);

    void reserve(Reservation reservation);

    void cancel(Integer reservationId);
}
