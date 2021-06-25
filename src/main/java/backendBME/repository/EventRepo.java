package backendBME.repository;

import backendBME.enums.EventRooms;
import backendBME.model.RegistrationEvent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventRepo extends CrudRepository<RegistrationEvent, Long> {

    @Query(value = "SELECT u FROM RegistrationEvent u WHERE u.approved = true")
    public List<RegistrationEvent> getApprovedEvents();

}
