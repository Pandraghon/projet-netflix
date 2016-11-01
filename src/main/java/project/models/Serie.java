package project.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name="series")
public class Serie {



	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	//@Column(name="serie_id")
	private long id;
	
	
	@OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
	@JoinColumn(name="media_id",referencedColumnName="id")
	private Media media;
	
	
	@OneToMany(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
	@JoinColumn(name="episode_id",referencedColumnName="id")
	private List<Episode> episode = new ArrayList<>();
	
	public Serie() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Serie(Media media) {
		this.media = media;
		
	}


	public long getId() {
		return id;
	}


	public Media getMedia() {
		return media;
	}


	public void setMedia(Media media) {
		this.media = media;
	}


	public void setId(long id) {
		this.id = id;
	}


	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.REFRESH})
	private List<Episode> episodes = new ArrayList<>();
	
	

	public List<Episode> getEpisodes() {
		return episodes;
	}


	public void setEpisodes(List<Episode> episodes) {
		this.episodes = episodes;
	}

	public void supprimerEpisode(Episode epi)
	{
		this.episode.remove(epi);
	}

	public void ajouterEpisode(Episode epi)
	{
		this.episode.add(epi);
	}

	public List<Long> listeSaisons() {
		boolean bool = true;
		Long nb_saisons1;
		List<Long> premieressaisons = new ArrayList<>();
		List<Long> saisons  = new ArrayList<>();
		
		if(this.episodes.size()>0)
		{
					
			for (Episode episode : this.episodes)
			{
				premieressaisons.add(episode.getSaison_number());
			}
		
			nb_saisons1 = premieressaisons.get(0);
			saisons.add(nb_saisons1);
			
			for (Long sais_nb : premieressaisons)
			{
				if(!saisons.contains(sais_nb))
				{
					saisons.add(sais_nb);
				}
			}
		}
		return saisons;
	}
}
