package backendBME.model;

import backendBME.enums.EventRooms;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "eventroom")
@Getter
@Setter
@NoArgsConstructor
public class EventRoom {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "roomNo")
    @Enumerated(EnumType.STRING)
    private EventRooms roomNo;
    @Column(name = "fromDate")
    private Date fromDate;
    @Column(name = "toDate")
    private Date toDate;
    @OneToOne
    @JoinColumn(
            nullable = false,
            name = "roomId"
    )
    private RegistrationEvent registrationEvent;

    public EventRoom(EventRooms roomNo, Date fromDate, Date toDate, RegistrationEvent registrationEvent) {
        this.roomNo = roomNo;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.registrationEvent = registrationEvent;
    }
}
