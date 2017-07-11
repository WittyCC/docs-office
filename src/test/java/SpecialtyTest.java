import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;

public class SpecialtyTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/docs_office_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deletePatientsQuery = "DELETE FROM patients *;";
      String deleteDoctorsQuery = "DELETE FROM doctors *;";
      String deleteSpecialtysQuery = "DELETE FROM specialtys *;";
      con.createQuery(deletePatientsQuery).executeUpdate();
      con.createQuery(deleteDoctorsQuery).executeUpdate();
      con.createQuery(deleteSpecialtysQuery).executeUpdate();
    }
  }

  @Test
  public void Specialty_instantiatesCorrectly_true() {
    Specialty newSpecialty = new Specialty("Neurology");
    assertEquals(true, newSpecialty instanceof Specialty);
  }


  @Test
  public void add_newSpecialtyAddedIntoDB_true() {
    Specialty newSpecialty = new Specialty("Diagnostics");
    newSpecialty.add();
    Specialty savedSpecialty = Specialty.find(newSpecialty.getId());
    assertEquals(savedSpecialty.getId(), newSpecialty.getId());
  }

  @Test
  public void all_returnsAllInstancesOfSpecialty_true() {
    Specialty firstSpecialty = new Specialty("dentistry");
    firstSpecialty.add();
    Specialty secondSpecialty = new Specialty("cardio");
    secondSpecialty.add();
    assertEquals(true, Specialty.all().get(0).equals(firstSpecialty));
    assertEquals(true, Specialty.all().get(1).equals(secondSpecialty));
  }

  @Test
  public void add_assignsIdToObject() {
    Specialty mySpecialty = new Specialty("orthodontics");
    mySpecialty.add();
    Specialty savedSpecialty = Specialty.all().get(0);
    assertEquals(mySpecialty.getId(), savedSpecialty.getId());
  }

  @Test
  public void getDoctors_retrievesALlDoctorsFromDatabase_tasksList() {
    Specialty mySpecialty = new Specialty("Pediatrics");
    mySpecialty.add();
    Doctor firstDoctor = new Doctor("Doctor 1", mySpecialty.getId());
    firstDoctor.add();
    Doctor secondDoctor = new Doctor("Doctor 2", mySpecialty.getId());
    secondDoctor.add();
    Doctor[] doctors = new Doctor[] { firstDoctor, secondDoctor };
    assertTrue(mySpecialty.getDoctors().containsAll(Arrays.asList(doctors)));
  }
}
