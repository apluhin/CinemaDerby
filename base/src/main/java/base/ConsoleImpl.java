package base;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by aleksejpluhin on 11.04.16.
 */
public class ConsoleImpl implements Console {
    Scanner scanner = new Scanner(System.in);

    public void choseReservation(Cinema cinema) throws SQLException {


    }

    public void addSeanceConsole(Cinema cinema) throws SQLException {
        Seance seance = new Seance();
        List<Halls> hallses = cinema.listHall();
        System.out.print("Введите название фильма:> ");
        seance.setNameFilm(scanner.next());

        while (true) {
            System.out.print("Введите номер зала:>");
            while (!scanner.hasNextInt()) {
                System.out.print("Введите номер зала:>");
                scanner.next();
            }
            int id = scanner.nextInt();
            if (findHall(cinema.listHall(), id)) {
                seance.setHall(id);
                break;
            }
        }
        System.out.print("Введите цену:>");
        while (!scanner.hasNextDouble()) {
            System.out.print("Введите цену:>");
            scanner.next();

        }
        seance.setPrice(scanner.nextDouble());

        System.out.print("Введите возраст:>");
        while (!scanner.hasNextInt()) {
            System.out.print("Введите возраст:>");
            scanner.next();
        }
        seance.setAge(scanner.nextInt());

        seance.setTime(inputTime("Введите дату и время", scanner));
        System.out.println(seance.toString());
        cinema.addSeances(seance.getNameFilm(), seance.getTime(), seance.getHall(), seance.getAge(), seance.getPrice());

        consoleWork(cinema);
    }

    public void consoleWork(Cinema cinema) throws SQLException {
        System.out.println("1. Создание сеансов \n2. Список сеансов \n3. Резервация");
        while (!scanner.hasNextInt()) {
            scanner.next();
        }
        int index = scanner.nextInt();
        switch (index) {
            case 1:
                addSeanceConsole(cinema);
            case 2:
                listSeances(cinema);
            case 3:
                addReservation(cinema);
            case 4:
                break;
        }
    }


    private void addReservation(Cinema cinema) throws SQLException {
        int age;
        System.out.println("Введите возраст");
        while (!scanner.hasNextInt()) {
            System.out.println("Введите возраст");
            scanner.next();
        }
        age = scanner.nextInt();


        List<Seance> seances = cinema.search(null, LocalDateTime.now(), null, age, null);
        int i = 1;
        for (Seance seance : seances) {
            System.out.println(i + " " + seance.toString());
            i++;
        }
        int indexSeance;
        System.out.println("Введите номер сеанта");
        while (!scanner.hasNextInt()) {
            System.out.println("Введите номер сеанса");
            scanner.next();
        }
        indexSeance = scanner.nextInt();
        Seance seance = seances.get(indexSeance - 1);
        int[][] mas = getSeats(cinema, seance);

        for (i = 0; i < mas.length; i++) {
            int index = i + 1;
            if (index >= 10) {
                System.out.print(index + "   ");
            } else {
                System.out.print(index + "    ");
            }
            for (int j = 0; j < mas[i].length; j++) {
                //          System.out.print(mas[i][j]);
                if (mas[i][j] == 1) {

                    System.out.print("[X]");
                } else {
                    System.out.print("[" + (j + 1) + "]");
                }
            }
            System.out.println();
        }

        int[][] mas1 = choseSeats(mas);
        for (i = 0; i < mas1.length; i++) {
            for (int j = 0; j < mas1[i].length; j++) {
                if (mas1[i][j] == 2) {
                    cinema.addReserve(i, j, (int) (Math.random() * 1000), seance.getId());
                    mas1[i][j] = 1;
                    System.out.println("PUT");
                }
            }
        }


        consoleWork(cinema);
    }


    private int[][] choseSeats(int[][] mas) {
        System.out.print("Введите ряд:>");
        int row;
        while (!scanner.hasNextInt()) {
            System.out.print("Введите ряд:>");
            scanner.next();
        }
        row = scanner.nextInt();
        System.out.print("Введите место:>");
        int seats;
        while (!scanner.hasNextInt()) {
            System.out.print("Введите место:>");
            scanner.next();
        }
        seats = scanner.nextInt();
        try {
            if (mas[row - 1][seats - 1] == 1) {
                System.out.println("место занято");
                choseSeats(mas);
            } else {
                mas[row - 1][seats - 1] = 2;
                System.out.println("1. Еще место");
                System.out.println("2. Выход");
                while (!scanner.hasNextInt()) {
                    System.out.println("1. Еще место");
                    System.out.println("2. Выход");
                    scanner.next();
                }
                if (scanner.nextInt() == 1) {
                    choseSeats(mas);
                } else {
                    return mas;
                }
            }
        } catch (Exception e) {
            System.out.println("Неверное место");
            choseSeats(mas);
        }
        return mas;
    }

    private int[][] getSeats(Cinema cinema, Seance seance) throws SQLException {
        List<Reservation> reservations = cinema.reservations(seance.getId());
        Halls halls = cinema.getHall(seance.getHall());
        int[][] mas = new int[halls.row][halls.seats];
        for (int i = 0; i < mas.length; i++) {
            for (int j = 0; j < mas[i].length; j++) {
                for (Reservation reservation : reservations) {
                    if (reservation.row == i && reservation.seat == j) {

                        mas[i][j] = 1;
                        break;
                    } else {
                        mas[i][j] = 0;
                    }

                }

            }

        }
        return mas;
    }

    private void listSeances(Cinema cinema) throws SQLException {
        int i = 1;
        for (Seance seance : cinema.listSeance()) {
            System.out.println(i + " " + seance.toString());
            i++;
        }
        consoleWork(cinema);
    }


    public boolean findHall(List<Halls> hallses, int id) {
        for (Halls halls : hallses) {
            if (halls.num == id) {
                return true;
            }
        }
        return false;
    }

    private static LocalDateTime inputTime(String name, Scanner scanner) {
        final Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}(:\\d{2})?");
        System.out.print(name + ":> ");
        while (!scanner.hasNext(pattern)) {
            scanner.next();
            System.out.print(name + ":> ");
        }
        final String nextStartDate = scanner.next();
        return LocalDateTime.parse(nextStartDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
