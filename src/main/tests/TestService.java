import dao.TweetDAO;
import dao.UserDAO;
import dao.UserDAOImpl;
import model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.UserService;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Jeroen0606 on 16-3-2017.
 */
public class TestService {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserDAO userDAO;

    @Mock
    private TweetDAO tweetDAO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetUser () {
        String mockUsername = "mockUsername";
        User mockUser = mock(User.class);


        when(userDAO.getUser(mockUsername)).thenReturn(mockUser);
        User result = userService.getUser(mockUsername);

        verify(userDAO, times(1)).getUser(mockUsername);
        assertEquals(result, mockUser);
    }
}
