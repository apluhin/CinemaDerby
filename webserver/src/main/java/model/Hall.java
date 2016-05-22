package model;

public final class Hall {
    private final int id;
    private final int seats;
    private final int nrows;
    private final String name;

    public Hall(int id, int seats, int nrows, String name) {
        this.id = id;
        this.seats = seats;
        this.nrows = nrows;
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public int getSeats() {
        return seats;
    }

    public int getNrows() {
        return nrows;
    }

    public String getName() {
        return name;
    }
}
