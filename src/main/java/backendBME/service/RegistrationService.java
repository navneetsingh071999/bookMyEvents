package backendBME.service;

import backendBME.model.Employee;
import backendBME.model.EmployeeRegistration;
import backendBME.enums.EmployeeRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    @Autowired
    private EmailValidator emailValidator;
    @Autowired
    private EmployeeService service;

    public String register(EmployeeRegistration employeeRegistration) {

        boolean isValidEmail = emailValidator.test(employeeRegistration.getEmail());

        if (!isValidEmail){
            throw new IllegalStateException("Invalid Email");
        }

        if(employeeRegistration.getFirstName().isEmpty() || employeeRegistration.getLastName().isEmpty()
        ||employeeRegistration.getEmail().isEmpty() || employeeRegistration.getEmail().isEmpty()
        ||employeeRegistration.getPassword().isEmpty() || employeeRegistration.getMobileNo().isEmpty()){
            throw new IllegalStateException("Details Empty");
        }

        return service.signUp(new Employee(
                employeeRegistration.getFirstName(),
                employeeRegistration.getLastName(),
                employeeRegistration.getEmail(),
                employeeRegistration.getPassword(),
                EmployeeRole.USER,
                employeeRegistration.getGender(),
                employeeRegistration.getMobileNo()
        ));

    }


}
