package com.me.project.service;

import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.me.project.app.CalculateRelativeAccountBalance;
import com.me.project.constants.MeConstants;
import com.me.project.domain.Transaction;
import com.me.project.util.DateUtil;
import com.opencsv.bean.CsvToBeanBuilder;

/**
 * @author Kanika
 * Service class containing the logic for calculating relative account balance
 */
public class CalculateRelativeAccountBalanceService {
	
	private static final Logger log = LoggerFactory.getLogger(CalculateRelativeAccountBalance.class);

	/**
	 * Method to read and parse csv file.
	 * @return List<Transaction> transactions
	 */
	public static List<Transaction> readTransactionCsv() throws IOException {
		List<Transaction> transactions = new ArrayList<>();
		transactions = new CsvToBeanBuilder<Transaction>(new FileReader(MeConstants.SAMPLE_CSV_FILE_PATH))
				.withType(Transaction.class).build().parse();
		return transactions;
	}

	/**
	 * Method to calculate relative account balance for a particular account between the input dates.
	 * @param transactions
	 * @param accountNumber
	 * @param fromDate
	 * @param toDate
	 */
	public static void calculateRelativeAccountbalance(List<Transaction> transactions, String accountNumber,
			LocalDateTime fromDate, LocalDateTime toDate) throws Exception {

		Double relativeAccountBalance = 0.0D;

		if (CollectionUtils.isNotEmpty(transactions) && null != accountNumber && null != fromDate && null != toDate) {
			List<Transaction> filteredTxns = transactions.stream()
					.filter(txn -> (accountNumber.equalsIgnoreCase(txn.getFromAccountId().trim())
							|| accountNumber.equalsIgnoreCase(txn.getToAccountId().trim()))
							&& (fromDate.isEqual(DateUtil.getDateTimeFromString(txn.getCreateAt()))
									|| fromDate.isBefore(DateUtil.getDateTimeFromString(txn.getCreateAt())))
							&& (toDate.isEqual(DateUtil.getDateTimeFromString(txn.getCreateAt()))
									|| toDate.isAfter(DateUtil.getDateTimeFromString(txn.getCreateAt())))
							&& (MeConstants.PAYMENT_TRANSACTION_TYPE.equalsIgnoreCase(txn.getTransactionType())))
					.collect(Collectors.toList());

			Set<String> reversalTxnIds = transactions.stream().filter(
					txn -> (null != txn.getRelatedTransaction() && StringUtils.isNotEmpty(txn.getRelatedTransaction())))
					.map(Transaction::getRelatedTransaction).collect(Collectors.toSet());

			filteredTxns.removeIf(txn -> reversalTxnIds.contains(txn.getTransactionId()));

			for (Transaction t : filteredTxns) {
				if (accountNumber.equalsIgnoreCase(t.getFromAccountId())) {
					relativeAccountBalance = relativeAccountBalance - t.getAmount();
				} else {
					relativeAccountBalance = relativeAccountBalance + t.getAmount();
				}
			}

			NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.CANADA);
			log.info("Relative balance for the period is : {}", currencyFormat.format(relativeAccountBalance));
			log.info("Number of transactions included is : {}", filteredTxns.size());
		} else {
			log.info("Either the transactions list is empty, account number is null, from date is null or to date is null.");
		}
	}
}
