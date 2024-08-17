package com.mybudget.igor;

import com.mybudget.igor.model.XmlImporter;
import com.mybudget.igor.service.AccountService;
import com.mybudget.igor.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;

@SpringBootApplication
public class MyBudgetApplication {
	@Autowired
	private AccountService accountService;
	@Autowired
	private TransactionService transactionService;

	public static void main(String[] args) {
		SpringApplication.run(MyBudgetApplication.class, args);
	}

	@Bean
	public ApplicationRunner applicationRunner() {
		return args -> {

			File xmlFile = new File("../my_budget_data.xml");
			if(xmlFile.exists()) {
				System.out.println("XML import start.");
				XmlImporter.importXml("../my_budget_data.xml", accountService, transactionService);
				System.out.println("XML file imported.");
				xmlFile.renameTo(new File("../my_budget_data_IMPORTED.xml"));
			} else {
				System.out.println("No xml file to import.");
			}
		};
	}
}
