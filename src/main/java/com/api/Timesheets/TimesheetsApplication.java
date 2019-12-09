package com.api.Timesheets;

import com.api.Timesheets.models.User;
import com.api.Timesheets.repositories.UserRepo;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TimesheetsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimesheetsApplication.class, args);
	}

	@Bean
	public CommandLineRunner demoData(UserRepo repo) {
		return args -> {
			List<User> l = new ArrayList<User>();
			l.add(new User(null,"test","test","test@gmail.com"));
			l.add(new User(null,"test1","test1","test1@gmail.com"));
			l.add(new User(null,"test2","test2","test2@gmail.com"));
			l.stream().forEach(user -> {
				repo.save(user);
			});
		};
	}
}
