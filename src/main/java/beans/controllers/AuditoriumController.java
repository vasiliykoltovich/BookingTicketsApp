package beans.controllers;

import beans.models.Auditorium;
import beans.services.AuditoriumService;
import beans.services.BookingService;
import beans.services.EventService;
import beans.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Controller
public class AuditoriumController {
    @Autowired
    private Environment environment;

    @Autowired
    private EventService eventService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;
    @Autowired
    private AuditoriumService auditoriumService;

    @GetMapping("/getAuditoriums")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Auditorium>> getAuditoriums(){

        List<Auditorium> auditoriums = auditoriumService.getAuditoriums();
        return new ResponseEntity<>(auditoriums,HttpStatus.OK);

    }

    @GetMapping("/getAuditoriumByName/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Auditorium> getAuditoriumByName(@PathVariable("name") String name){

        Auditorium auditorium = auditoriumService.getByName(name);
        return new ResponseEntity<>(auditorium,HttpStatus.OK);

    }

    @GetMapping("/getSeatsNumber/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Integer> getSeatsNumber(@PathVariable("name") String name){

        Integer  seatsNumber = auditoriumService.getSeatsNumber(name);
        return new ResponseEntity<>(seatsNumber,HttpStatus.OK);

    }

    @GetMapping("/getVipSeats/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Integer>> getVipSeats(@PathVariable("name") String name){

        List<Integer>  vipSeats = auditoriumService.getVipSeats(name);
        return new ResponseEntity<>(vipSeats,HttpStatus.OK);

    }


}
