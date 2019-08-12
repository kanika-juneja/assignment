package com.meProject.domain;

import com.opencsv.bean.CsvBindByName;

public class Transaction {
	
	@CsvBindByName(required = true)
	private String transactionId;
	
	@CsvBindByName(required = true)
	private String fromAccountId;
	
	@CsvBindByName(required = true)
	private String toAccountId;
	
	@CsvBindByName(required = true)
	private String createAt;
	
	@CsvBindByName(required = true)
	private Double amount;
	
	@CsvBindByName(required = true)
	private String transactionType;
	
	@CsvBindByName(required = false)
	private String relatedTransaction;

	/**
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the fromAccountId
	 */
	public String getFromAccountId() {
		return fromAccountId;
	}

	/**
	 * @param fromAccountId the fromAccountId to set
	 */
	public void setFromAccountId(String fromAccountId) {
		this.fromAccountId = fromAccountId;
	}

	/**
	 * @return the toAccountId
	 */
	public String getToAccountId() {
		return toAccountId;
	}

	/**
	 * @param toAccountId the toAccountId to set
	 */
	public void setToAccountId(String toAccountId) {
		this.toAccountId = toAccountId;
	}

	/**
	 * @return the createAt
	 */
	public String getCreateAt() {
		return createAt;
	}

	/**
	 * @param createAt the createAt to set
	 */
	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * @return the relatedTransaction
	 */
	public String getRelatedTransaction() {
		return relatedTransaction;
	}

	/**
	 * @param relatedTransaction the relatedTransaction to set
	 */
	public void setRelatedTransaction(String relatedTransaction) {
		this.relatedTransaction = relatedTransaction;
	}
}
