package app.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import app.App;

@Component
public class AppJob {

	@Autowired
	App app;

	@Scheduled(initialDelay = 3000, fixedDelay = 3600000)
	public void execute() {
		app.update();
	}

}
