package app.service;

import java.io.Serializable;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.domain.Account;
import app.domain.Redpacket;
import app.jdbc.JdbcTemplate;
import app.domain.AccountRedpacket;

@Service
@Transactional
public class RedpacketService {

	@Autowired
	public JdbcTemplate jdbcTemplate;

	@Autowired
	RedisTemplate<Serializable, Serializable> redisTemplate;

	@Autowired
	AccountService accountService;

	public Redpacket get(Long id) {
		return (Redpacket) redisTemplate.opsForHash().get("redpackets", id);
	}

	public void put(Long id, Redpacket redpacket) {
		redisTemplate.opsForHash().put("redpackets", id, redpacket);
	}

	public AccountRedpacket generate(Long redpacket_id, Long account_id, String mode) {
		Redpacket redpacket = this.get(redpacket_id);
		if (redpacket == null) {
			return null;
		}
		Account account = accountService.get(account_id);
		if (account == null) {
			return null;
		}
		AccountRedpacket ar = new AccountRedpacket();
		ar.setAccount(account);
		ar.setRedpacket(redpacket);
		ar.setMode(mode);
		ar.setAmount(redpacket.generateRandomMoney());
		ar.setCreated_at(new Date());
		// redis
		redisTemplate.opsForSet().add("account_redpacket", ar);
		return ar;
	}

	public Redpacket add(Redpacket redpacket) {
		redpacket.setCreated_at(new Date());
		redpacket.setUpdated_at(new Date());
		redpacket.setState("init");
		// db
		String sql = "insert into redpacket(account_id,nickname,head,count,amount,wishing,create_time,update_time,state)values(?,?,?,?,?,?,?,?,?)";
		Object[] args = { redpacket.getAccount().getId(), redpacket.getNickname(), redpacket.getHead(),
				redpacket.getCount(), redpacket.getAmount(), redpacket.getWishing(), redpacket.getCreated_at(),
				redpacket.getUpdated_at(), redpacket.getState() };
		long id = jdbcTemplate.insertAndGetId(sql, args);
		redpacket.setId(id);
		/// redis
		this.put(id, redpacket);
		return redpacket;
	}

}
