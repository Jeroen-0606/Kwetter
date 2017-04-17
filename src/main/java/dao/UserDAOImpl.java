package dao;

import model.Role;
import model.Tweet;
import model.User;

import javax.annotation.ManagedBean;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Jeroen0606 on 15-3-2017.
 */
@Default
@Stateless
public class UserDAOImpl implements UserDAO, Serializable {

    @PersistenceContext//(unitName = "KwetterPU")
    EntityManager em;

    public UserDAOImpl() {

    }

    public void createTest(EntityManager em) {
        this.em = em;
    }

    public boolean createUser(User user) {
        em.persist(user);
        return true;
    }

    public boolean updateUser(User user) {
        em.merge(user);
        return true;
    }

    public boolean addFollowing(User user, User following) {
        user.followUser(following);
        following.addFollower(user);
        em.merge(user);
        em.merge(following);
        return true;
    }

    public User getUser(String username) {
        Query query = em.createNamedQuery("findUserByUsername", User.class);
        query.setParameter("username", username);
        return (User) query.getSingleResult();
    }

    public List<User> searchUser(String search) {
        Query query = em.createNamedQuery("searchUser", User.class);
        query.setParameter("search", search);
        return (List<User>) query.getResultList();
    }

    public boolean createTweet(String message, User user) {
        em.persist(user.createTweet(message));
        return true;
    }

    public boolean likeTweet(Tweet tweet, User user) {
        user.likeTweet(tweet);
        em.merge(tweet);
        return true;
    }

    public boolean addRole(User user, Role role) {
        user.addRole(role);
        em.merge(user);
        return true;
    }

    public List<Role> getAllRoles() {
        Query query = em.createNamedQuery("getAllRoles", Role.class);
        return (List<Role>) query.getResultList();
    }

    public Role getRoleByName(String roleName) {
        Query query = em.createNamedQuery("getRoleByName", Role.class);
        query.setParameter("roleName", roleName);
        return (Role) query.getSingleResult();
    }
}
