package backendBME.model;

import backendBME.enums.EmployeeRole;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //this is required as data type is long
    @Column(name = "id")
    private Long id;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private EmployeeRole role;
    @Column(name = "enabled")
    private boolean enabled = false;
    @Column(name = "gender")
    private String gender;
    @Column(name = "mobileNo")
    private String mobileNo;

    public Employee(String firstName, String lastName, String email, String password,
                    EmployeeRole role, String gender, String mobileNo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.gender = gender;
        this.mobileNo = mobileNo;
    }
}
