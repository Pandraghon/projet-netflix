package project.models;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import project.models.User;

@Entity
@Table(name="videos")
public class Video {

	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)

	private Long id;
	
	@NotNull
	private Long duration;
	
	@NotNull
	private String content;
	
	@ManyToMany(mappedBy="seen")
	private List<User> watchers = new ArrayList<>();
	
	private Date created = new Date();
	
	public Video() {
		super();
	}

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Video(Long duration, String content, Date created) {
		super();
		this.duration = duration;
		this.content = content;
		this.created = created;
	}

	public List<User> getWatchers() {
		return watchers;
	}

	public void setWatchers(List<User> watchers) {
		this.watchers = watchers;
	}

	@Override
	public String toString() {
		return "Video [id=" + id + ", duration=" + duration + ", content=" + content + ", created=" + created + "]";
	}

	
	
}
