package beans.models.soap;

import javax.xml.bind.annotation.*;

@XmlType(name = "createEventResponse",namespace = "http://www.booking.org/service")
@XmlRootElement(name = "createEventResponse" ,namespace = "http://www.booking.org/service" )
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateEventResponse {
    @XmlElement
    private Event event;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
