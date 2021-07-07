package backendBME.controllers;

import backendBME.model.BookEvent;
import backendBME.service.BookedEventsService;
import backendBME.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value ="/book")
@CrossOrigin(origins = "*")
public class BookedEventsController {

    @Autowired
    private BookedEventsService bookedEventsService;

    @Autowired
    private RegistrationService registrationService;

    @PostMapping(value ="/bookevent/{eventId}")
    public String bookevents(@RequestBody BookEvent bookEvent, @PathVariable Long eventId){
        return bookedEventsService.bookevents(bookEvent,eventId);
    }
}
