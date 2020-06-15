package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.YogaClassDTO;
import entities.Course;
import entities.YogaClass;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.sql.SQLException;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class CourseRessourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Course c1, c2, c3;
    private static YogaClass yc1;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.CREATE);

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        c1 = new Course("How to cope with Exam Pressure", "Learn to control your stress levels during exam period", 40, 200.0);
        c2 = new Course("Advanced Flexibility", "Advanced class! Become more flexible than ever!", 10, 499.95);
        c3 = new Course("Beginner Flexibility", "Learn the basic excersises to become more flexible, this course gives acces to the advanced course", 25, 249.95);
        yc1 = new YogaClass(new Date(2020, 06, 15, 14, 50, 00), 1);
        try {
            em.getTransaction().begin();
            em.createNamedQuery("YogaClass.deleteAllRows").executeUpdate();
            em.createNamedQuery("Course.deleteAllRows").executeUpdate();
            em.persist(c1);
            em.persist(c2);
            em.persist(c3);
            em.persist(yc1);
            yc1.setCourse(c3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/course").then().statusCode(200);
    }

    //GET
    @Test
    public void testCount() {
        given()
                .contentType("application/json")
                .get("/course/count").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("count", equalTo(3));
    }

    @Test
    public void test_GetAllCourses_ReturnsAllCourses_EqualResults() {
        given()
                .contentType("application/json")
                .get("/course/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("courses", hasSize(3));
    }

    @Test
    public void test_addCourse_ReturnsNewCourse_EqualResults() {
        Course expectedCourse = new Course("testCourseRest", "testCourseRest", 10, 10.0);
        String json = GSON.toJson(expectedCourse);
        given().contentType(ContentType.JSON)
                .body(json)
                .when()
                .post("/course/add")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("id", notNullValue())
                .body("courseName", is(expectedCourse.getCourseName()))
                .body("description", is(expectedCourse.getDescription()))
                .body("maxParticipants", is(expectedCourse.getMaxParticipants()));

    }

    @Test
    public void test_editCourse_ReturnsEditedCourse_EqualResults() throws SQLException {     
        Course expectedCourse = new Course(c1.getId(),"testCourseRest", "testCourseRest", 10, 10.0);
        String json = GSON.toJson(expectedCourse);
        given().contentType(ContentType.JSON)
                .body(json)
                .when()
                .put("/course/edit")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("id", is(expectedCourse.getId()))
                .body("courseName", is(expectedCourse.getCourseName()))
                .body("description", is(expectedCourse.getDescription()))
                .body("maxParticipants", is(expectedCourse.getMaxParticipants()));
    }
    @Test
    public void test_editCourse_InvalidCourseID_ExceptionAssertion() {
        Course expectedCourse = new Course(-1, "testCourse", "testCourse", 10, 10.0);
        String json = GSON.toJson(expectedCourse);
        given().contentType(ContentType.JSON)
                .body(json)
                .when()
                .put("/course/edit")
                .then()
                .assertThat()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR_500.getStatusCode());
    }

    @Test
    public void test_deleteCourse_ReturnsTrueBooleanValue_EqualResults() throws SQLException {
         given().when()
                .delete("/course/delete/" + c1.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode());
    }
    
     @Test
    public void test_addClass_ReturnsNewClass_EqualResults() {
        YogaClassDTO expectedYogaClass = new YogaClassDTO(new Date(2020, 06, 15, 14, 50, 00), 1,c3.getId());
        String json = GSON.toJson(expectedYogaClass);
        given().contentType(ContentType.JSON)
                .body(json)
                .when()
                .post("/course/add/yogaclass")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("id", notNullValue())
                .body("date", notNullValue())
                .body("room", is(expectedYogaClass.getRoom()));
    }

    @Test
    public void test_addClass_InvalidCourseID_ExceptionAssertion() {
        YogaClassDTO expectedYogaClass = new YogaClassDTO(new Date(2020, 06, 15, 14, 50, 00), 1,-1);
        String json = GSON.toJson(expectedYogaClass);
        given().contentType(ContentType.JSON)
                .body(json)
                .when()
                .post("/course/add/yogaclass")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode());
    }
    
        @Test
    public void test_editClass_ReturnsEditedClass_EqualResults() throws SQLException {     
        YogaClassDTO expectedYogaClass = new YogaClassDTO(yc1.getId(), new Date(2020, 06, 15, 14, 50, 00), 54);
        String json = GSON.toJson(expectedYogaClass);
        given().contentType(ContentType.JSON)
                .body(json)
                .when()
                .put("/course/edit/yogaclass")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("id", notNullValue())
                .body("date", notNullValue())
                .body("room", is(expectedYogaClass.getRoom()));
    }
    @Test
    public void test_editClass_InvalidClassID_ExceptionAssertion() {
        YogaClassDTO expectedYogaClass = new YogaClassDTO(-1, new Date(2020, 06, 15, 14, 50, 00), 54);
        String json = GSON.toJson(expectedYogaClass);
        given().contentType(ContentType.JSON)
                .body(json)
                .when()
                .put("/course/edit/yogaclass")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode());
    }
    
    @Test
    public void test_deleteClass_ReturnsTrueBooleanValue_EqualResults() throws SQLException {
         given().when()
                .delete("/course/delete/yogaclass/" + yc1.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode());
    }
    @Test
    public void test_deleteClass_invalidClassID_AssertionException() throws SQLException {
         given().when()
                .delete("/course/delete/yogaclass/" + -1)
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode());
    }
}
