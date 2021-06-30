package backendBME.repository;

import backendBME.model.BookedEvent;
import org.springframework.data.repository.CrudRepository;

public interface BookedEventsRepository extends CrudRepository<BookedEvent,Long> {
}
