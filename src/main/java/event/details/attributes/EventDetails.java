package event.details.attributes;

public  interface EventDetails {
    String getEventID();
    String getEventState();
    String getEventType();
    String getEventHost();
    Long getEventTimeStamp();
    boolean alertNeeded();
    void setAlertNeeded(boolean alert);
}
