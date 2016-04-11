/**
 * Created by aleksejpluhin on 21.03.16.
 */
public class Halls {
    public final int num;
    public final int  row;
    public final int seats;

    public Halls(int num, int row, int seats) {
        this.num = num;
        this.row = row;
        this.seats = seats;
    }

    @Override
    public String toString() {
        return "Hall " +  num +

                " { row=" + row +
                ", seats=" + seats +
                '}';
    }
}
