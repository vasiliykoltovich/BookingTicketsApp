package beans.models.soap;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "getUserResponse",namespace = "http://www.booking.org/service")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getUserResponse",namespace = "http://www.booking.org/service")
public class GetUserResponse {
    @XmlElement(required = true)
    private List<User> users=new ArrayList<>();

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
