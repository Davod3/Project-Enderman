package com.project.enderman;

import com.project.enderman.entities.ServerData;
import com.project.enderman.handlers.RunServerHandler;
import com.project.enderman.repositories.ServerDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.util.List;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
@EnableScheduling
public class EndermanApplication {

	@Autowired private ServerDataRepository serverRepo;

	public static void main(String[] args) {
		SpringApplication.run(EndermanApplication.class, args);
	}

	@Scheduled(fixedRate = 10000)
	public void updateServerStatus() {

		System.out.println("Checking status of servers...");

		List<ServerData> servers = this.serverRepo.findAll();
		RunServerHandler runHandler = new RunServerHandler(this.serverRepo);

		for(ServerData sv : servers) {

			try {
				sv.setRunning(runHandler.isRunning(sv.getID()));
			} catch (IOException | InterruptedException e) {
				sv.setRunning(false);
			}

			this.serverRepo.save(sv);

		}

		System.out.println("All servers checked");

	}

}
