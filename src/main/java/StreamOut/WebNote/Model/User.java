package StreamOut.WebNote.Model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class User {
	@Id
	private String email;
	private String pseudo;
	private String password;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user") //␣il␣faut␣indiquer␣le␣type␣de␣la␣classe␣parent␣dans␣mappedBy,␣et␣non␣la␣propriete␣id
	@Cascade({CascadeType.REMOVE}) //␣permet␣de␣supprimer␣en␣cascade␣les␣liikes␣associes
	private Set<Note> notes = new HashSet<Note>(0);
	
	public User(){}
	
	public User(String email, String pseudo, String password) {
		this.email = email;
		this.pseudo = pseudo;
		this.password = password;
	}
	
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Note> getNotes() {
		return notes;
	}

	public void setNotes(Set<Note> notes) {
		this.notes = notes;
	}

}
