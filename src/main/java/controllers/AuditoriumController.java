package controllers;

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
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AuditoriumController extends  GenericController{

    @GetMapping("/getAuditoriums")
    @ResponseStatus(HttpStatus.OK)   @PermitAll
    public ModelAndView getAuditoriums(){

        List<Auditorium> auditoriums = auditoriumService.getAuditoriums();
        ModelAndView view = new ModelAndView("auditories");
        view.addObject("auditoriums", auditoriums);
        return view;
//        return new ResponseEntity<>(auditoriums,HttpStatus.OK);

    }

    @GetMapping("/getAuditoriumByName/{name}")
    @ResponseStatus(HttpStatus.OK)   @PermitAll
    public ModelAndView getAuditoriumByName(@PathVariable("name") String name) {

        Auditorium auditorium = auditoriumService.getByName(name);
        ModelAndView view = new ModelAndView("auditories");
        List<Auditorium> auditoriums = new ArrayList<>();
        auditoriums.add(auditorium);
        view.addObject("auditoriums", auditoriums);
        return view;
        //        return new ResponseEntity<>(auditorium,HttpStatus.OK);

    }

    @GetMapping("/getSeatsNumber/{name}")
    @ResponseStatus(HttpStatus.OK)   @PermitAll
    public ResponseEntity<Integer> getSeatsNumber(@PathVariable("name") String name) {

        Integer seatsNumber = auditoriumService.getSeatsNumber(name);
        return new ResponseEntity<>(seatsNumber, HttpStatus.OK);

    }

    @GetMapping("/getVipSeats/{name}")
    @ResponseStatus(HttpStatus.OK)   @PermitAll
    public ResponseEntity<List<Integer>> getVipSeats(@PathVariable("name") String name) {

        List<Integer> vipSeats = auditoriumService.getVipSeats(name);
        return new ResponseEntity<>(vipSeats, HttpStatus.OK);

    }

}
