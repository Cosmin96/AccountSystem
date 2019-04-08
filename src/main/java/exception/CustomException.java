package exception;

import model.CustomResponse;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class CustomException extends WebApplicationException {
    public CustomException(Response.Status status, String message) {
        super(Response.status(status)
                .entity(new CustomResponse(message)).build());
    }
}
