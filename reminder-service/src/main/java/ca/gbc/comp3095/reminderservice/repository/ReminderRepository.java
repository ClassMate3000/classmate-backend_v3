package ca.gbc.comp3095.reminderservice.repository;

import ca.gbc.comp3095.reminderservice.model.Reminder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReminderRepository extends MongoRepository<Reminder, String> {

    Optional<Reminder> findByReminderId(Long reminderId);

    boolean existsByReminderId(Long reminderId);

    void deleteByReminderId(Long reminderId);

    List<Reminder> findByTaskId(Long taskId);
}
