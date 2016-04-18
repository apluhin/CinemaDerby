import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by aleksejpluhin on 10.03.16.
 */
public class Seance {
    public int id;
    public String nameFilm;
    public LocalDateTime time;
    public int hall;
    public double price;
    public int age;


    public Seance(int id, String nameFilm, LocalDateTime time, int hall, double price, int age) {
        this.id = id;
        this.nameFilm = nameFilm;
        this.time = time;
        this.hall = hall;
        this.price = price;
        this.age = age;

    }

    public Seance() {

    }

    public int getId() {
        return id;
    }

    public String getNameFilm() {
        return nameFilm;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public int getHall() {
        return hall;
    }

    public double getPrice() {
        return price;
    }

    public int getAge() {
        return age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNameFilm(String nameFilm) {
        this.nameFilm = nameFilm;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setHall(int hall) {
        this.hall = hall;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAge(int age) {
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
