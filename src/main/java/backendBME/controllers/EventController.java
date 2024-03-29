package backendBME.controllers;

import backendBME.model.BookedEvent;
import backendBME.model.EventRegister;
import backendBME.model.RegistrationEvent;
import backendBME.service.BookedEventsService;
import backendBME.service.EmployeeService;
import backendBME.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "event")
public class EventController {

    @Autowired
    private EventService eventService;
    @Autowired
    private BookedEventsService bookedEventsService;

    @PostMapping(value = "register")
    public String registerEvent(@RequestBody EventRegister event){
        return eventService.addEvent(event);
    }

    @DeleteMapping(value = "delete/{id}")
    public String deleteEvent(@PathVariable Long id){
        return eventService.deleteEvent(id);
    }

    @PutMapping(value = "update")
    public String updateEvent(@RequestBody EventRegister eventRegister){
        return eventService.updateEvent(eventRegister);
    }


    @GetMapping(value = "show")
    public List<RegistrationEvent> getEvents(){
        return eventService.showAllApprovedEvents();
    }

    @GetMapping(value = "/approvedEvents")
    public List<RegistrationEvent> registeredEvents(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return eventService.registeredEvents(email);

    }

    @GetMapping(value = "/pendingEvents")
    public List<RegistrationEvent> pendingApproval(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return eventService.pendingApproval(email);

    }

    @DeleteMapping(value="/deletebookedevent/{id}")
    public String deleteBookedEvent(@PathVariable Long id){
        return bookedEventsService.deleteBookedEvent(id);

    }

    //To get all booked event of loggedIn user
    @GetMapping(value = "/bookedevents")
    public List<BookedEvent> bookedEvents(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return bookedEventsService.getBookedEvents(email);
    }



}
