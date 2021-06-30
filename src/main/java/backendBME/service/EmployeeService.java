package backendBME.service;

import backendBME.model.Employee;
import backendBME.model.EmployeeRegistration;
import backendBME.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Service
@CrossOrigin(origins = "*")
public class EmployeeService implements UserDetailsService {

    @Autowired
    private EmployeeRepository repo;
    @Autowired
    private EmailSender emailSender;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    //For login and is connected with spring security method implementation of UserDetailsService
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Employee employee = repo.findByEmail(s);
        if (employee == null) {
            throw new UsernameNotFoundException("Username not found");
        }
        return new UserPrinciples(employee);
    }

    //For signup and connected to registration (for everyone)
    public String signUp(Employee employee) {

        boolean isEmailExist = repo.existsByEmail(employee.getEmail());
        if (isEmailExist) {
            throw new IllegalStateException("Email already in use");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(employee.getPassword());
        employee.setPassword(encodedPassword);
        repo.save(employee);

        //.................................................
        //Send Event Updated mail
        emailSender.sendSimpleEmail(employee.getEmail(),
                "SignUp Success! \n " +
                        "You have successfully created your account with Book My Events. \n" +
                        "Wishing you a great journey ahead. \n" +
                        "Regards,\n" +
                        "Team Book My Events.",
                "Registered With Book My Events");
        //....................................................

        return "Registered";
    }

    //To enable employees account (Only for ADMIN)
    public String authenticate(String email) {

        Employee employee = repo.findByEmail(email);
        employee.setEnabled(true);
        repo.save(employee);
        return "Authorized";
    }

    //To get all disabled employees (Only for ADMIN)
    public List<Employee> getNewUsers() {
        return repo.findAllByEnabled(false);
    }


    //delete any user feature (Only for ADMIN)
    public String deleteUser(String email) {
        repo.deleteByEmail(email);
        return "deleted";
    }

    //update any employee details (Only for employee)
    public String updateEmployee(Long id, EmployeeRegistration employee) {

        Employee newEmployee = new Employee();

        if (id != employee.getId()) {
            throw new IllegalStateException("Invalid Update");
        }
        newEmployee.setId(id);
        newEmployee.setFirstName(employee.getFirstName());
        newEmployee.setLastName(employee.getLastName());
        newEmployee.setEmail(employee.getEmail());
        String encodedPassword = bCryptPasswordEncoder.encode(employee.getPassword());
        employee.setPassword(encodedPassword);
        newEmployee.setPassword(employee.getPassword());
        newEmployee.setRole(repo.getRole(id));
        newEmployee.setGender(employee.getGender());
        newEmployee.setMobileNo(employee.getMobileNo());
        newEmployee.setEnabled(true);
        repo.save(newEmployee);
        return "Updated";

    }

}

