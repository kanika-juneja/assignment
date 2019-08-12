package com.meProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meProject.constants.MeConstants;
import com.meProject.domain.Transaction;
import com.meProject.util.DateUtil;
import com.opencsv.bean.CsvToBeanBuilder;

/**
 * @author Kanika 
 * Class to calculate relative account balance
 */
public class CalculateRelativeAccountBalance {

	private static final Logger log = LoggerFactory.getLogger(CalculateRelativeAccountBalance.class);

	public static void main(String[] args) {
		String transactionId = null;
		LocalDateTime fromDate = null;
		LocalDateTime toDate = null;

		// Read inputs from cosole
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			System.out.print("Enter the account number for which the relative amount is to be calculated : ");
			transactionId = reader.readLine();

			System.out.print("Enter the fromDate in yyyy-MM-dd HH:mm:ss format : ");
			String fromDateString = reader.readLine();
			fromDate = DateUtil.getDateTimeFromString(fromDateString);

			System.out.print("Enter the toDate in yyyy-MM-dd HH:mm:ss format : ");
			String toDateString = reader.readLine();
			toDate = DateUtil.getDateTimeFromString(toDateString);

			List<Transaction> transactions = readTransactionCsv();
			calculateRelativeAccountbalance(transactions, transactionId, fromDate, toDate);

		} catch (Exception e) {
			log.error("Some error occurred : {}", e.getMessage());
			//Printed the error to see stack trace, can be removed
			e.printStackTrace();
		}
	}

	/**
	 * Method to read and parse csv file.
	 * @return List<Transaction> transactions
	 */
	private static List<Transaction> readTransactionCsv() throws IOException {
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
	private static void calculateRelativeAccountbalance(List<Transaction> transactions, String accountNumber,
			LocalDateTime fromDate, LocalDateTime toDate) throws Exception {

		Double relativeAccountBalance = 0.0D;

		if (CollectionUtils.isNotEmpty(transactions) && null != accountNumber && null != fromDate && null != toDate) {
			// Filter transactions list for the input account number and dates
			List<Transaction> filteredTxns = transactions.stream()
					.filter(txn -> (accountNumber.equalsIgnoreCase(txn.getFromAccountId().trim())
							|| accountNumber.equalsIgnoreCase(txn.getToAccountId().trim()))
							&& (fromDate.isEqual(DateUtil.getDateTimeFromString(txn.getCreateAt()))
									|| fromDate.isBefore(DateUtil.getDateTimeFromString(txn.getCreateAt())))
							&& (toDate.isEqual(DateUtil.getDateTimeFromString(txn.getCreateAt()))
									|| toDate.isAfter(DateUtil.getDateTimeFromString(txn.getCreateAt())))
							&& (MeConstants.PAYMENT_TRANSACTION_TYPE.equalsIgnoreCase(txn.getTransactionType())))
					.collect(Collectors.toList());

			// Fetch reversal transaction ids
			Set<String> reversalTxnIds = transactions.stream().filter(
					txn -> (null != txn.getRelatedTransaction() && StringUtils.isNotEmpty(txn.getRelatedTransaction())))
					.map(Transaction::getRelatedTransaction).collect(Collectors.toSet());

			// Ignore/remove the Reversal transactions from the filtered list.
			filteredTxns.removeIf(txn -> reversalTxnIds.contains(txn.getTransactionId()));

			for (Transaction t : filteredTxns) {
				if (accountNumber.equalsIgnoreCase(t.getFromAccountId())) {
					relativeAccountBalance = relativeAccountBalance - t.getAmount();
				} else {
					relativeAccountBalance = relativeAccountBalance + t.getAmount();
				}
			}

			// Used CANADA currency now for dollar representation. Can be changed later.
			NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.CANADA);
			log.info("Relative balance for the period is : {}", currencyFormat.format(relativeAccountBalance));
			log.info("Number of transactions included is : {}", filteredTxns.size());
		} else {
			log.info("Either the transactions list is empty, account number is null, from date is null or to date is null.");
		}
	}
}
