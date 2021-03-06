package app.job;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import app.App;
import app.domain.AccountRedpacket;
import app.jdbc.JdbcTemplate;

@Component
public class AccountRedpacketJob {

	static Logger log = LoggerFactory.getLogger(AccountRedpacketJob.class);

	@Autowired
	App app;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	RedisTemplate<Serializable, Serializable> redisTemplate;

	@Scheduled(initialDelay = 4 * 1000, fixedDelay = 60 * 1000)
	public void execute() {
		String key = "account_redpacket";
		long size = redisTemplate.opsForSet().size(key);
		log.info(key + " size=>" + size);
		while (size <= 0) {
			size--;
			AccountRedpacket rs = (AccountRedpacket) redisTemplate.opsForSet().pop(key);
			this.execute(rs);
		}
	}

	public void execute(AccountRedpacket accountRedpacket) {
		String openid = accountRedpacket.getAccount().getOpenid();// 微信公开ip
		String desc = app.app_name;// 描述
		double amount = accountRedpacket.getAmount();// 金额
		String wishing = accountRedpacket.getRedpacket().getWishing();// 祝语
		if (wishing == null || wishing.isEmpty()) {
			wishing = "事事顺意.";
		}
		int r = app.sendRedpacket(openid, amount, desc, wishing);
		switch (r) {
		case 1: // 完全成功
			String sql = "insert into account_redpacket(account_id,redpacket_id,mode,amount,create_time)values(?,?,?,?,?)";
			Object[] args = { accountRedpacket.getAccount().getId(), accountRedpacket.getRedpacket().getId(), accountRedpacket.getMode(), accountRedpacket.getAmount(),
					accountRedpacket.getCreated_at() };
			jdbcTemplate.update(sql, args);
			log.info("send success,db::account_redpacket add...");
			break;
		case 0:// 发送成功，但结果集不成功
			redisTemplate.opsForSet().add("account_redpacket/tomorrow", accountRedpacket);
			log.info("send faild,redis::account_redpacket add...");
			break;
		case -1:// 发送失败
			log.info("send faild...");
			break;
		default:
			break;
		}
	}
}
