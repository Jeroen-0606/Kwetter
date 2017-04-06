package managedBeans;

import model.Tweet;
import model.User;
import service.UserService;


import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Jeroen0606 on 28-3-2017.
 */
@Named("homeBean")
@RequestScoped
public class HomeBean implements Serializable {
    @Inject
    private UserService userService;

    private String tweet;
    private User user;
    private ExternalContext externalContext;

    @PostConstruct
    public void init() {
        externalContext = FacesContext.getCurrentInstance().getExternalContext();
        user = userService.getUser(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public List<Tweet> getOwnTweets() {
        return userService.getOwnTweets(user.getUsername());
    }

    public void createTweet() {
        userService.createTweet(tweet, user.getUsername());
        tweet = "";
    }

    public List<Tweet> getAllTweets() {
        return userService.getAllTweets();
    }

    public void likeTweet(int tweetId) {
        userService.likeTweet(tweetId, user.getUsername());
    }

    public void logOut() throws IOException {
            String redirectTo = externalContext.getRequestContextPath();
            externalContext.invalidateSession();
            externalContext.redirect(redirectTo);
    }

    public void goToProfile() {
        try {
            externalContext.redirect("profile.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<User> getFollowing() {
        return userService.getFollowing(user.getUsername());
    }

    public List<User> getFollowers() {
        return userService.getFollowers(user.getUsername());
    }
}
