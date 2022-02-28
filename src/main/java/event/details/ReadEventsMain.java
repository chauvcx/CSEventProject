package event.details;

import event.details.attributes.EventDetails;
import event.details.impl.PersistEventDetailsImpl;
import event.details.persist.PersistEventDetails;

import java.util.List;
import java.util.Map;

public class ReadEventsMain {

    public static void main(String[] args)
    {
        ReadEvents events = new ReadEvents();

        Map<String, List<EventDetails>> eventsMap =  events.readEventFromFile();
        PersistEventDetails persistEvent = new PersistEventDetailsImpl();

        for (Map.Entry<String, List<EventDetails>> entry : eventsMap.entrySet()) {
            List<EventDetails> eventList = entry.getValue();
            eventList.forEach( emp -> persistEvent.persistData( emp) );
        }
            persistEvent.close();
    }

}
