package app.domain;

import java.io.Serializable;
import java.util.Random;

@SuppressWarnings("serial")
public class Redpacket implements Serializable {

	private Long id;

	private Account account;

	private String type;
	private Long create_time;
	private Long update_time;
	private String state;

	private String nickname;
	private String head;

	private String wishing;
	private Long count;
	private double amount;

	private Long received_amount;
	private double received_count;

	/**
	 * 随机红包
	 */
	public double generateRandomMoney() {
		this.update_time = System.currentTimeMillis();
		if (this.count == 1) {
			this.count--;
			return (double) Math.round(this.amount * 100) / 100;
		}
		Random r = new Random();
		double min = 1.0; //
		double max = this.amount / this.count * 2;
		double money = r.nextDouble() * max;
		money = money <= min ? 1.0 : money;
		money = Math.floor(money * 100) / 100;

		this.count--;
		this.amount -= money;

		return money;
	}

	/**
	 * 普通红包
	 */
	public double generateNormalMoney() {
		this.update_time = System.currentTimeMillis();
		if (this.count == 1) {
			this.count--;
			return (double) Math.round(this.amount * 100) / 100;
		}
		double money = this.amount / this.count;
		money = Math.floor(money * 100) / 100;
		this.count--;
		this.amount -= money;
		return money;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Long create_time) {
		this.create_time = create_time;
	}

	public Long getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Long update_time) {
		this.update_time = update_time;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getWishing() {
		return wishing;
	}

	public void setWishing(String wishing) {
		this.wishing = wishing;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Long getReceived_amount() {
		return received_amount;
	}

	public void setReceived_amount(Long received_amount) {
		this.received_amount = received_amount;
	}

	public double getReceived_count() {
		return received_count;
	}

	public void setReceived_count(double received_count) {
		this.received_count = received_count;
	}

}
