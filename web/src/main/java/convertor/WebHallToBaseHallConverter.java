package convertor;

import model.Halls;

/**
 * Created by aleksejpluhin on 25.04.16.
 */
public class WebHallToBaseHallConverter implements Convertor<Halls, base.Halls> {
    @Override
    public base.Halls convert(Halls halls) {
        base.Halls value = new base.Halls(halls.num, halls.row, halls.seats);
        return value;
    }
}
