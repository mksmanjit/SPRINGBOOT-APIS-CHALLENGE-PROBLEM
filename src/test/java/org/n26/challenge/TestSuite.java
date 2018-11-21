package org.n26.challenge;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.n26.challenge.business.dao.TransactionDAOTest;
import org.n26.challenge.business.service.StatisticServiceTest;
import org.n26.challenge.business.service.TransactionServiceTest;
import org.n26.challenge.business.util.DateUtilTest;
import org.n26.challenge.presentation.controller.StatisticControllerTest;
import org.n26.challenge.presentation.controller.TransactionControllerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ DateUtilTest.class, StatisticServiceTest.class, TransactionServiceTest.class, TransactionControllerTest.class, StatisticControllerTest.class, TransactionDAOTest.class })
public class TestSuite {

}
