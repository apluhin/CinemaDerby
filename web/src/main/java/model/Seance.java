package model;

import java.time.LocalDateTime;

/**
 * Created by aleksejpluhin on 10.03.16.
 */
public class Seance {

    public String nameFilm;
    public LocalDateTime time;
    public int hall;
    public double price;
    public int age;




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

                ", nameFilm='" + nameFilm + '\'' +
                ", time='" + time + '\'' +
                ", hall=" + hall +
                ", price=" + price +
                ", age=" + age +
                '}';
    }

}
