package beans.models.soap;

import util.LocalDateAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
@XmlType(name = "User",namespace = "http://www.booking.org/service/user")
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlSeeAlso(UserAccount.class)
public class User {
    @XmlAttribute(name = "id")
    private long      id;
    @XmlElement(name = "email")
    private String email;
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "birthday")
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate birthday;
    @XmlElement(name = "password")
    private String password;
    @XmlElement(name = "roles")
    private String roles = "REGISTERED_USER";
    @XmlElement(name = "userAccount" ,namespace="http://www.booking.org/service/account")
//    @XmlTransient
    @XmlSchemaType(name = "userAccount")
    private UserAccount userAccount;

    public User() {
    }

    public User(long id, String email, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.birthday = birthday;
    }

    public User(long id, String email, String name, LocalDate birthday,String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.birthday = birthday;
        this.password = password;
    }

    public User(long id, String email, String name, LocalDate birthday,String password,String role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.birthday = birthday;
        this.password = password;
        this.setRoles(role);
    }

    public User(String email, String name, LocalDate birthday) {
        this(-1, email, name, birthday);
    }

    public User(String email, String name, LocalDate birthday,String password) {
        this(-1, email, name, birthday,password);
    }
    public User(String email, String name, LocalDate birthday,String password,String role) {
        this(-1, email, name, birthday,password,role);
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public User withId(long id) {
        return new User(id, email, name, birthday,password,roles);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String role) {
        if(role!=null &&!this.roles.contains(role) && !role.equals(this.roles)){
           this.roles=String.join(",",this.roles,role);
           this.roles= String.join(",",new HashSet<>(Arrays.asList(roles.split(","))));
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        User user = (User) o;

        if (id != user.id)
            return false;
        if (email != null ? !email.equals(user.email) : user.email != null)
            return false;
        if (name != null ? !name.equals(user.name) : user.name != null)
            return false;
        return birthday != null ? birthday.equals(user.birthday) : user.birthday == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", password='" + password + '\'' +
                ", roles='" + roles + '\'' +
                '}';
    }
}
