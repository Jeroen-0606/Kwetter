package dao;

import model.Tweet;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by Jeroen0606 on 15-3-2017.
 */
@Local
public interface TweetDAO {
    Tweet getTweet(int id);
    List<Tweet> getAllTweets();
}
