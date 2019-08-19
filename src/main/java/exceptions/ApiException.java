package exceptions;

public class ApiException extends RuntimeException {

    int status;
    String msg;

    public ApiException(int status, String msg){
        super(msg, (Throwable)null);
        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

