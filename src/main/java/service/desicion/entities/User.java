package service.desicion.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "second_name")
    private String secondName;
    @Column(name = "third_name")
    private String thirdName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "series_passport")
    private Integer seriesPassport;
    @Column(name = "number_passport")
    private Integer numberPassport;
    @Column(name = "issued_by")
    private String issuedBy;
    @Column(name = "expiration_date")
    private LocalDate expirationDate;
    @Column(name = "issued_date")
    private LocalDate issuedDate;
    @Column(name = "sex")
    private Boolean sex;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;
    @Column(name = "department_code")
    private String departmentCode;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "salary")
    private Integer salary;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "inn")
    private String inn;

    @OneToMany
    private List<Credit> credits = new ArrayList<>();
}
