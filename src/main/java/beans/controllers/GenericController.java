package beans.controllers;

import beans.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

//@ControllerAdvice
public abstract class GenericController {

    @Autowired
    protected  Environment environment;

    @Autowired
    protected  EventService eventService;

    @Autowired
    protected  BookingService bookingService;

    @Autowired
    protected UserService userService;

    @Autowired
    protected  AuditoriumService auditoriumService;

    @Autowired
    protected  DiscountService discountService;


    @ExceptionHandler(Exception.class)
    public ModelAndView errorHandle(Exception ex) {
        ModelAndView view = new ModelAndView("exceptions");
        view.addObject("exception", ex);
        return view;
    }

}
