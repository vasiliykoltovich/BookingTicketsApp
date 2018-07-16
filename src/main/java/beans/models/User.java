package beans.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;


public class User {

    private long      id;
    private String email;
    private String name;
    private LocalDate birthday;
    @NotBlank
    @Size(max = 100)
    private String password;

//    private Set<Role> roles = Stream.of(new Role(RoleName.REGISTERED_USER)).collect(Collectors.toCollection(HashSet::new));
    private String roles = "REGISTERED_USER";

    public User() {
    }

    public User(long id, String email, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.birthday = birthday;
    }

    public User(long id, String email, String name, LocalDate birthday, @NotBlank @Size(max = 100) String password, String roles) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.birthday = birthday;
        this.password = password;

    }

    public User(String email, String name, LocalDate birthday) {
        this(-1, email, name, birthday);
    }

    public User withId(long id) {
        return new User(id, email, name, birthday);
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

    public void setRoles(String roles) {
        if(roles!=null){
            this.roles=String.join(",",this.roles,roles);
        }
    }

    public void addRole(String role){
        if(!this.roles.contains(role)){
            this.roles=String.join(",",this.roles,role);
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
               '}';
    }
}
