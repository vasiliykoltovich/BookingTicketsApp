package beans.models.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "createUserRequest",namespace = "http://www.booking.org/service")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createUserRequest",namespace = "http://www.booking.org/service")
public class CreateUserRequest {
    @XmlElement(required = true)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}