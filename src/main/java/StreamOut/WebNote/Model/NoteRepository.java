package StreamOut.WebNote.Model;

import org.springframework.data.repository.CrudRepository;

public interface NoteRepository extends CrudRepository<Note, Long> {
	Iterable<Note> findByPrioriteAndUserId(String priorite, String userId);
	Iterable<Note> findByUserId(String userId);
}
