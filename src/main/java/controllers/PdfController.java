package controllers;

import beans.models.Event;
import beans.models.Ticket;
import beans.models.User;
import beans.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import util.PdfView;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class PdfController extends GenericController {

    @GetMapping(value = "/pdf/getTicketForEvent",produces = MediaType.APPLICATION_PDF_VALUE)
    public ModelAndView getTicketForEvent(@RequestParam("event") String eventName, @RequestParam("auditorium") String auditorium,
                                          @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {

        List<Ticket> tickets = bookingService.getTicketsForEvent(eventName, auditorium, date);
        Map<String, Object> model = new HashMap<>();
        model.put("tickets", tickets);

        return new ModelAndView(new PdfView(), model);

    }

    @GetMapping(value = "/pdf/getBookedTicketsByUser", produces = MediaType.APPLICATION_PDF_VALUE)
    public ModelAndView getBookedTicketsByUser(@RequestParam("email") String email) {
        List<Ticket> bookedTickets = bookingService.getTicketsByUser(email);
        Map<String, Object> model = new HashMap<>();
        model.put("tickets", bookedTickets);
        return new ModelAndView(new PdfView(), model);

    }

    @PostMapping("/pdf/bookTicket")
    @ResponseStatus(HttpStatus.CREATED)
    public ModelAndView bookTicket(@RequestParam("email") String email, @RequestParam("event") String eventName,
                                   @RequestParam("auditorium") String auditorium,
                                   @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
                                   @RequestParam("seats") String seats) {

        User user = userService.getUserByEmail(email);
        List<Integer> seatList = Arrays.asList(seats.split(",")).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());

        Event event = eventService.getEvent(eventName, auditoriumService.getByName(auditorium), date);
        double eventPrice = bookingService.getTicketPrice(event.getName(), event.getAuditorium().getName(), event.getDateTime(), seatList, user);
        Ticket booked = bookingService.bookTicket(user, new Ticket(event, LocalDateTime.now(), seatList, user, eventPrice));
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(booked);
        Map<String, Object> model = new HashMap<>();
        model.put("tickets", tickets);
        return new ModelAndView(new PdfView(), model);
//        return new ResponseEntity<>(booked, HttpStatus.CREATED);

    }

}
