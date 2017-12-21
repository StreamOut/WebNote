package StreamOut.WebNote.Model;


import java.util.List;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	private UserRepository repository;
	@Autowired
	private HttpSession httpSession;
	
	public String connect(String email, String password) {
		JsonObjectBuilder objectbuider = Json.createObjectBuilder();
		User user = repository.findOne(email);
		if(user != null && user.getPassword().equals(password)) {
			objectbuider.add("status", "SUCESS");
			objectbuider.add("pseudo", user.getPseudo());
			httpSession.setAttribute("user", user.getPseudo());
		}
		else {
			objectbuider.add("status", "FAIL");
			objectbuider.add("message", "Identifiant ou mot de passe érroné !");
		}
		return objectbuider.build().toString();
	}
	
	public String registration(String pseudo, String email, String password) {
		JsonObjectBuilder objectbuider = Json.createObjectBuilder();
		User user = repository.findOne(email);
		if(user != null) {
			objectbuider.add("status", "FAIL");
			objectbuider.add("message", "Email déjà utulisé !");
		}
		else {
			List<User> users = (List<User>) repository.findByPseudo(pseudo);
			if(! users.isEmpty()) {
				objectbuider.add("status", "FAIL");
				objectbuider.add("message", "Pseudo déjà utulisé !");
			}
			else if (password != null && !password.equals("")){
				objectbuider.add("status", "SUCCES");
				objectbuider.add("pseudo", pseudo);
				repository.save(new User(email, pseudo, password));
				httpSession.setAttribute("user", pseudo);
			}
			else {
				objectbuider.add("status", "FAIL");
				objectbuider.add("message", "Mot de passe mal formé !");
			}
		}
		return objectbuider.build().toString();
	}
	
	public String disconnect() {
		JsonObjectBuilder objectbuider = Json.createObjectBuilder();
		String pseudo = (String) httpSession.getAttribute("user");
		httpSession.removeAttribute("user");
		objectbuider.add("status", "SUCCES");
		objectbuider.add("message", pseudo + "déconnecter avec succés !");
		return objectbuider.build().toString();
	}
}
