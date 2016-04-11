/**
 * Created by aleksejpluhin on 21.03.16.
 */
public class Reservation {
    public final int id;
    public final int row;
    public final int seat;
    public final Seance seance;
    public final int user_id;

    public Reservation(int user_id, Seance seance, int seat, int row, int id) {
        this.user_id = user_id;
        this.seance = seance;
        this.seat = seat;
        this.row = row;
        this.id = id;
    }
}
