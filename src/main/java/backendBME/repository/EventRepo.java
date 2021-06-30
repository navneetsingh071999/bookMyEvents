package backendBME.repository;

import backendBME.enums.EventRooms;
import backendBME.model.RegistrationEvent;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional

public interface EventRepo extends CrudRepository<RegistrationEvent, Long> {

    @Query(value = "SELECT u FROM RegistrationEvent u WHERE u.approved = true")
    public List<RegistrationEvent> getApprovedEvents();

    @Modifying
    @Query(value = "UPDATE `project`.`eventregistration` SET `capacity` = ?1 WHERE (`id` = ?2);", nativeQuery = true)
    public void updateCapacity(int newSeats, Long id);
}
