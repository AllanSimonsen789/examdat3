/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.YogaClassDTO;
import entities.YogaClass;
import errorhandling.NotFoundException;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

/**
 *
 * @author allan
 */
public class YogaClassFacadeTest {


    private static EntityManagerFactory emf;
    private static YogaClassFacade facade;
    private static YogaClass yc1, yc2, yc3;

    public YogaClassFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST,
                EMF_Creator.Strategy.DROP_AND_CREATE);
        facade = YogaClassFacade.getYogaClassFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("YogaClass.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        yc1 = new YogaClass(new Date(2020, 06, 15, 14, 50, 0), 1);
        yc2 = new YogaClass(new Date(2020, 07, 15, 14, 50, 0), 2);
        yc3 = new YogaClass(new Date(2020, 03, 15, 14, 50, 0), 42);
        try {
            em.getTransaction().begin();
            em.createNamedQuery("YogaClass.deleteAllRows").executeUpdate();
            em.persist(yc1);
            em.persist(yc2);
            em.persist(yc3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void test_GetYogaClassesCount_ReturnsAmountOfClasses_EqualResults() {
        assertEquals(3, facade.getYogaClassesCount(), "Expects three rows in the database");
    }

    @Test
    public void test_editYogaClass_ReturnsEditClass_EqualResults() throws NotFoundException {
        YogaClass expectedClass = new YogaClass(yc1.getId(), new Date(2020, 02, 10, 12, 21, 0), 10);
        YogaClass resultClass = new YogaClass(facade.editYogaClass(new YogaClassDTO(expectedClass)));
        assertEquals(expectedClass.getId(), resultClass.getId());
        assertEquals(expectedClass.getDate(), resultClass.getDate());
        assertEquals(expectedClass.getRoom(), resultClass.getRoom());
    }

    @Test
    public void test_editYogaClass_InvalidClassID_ExceptionAssertion() {
        YogaClass expectedClass = new YogaClass(-1, new Date(2020, 02, 10, 12, 21, 0), 10);
        Exception exception = assertThrows(NotFoundException.class, () -> {
            facade.editYogaClass(new YogaClassDTO(expectedClass));

        });
        String expectedMessage = "Could not find Class with provided ID";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void test_deleteClass_ReturnsTrueBooleanValue_EqualResults() throws NotFoundException {
        assertTrue(facade.deleteYogaClass(yc1.getId()));

    }

    @Test
    public void test_deleteYogaClass_InvalidClassID_ExceptionAssertion() {
        Exception exception = assertThrows(NotFoundException.class, () -> {
            assertTrue(facade.deleteYogaClass(-1));

        });
        String expectedMessage = "Could not find Class with provided ID";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}

