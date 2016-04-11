import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by aleksejpluhin on 10.03.16.
 */
public class Seance {
    public final int id;
    public final String nameFilm;
    public final LocalDateTime time;
    public final int hall;
    public final double price;
    public final int age;


    public Seance(int id, String nameFilm, LocalDateTime time, int hall, double price, int age) {
        this.id = id;
        this.nameFilm = nameFilm;
        this.time = time;
        this.hall = hall;
        this.price = price;
        this.age = age;

    }




    @Override
    public String toString() {
        return "Seance{" +
                "id=" + id +
                ", nameFilm='" + nameFilm + '\'' +
                ", time='" + time + '\'' +
                ", hall=" + hall +
                ", price=" + price +
                ", age=" + age +
                '}';
    }
}
