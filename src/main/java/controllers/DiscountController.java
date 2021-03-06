package controllers;

import beans.models.Event;
import beans.models.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@Controller
public class DiscountController extends GenericController {


    @GetMapping("/getDiscount")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Double> getTicketPrice(@RequestParam("email") String email,
                                                 @RequestParam("event") String eventName,
                                                 @RequestParam("auditorium") String auditorium,
                                                 @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {

        Event event = eventService.getEvent(eventName,auditoriumService.getByName(auditorium),date);
        User user = userService.getUserByEmail(email);
        double price = discountService.getDiscount(user,event);
        return new ResponseEntity<>(price, HttpStatus.OK);

    }


}
