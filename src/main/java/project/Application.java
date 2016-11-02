package project;


import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import project.models.User;
import project.repositories.UserRepository;
import project.storage.StorageProperties;
import project.storage.StorageService;







@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class Application implements CommandLineRunner {
	
	@Autowired
	private UserRepository userRep;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
            storageService.deleteAll();
            storageService.init();
		};
	}
	
	@Override
	public void run(String... arg0) throws Exception {
		User admin = new User();
		admin.setUsername("admin");
		admin.setPassword(BCrypt.hashpw("admin", BCrypt.gensalt(10)));
		admin.setRole(true);
		admin.setEmail("admin.admin@uha.fr");
		userRep.save(admin);
		
		User user = new User();
		user.setUsername("Akim");
		user.setPassword(BCrypt.hashpw("1111", BCrypt.gensalt(10)));
		user.setRole(false);
		user.setEmail("akim.neghouche@uha.fr");
		userRep.save(user);
		
	}


}
