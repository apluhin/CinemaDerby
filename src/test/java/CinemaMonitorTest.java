import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by aleksejpluhin on 04.04.16.
 */
public class CinemaMonitorTest {
   private Cinema cinema;


 @Before
 public void setUp() throws Exception {
  cinema = new Cinema();
 }

 @After
 public void tearDown() throws Exception {
  cinema.shutdown();
 }

 @Test
    public void testSearch() throws Exception {
       cinema.addHall(5,5);

     cinema.addSeances("Film", LocalDateTime.of(2010,10,10,10,30),cinema.listHall().get(0).num, 100,20);
     cinema.addSeances("Movie", LocalDateTime.of(2010,10,10,11,30),cinema.listHall().get(0).num, 100,20);
     List<Seance> seances =  cinema.search("Film", null, null, null, null);
     List<Seance> seances1 = cinema.search("Movie", null,null,19, null);
     assertEquals(seances.size(), 1);
     assertEquals(seances1.size(), 1);
     assertEquals(seances.get(0).nameFilm, "Film");



    }

    @Test
    public void testAddHall() throws Exception {
       cinema.addHall(1,1);
       cinema.addHall(2,2);
        List<Halls> halles = cinema.listHall();
        assertTrue(halles.size() > 0);
        assertEquals(halles.size(), 2);
        assertEquals(halles.get(0).row, halles.get(0).seats);
    }

    @Test
    public void testListHall() throws Exception {
        cinema.addHall(4,4);
        cinema.addHall(5,5);
        cinema.addHall(6,6);
        List<Halls> hallses = cinema.listHall();
        assertEquals(hallses.size(),3);


    }

    @Test
    public void testAddSeances() throws Exception {
      cinema.addHall(5,5);
      cinema.addSeances("Film", LocalDateTime.of(2010, 10, 10, 20, 30), cinema.listHall().get(0).num, 100, 20);
        cinema.addSeances("Film1", LocalDateTime.of(2010,10,10,20,30), cinema.listHall().get(0).num, 100,20);
      List<Seance> seances = cinema.search("Film",null,null,null,null);
        assertEquals(seances.size(),2);
        assertNotNull(seances);

    }

    @Test
    public void testAddReserve() throws Exception {
        cinema.addHall(5,5);
        cinema.addSeances("Film", LocalDateTime.of(2010, 10, 10, 20, 30), cinema.listHall().get(0).num, 100, 20);
        cinema.addSeances("Film1", LocalDateTime.of(2010,10,10,20,30), cinema.listHall().get(0).num, 100,20);
        List<Seance> seances = cinema.search(null, LocalDateTime.now(), null, null, null);
        cinema.addReserve(1,1,1,seances.get(0).id);
        cinema.addReserve(1,1,1,seances.get(0).id);


    }
}