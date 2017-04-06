package dao;

import model.Tweet;
import model.User;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Jeroen0606 on 15-3-2017.
 */
@Default
@Stateless
public class TweetDAOImpl implements TweetDAO{

    @PersistenceContext
    EntityManager em;

    public TweetDAOImpl() {

    }

    public Tweet getTweet(int id) {
        Query query = em.createNamedQuery("getTweetById", Tweet.class);
        query.setParameter("id", id);
        return (Tweet) query.getSingleResult();
    }

    public List<Tweet> getAllTweets() {
        Query query = em.createNamedQuery("getAllTweets", Tweet.class);
        return (List<Tweet>) query.getResultList();
    }
}
