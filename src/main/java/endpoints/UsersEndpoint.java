package endpoints;

import beans.models.soap.User;
import beans.models.soap.GetUserRequest;
import beans.models.soap.GetUserResponse;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;


@Endpoint
public class UsersEndpoint extends GenericEndpoint{

    private static final String NAMESPACE_URI = "http://www.booking.org/service";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequest")
    @ResponsePayload
    public GetUserResponse getUser(@RequestPayload GetUserRequest request) {
        GetUserResponse response = new GetUserResponse();
        try {
            if (request.getId() != 0) {
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
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }



}
