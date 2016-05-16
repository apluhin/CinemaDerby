package convertor;

import model.Reservation;
import model.Seance;

/**
 * Created by aleksejpluhin on 25.04.16.
 */
public class BaseSeanceToWebSeanceConverter implements Convertor<base.Seance, Seance> {

    @Override
    public Seance convert(base.Seance seance) {
        Seance value = new Seance();
        value.age = seance.age;
        value.hall = seance.age;
        value.nameFilm = seance.nameFilm;
        value.price = seance.price;
        value.time = seance.time;
        return value;
    }
}
