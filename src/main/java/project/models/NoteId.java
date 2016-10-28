package project.models;
/*package models;

import java.io.Serializable;

public class NoteId implements Serializable {

	private long user_id;
	private long media_id;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (media_id ^ (media_id >>> 32));
		result = prime * result + (int) (user_id ^ (user_id >>> 32));
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) 	return true;
		if(obj == null)		return false;
		
		if(obj instanceof NoteId) {
			NoteId n = (NoteId)obj;
			return n.media_id == this.media_id && n.user_id == this.user_id;
		}
		
		return false;
	}
	
}
*/