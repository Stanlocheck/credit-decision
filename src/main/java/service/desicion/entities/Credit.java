package service.desicion.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "credits")
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "credit_amount")
    private Integer creditAmount;
    @Column(name = "type")
    private String type;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "percent")
    private Integer percent;
    @Column(name = "payment")
    private Integer payment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
