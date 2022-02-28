package event.details.persist;

import event.details.attributes.EventDetails;

public interface PersistEventDetails {

    void persistData(EventDetails event);

    void close() ;
}
