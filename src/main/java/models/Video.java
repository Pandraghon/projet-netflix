package models;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="videos")
public class Video {

	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="video_id")
	private Long id;
	
	@NotNull
	private Long duration;
	
	@NotNull
	private Blob content;
	
	@ManyToMany(mappedBy="seen")
	private List<User> watchers = new ArrayList<>();
	
	private Calendar created = Calendar.getInstance();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public Blob getContent() {
		return content;
	}

	public void setContent(Blob content) {
		this.content = content;
	}

	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}

	public Video(Long duration, Blob content, Calendar created) {
		super();
		this.duration = duration;
		this.content = content;
		this.created = created;
	}

	@Override
	public String toString() {
		return "Video [id=" + id + ", duration=" + duration + ", content=" + content + ", created=" + created + "]";
	}

	
	
}
