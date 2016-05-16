package convertor;

import base.Reservation;

/**
 * Created by aleksejpluhin on 25.04.16.
 */
public class WebReservationToBaseReservationConverter implements Convertor<Reservation, base.Reservation> {
    @Override
    public Reservation convert(Reservation reservation) {
        Reservation value = new Reservation(reservation.user_id, reservation.seance, reservation.seat, reservation.row, -1);
        return value;
    }
}
