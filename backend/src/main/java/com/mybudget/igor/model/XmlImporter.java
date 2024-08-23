package com.mybudget.igor.model;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import com.mybudget.igor.service.AccountService;
import com.mybudget.igor.service.TransactionService;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class XmlImporter {

    public static void importXml(String fileName, AccountService accountService, TransactionService transactionService) {
        try {
            // Create a new instance of DocumentBuilderFactory and DocumentBuilder using the factory, parse XML file by fileName
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(fileName);

            doc.getDocumentElement().normalize();

            // Get the first "Accounts" element and retrieve a NodeList of all "Account" nodes
            Element accountsRoot = (Element) doc.getElementsByTagName("Accounts").item(0);
            NodeList accountList = accountsRoot.getElementsByTagName("Account");

            for(int i = 0; i < accountList.getLength(); i++) {
                Node accountNode = accountList.item(i);

                // Check if the current node is an element node and cast it to an Element so getAttribute, etc. can be used
                if(accountNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element accountElement = (Element) accountNode;
                    Account newAccount = new Account();

                    newAccount.setName(accountElement.getAttribute("name"));
                    newAccount.setCurrency(accountElement.getAttribute("currency").toLowerCase());

                    String balanceText = accountElement.getElementsByTagName("Balance").item(0).getTextContent();
                    Double balance = Double.parseDouble(balanceText);
                    newAccount.setBalance(balance);

                    accountService.addAccount(newAccount);

                    // "Transaction" nodes are all under "Transactions" node so get that first
                    Element transactionsRoot = (Element) accountElement.getElementsByTagName("Transactions").item(0);
                    if(transactionsRoot == null) {
                        continue;
                    }
                    NodeList transactionList = transactionsRoot.getElementsByTagName("Transaction");

                    for(int j = 0; j <transactionList.getLength(); j++) {
                        Node transactionNode = transactionList.item(j);

                        if(transactionNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element transactionElement = (Element) transactionNode;
                            Transaction newTransaction = new Transaction();

                            newTransaction.setAccount(newAccount);
                            newTransaction.setDescription(transactionElement.getElementsByTagName("Description").item(0).getTextContent());
                            Element amountElement = (Element) transactionElement.getElementsByTagName("Amount").item(0);
                            String amountText = amountElement.getTextContent();

                            // Check if the amount is positive or negative and set transaction type accordingly,
                            // flip the sign if negative as all the amounts are expected to be positive internally
                            Double amount = Double.parseDouble(amountText);
                            if(amount >= 0) {
                                newTransaction.setType(TransactionType.INCOME);
                            } else {
                                newTransaction.setType(TransactionType.EXPENSE);
                                amount *= -1;
                            }
                            newTransaction.setAmount(amount);
                            newTransaction.setCurrency(amountElement.getAttribute("currency").toLowerCase());

                            transactionService.addTransaction(newTransaction);

                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to import XML: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
