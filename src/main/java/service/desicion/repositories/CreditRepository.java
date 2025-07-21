package service.desicion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import service.desicion.entities.Credit;

public interface CreditRepository extends JpaRepository<Credit, Long> {
}
