package model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeroen0606 on 15-3-2017.
 */
@XmlRootElement
@Entity
@NamedQueries({
        @NamedQuery(
                name = "findUserByUsername",
                query = "SELECT u FROM User u WHERE u.username = :username"
        )
})
public class User implements Serializable {
    @Id
    private String username;
    private String password;
    private String name;
    private String photo;
    private String bio;
    private String location;
    private String website;

    @ManyToMany
    private List<User> followers;
    @ManyToMany(mappedBy = "followers")
    private List<User> following;
    @OneToMany
    private List<Tweet> tweets;
    @ManyToMany(mappedBy = "users")
    private List<Role> roles;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        setRoles();
        setFollowers();
        setFollowing();
        setTweets();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles() {
        roles = new ArrayList<Role>();
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers() {
        this.followers = new ArrayList<User>();
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing() {
        this.following = new ArrayList<User>();
    }

    public void setTweets() {
        this.tweets = new ArrayList<Tweet>();
    }

    public List<Tweet> getTweets() {
        return tweets;
    }

    public Tweet createTweet(String message){
        Tweet tweet = new Tweet(message, this);
        tweets.add(tweet);
        return tweet;
    }

    public void likeTweet(Tweet tweet){
        tweet.addLike(this, tweet);
    }

    public void followUser(User user){
        following.add(user);
    }

    public void addFollower(User user){
        followers.add(user);
    }

    public Tweet getTweet(int id) {
        for (Tweet tweet: tweets) {
            if(tweet.getId() == id) {
                return tweet;
            }
        }
        return null;
    }


}
