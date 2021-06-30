package backendBME.model;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="bookedevents")
public class BookedEvent {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            nullable=false,
            name = "bookId")
    private RegistrationEvent bookId;

    @Column(name = "personName")
    private String personName;

    @Column(name = "seats")
    private int seats;

    @Column(name = "status")
    private boolean status;

    @Column(name = "eventName")
    private String eventName;

    @Column(name = "dateTime")
    private Date dateTime;

    @ManyToOne
    @JoinColumn(
            nullable=false,
            name = "empId")
    private Employee empId;


    public BookedEvent(RegistrationEvent bookId, String personName,
                       int seats, boolean status,
                       String eventName, Date dateTime, Employee empId) {
        this.bookId = bookId;
        this.personName = personName;
        this.seats = seats;
        this.status = status;
        this.eventName = eventName;
        this.dateTime = dateTime;
        this.empId = empId;
    }


}
