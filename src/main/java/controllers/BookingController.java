package controllers;

import beans.models.Event;
import beans.models.Ticket;
import beans.models.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BookingController extends GenericController {

    @GetMapping("/getTicketPrice")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Double> getTicketPrice(@RequestParam("eventName") String eventName,
                                                 @RequestParam("auditorium") String auditorium,
                                                 @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
                                                 @RequestParam("email") String email,
                                                 @RequestParam("seats") String seats) {
        List<Integer> seatList = Arrays.asList(seats.split(",")).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
        User user = userService.getUserByEmail(email);
        double price = bookingService.getTicketPrice(eventName, auditorium, date, seatList, user);
        return new ResponseEntity<>(price, HttpStatus.OK);

    }


    @PostMapping("/bookTicket")
    public ModelAndView bookTicket(@RequestParam("email") String email, @RequestParam("event") String eventName,
                                             @RequestParam("auditorium") String auditorium,
                                             @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
                                             @RequestParam("seats") String seats) {

        User user = userService.getUserByEmail(email);
        List<Integer> seatList = Arrays.asList(seats.split(",")).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());

        Event event = eventService.getEvent(eventName, auditoriumService.getByName(auditorium), date);
        double eventPrice = bookingService.getTicketPrice(event.getName(), event.getAuditorium().getName(), event.getDateTime(), seatList, user);
        Ticket booked = bookingService.bookAndReturnTicket(user, new Ticket(event, LocalDateTime.now(), seatList, user, eventPrice));
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(booked);
        ModelAndView view = new ModelAndView("tickets");
        view.addObject("tickets", tickets);
        return view;
//        return new ResponseEntity<>(booked, HttpStatus.CREATED);

    }

    @GetMapping("/getTicketForEvent")
    public ModelAndView getTicketForEvent(@RequestParam("event") String eventName,
                                                          @RequestParam("auditorium") String auditorium,
                                                          @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {

        List<Ticket> tickets = bookingService.getTicketsForEvent(eventName, auditorium, date);
        ModelAndView view = new ModelAndView("tickets");
        view.addObject("tickets", tickets);
//        return new ResponseEntity<>(tickets, HttpStatus.OK);
        return  view;

    }

}
