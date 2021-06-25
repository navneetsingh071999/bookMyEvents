package backendBME.model;

import lombok.*;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class EventRegister {

    private Long id;
    private String eventName;
    private Date dateTime;
    private boolean beveregesNeeded;
    private int capacity;
    private Date duration;
    private String email;

    SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");

    public EventRegister(String eventName, boolean beveregesNeeded, int capacity,
                         String email, String dateTime, String duration) throws ParseException {
        this.eventName = eventName;
        this.beveregesNeeded = beveregesNeeded;
        this.capacity = capacity;
        this.email = email;
        this.dateTime = formatter.parse(dateTime);
        this.duration = formatter.parse(duration);
    }
}
