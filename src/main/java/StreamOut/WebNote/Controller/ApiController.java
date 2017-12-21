package StreamOut.WebNote.Controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import StreamOut.WebNote.Model.NoteService;

@RestController
public class ApiController {
	@Autowired
	private NoteService ns;
	
	@GetMapping("/api")
	public String api(@RequestParam(value = "content", required = false) String content,
			@RequestParam(value = "action", required = false) String action,
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "message", required = false) String message,
			@RequestParam(value = "echeance", required = false) String echeance,
			@RequestParam(value = "priorite", required = false) String priorite,
			@RequestParam(value = "mode", required = false) String mode) throws JsonProcessingException, ParseException {
		String result = "";
		if(action != null) {
			switch (action) {
			case "lookup":
				result = ns.getNotes();
				break;
			case "priorite":
				result = ns.getNotesByPriorite(priorite);
				break;
			case "add":
				System.out.println("Prio :"+priorite);
				result = ns.addNote(message, priorite, echeance);
				break;
			case "remove":
				result = ns.deleteNote(new Long(id));
				break;
			case "sortByTime" : 
				result = ns.getNotesBydates(mode);
				break;
			default:
				break;
			}
		}

		return result;
	}

}
