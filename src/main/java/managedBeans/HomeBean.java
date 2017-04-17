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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    private int setList = 1;
    private String search;
    private boolean showFollow = false;
    private ExternalContext externalContext;

    @PostConstruct
    public void init() {
        externalContext = FacesContext.getCurrentInstance().getExternalContext();
        user = userService.getUser(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
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
        List<Tweet> allTweets = new ArrayList<Tweet>();
        allTweets.addAll(userService.getOwnTweets(user.getUsername()));
        for(User following: userService.getFollowing(user.getUsername())) {
            allTweets.addAll(userService.getOwnTweets(following.getUsername()));
        }
        Collections.sort(allTweets, new Comparator<Tweet>(){
            public int compare(Tweet o1, Tweet o2){
                return o2.getId() - o1.getId();
            }
        });
        return allTweets;
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

    public void goToHome() {
        try {
            externalContext.redirect("home.xhtml");
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

    public int getFollowingNr() {
        return userService.getFollowing(user.getUsername()).size();
    }

    public int getFollowersNr() {
        return userService.getFollowers(user.getUsername()).size();
    }

    public void setListNr(int setList) {
        this.setList = setList;
    }

    public List<User> getUserList() {
        switch (setList) {
            case 1:
                return getFollowing();
            case 2:
                return getFollowers();
            case 3:
                return userService.searchUser(search);
        }
        return null;
    }

    public void updateUser() {
        userService.updateUser(user.getUsername(), user.getName(), user.getBio(), user.getLocation());
    }
}
