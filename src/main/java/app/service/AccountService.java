package app.service;

import java.io.Serializable;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.domain.Account;
import app.jdbc.JdbcTemplate;

@Service
@Transactional
public class AccountService {

	@Autowired
	public JdbcTemplate jdbcTemplate;

	@Autowired
	public RedisTemplate<Serializable, Serializable> redisTemplate;

	public Account get(Long id) {
		return (Account) redisTemplate.opsForHash().get("accounts", id);
	}

	public Account update(Account account) {
		Long account_id = (Long) redisTemplate.opsForHash().get("openid/account_id", account.getOpenid());
		if (account_id != null) {
			// redis
			Account acc = this.get(account_id);
			account.setCreated_at(new Date());
			account.setUpdated_at(new Date());
			account.setState(acc.getState());
			account.setId(acc.getId());
			redisTemplate.opsForHash().put("accounts", account_id, account);
			return account;
		}
		// insert
		account.setState("valid");
		account.setCreated_at(new Date());
		account.setUpdated_at(new Date());

		Object[] args = { account.getNickname(), account.getHead(), account.getAccess_token(),
				account.getRefresh_token(), account.getExpires_in(), account.getCreated_at(), account.getUpdated_at(),
				account.getOpenid(), account.getState() };
		String sql = "insert into account(nickname,head,access_token,refresh_token,expires_in,update_time,create_time,openid,state)values(?,?,?,?,?,?,?,?,?)";
		long id = jdbcTemplate.insertAndGetId(sql, args);
		account.setId(id);
		// redis
		redisTemplate.opsForHash().put("accounts", id, account);
		redisTemplate.opsForHash().put("openid/account_id", account.getOpenid(), id);
		return account;
	}

}
