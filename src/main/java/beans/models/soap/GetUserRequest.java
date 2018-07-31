package beans.models.soap;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getUserRequest",namespace = "http://www.booking.org/service")
public class GetUserRequest {
    @XmlElement(name="id")
    private long      id;
    @XmlElement(name = "email")
    private String email;
    @XmlElement(name = "name")
    private String name;


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
}
