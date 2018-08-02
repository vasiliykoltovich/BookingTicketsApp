package beans.models.soap;

import beans.models.Rate;
import util.DateTimeAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;

@XmlRootElement(name = "createEventRequest",namespace = "http://www.booking.org/service")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createEventRequest",namespace = "http://www.booking.org/service")
public class CreateEventRequest {
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "rate")
    private Rate rate;
    @XmlElement(name = "basePrice")
    private double        basePrice;
    @XmlElement(name = "dateTime")
    @XmlJavaTypeAdapter(value = DateTimeAdapter.class)
    private LocalDateTime dateTime;
    @XmlElement(name = "auditorium")
    private String auditorium;
    @XmlElement(name = "ticketPrice")
    private double ticketPrice;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(String auditorium) {
        this.auditorium = auditorium;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}
