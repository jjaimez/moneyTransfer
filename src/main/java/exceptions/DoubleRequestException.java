package exceptions;

import org.eclipse.jetty.http.HttpStatus;

public class DoubleRequestException extends ApiException {

    public DoubleRequestException() {
        super(HttpStatus.LOCKED_423, "The resource is in use, please try again later");
    }
}
