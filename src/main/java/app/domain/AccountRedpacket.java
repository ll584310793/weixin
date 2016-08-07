package app.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AccountRedpacket implements Serializable {

	private Account account;
	private Redpacket redpacket;

	private String mode;

	private double amount;
	private Long create_time;

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Redpacket getRedpacket() {
		return redpacket;
	}

	public void setRedpacket(Redpacket redpacket) {
		this.redpacket = redpacket;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Long getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Long create_time) {
		this.create_time = create_time;
	}

}
