package service.desicion.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserInfo {

    private String firstName;
    private String secondName;
    private String thirdName;
    private String phoneNumber;
    private String city;
    private String country;
    private Passport passport;
    private EmployeeInfo employeeInfo;

    @Getter
    @Setter
    public static class Passport {
        private Integer seriesPassport;
        private Integer numberPassport;
        private String issuedBy;
        private LocalDate expirationDate;
        private LocalDate issuedDate;
        private Boolean sex;
        private LocalDate birthDate;
        private String departmentCode;
    }

    @Getter
    @Setter
    public static class EmployeeInfo {
        private String companyName;
        private Integer salary;
        private LocalDate startDate;
        private String inn;
    }
}
