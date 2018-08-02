package endpoints;

import beans.models.Auditorium;
import beans.models.UserAccount;
import beans.models.soap.*;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class WSEndpoint extends GenericEndpoint {

    private static final String NAMESPACE_URI = "http://www.booking.org/service";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequest")
    @ResponsePayload
    public GetUserResponse getUser(@RequestPayload GetUserRequest request) {
        GetUserResponse response = new GetUserResponse();
        if (request.getId()!= null) {
            User user = userService.getById(Long.valueOf(request.getId()));
            response.getUsers().add(user);
            return response;
        } else if (request.getEmail() != null) {
            User user = userService.getUserByEmail(request.getEmail());
            response.getUsers().add(user);
            return response;
        } else if (request.getName() != null) {
            List<User> users = userService.getUsersByName(request.getName());
            response.getUsers().addAll(users);
            return response;
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createUserRequest")
    @ResponsePayload
    public CreateUserResponse createUser(@RequestPayload CreateUserRequest request) {
        CreateUserResponse response = new CreateUserResponse();
        User user = userService.register(request.getUser());
        response.setUser(user);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "fillMoneyRequest")
    @ResponsePayload
    public GetUserResponse fillMoneyForUser(@RequestPayload FillMoneyRequest request) {
        GetUserResponse response = new GetUserResponse();
        User user = userService.getUserByEmail(request.getEmail());

        UserAccount account = userAccountService.getByUser(user);
        userAccountService.fillInAccount(user, request.getSumm());
        response.getUsers().add(user);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteUserRequest")
    @ResponsePayload
    public DeleteUserResponse deleteUser(@RequestPayload DeleteUserRequest request) {
        DeleteUserResponse response = new DeleteUserResponse();
        if (request.getId() != null) {
            User user = userService.getById(Long.valueOf(request.getId()));
            userService.remove(user);
            response.setId(user.getId());
            return response;
        } else if (request.getEmail() != null) {
            User user = userService.getUserByEmail(request.getEmail());
            userService.remove(user);
            response.setId(user.getId());
            return response;
        }
        return response;
    }



    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getEventRequest")
    @ResponsePayload
    public GetEventResponse getEvent(@RequestPayload GetEventRequest request) {
        GetEventResponse response = new GetEventResponse();
         if (request.getName() != null) {
            List <Event> event = eventService.getByName(request.getName());
            response.getEvents().addAll(event);
            return response;
        } else if (request.getDateTime()==null && request.getName()==null) {
            List<Event> events = eventService.getAll();
            response.getEvents().addAll(events);
            return response;
        }
        return response;
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteEventRequest")
    @ResponsePayload
    public DeleteEventResponse deleteEvent(@RequestPayload DeleteEventRequest request) {
        DeleteEventResponse response = new DeleteEventResponse();

        if (request.getEvent().getName() != null & request.getEvent().getDateTime()==null
                & request.getEvent().getRate()==null
                & request.getEvent().getBasePrice()==0
                & request.getEvent().getAuditorium()==null) {
            List <Event> event = eventService.getByName(request.getEvent().getName());
            eventService.remove(event.get(0));
            response.setId(event.get(0).getId());
            return response;

        } else if (request.getEvent().getName()!=null) {
            List <Event> event = eventService.getByName(request.getEvent().getName());
            eventService.remove(event.get(0));
            response.setId(event.get(0).getId());
            return response;
        }
        return response;
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createEventRequest")
    @ResponsePayload
    public CreateEventResponse createEvent(@RequestPayload CreateEventRequest request) {
        CreateEventResponse response = new CreateEventResponse();

            Auditorium auditorium = auditoriumService.getByName(request.getAuditorium());
            Event event = eventService.create(new Event(
                    request.getName(),
                    request.getRate(),
                    request.getBasePrice(),
                    request.getTicketPrice(),
                    request.getDateTime(),
                    auditorium
                    ));
            response.setEvent(event);
            return response;


    }
}
