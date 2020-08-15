package rest.bankaccount;

public class Response {

    private Dto data;
    private EndpointStatus status;
    private String message;

    public Dto getData() {
        return data;
    }

    public void setData(Dto data) {
        this.data = data;
    }

    public EndpointStatus getStatus() {
        return status;
    }

    public void setStatus(EndpointStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
