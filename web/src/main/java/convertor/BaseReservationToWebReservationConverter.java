package convertor;

import model.Halls;
import model.Reservation;

/**
 * Created by aleksejpluhin on 25.04.16.
 */
public class BaseReservationToWebReservationConverter implements Convertor<base.Reservation, Reservation> {

    @Override
    public Reservation convert(base.Reservation reservation) {
        Reservation value = new Reservation();
        value.row = reservation.row;
        value.seance = reservation.seance;
        value.user_id = reservation.user_id;
        value.seat = reservation.seat;
        return value;
    }
}
