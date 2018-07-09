package beans.controllers;

import beans.models.Auditorium;
import beans.models.Event;
import beans.models.Rate;
import beans.services.AuditoriumService;
import beans.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EventController {
    @Autowired
    private Environment environment;

    @Autowired
    private EventService service;
    @Autowired
    private AuditoriumService auditoriumService;

    @GetMapping("/getEventByName/{name}")
    public ModelAndView getEventByName(@PathVariable("name") String name) {
        List<Event> events = service.getByName(name);
        ModelAndView view = new ModelAndView("events");
        view.addObject("events", events);
        return view;

    }

    @GetMapping("/getEvent")
    public ResponseEntity<Event> getEvent(@RequestParam("name") String name,
                                 @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
                                 @RequestParam("auditorium") String auditoriumName) {

        Event event = service.getEvent(name,auditoriumService.getByName(auditoriumName) , date);
        ModelAndView view = new ModelAndView("events");
        view.addObject("events", event);
//        return view;
        return new ResponseEntity<Event>(event, HttpStatus.OK);

    }

    @GetMapping("/getEventByRequest")
    public ResponseEntity<Event> getEvent(@RequestParam("name") String name,
                                 @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
                                 @RequestBody Auditorium auditorium) {

        Event event = service.getEvent(name,auditoriumService.getByName(auditorium.getName()), date);
        ModelAndView view = new ModelAndView("events");
        view.addObject("events", event);
//        return view;
        return new ResponseEntity<Event>(event, HttpStatus.OK);

    }


    @GetMapping("/getAllEvents")
    public ModelAndView getAllEvents() {
        List<Event> events = service.getAll();
        ModelAndView view = new ModelAndView("events");
        view.addObject("events", events);
        return view;
    }
//
//    @PostMapping(path = "/createEvent", consumes = "application/json", produces = "application/json")
//    @ResponseStatus(HttpStatus.CREATED)
//    public ResponseEntity<Event> createEvent(@RequestBody Event eventTemplate) {
//        Event event = service.create(eventTemplate);
//                ModelAndView view = new ModelAndView("events");
//                view.addObject("event", event);
//        return new ResponseEntity<Event>(event, HttpStatus.OK);
//    }

    @PostMapping(path = "/createEvent")
    @ResponseStatus(HttpStatus.CREATED)
    public ModelAndView createEvent(@RequestParam("name") String name,
                                    @RequestParam("rate") Rate rate,
                                    @RequestParam("basePrice") double basePrice,
                                    @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
                                    @RequestParam("auditorium") String auditoriumName) {
        Auditorium auditorium = auditoriumService.getByName(auditoriumName);
        Event event = service.create(new Event(name,rate,basePrice,date,auditorium));
        List<Event> events = new ArrayList<>();
        events.add(event);
        ModelAndView view = new ModelAndView("events");
        view.addObject("events", events);
        return view;
    }

    @DeleteMapping("/deleteEvent")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEvent(@RequestBody Event eventTemplate) {
        service.remove(eventTemplate);
    }

}
