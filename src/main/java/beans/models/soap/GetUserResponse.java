package beans.models.soap;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getUserResponse",namespace = "http://www.booking.org/service")
public class GetUserResponse {
    @XmlElement(required = true)
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
