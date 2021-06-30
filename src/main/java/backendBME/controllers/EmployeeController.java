package backendBME.controllers;

import backendBME.jwtToken.JwtUtil;
import backendBME.model.Employee;
import backendBME.model.EmployeeRegistration;
import backendBME.model.JwtRequest;
import backendBME.model.JwtResponse;
import backendBME.service.EmployeeService;
import backendBME.service.EventService;
import backendBME.service.UserPrinciples;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EventService eventService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(value = "/token")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {

        try {
                this.authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(), jwtRequest.getPassword())
                );
        }catch (UsernameNotFoundException e){
            e.printStackTrace();
            throw new Exception("Bad Crenditials");
        }catch (BadCredentialsException e){
            e.printStackTrace();
            throw new BadCredentialsException("Bad Credentials");
        }

        UserDetails employee = this.employeeService.loadUserByUsername(jwtRequest.getUserName());
        String token = this.jwtUtil.generateToken(employee);

        return ResponseEntity.ok(new JwtResponse(token));
    }

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
