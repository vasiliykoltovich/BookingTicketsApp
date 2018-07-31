package controllers;

import beans.models.soap.Auditorium;
import beans.models.soap.Event;
import beans.models.Rate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AuditoriumController extends  GenericController{

    @GetMapping("/getAuditoriums")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('REGISTERED_USER','BOOKING_MANAGER')")
    public ModelAndView getAuditoriums(){

        List<Auditorium> auditoriums = auditoriumService.getAuditoriums();
        ModelAndView view = new ModelAndView("auditories");
        view.addObject("auditoriums", auditoriums);
        return view;

    }

    @GetMapping("/getAuditoriumByName/{name}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('REGISTERED_USER','BOOKING_MANAGER')")
    public ModelAndView getAuditoriumByName(@PathVariable("name") String name) {

        Auditorium auditorium = auditoriumService.getByName(name);
        ModelAndView view = new ModelAndView("auditories");
        List<Auditorium> auditoriums = new ArrayList<>();
        auditoriums.add(auditorium);
        view.addObject("auditoriums", auditoriums);
        return view;

    }

    @GetMapping("/getSeatsNumber/{name}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('REGISTERED_USER','BOOKING_MANAGER')")
    public ResponseEntity<Integer> getSeatsNumber(@PathVariable("name") String name) {

        Integer seatsNumber = auditoriumService.getSeatsNumber(name);
        return new ResponseEntity<>(seatsNumber, HttpStatus.OK);

    }

    @GetMapping("/getVipSeats/{name}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('REGISTERED_USER','BOOKING_MANAGER')")
    public ResponseEntity<List<Integer>> getVipSeats(@PathVariable("name") String name) {

        List<Integer> vipSeats = auditoriumService.getVipSeats(name);
        return new ResponseEntity<>(vipSeats, HttpStatus.OK);

    }


    @PutMapping(path = "/assignAuditorium")
    @PreAuthorize("hasAnyAuthority('REGISTERED_USER','BOOKING_MANAGER')")
    @ResponseStatus(HttpStatus.CREATED)
    public ModelAndView assignAuditorium(@RequestParam("event") String eventName, @RequestParam("auditorium") String auditoriumName,
                                         @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        Auditorium auditorium = auditoriumService.getByName(auditoriumName);
        Event event = eventService.getEvent(eventName,auditorium,date);

        if(eventService.getEvent(eventName,auditorium,date)!=null){
            event=eventService.assignAuditorium(event,auditorium,date);
        } else{
            event=eventService.create(new Event(eventName,Rate.HIGH,68,date,auditorium));
        }

        List<Event> events = new ArrayList<>();
        events.add(event);
        ModelAndView view = new ModelAndView("events");
        view.addObject("events", events);
        return view;
    }

}
