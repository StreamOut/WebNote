package StreamOut.WebNote.Model;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String>{
	Iterable<User> findByPseudo(String pseudo);
}
