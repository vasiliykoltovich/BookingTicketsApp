package beans.models.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "deleteEventRequest",namespace = "http://www.booking.org/service")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteEventRequest",namespace = "http://www.booking.org/service")
public class DeleteEventRequest {

    @XmlElement
    private Event event;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
