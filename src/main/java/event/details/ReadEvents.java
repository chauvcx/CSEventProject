package event.details;

import event.details.attributes.EventDetails;
import event.details.impl.EventDetailsImpl;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class ReadEvents {
    private static final Logger LOGGER = Logger.getLogger(ReadEvents.class.getName());

    public Map<String, List<EventDetails>> readEventFromFile()
    {
        LOGGER.info("Reading event details from logfile.txt");
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
        Map<String, List<EventDetails>> eventMap = new HashMap<>();

        try (FileInputStream inputStream = new FileInputStream("C:\\dev\\logfile.txt");
             Scanner sc = new Scanner(inputStream, "UTF-8");)
        {
            //Using scanner to avoid loading entire file in memory which could lead to out of memory
            //hence reading line by line
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                Object obj = jsonParser.parse(line);
                parseEventObject( (JSONObject) obj , eventMap);
            }
        } catch (FileNotFoundException e) {
            LOGGER.severe("File not found "+e);
        } catch (IOException e) {
            LOGGER.severe("IO related issue "+e);
        } catch (ParseException e) {
            LOGGER.severe("Error while parsing json file "+e);
        }

        return eventMap;
    }

    private void parseEventObject(JSONObject event, Map<String, List<EventDetails>> eventMap )
    {
        String id = (String) event.get("id");
        String state = (String) event.get("state");
        String type = (String) event.get("type");
        String host = (String) event.get("host");
        Long timeStamp = (Long) event.get("timestamp");
        EventDetailsImpl eventDetails = new EventDetailsImpl(id, state,type,host,timeStamp);

        if(eventMap.containsKey(id)){
            List<EventDetails> list =  eventMap.get(id);
            list.add(eventDetails);
            checkAlert(list);

        } else{
            List<EventDetails> eventDetailsList = new ArrayList<>();
            eventDetailsList.add(eventDetails);
            eventMap.put(id,eventDetailsList);
        }
    }

    private void checkAlert(List<EventDetails> eventDetailsList){
        EventDetails startEvent = eventDetailsList.stream().filter(e -> e.getEventState().equals("STARTED")).findAny().get();
        EventDetails finishedEvent = eventDetailsList.stream().filter(e -> e.getEventState().equals("FINISHED")).findAny().get();

        LOGGER.info("Checking alert status for event id : " +startEvent.getEventID());
        if((finishedEvent.getEventTimeStamp() - startEvent.getEventTimeStamp() )> 4){
            finishedEvent.setAlertNeeded(true);
            LOGGER.info("Event took more than 4ms ");
        } else {
            LOGGER.info("Event took less than 4ms ");
            finishedEvent.setAlertNeeded(false);
        }

    }

}
