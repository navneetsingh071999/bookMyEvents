package backendBME.service;

import backendBME.model.BookEvent;
import backendBME.model.BookedEvent;
import backendBME.model.Employee;
import backendBME.model.RegistrationEvent;
import backendBME.repository.BookedEventsRepository;
import backendBME.repository.EmployeeRepository;
import backendBME.repository.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookedEventsService {

    @Autowired
    private EmailSender emailSender;
    @Autowired
    private BookedEventsRepository bookedeventsrepo;
    @Autowired
    private EventRepo eventrepo;
    @Autowired
    private EmployeeRepository employeeRepository;

    public String bookevents(BookEvent bookEvent, Long eventid) {

        BookedEvent newBookedEvent = new BookedEvent();
        RegistrationEvent registrationEvent = eventrepo.findById(eventid).get();
        Employee employee= employeeRepository.findByEmail(bookEvent.getEmail());

        if(registrationEvent.isApproved()) {
            newBookedEvent.setBookId(registrationEvent);
            newBookedEvent.setPersonName(bookEvent.getPersonName());
            if (bookEvent.getSeats() <= registrationEvent.getCapacity()) {
                newBookedEvent.setSeats(bookEvent.getSeats());
            } else {
                return "Capacity Exceeded";

            }
            newBookedEvent.setStatus(true);
            newBookedEvent.setEventName(registrationEvent.getEventName());
            newBookedEvent.setDateTime(registrationEvent.getDateTime());
            newBookedEvent.setEmpId(employee);

            String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
            if (role.equals("[ADMIN]")) {
                bookedeventsrepo.save(newBookedEvent);
            } else {
                if (SecurityContextHolder.getContext().getAuthentication().getName().equals(bookEvent.getEmail())) {
                    bookedeventsrepo.save(newBookedEvent);
                } else {
                    return "Unauthorized Email";
                }
            }

            int newCapacity = registrationEvent.getCapacity() - bookEvent.getSeats();
            eventrepo.updateCapacity(newCapacity, registrationEvent.getId());

            //.................................................
            //Send Event Updated mail
            emailSender.sendSimpleEmail(employee.getEmail(),
                    "Event Booked Successfully! \n " +
                            "You have successfully booked your Event with BookMyEvents." +
                            " \n Event Name : " + registrationEvent.getEventName() + "\n"
                            + "Event Date : " + registrationEvent.getDateTime() + "\n"
                            + "Seats : " + bookEvent.getSeats() + "\n" +
                            "Thank You for Choosing BookMyEvents. \n" +
                            "Regards,\n" +
                            "Team Book My Events.",
                    "Registered With Book My Events");
            //....................................................


            return "Event Booked Successfully!!";
        }else {
            return "Event not booked";
        }
    }

    public String deleteBookedEvent(Long id) {

        BookedEvent bookedEvent =bookedeventsrepo.findById(id).get();
        RegistrationEvent registrationEvent = bookedEvent.getBookId();
        int newSeats= registrationEvent.getCapacity()+ bookedEvent.getSeats();
        bookedeventsrepo.deleteById(id);
        eventrepo.updateCapacity(newSeats,registrationEvent.getId());
        return "Event Deleted!!";

    }

    public List<BookedEvent> getBookedEvents(String email) {
        Employee employee = employeeRepository.findByEmail(email);
        return bookedeventsrepo.findAllByEmpId(employee.getId());
    }
}

