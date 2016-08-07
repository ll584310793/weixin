package app.job;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import app.App;
import app.jdbc.JdbcTemplate;

public class AccountJob {
	
	static Logger log = LoggerFactory.getLogger(AccountRedpacketJob.class);

	@Autowired
	App app;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	RedisTemplate<Serializable, Serializable> redisTemplate;

	@Scheduled(initialDelay = 60 * 1000, fixedDelay = 60 * 1000)
	public void _import() {
		// 临时id
	}

}
