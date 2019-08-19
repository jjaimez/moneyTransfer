package exceptions;

import org.eclipse.jetty.http.HttpStatus;

public class BadRequestException extends ApiException {

    public BadRequestException() {
        super(HttpStatus.BAD_REQUEST_400, "Bad Request");
    }
}