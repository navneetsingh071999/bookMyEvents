package backendBME.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "eventregistration")
public class RegistrationEvent {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "eventName")
    private String eventName;
    @ManyToOne
    @JoinColumn(
            name = "eventId",
            nullable = false
    )
    private Employee eventId;
    @Column(name = "dateTime")
    private Date dateTime;
    @Column(name = "approved")
    private boolean approved = false;
    @Column(name = "beveregesNeeded")
    private boolean beveregesNeeded;
    @Column(name = "capacity")
    private int capacity;
    @Column(name = "duration")
    private Date duration;


    public RegistrationEvent(String eventName, Employee eventId, Date dateTime,
                             boolean beveregesNeeded, int capacity, Date duration) {

        this.eventName = eventName;
        this.eventId = eventId;
        this.dateTime = dateTime;
        this.beveregesNeeded = beveregesNeeded;
        this.capacity = capacity;
        this.duration = duration;
    }
}
