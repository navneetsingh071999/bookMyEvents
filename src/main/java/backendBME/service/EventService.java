package backendBME.service;

import backendBME.enums.EventRooms;
import backendBME.model.Employee;
import backendBME.model.EventRegister;
import backendBME.model.EventRoom;
import backendBME.model.RegistrationEvent;
import backendBME.repository.EmployeeRepository;
import backendBME.repository.EventRepo;
import backendBME.repository.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EventRepo eventRepo;
    @Autowired
    private RoomRepo roomRepo;

    //To Add Event by registered employee
    public String addEvent(EventRegister event) {

        if(!event.getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
            throw new IllegalStateException("Not Authorized!");
        }

        Employee employee = employeeRepository.findByEmail(event.getEmail());
        eventRepo.save(new RegistrationEvent(
                event.getEventName(),
                employee,
                event.getDateTime(),
                event.isBeveregesNeeded(),
                event.getCapacity(),
                event.getDuration()

        ));

        return "Event Added";
    }

    //Delete Event Function
    public String deleteEvent(Long id) {

        String role  = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if(role.equals("[ADMIN]")){
            eventRepo.deleteById(id);
        }
        else{
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            Employee employee = employeeRepository.findByEmail(email);
            RegistrationEvent event = eventRepo.findById(id).get();
            if(employee.getId() == event.getEventId().getId()){
                eventRepo.deleteById(id);
            }else{
                return "Not Authorize";
            }
        }

        return "Event deleted";
    }

    //function to set new Event for Update
    public RegistrationEvent setRegistrationEvent(EventRegister event, Employee employee){
        RegistrationEvent registrationEvent = new RegistrationEvent();
        registrationEvent.setId(event.getId());
        registrationEvent.setEventName(event.getEventName());
        registrationEvent.setEventId(employee);
        registrationEvent.setDateTime(event.getDateTime());
        registrationEvent.setBeveregesNeeded(event.isBeveregesNeeded());
        registrationEvent.setCapacity(event.getCapacity());
        registrationEvent.setDuration(event.getDuration());
        return registrationEvent;
    }

    //Update Event based on roles
    public String updateEvent(EventRegister event) {
        String role  = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Employee employee = employeeRepository.findByEmail(email);
        if(role.equals("[ADMIN]")){
                    RegistrationEvent registrationEvent = setRegistrationEvent(event, employee);
                    eventRepo.save(registrationEvent);
        }
        else {
            RegistrationEvent existingEvent = eventRepo.findById(event.getId()).get();
            if(employee.getId() == existingEvent.getEventId().getId()){
                RegistrationEvent registrationEvent = setRegistrationEvent(event, employee);
                eventRepo.save(registrationEvent);
            }else{
                return "Not Authorize";
            }
        }
        return "Event Values Updated";
    }

    //List all approved events to anyone
    public List<RegistrationEvent> showAllApprovedEvents() {
        List<RegistrationEvent> approvedList = new ArrayList<>();

        for(RegistrationEvent approvedEvent : eventRepo.getApprovedEvents()){
            approvedList.add(approvedEvent);
        }
        return approvedList;
    }

    //List event registered by logged in user which are approved by admin
    public List<RegistrationEvent> registeredEvents(String email) {
        List<RegistrationEvent> userEventList = new ArrayList<>();
        Employee employee = employeeRepository.findByEmail(email);
        for (RegistrationEvent event: eventRepo.findAll()){
            if(event.isApproved() && (event.getEventId().getId() ==  employee.getId())){
                userEventList.add(event);
            }
        }
        return userEventList;
    }

    //Lists all registered event of logged in user which are not approved
    public List<RegistrationEvent> pendingApproval(String email) {
        List<RegistrationEvent> userEventList = new ArrayList<>();
        Employee employee = employeeRepository.findByEmail(email);
        for (RegistrationEvent event: eventRepo.findAll()){
            if(!event.isApproved() && (event.getEventId().getId() ==  employee.getId())){
                userEventList.add(event);
            }
        }
        return userEventList;
    }

    //Approves Registered event based on availability of room by admin (Only for admin)
    public String approveEvents(Long id) {
        RegistrationEvent event = eventRepo.findById(id).get();
        //Approve Event
        event.setApproved(true);
        //Allot room based on capacity
        EventRooms rooms = EventRooms.Room_1;
        if(event.getCapacity() <= 60){
            rooms = EventRooms.Room_1;
        }else if(event.getCapacity() >60 && event.getCapacity() <= 111){
            rooms = EventRooms.Room_2;
        }else if(event.getCapacity() >111 && event.getCapacity() <= 171){
            rooms = EventRooms.Room_3;
        }else if(event.getCapacity() >171 && event.getCapacity() <= 301){
            rooms = EventRooms.Room_4;
        }

        boolean flag;
        //checks for already booked slots in event room table
        if(!roomRepo.existsByroomNO(rooms.toString()).isEmpty()){
            if(!roomRepo.existsByfromDate(event.getDateTime().toString().substring(0,10)).isEmpty()){
                flag = false;
            }else {
                flag = true;
            }
        }else {
            flag = true;
        }

        //approves event and add it to event room table
        if(flag){
            eventRepo.save(event);
            roomRepo.save(new EventRoom(
                    rooms,
                    event.getDateTime(),
                    event.getDuration(),
                    event
            ));

            return "Approved";
        }
        return "No rooms Available";
    }
}
