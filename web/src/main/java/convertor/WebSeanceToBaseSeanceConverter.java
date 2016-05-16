package convertor;

import model.Halls;
import model.Seance;

/**
 * Created by aleksejpluhin on 25.04.16.
 */
public class WebSeanceToBaseSeanceConverter implements Convertor<Seance, base.Seance> {

    @Override
    public base.Seance convert(Seance seance) {
        base.Seance value = new base.Seance(-1, seance.nameFilm, seance.time, seance.hall, seance.price, seance.age);
        return value;
    }
}
