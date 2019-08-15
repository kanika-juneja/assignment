package com.me.project.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.me.project.domain.Transaction;
import com.me.project.service.CalculateRelativeAccountBalanceService;
import com.me.project.util.DateUtil;

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

			System.out.print("Enter the fromDate in dd/MM/yyyy HH:mm:ss format : ");
			String fromDateString = reader.readLine();
			fromDate = DateUtil.getDateTimeFromString(fromDateString);

			System.out.print("Enter the toDate in dd/MM/yyyy HH:mm:ss format : ");
			String toDateString = reader.readLine();
			toDate = DateUtil.getDateTimeFromString(toDateString);

			List<Transaction> transactions = CalculateRelativeAccountBalanceService.readTransactionCsv();
			CalculateRelativeAccountBalanceService.calculateRelativeAccountbalance(transactions, transactionId, fromDate, toDate);

		} catch (Exception e) {
			log.error("Some error occurred : {}", e.getMessage());
			e.printStackTrace();
		}
	}
}
