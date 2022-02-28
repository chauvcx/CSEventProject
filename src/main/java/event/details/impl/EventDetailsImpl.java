package event.details.impl;

import event.details.attributes.EventDetails;

public class EventDetailsImpl implements EventDetails
{
    private String eventId;
    private String eventState;
    private String eventType;
    private String eventHost;
    private Long eventTimeStamp;
    private boolean alertNeeded;

    public EventDetailsImpl(String eventId, String eventState, String eventType, String eventHost, Long eventTimeStamp) {
        this.eventId = eventId;
        this.eventState = eventState;
        this.eventType = eventType;
        this.eventHost = eventHost;
        this.eventTimeStamp = eventTimeStamp;
    }

    @Override
    public String getEventID() {
        return eventId;
    }

    @Override
    public String getEventState() {
        return eventState;
    }

    @Override
    public String getEventType() {
        return eventType;
    }

    @Override
    public String getEventHost() {
        return eventHost;
    }

    @Override
    public Long getEventTimeStamp() {
        return eventTimeStamp;
    }

    @Override
    public boolean alertNeeded() {
        return alertNeeded;
    }

    @Override
    public void setAlertNeeded(boolean alert) {
        alertNeeded = alert;
    }

}
