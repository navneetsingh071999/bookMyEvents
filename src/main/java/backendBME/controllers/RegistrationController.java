package backendBME.controllers;

import backendBME.model.Employee;
import backendBME.model.EmployeeRegistration;
import backendBME.repository.EmployeeRepository;
import backendBME.service.EmployeeService;
import backendBME.service.RegistrationService;
import backendBME.service.UserPrinciples;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "registration")
@CrossOrigin(origins = "*")
public class RegistrationController {

    @Autowired
    private RegistrationService service;
    @Autowired
    private EmployeeService employeeService;


    @RequestMapping(value = "register")
    public String registerEmployee(@RequestBody EmployeeRegistration employeeRegistration){
        return service.register(employeeRegistration);
    }


}
