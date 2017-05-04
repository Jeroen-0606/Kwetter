package service;

import dao.TweetDAO;
import dao.UserDAO;
import dao.UserDAOImpl;
import model.Role;
import model.Tweet;
import model.User;
import sun.nio.cs.StandardCharsets;

import javax.annotation.ManagedBean;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by Jeroen0606 on 15-3-2017.
 */

@Stateless
public class UserService {

    @Inject
    private UserDAO userDAO;

    @Inject
    private TweetDAO tweetDAO;

    public UserService() {

    }

    public User getUser(String username) {
        return userDAO.getUser(username);
    }

    public List<User> searchUser(String search) {
        return userDAO.searchUser("%" + search + "%");
    }

    public boolean createUser(String username, String password) {
        byte[] digest;
        try {
            digest = MessageDigest.getInstance("SHA-256").digest(password.getBytes("UTF-8"));
            password = String.format("%064x", new java.math.BigInteger(1, digest));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        userDAO.createUser(new User(username, password));

        return true;
    }

    public boolean updateUser(String username, String name, String bio, String location) {
        User user = getUser(username);
        user.setName(name);
        user.setBio(bio);
        user.setLocation(location);
        userDAO.updateUser(user);
        return true;
    }

    public void addFollowing(String username, String following) {
        userDAO.addFollowing(userDAO.getUser(username), userDAO.getUser(following));
    }

    public void createTweet(String message, String username) {
        userDAO.createTweet(message, userDAO.getUser(username));
    }

    public void likeTweet(int tweetId, String username) {
        userDAO.likeTweet(tweetDAO.getTweet(tweetId), userDAO.getUser(username));
    }

    public List<User> getFollowers(String username) {
        return userDAO.getUser(username).getFollowers();
    }

    public List<User> getFollowing(String username) {
        return userDAO.getUser(username).getFollowing();
    }

    public List<Tweet> getOwnTweets(String username) {
        List<Tweet> tweets = userDAO.getUser(username).getTweets();
        int listSize = tweets.size();
        //This if statement will depend on how the front end is set up
        if(listSize >=100) {
            return tweets.subList(listSize - 11, listSize - 1);
        } else {
            return tweets;
        }
    }

    public List<Tweet> getAllTweets() {
        return tweetDAO.getAllTweets();
    }

    public Tweet getTweet(int id) {
        return null;
    }

    public List<Role> getAllRoles() {
        return userDAO.getAllRoles();
    }

    public void addRole(String roleName, String username) {
        userDAO.addRole(userDAO.getUser(username), userDAO.getRoleByName(roleName));
    }

    public List<Role> getRole(String username) {
        return userDAO.getUser(username).getRoles();
    }

}
