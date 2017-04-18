package rest;

import com.sun.jersey.api.core.ResourceConfig;
import model.Role;
import model.Tweet;
import model.User;
import service.UserService;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 * Created by Jeroen0606 on 15-3-2017.
 */
@Path("/RestService")
@ApplicationPath("/resources")
@Stateless
public class RestService extends Application {
    @Inject
    UserService userService;

    @GET
    @Path("/pages/sayHello")
    //@RolesAllowed("regulars")
    public String getHello() {
        return "Hello";
    }

    @GET
    @Path("/getUser/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("username") String username) {
        return userService.getUser(username);
    }

    @POST
    @Path("/createUser")
    public void createUser(@FormParam("username") String username,@FormParam("password") String password){
        userService.createUser(username, password);
    }

    @GET
    @Path("/getOwnTweets/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tweet> getOwnTweets(@PathParam("username") String username) {
        return userService.getOwnTweets(username);
    }

    @POST
    @Path("/likeTweet")
    public void likeTweet(@FormParam("tweetId") int tweetId, @FormParam("username") String username) {
        userService.likeTweet(tweetId, username);
    }

    @POST
    @Path("/createTweet")
    public void createTweet(@FormParam("message") String message, @FormParam("username") String username) {
        userService.createTweet(message, username);
    }

    @POST
    @Path("/addFollowing")
    public void addFollowing(@FormParam("username") String username, @FormParam("following") String following) {
        userService.addFollowing(username, following);
    }

    @GET
    @Path("/getFollowing/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getFollowing(@PathParam("username") String username) {
        return userService.getFollowing(username);
    }

    @GET
    @Path("/getFollowers/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getFollowers(@PathParam("username") String username) {
        return userService.getFollowers(username);
    }

    @POST
    @Path("/addRole")
    public void addRole(@FormParam("username") String username, @FormParam("roleName") String roleName) {
        userService.addRole(roleName, username);
    }

    @GET
    @Path("/getRolesOfUser/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Role> getRolesOfUser(@PathParam("username") String username) {
        return userService.getRole(username);
    }
}
