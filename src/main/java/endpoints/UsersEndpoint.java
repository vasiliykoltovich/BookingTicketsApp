package endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


@Endpoint
public class UsersEndpoint extends GenericEndpoint{

    private static final String NAMESPACE_URI = "http://www.booking.org/soap-service";
//    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
//    @ResponsePayload
//    public UserResponse getCountry(@RequestPayload UserRequest request) {
//
//
//        return response;
//    }


}
