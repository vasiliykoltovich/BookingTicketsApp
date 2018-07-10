package controllers;

import beans.models.Auditorium;
import beans.models.Event;
import beans.models.Rate;
import beans.models.Ticket;
import beans.models.User;
import beans.services.AuditoriumService;
import beans.services.EventService;
import beans.services.UploadService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class EventController extends GenericController {

    @Autowired
    private UploadService uploadService;

    @Value("${upload.events.path}")
    private String path;

    @GetMapping("/getEventByName/{name}")
    public ModelAndView getEventByName(@PathVariable("name") String name) {
        List<Event> events = eventService.getByName(name);
        ModelAndView view = new ModelAndView("events");
        view.addObject("events", events);
        return view;

    }

    @GetMapping("/getEvent")
    public ResponseEntity<Event> getEvent(@RequestParam("name") String name,
                                 @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
                                 @RequestParam("auditorium") String auditoriumName) {

        Event event = eventService.getEvent(name,auditoriumService.getByName(auditoriumName) , date);
        ModelAndView view = new ModelAndView("events");
        view.addObject("events", event);
//        return view;
        return new ResponseEntity<Event>(event, HttpStatus.OK);

    }

    @GetMapping("/getEventByRequest")
    public ResponseEntity<Event> getEvent(@RequestParam("name") String name,
                                 @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
                                 @RequestBody Auditorium auditorium) {

        Event event = eventService.getEvent(name,auditoriumService.getByName(auditorium.getName()), date);
        ModelAndView view = new ModelAndView("events");
        view.addObject("events", event);
//        return view;
        return new ResponseEntity<Event>(event, HttpStatus.OK);

    }


    @GetMapping("/getAllEvents")
    public ModelAndView getAllEvents() {
        List<Event> events = eventService.getAll();
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
        Event event = eventService.create(new Event(name,rate,basePrice,date,auditorium));
        List<Event> events = new ArrayList<>();
        events.add(event);
        ModelAndView view = new ModelAndView("events");
        view.addObject("events", events);
        return view;
    }

    @DeleteMapping("/deleteEvent")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEvent(@RequestBody Event eventTemplate) {
        eventService.remove(eventTemplate);
    }

    @GetMapping(path = "/getForDateRange")
    @ResponseStatus(HttpStatus.CREATED)
    public ModelAndView getForDateRange(
                                    @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                    @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        List<Event> events = eventService.getForDateRange(from,to);
        ModelAndView view = new ModelAndView("events");
        view.addObject("events", events);
        return view;
    }

    @GetMapping(path = "/getNextEvents")
    @ResponseStatus(HttpStatus.CREATED)
    public ModelAndView getNextEvents(
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        List<Event> events = eventService.getNextEvents(to);
        ModelAndView view = new ModelAndView("events");
        view.addObject("events", events);
        return view;
    }

    @PutMapping(path = "/assignAuditorium")
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


    @GetMapping("/loadFiles")
    public ModelAndView uploading() {
        return new ModelAndView("upload");
    }

    @PostMapping("/loadEvents")
    public ModelAndView loadEvents(@RequestParam MultipartFile file,
                                             RedirectAttributes redirectAttributes) {
        ModelAndView view = new ModelAndView("events");
        List<Event> newEvents=new ArrayList<>();

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return view;
        }

        try {
            List<Event> list= uploadService.uploadEventFile(file);
            for(Event ev:list){
                Auditorium auditorium= auditoriumService.getByName(ev.getAuditorium().getName());
                ev.setAuditorium(auditorium);
                newEvents.add(eventService.create(ev));
            }

        } catch (IOException e) {
            throw  new RuntimeException(e);
        }

     view.addObject("events", newEvents);
        return getAllEvents();

    }

}
