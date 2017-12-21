package StreamOut.WebNote.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NoteService {
	@Autowired
	private NoteRepository nr;
	@Autowired
	private HttpSession httpSession;
	
	public String getNotes() throws JsonProcessingException {
		List<Note> notes = (List<Note>) (nr.findByUserId((String) httpSession.getAttribute("user")));
		return new ObjectMapper().writeValueAsString(notes);
	}
	
	public String addNote(String message, String priorite, String echeance) throws JsonProcessingException {
		Note note = new Note(message, priorite, echeance, (String) httpSession.getAttribute("user"));
		nr.save(note);
		return new ObjectMapper().writeValueAsString(note);
	}
	
	public String deleteNote(Long id) throws JsonProcessingException {
		Note note = nr.findOne(id);
		if(note != null)
			nr.delete(note);
		return new ObjectMapper().writeValueAsString(note);
	}
	
	public String getNotesByPriorite(String priorite) throws JsonProcessingException {
		List<Note> notes = (List<Note>) nr.findByPrioriteAndUserId(priorite,((String) httpSession.getAttribute("user")));
		return new ObjectMapper().writeValueAsString(notes);
	}
	
	@SuppressWarnings("deprecation")
	public String getNotesBydates(String mode) throws ParseException, JsonProcessingException {
		Date aujourdhui = new Date();
		ArrayList<Note> notesResult = new ArrayList<Note>();
		List<Note> notes = (List<Note>) (nr.findByUserId((String) httpSession.getAttribute("user")));
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yy");
		
		for(Note note : notes) {
			switch(mode) {
				case "Jour": 
					System.out.println(note.getMessage() + " : " +formater.parse(note.getEcheance()) + " int : " +(formater.parse(note.getEcheance()).getTime()));
					System.out.println(aujourdhui + " int : "+aujourdhui.getTime());
					System.out.println((formater.parse(note.getEcheance()).getTime()) - aujourdhui.getTime());
					if((aujourdhui.getTime() - (formater.parse(note.getEcheance()).getTime())) <= 86400000 && (aujourdhui.getTime() - formater.parse(note.getEcheance()).getTime() ) >= 0)
						notesResult.add(note); 
					break;
				case "Semaine" :
					if(formater.parse(note.getEcheance()).getMonth() == aujourdhui.getMonth() && formater.parse(note.getEcheance()).getYear() == aujourdhui.getYear() && (formater.parse(note.getEcheance()).getDay() - aujourdhui.getDay()) <= 7 && (formater.parse(note.getEcheance()).getDay() - aujourdhui.getDay()) >= 0) {
						notesResult.add(note);
					}
					break;
				case "Mois" : 
					if(formater.parse(note.getEcheance()).getYear() == aujourdhui.getYear() && formater.parse(note.getEcheance()).getMonth() == aujourdhui.getMonth())
						notesResult.add(note);
					break;
				default : break; 
			}
			
		}
		return new ObjectMapper().writeValueAsString(notesResult);
		
	}

}
