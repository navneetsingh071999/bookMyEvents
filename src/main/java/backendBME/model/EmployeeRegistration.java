package backendBME.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class EmployeeRegistration {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String gender;
    private String mobileNo;

    public EmployeeRegistration(String firstName, String lastName, String email, String password, String gender, String mobileNo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.mobileNo = mobileNo;
    }
}
