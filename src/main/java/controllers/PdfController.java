package controllers;

import beans.models.soap.Event;
import beans.models.Ticket;
import beans.models.rest.TicketHolder;
import beans.models.soap.Event;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import util.PdfView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
public class PdfController extends GenericController {

    @PreAuthorize("hasAuthority('BOOKING_MANAGER') and hasAuthority('REGISTERED_USER') ")
    @GetMapping(value = "/getTicketForEvent", produces = MediaType.APPLICATION_PDF_VALUE,params = {"event","auditorium","date"},headers = {"accept"})
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

    @PreAuthorize("hasAnyAuthority('REGISTERED_USER','BOOKING_MANAGER')")
    @GetMapping(value = "/getTicketForEvent",produces = MediaType.APPLICATION_JSON_VALUE,params = {"event","auditorium","date"})
    public ResponseEntity<Object> getTicketForEvent(
                                           @RequestParam("event") String eventName,
                                                      @RequestParam("auditorium") String auditorium,
                                                      @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date
                                                   ) {
        List<Ticket> tickets = bookingService.getTicketsForEvent(eventName, auditorium, date);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }




    @GetMapping(value = "/getBookedTicketsByUser", produces = MediaType.APPLICATION_PDF_VALUE,params = {"email"},headers = {"accept"})
    @PreAuthorize("hasAuthority('BOOKING_MANAGER') and hasAuthority('REGISTERED_USER') ")
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



    @PostMapping(value = "/bookTicket",params = {"email","event","auditorium","date","seats"},headers = {"accept"},produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('BOOKING_MANAGER') and hasAuthority('REGISTERED_USER') ")
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

    @PostMapping(value = "/bookTicket",headers = {"accept"})
    @PreAuthorize("hasAnyAuthority('REGISTERED_USER','BOOKING_MANAGER')")
    public void bookTicket(@Nullable @RequestHeader("accept") String accept,
                                   @RequestBody TicketHolder holder,HttpServletResponse response) throws DocumentException, IOException {

        User user = userService.getUserByEmail(holder.getEmail());
        List<Integer> seatList = Arrays.asList(holder.getSeats().split(",")).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
        Event event = eventService.getEvent(holder.getEventName(), auditoriumService.getByName(holder.getAuditorium()), holder.getDate());
        double eventPrice = bookingService.getTicketPrice(event.getName(), event.getAuditorium().getName(), event.getDateTime(), seatList, user);
        Ticket booked = bookingService.bookAndReturnTicket(user, new Ticket(event, LocalDateTime.now(), seatList, user, eventPrice));
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(booked);

        List<String> ticketsList = new ArrayList<>();
        if (tickets != null && !tickets.isEmpty()) {
            for (Ticket t : tickets) {
                ticketsList.add(t.toString());
            }

            Table table = new Table(1);
            table.addCell("Ticket");

            for (String t : ticketsList) {

                table.addCell(t);

            }

            Document doc = new Document();
            PdfWriter.getInstance(doc, response.getOutputStream());
            doc.open();

            doc.add(table);
            doc.close();

        }

    }

}
