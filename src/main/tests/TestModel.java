import model.Tweet;
import model.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jeroen0606 on 16-3-2017.
 */
public class TestModel {
    private User jeroen;
    private User evelien;
    private User thijs;
    private User dyllian;
    private User johan;

    @Before
    public void initiate() {
        jeroen = new User("jeroen0606@gmail.com", "test1");
        evelien = new User("evelien@gmail.com", "test2");
        thijs = new User("thijs@gmail.com","test3");
        dyllian = new User("dyllian@gmail.com","test4");
        johan = new User("johan@gmail.com","test5");
    }

    @Test
    public void testAddFollowing() {
        jeroen.followUser(dyllian);
        dyllian.followUser(thijs);
        dyllian.followUser(evelien);

        assertEquals(1, jeroen.getFollowing().size());
        assertEquals(2, dyllian.getFollowing().size());
    }

    @Test
    public void testCreateKweet() {
        jeroen.createTweet("Leuke dag vandaag");
        jeroen.createTweet("Goedenacht iedereen");
        dyllian.createTweet("Hey Jeroen, goede dag gehad?");
        dyllian.createTweet("geen zin meer....");
        dyllian.createTweet("weer naar school....");

        assertEquals(2, jeroen.getTweets().size());
        assertEquals(3, dyllian.getTweets().size());
    }

    @Test
    public void testLikes() {
        Tweet tweet1 = jeroen.createTweet("Dit wordt een interessante test!");
        Tweet tweet2 = jeroen.createTweet("Nog altijd geen test gedaan....");
        jeroen.likeTweet(tweet1);
        jeroen.likeTweet(tweet2);
        dyllian.likeTweet(tweet1);

        assertEquals(tweet1.getAmountOfLikes(), 2);
        assertEquals(tweet2.getAmountOfLikes(), 1);
    }

    @Test
    public void testFollowers() {
        thijs.followUser(evelien);
        evelien.addFollower(thijs);
        dyllian.followUser(evelien);
        evelien.addFollower(dyllian);
        johan.followUser(evelien);
        evelien.addFollower(johan);
        evelien.followUser(jeroen);
        jeroen.addFollower(evelien);

        assertEquals(3, evelien.getFollowers().size());
        assertEquals(1, jeroen.getFollowers().size());
    }
}
