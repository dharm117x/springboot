package com.example.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

public class Payment implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String ref;
	private BigDecimal amt;
	private Currency currency;
	
	public static Payment create(String ref) {
		Payment p = new Payment();
		p.setRef(ref);
		p.setAmt(BigDecimal.valueOf(100.90));
		p.setCurrency(Currency.getInstance("USD"));
		
		return p;
	}

	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public BigDecimal getAmt() {
		return amt;
	}
	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	@Override
	public String toString() {
		return "Payment [ref=" + ref + ", amt=" + amt + ", currency=" + currency + "]";
	}
	
}
