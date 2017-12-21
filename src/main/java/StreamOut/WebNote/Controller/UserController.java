package StreamOut.WebNote.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import StreamOut.WebNote.Model.UserService;

@RestController
public class UserController {
	@Autowired
	UserService userService;

	@GetMapping("/user")
	public String api(@RequestParam(value = "action", required = false) String action,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "pseudo", required = false) String pseudo,
			@RequestParam(value = "password", required = false) String password) throws JsonProcessingException {
		String result = "";
		if(action != null) {
			switch (action) {
				case "register": result = userService.registration(pseudo, email, password); break;
				case "login": result = userService.connect(email, password); break;
				case "disconnect": result = userService.disconnect(); break;
				default : break;
			}
		}
		return result;
	}
}
