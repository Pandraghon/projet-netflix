package project.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotNull
	@Column(unique=true)
	@Size(min=3, max=20)
	private String username = null;
	
	@Email
	@NotNull
	@Column(unique=true)
	@Size(min=5, max=50)
	private String email = null;
	
	@NotNull
	@Size(min=8, max=50)
	private String password = null;
	
	private Boolean role = false;
	
	private Calendar created = Calendar.getInstance();
	
	
	//Links
	
	@ManyToMany
	@JoinTable(
		name="wanted",
		joinColumns=@JoinColumn(name="user_id", referencedColumnName="id"),
		inverseJoinColumns=@JoinColumn(name="media_id", referencedColumnName="id")
	)
	private List<Media> wanted = new ArrayList<>();
	
	/*@OneToMany(mappedBy="user")
	private List<Note> noted = new ArrayList<>();*/
	
	public User() {
		
	}
	
	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public Boolean getRole() {
		return role;
	}

	public void setRole(Boolean role) {
		this.role = role;
	}

	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}
	
	@Override
	public String toString() {
		return String.format(
				"User[id=%, username=%, email=%, created=%]",
				id, username, email, created
		);
	}
	
}
