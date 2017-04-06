package dao;

import model.Role;
import model.Tweet;
import model.User;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by Jeroen0606 on 15-3-2017.
 */
@Local
public interface UserDAO {
    boolean createUser(User user);

    boolean addFollowing(User user, User following);

    User getUser(String username);

    boolean createTweet(String message, User user);

    boolean likeTweet(Tweet tweet, User user);

    boolean addRole(User user, Role role);

    List<Role> getAllRoles();

    Role getRoleByName(String roleName);
}
