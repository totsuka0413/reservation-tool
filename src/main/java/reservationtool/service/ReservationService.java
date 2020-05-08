package reservationtool.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reservationtool.domain.model.Reservation;
import reservationtool.domain.model.User;
import reservationtool.dto.ReservableRoomId;
import reservationtool.exception.AlreadyReservedException;
import reservationtool.mapper.ReservationMapper;
import reservationtool.mapper.response.ReservationResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationMapper mapper;

    /**
     * 予約一覧取得
     *
     * @param params
     * @return
     */
    public List<Reservation> getReservations(ReservableRoomId params) {
        List<Reservation> reservations = new ArrayList<>();
        List<ReservationResponse> reserveResponse = mapper.findByReservationsReservableRoomId(params);
        for (ReservationResponse res : reserveResponse) {
            User user = User.builder()
                    .userId(res.getUserId())
                    .firstName(res.getFirstName())
                    .lastName(res.getLastName())
                    .build();
            Reservation reservation = Reservation.builder()
                    .reservationId(res.getReservationId())
                    .startTime(res.getStartTime())
                    .endTime(res.getEndTime())
                    .roomId(res.getRoomId())
                    .reservedDate(res.getReservedDate())
                    .roomName(res.getRoomName())
                    .user(user)
                    .build();
            reservations.add(reservation);
        }
        return reservations;
    }

    /**
     * 予約処理
     *
     * @param reservation
     * @return
     */
    public void reserve(Reservation reservation) {
        ReservableRoomId getRequest = ReservableRoomId.builder()
                .roomId(reservation.getRoomId())
                .reservedDate(reservation.getReservedDate())
                .build();
        List<Reservation> reservations = getReservations(getRequest);
        boolean overlap = reservations.stream().anyMatch(it -> it.overlap(reservation));
        if (overlap) {
            // 例外throw
            throw new AlreadyReservedException("入力の時間帯はすでに予約済みです。");
        }
        mapper.reserve(reservation);
    }

    /**
     * 予約キャンセル
     *
     * @param reservationId
     */
    public void cancel(Integer reservationId) {
        mapper.cancel(reservationId);
    }
}
