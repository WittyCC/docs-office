import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class DoctorTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/docs_office_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deletePatientsQuery = "DELETE FROM patients *;";
      String deleteDoctorsQuery = "DELETE FROM doctors *;";
      con.createQuery(deletePatientsQuery).executeUpdate();
      con.createQuery(deleteDoctorsQuery).executeUpdate();
    }
  }

  @Test
  public void Doctor_instantiatesCorrectly_true() {
    Doctor newDoctor = new Doctor("John Doe", "orthopedics");
    assertEquals(true, newDoctor instanceof Doctor);
  }

  @Test
  public void getId_doctorInstantiatesName() {
    Doctor newDoctor = new Doctor("John Doe", "pediatrics");
    assertEquals("John Doe", newDoctor.getName());
  }

  // @Test
  // public void getId_doctorsInstantiateWithAnId() {
  //   Doctor newDoctor = new Doctor("John Doe", "obstetrics");
  //   assertTrue(newDoctor.getId() > 0);
  // }

  @Test
  public void add_newDoctorAddedIntoDB_true() {
    Doctor newDoctor = new Doctor("Dr. Pepper", "dentistry");
    newDoctor.add();
    Doctor savedDoctor = Doctor.find(newDoctor.getId());
    assertEquals(savedDoctor.getId(), newDoctor.getId());
  }

  @Test
  public void all_returnsAllInstancesOfDoctor_true() {
    Doctor firstDoctor = new Doctor("Dr. Jane Doe", "pediatrics");
    firstDoctor.add();
    Doctor secondDoctor = new Doctor("Dr. Witty Chang", "geriatrics");
    secondDoctor.add();
    assertEquals(true, Doctor.all().get(0).equals(firstDoctor));
    assertEquals(true, Doctor.all().get(1).equals(secondDoctor));
  }

  @Test
  public void add_assignsIdToObject() {
    Doctor myDoctor = new Doctor("Dr. Pepper", "dentistry");
    myDoctor.add();
    Doctor savedDoctor = Doctor.all().get(0);
    assertEquals(myDoctor.getId(), savedDoctor.getId());
  }
}
