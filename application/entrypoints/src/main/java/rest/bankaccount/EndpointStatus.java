package rest.bankaccount;

public enum EndpointStatus {

    SUCCESS("SUCCESS"),
    ERROR("ERROR");

    private final String description;

    public String getDescription() {
        return description;
    }

    private EndpointStatus(String description) {
        this.description = description;
    }

    public static EndpointStatus customValueOf(String description) {
        for (EndpointStatus status : values())
            if (status.getDescription().equalsIgnoreCase(description)) return status;

        throw new InvalidStatusException(description);
    }
}
