package backendBME.repository;

import backendBME.model.BookedEvent;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface BookedEventsRepository extends CrudRepository<BookedEvent,Long> {

    @Query(value = "SELECT * FROM project.bookedevents where empId = ?1 ;", nativeQuery = true)
    List<BookedEvent> findAllByEmpId(Long id);

    @Modifying
    @Query(value = "DELETE FROM project.bookedevents WHERE (bookId = ?1 );", nativeQuery = true)
    public void deleteAllByBookId(Long id);
}
