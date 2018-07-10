package controllers;

import beans.models.Ticket;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import util.PdfView;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PdfController extends GenericController {

    @GetMapping("/pdf/getTicketForEvent")
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
//        return new ModelAndView(new PdfView(), model);
        throw  new RuntimeException("Expected message");

    }

}
