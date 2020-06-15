package facades;

import dto.YogaClassDTO;
import entities.YogaClass;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class YogaClassFacade {

    private static YogaClassFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private YogaClassFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static YogaClassFacade getYogaClassFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new YogaClassFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public long getYogaClassesCount() {
        EntityManager em = getEntityManager();
        try {
            long renameMeCount = (long) em.createQuery("SELECT COUNT(yc) FROM YogaClass yc").getSingleResult();
            return renameMeCount;
        } finally {
            em.close();
        }
    }

    public YogaClassDTO addYogaClass(YogaClass yogaclass) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(yogaclass);
            em.getTransaction().commit();
            return new YogaClassDTO(yogaclass);
        } finally {
            em.close();
        }
    }

    public YogaClassDTO editYogaClass(YogaClassDTO ycDTO) throws SQLException {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            YogaClass yogaclass = em.find(YogaClass.class, ycDTO.getId());
            if (yogaclass == null) {
                throw new SQLException("Nothing found with id.");
            }
            yogaclass.setDate(ycDTO.getDate());
            yogaclass.setRoome(ycDTO.getRoom());
            em.getTransaction().commit();
            return new YogaClassDTO(yogaclass);
        } finally {
            em.close();
        }
    }

    public boolean deleteYogaClass(int id) throws SQLException {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            YogaClass yogaclass = em.find(YogaClass.class, id);
            if (yogaclass == null) {
                throw new SQLException("Nothing found with id.");
            }
            em.remove(yogaclass);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return true;
    }

}
