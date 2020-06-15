package facades;

import dto.CourseDTO;
import entities.Course;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class CourseFacadeTest {

    private static EntityManagerFactory emf;
    private static CourseFacade facade;
    private static Course c1, c2, c3;

    public CourseFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST,
                EMF_Creator.Strategy.DROP_AND_CREATE);
        facade = CourseFacade.getCourseFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            //em.createNamedQuery("Course.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        c1 = new Course("How to cope with Exam Pressure", "Learn to control your stress levels during exam period", 40, 200.0);
        c2 = new Course("Advanced Flexibility", "Advanced class! Become more flexible than ever!", 10, 499.95);
        c3 = new Course("Beginner Flexibility", "Learn the basic excersises to become more flexible, this course gives acces to the advanced course", 25, 249.95);
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Course.deleteAllRows").executeUpdate();
            em.persist(c1);
            em.persist(c2);
            em.persist(c3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void test_GetCourseCount_ReturnsAmountOfActiveCourses_EqualResults() {
        assertEquals(3, facade.getCourseCount(), "Expects three rows in the database");
    }

    @Test
    public void test_GetAllCourses_ReturnsAllCourses_EqualResults() {
        assertEquals(3, facade.getAllCourses().getCourses().size());
    }

    @Test
    public void test_addCourse_ReturnsNewCourse_EqualResults() {
        Course expectedCourse = new Course("testCourse", "testCourse", 10, 10.0);
        Course resultCourse = new Course(facade.addCourse(new CourseDTO(expectedCourse)));
        assertNotNull(resultCourse.getId());
        assertEquals(expectedCourse.getCourseName(), resultCourse.getCourseName());
        assertEquals(expectedCourse.getDescription(), resultCourse.getDescription());
        assertEquals(expectedCourse.getMaxParticipants(), resultCourse.getMaxParticipants());
        assertEquals(expectedCourse.getPrice(), resultCourse.getPrice());

    }

    @Test
    public void test_editCourse_ReturnsEditedCourse_EqualResults() throws SQLException {
        Course expectedCourse = new Course(c1.getId(), "testCourse", "testCourse", 10, 10.0);
        Course resultCourse = new Course(facade.editCourse(new CourseDTO(expectedCourse)));
        assertEquals(c1.getId(), resultCourse.getId());
        assertEquals(expectedCourse.getCourseName(), resultCourse.getCourseName());
        assertEquals(expectedCourse.getDescription(), resultCourse.getDescription());
        assertEquals(expectedCourse.getMaxParticipants(), resultCourse.getMaxParticipants());
        assertEquals(expectedCourse.getPrice(), resultCourse.getPrice());
    }

    @Test
    public void test_editCourse_InvalidCourseID_ExceptionAssertion() {
        Course expectedCourse = new Course(-1, "testCourse", "testCourse", 10, 10.0);
        Exception exception = assertThrows(SQLException.class, () -> {
            facade.editCourse(new CourseDTO(expectedCourse));

        });
        String expectedMessage = "Nothing found with id.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void test_deleteCourse_ReturnsTrueBooleanValue_EqualResults() throws SQLException {
        assertTrue(facade.deleteCourse(c1.getId()));

    }

    @Test
    public void test_deleteCourse_InvalidCourseID_ExceptionAssertion() {
        Exception exception = assertThrows(SQLException.class, () -> {
            assertTrue(facade.deleteCourse(-1));

        });
        String expectedMessage = "Nothing found with id.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
