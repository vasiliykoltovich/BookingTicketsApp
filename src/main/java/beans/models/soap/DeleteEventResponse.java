package beans.models.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "deleteEventResponse",namespace = "http://www.booking.org/service")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteEventResponse",namespace = "http://www.booking.org/service")
public class DeleteEventResponse {
    @XmlElement
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
