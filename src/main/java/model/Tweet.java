package model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jeroen0606 on 15-3-2017.
 */
@XmlRootElement
@Entity
@NamedQueries({
        @NamedQuery(
                name = "getTweetById",
                query = "SELECT t FROM Tweet t WHERE t.id = :id"
        ),
        @NamedQuery(
                name = "getAllTweets",
                query = "SELECT t FROM Tweet t"
        )
})
public class Tweet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String message;
    private Date date;

    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "tweet")
    private List<Love> loves;

    public Tweet() {
    }

    public Tweet(String message, User user) {
        this.message = message;
        this.user = user;
        setLikes();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Love> getLoves() {
        return loves;
    }

    public void setLikes() {
        this.loves = new ArrayList<Love>();
    }

    public int getAmountOfLikes() {
        return loves.size();
    }

    public void addLike(User user, Tweet tweet) {
        loves.add(new Love(tweet, user));
    }
}
