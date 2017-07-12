import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl" );
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/patients/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/patient-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/patients", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Doctor doctor = Doctor.find(Integer.parseInt(request.queryParams("doctorId")));
      String name = request.queryParams("name");
      String dob = request.queryParams("dob");
      Patient newPatient = new Patient(name, dob);

      doctor.addPatient(newPatient);

      model.put("team", team);
      model.put("template", "templates/doctor-patients-success.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/patients", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String dob = request.queryParams("dob");
      model.put("patient-name", name);
      model.put("dob", dob);
      model.put("template", "templates/patients.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/patients/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Patient patient = Patient.find(Integer.parseInt(request.params(":id")));
      model.put("patient", patient);
      model.put("template", "templates/patient.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("doctors", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      int specialtyId = Integer.parseInt(request.queryParams("specialtyId"));
      model.put("doctors", Doctors.all());
      model.put("template", "templates/doctors.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("doctors/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Doctor doctor = Doctor.find(Integer.parseInt(request.params(":id")));
      model.put("doctor", doctor);
      model.put("template", "templates/doctor.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/doctors/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/doctor-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/doctors", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      int specialtyId = (Integer.parseInt(request.queryParams("specialtyId"));
      Doctor newDoctor = new Doctor(name, specialtyId);
      model.put("template", "templates/doctor-success.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("specialtys", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("specialtys", Specialty.all());
      model.put("template", "templates/specialtys.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("specialtys/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Specialty specialty = Specialty.find(Integer.parseInt(request.params(":id")));
      model.put("specialty", specialty);
      model.put("template", "templates/specialty.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("specialtys/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/specialty-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/specialtys", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      Specialty newSpecialty = new Specialty(name);
      model.put("template", "templates/specialty-success.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
