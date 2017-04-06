package managedBeans;

import service.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

/**
 * Created by Jeroen0606 on 4-4-2017.
 */
@Named("registerBean")
@RequestScoped
public class RegisterBean {
    @Inject
    private UserService userService;

    private String username;
    private String password;

    private ExternalContext externalContext;

    @PostConstruct
    public void init() {
        externalContext = FacesContext.getCurrentInstance().getExternalContext();
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

    public void register() {
        userService.createUser(username, password);
        userService.addRole("regulars", username);
        try {
            externalContext.redirect("home.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
