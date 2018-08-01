package controllers;

import beans.models.rest.TicketHolder;
import beans.models.soap.Event;
import beans.models.Ticket;
import beans.models.soap.User;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookingController extends GenericController {

    @GetMapping("/getTicketPrice")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('BOOKING_MANAGER') and hasAuthority('REGISTERED_USER') ")
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


    @PostMapping(value = "/bookTicket", params = {"email", "event", "auditorium", "date", "seats"})
    @PreAuthorize("hasAuthority('BOOKING_MANAGER') and hasAuthority('REGISTERED_USER') ")
    public ModelAndView bookTicket(@RequestParam("email") String email, @RequestParam("event") String eventName,
                                   @RequestParam("auditorium") String auditorium,
                                   @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
                                   @RequestParam("seats") String seats) {

        User user = userService.getUserByEmail(email);
        List<Integer> seatList = Arrays.asList(seats.split(",")).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());

        Event event = eventService.getEvent(eventName, auditoriumService.getByName(auditorium), date);
        double eventPrice = bookingService.getTicketPrice(event.getName(), event.getAuditorium().getName(), event.getDateTime(), seatList, user);
        Ticket booked = bookingService.bookAndReturnTicket(user, new Ticket(event, LocalDateTime.now(), seatList, user, eventPrice));
        ModelAndView view = new ModelAndView("tickets");
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(booked);
        view.addObject("tickets", tickets);
        return view;
    }


    @GetMapping(value = "/getTicketForEvent", params = {"event", "auditorium", "date"})
    @PreAuthorize("hasAuthority('BOOKING_MANAGER') and hasAuthority('REGISTERED_USER') ")
    public ModelAndView getTicketForEvent(@RequestParam("event") String eventName,
                                          @RequestParam("auditorium") String auditorium,
                                          @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {

        List<Ticket> tickets = bookingService.getTicketsForEvent(eventName, auditorium, date);
        ModelAndView view = new ModelAndView("tickets");
        view.addObject("tickets", tickets);
        return view;

    }


    @PreAuthorize("hasAuthority('BOOKING_MANAGER') and hasAuthority('REGISTERED_USER') ")
    @GetMapping(value = "/getTicketForEvent", params = {"event", "auditorium", "date"},
            produces = MediaType.APPLICATION_JSON_VALUE,
            headers = {"accept"})
    public ResponseEntity<Object> getTicketForEvent(
            @Nullable @RequestHeader("accept") String accept,
            @RequestParam("event") String eventName,
            @RequestParam("auditorium") String auditorium,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
            HttpServletResponse response) {
        List<Ticket> tickets = bookingService.getTicketsForEvent(eventName, auditorium, date);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }
    

}
