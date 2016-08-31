package app.domain;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class AccountRedpacket implements Serializable {

	private Account account;
	private Redpacket redpacket;

	private String mode;

	private double amount;
	private Date created_at;

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

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

}
