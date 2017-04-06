import dao.UserDAOImpl;
import model.Tweet;
import model.User;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jeroen0606 on 15-3-2017.
 */
public class TestJPA {
    private User jeroen;
    private User evelien;
    private Tweet tweet;
    private UserDAOImpl userDAO;
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("KwetterTestPU");
    private EntityManager em;
    private EntityTransaction tx;

    @Before
    public void initiate() {
        jeroen = new User("jeroen0606@gmail.com", "test1");
        evelien = new User("evelien@gmail.com", "test2");
        tweet = new Tweet("Hallo allemaal!", jeroen);
        userDAO = new UserDAOImpl();
        em = emf.createEntityManager();
        userDAO.createTest(em);
    }

    @Test
    public void testJPA(){
        em.getTransaction().begin();
        userDAO.createUser(jeroen);
        userDAO.createUser(evelien);
        em.getTransaction().commit();
        assertEquals(userDAO.getUser("jeroen0606@gmail.com").getUsername(), "jeroen0606@gmail.com");
        assertEquals(userDAO.getUser("evelien@gmail.com").getUsername(), "evelien@gmail.com");

        em.getTransaction().begin();
        userDAO.createTweet("Dit is de eerste tweet voor JPA!", jeroen);
        userDAO.createTweet("Dit is de tweede tweet voor JPA!", evelien);
        userDAO.createTweet("Ik heb geen zin meer!", evelien);

        userDAO.addFollowing(jeroen, evelien);
        userDAO.addFollowing(evelien, jeroen);

        userDAO.likeTweet(tweet, evelien);
        em.getTransaction().commit();
    }
}
