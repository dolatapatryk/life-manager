package pl.patrykdolata.lifemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.patrykdolata.lifemanager.domain.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
}
