package endpoints;

import beans.services.AuditoriumService;
import beans.services.BookingService;
import beans.services.DiscountService;
import beans.services.EventService;
import beans.services.UserAccountService;
import beans.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class GenericEndpoint {

    @Autowired
    protected EventService eventService;

    @Autowired
    protected BookingService bookingService;

    @Autowired
    protected UserService userService;

    @Autowired
    protected AuditoriumService auditoriumService;

    @Autowired
    protected DiscountService discountService;

    @Autowired
    protected UserAccountService userAccountService;


}
