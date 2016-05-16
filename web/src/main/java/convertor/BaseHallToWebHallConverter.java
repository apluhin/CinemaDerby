package convertor;

import model.Halls;

/**
 * Created by aleksejpluhin on 25.04.16.
 */
public final class BaseHallToWebHallConverter implements Convertor<base.Halls,Halls> {
    @Override
    public Halls convert(base.Halls halls) {
        Halls value = new Halls();
        value.num = halls.num;
        value.row = halls.row;
        value.seats = halls.seats;
        return value;
    }
}
