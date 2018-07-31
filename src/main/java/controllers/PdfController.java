package controllers;

import beans.models.soap.Event;
import beans.models.Ticket;
import beans.models.soap.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import util.PdfView;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

//@Controller
@RestController
public class PdfController extends GenericController {

    @PreAuthorize("hasAuthority('BOOKING_MANAGER')")
    @GetMapping(value = "/getTicketForEvent", produces = MediaType.APPLICATION_PDF_VALUE,params = {"event","auditorium","date"},
            headers = {"accept"})
    public ModelAndView getTicketForEvent(@Nullable @RequestHeader("accept") String accept, @RequestParam("event") String eventName,
                                          @RequestParam("auditorium") String auditorium,
                                          @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {

        List<Ticket> tickets = bookingService.getTicketsForEvent(eventName, auditorium, date);
        ModelAndView view = null;
        if (accept != null && accept.equals(MediaType.APPLICATION_PDF_VALUE)) {
            Map<String, Object> model = new HashMap<>();
            model.put("tickets", tickets);
            view = new ModelAndView(new PdfView(), model);
        } else {
            view = new ModelAndView("tickets");
            view.addObject("tickets", tickets);
        }

        return view;

    }


    @GetMapping(value = "/getBookedTicketsByUser", produces = MediaType.APPLICATION_PDF_VALUE,params = {"email"},headers = {"accept"})
    @PreAuthorize("hasAuthority('BOOKING_MANAGER')")
    public ModelAndView getBookedTicketsByUser(@Nullable @RequestHeader("accept") String accept, @RequestParam("email") String email) {
        List<Ticket> tickets = bookingService.getTicketsByUser(email);
        ModelAndView view = null;
        if (accept != null && accept.equals(MediaType.APPLICATION_PDF_VALUE)) {
            Map<String, Object> model = new HashMap<>();
            model.put("tickets", tickets);
            view = new ModelAndView(new PdfView(), model);
        } else {
            view = new ModelAndView("tickets");
            view.addObject("tickets", tickets);
        }

        return view;
    }

    @PostMapping(value = "/bookTicket",headers = {"accept","content-type"},
            params = {"email","event","auditorium","date","seats"},
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('REGISTERED_USER','BOOKING_MANAGER')")
    public ResponseEntity<Ticket> bookTicket(Model model, @Nullable @RequestHeader("accept") String accept,
                             @Nullable @RequestHeader("content-type") String contentType,
                             @RequestParam("email") String email,
                             @RequestParam("event") String eventName, @RequestParam("auditorium") String auditorium,
                             @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
                             @RequestParam("seats") String seats) {

        User user = userService.getUserByEmail(email);
        List<Integer> seatList = Arrays.asList(seats.split(",")).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
        Event event = eventService.getEvent(eventName, auditoriumService.getByName(auditorium), date);
        double eventPrice = bookingService.getTicketPrice(event.getName(), event.getAuditorium().getName(), event.getDateTime(), seatList, user);
        Ticket booked = bookingService.bookAndReturnTicket(user, new Ticket(event, LocalDateTime.now(), seatList, user, eventPrice));
//       return tickets;
//        model.
        return new ResponseEntity<>(booked,HttpStatus.CREATED);

    }






    @PostMapping(value = "/bookTicket",params = {"email","event","auditorium","date","seats"},headers = {"accept"},produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('REGISTERED_USER','BOOKING_MANAGER')")
    public ModelAndView bookTicket(@Nullable @RequestHeader("accept") String accept, @RequestParam("email") String email,
                                   @RequestParam("event") String eventName, @RequestParam("auditorium") String auditorium,
                                   @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
                                   @RequestParam("seats") String seats) {

        User user = userService.getUserByEmail(email);
        List<Integer> seatList = Arrays.asList(seats.split(",")).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
        Event event = eventService.getEvent(eventName, auditoriumService.getByName(auditorium), date);
        double eventPrice = bookingService.getTicketPrice(event.getName(), event.getAuditorium().getName(), event.getDateTime(), seatList, user);
        Ticket booked = bookingService.bookAndReturnTicket(user, new Ticket(event, LocalDateTime.now(), seatList, user, eventPrice));
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(booked);
        ModelAndView view = null;
        if (accept != null && accept.equals(MediaType.APPLICATION_PDF_VALUE)) {
            Map<String, Object> model = new HashMap<>();
            model.put("tickets", tickets);
            view = new ModelAndView(new PdfView(), model);
        } else {
            view = new ModelAndView("tickets");
            view.addObject("tickets", tickets);
        }
        return view;

    }


}
