package model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Jeroen0606 on 28-3-2017.
 */
@XmlRootElement
@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllRoles",
                query = "SELECT r FROM Role r"
        ),
        @NamedQuery(
                name = "getRoleByName",
                query = "SELECT r FROM Role r WHERE r.roleName = :roleName"
        )
})
public class Role implements Serializable{
    @Id
    private String roleName;
    private String description;

    @ManyToMany
    @JoinTable(name="USER_GROUP",
    joinColumns = @JoinColumn(name = "roleName",
    referencedColumnName = "roleName"),
    inverseJoinColumns = @JoinColumn(name = "username",
            referencedColumnName = "username"))
    private List<User> users;

    public Role() {
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getUsers() {
        return users;
    }


}
