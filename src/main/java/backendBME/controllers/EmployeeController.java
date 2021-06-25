package backendBME.controllers;

import backendBME.model.Employee;
import backendBME.model.EmployeeRegistration;
import backendBME.service.EmployeeService;
import backendBME.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EventService eventService;

    @GetMapping(value = "/")
    public String home(){
            return "Welcome " + SecurityContextHolder.getContext().getAuthentication().getName();
       }


    @PutMapping(value = "/admin/authenticate/{email}")
    public String authenticateEmployee(@PathVariable String email){
        return employeeService.authenticate(email);
    }

    @GetMapping(value = "/admin/newUsers")
    public List<Employee> newUsers(){
        return employeeService.getNewUsers();
    }

    @DeleteMapping(value = "admin/delete/{email}")
    public String deleteUser(@PathVariable String email){
        return employeeService.deleteUser(email);
    }

    @PutMapping(value = "admin/update/{id}")
    public String updateUser(@PathVariable Long id, @RequestBody EmployeeRegistration employee){
        return employeeService.updateEmployee(id, employee);
    }

    //Approve Register Event

    @PutMapping(value = "admin/approve/event/{id}")
    public String approveEvent(@PathVariable Long id){
        return eventService.approveEvents(id);
    }



}
