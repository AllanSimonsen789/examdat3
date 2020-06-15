package facades;

import dto.CourseDTO;
import dto.CourseListDTO;
import dto.YogaClassDTO;
import entities.Course;
import entities.YogaClass;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class CourseFacade {

    private static CourseFacade instance;
    private static YogaClassFacade YCFACADE;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private CourseFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static CourseFacade getCourseFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CourseFacade();
            YCFACADE = YogaClassFacade.getYogaClassFacade(emf);
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public long getCourseCount() {
        EntityManager em = getEntityManager();
        try {
            long renameMeCount = (long) em.createQuery("SELECT COUNT(c) FROM Course c").getSingleResult();
            return renameMeCount;
        } finally {
            em.close();
        }
    }

    

    public CourseListDTO getAllCourses() {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            TypedQuery<Course> q = em.createQuery("SELECT c FROM Course c ", Course.class);
            em.getTransaction().commit();
            ArrayList<CourseDTO> courses = new ArrayList<>();
            for (Course c : q.getResultList()) {
                courses.add(new CourseDTO(c));
            }
            return new CourseListDTO(courses);
        } finally {
            em.close();
        }
    }

    public CourseDTO addCourse(CourseDTO c) {
        EntityManager em = getEntityManager();
        Course course = new Course(c);
        try {
            em.getTransaction().begin();
            em.persist(course);
            em.getTransaction().commit();
            return new CourseDTO(course);
        } finally {
            em.close();
        }
    }

    public CourseDTO editCourse(CourseDTO cdto) throws SQLException {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Course course = em.find(Course.class, cdto.getId());
            if (course == null) {
                throw new SQLException("Nothing found with id.");
            }
            course.setCourseName(cdto.getCourseName());
            course.setDescription(cdto.getDescription());
            course.setMaxParticipants(cdto.getMaxParticipants());
            course.setPrice(cdto.getPrice());
            em.getTransaction().commit();
            return new CourseDTO(course);
        } finally {
            em.close();
        }
    }

    public boolean deleteCourse(int id) throws SQLException {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Course course = em.find(Course.class, id);
            if (course == null) {
                throw new SQLException("Nothing found with id.");
            }
            em.remove(course);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return true;
    }
    
    public YogaClassDTO addYogaClassToCourse(YogaClassDTO ycdto) throws SQLException{
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Course course = em.find(Course.class, ycdto.getCourseID());
            em.getTransaction().commit();
            if (course == null) {
                throw new SQLException("Nothing found with id.");
            }
            YogaClass yc = new YogaClass(ycdto);
            yc.setCourse(course);
            return YCFACADE.addYogaClass(yc);
        } finally {
            em.close();
        }
    }

}
