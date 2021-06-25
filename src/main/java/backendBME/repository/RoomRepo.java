package backendBME.repository;

import backendBME.enums.EventRooms;
import backendBME.model.EventRoom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface RoomRepo extends CrudRepository<EventRoom, Long> {

    @Query(value = "SELECT * FROM project.eventroom " +
            "WHERE exists (select roomNo from project.eventroom where roomNo = ?1);", nativeQuery = true)
    public List<EventRoom> existsByroomNO(String rooms);
    @Query(value = "SELECT * FROM project.eventroom " +
            "WHERE exists (select roomNo from project.eventroom where fromDate = ?1);", nativeQuery = true)
    public List<EventRoom> existsByfromDate(String dateTime);
}
