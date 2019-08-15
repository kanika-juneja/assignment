package com.me.project.service.test;

import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.me.project.domain.Transaction;
import com.me.project.service.CalculateRelativeAccountBalanceService;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.me.project.service.*")
public class CalculateRelativeAccountBalanceServiceTest {
	
	@Test
	public void readTransactionCsv() throws IOException{
		PowerMockito.spy(CalculateRelativeAccountBalanceService.class);
		PowerMockito.when(CalculateRelativeAccountBalanceService.readTransactionCsv()).thenReturn(Mockito.anyList());
	}
	
	@Test
	public void calculateRelativeAccountbalance() throws Exception {
		PowerMockito.spy(CalculateRelativeAccountBalanceService.class);
		List<Transaction> txns = new ArrayList<Transaction>();
		Transaction t1 = new Transaction();
		t1.setAmount(new Double ("25"));
		t1.setCreateAt("20/10/2018 12:47:55");
		t1.setFromAccountId("ACC334455");
		t1.setToAccountId("ACC778899");
		t1.setTransactionId("TX10001");
		t1.setTransactionType("PAYMENT");
		txns.add(t1);
		
		Clock clock1 = Clock.fixed(Instant.parse("2018-10-20T12:00:00.00Z"), ZoneId.of("UTC"));
        LocalDateTime fromDateTime = LocalDateTime.now(clock1);
        Clock clock2 = Clock.fixed(Instant.parse("2018-10-20T19:00:00.00Z"), ZoneId.of("UTC"));
        LocalDateTime toDateTime = LocalDateTime.now(clock2);
		CalculateRelativeAccountBalanceService.calculateRelativeAccountbalance(txns,"ACC334455",fromDateTime,toDateTime);
	}
	
	@Test
	public void calculateRelativeAccountbalanceWithNullAccNum() throws Exception {
		PowerMockito.spy(CalculateRelativeAccountBalanceService.class);
		List<Transaction> txns = new ArrayList<Transaction>();
		Transaction t1 = new Transaction();
		t1.setAmount(new Double ("25"));
		t1.setCreateAt("20/10/2018 12:47:55");
		t1.setFromAccountId("ACC334455");
		t1.setToAccountId("ACC778899");
		t1.setTransactionId("TX10001");
		t1.setTransactionType("PAYMENT");
		txns.add(t1);
		
		Clock clock1 = Clock.fixed(Instant.parse("2018-10-20T12:00:00.00Z"), ZoneId.of("UTC"));
        LocalDateTime fromDateTime = LocalDateTime.now(clock1);
        Clock clock2 = Clock.fixed(Instant.parse("2018-10-20T19:00:00.00Z"), ZoneId.of("UTC"));
        LocalDateTime toDateTime = LocalDateTime.now(clock2);
		CalculateRelativeAccountBalanceService.calculateRelativeAccountbalance(txns,null,fromDateTime,toDateTime);
	}
	
}
