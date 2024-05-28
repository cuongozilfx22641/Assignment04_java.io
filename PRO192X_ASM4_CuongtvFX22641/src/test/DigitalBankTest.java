package test;

import dao.AccountDAO;
import dao.CustomerDAO;
import model.Customer;
import model.DigitalBank;
import model.SavingAccount;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.Assert.*;

public class DigitalBankTest {
    private DigitalBank ac = new DigitalBank();
    private Customer customer;
    private CustomerDAO customerDAO;
    private AccountDAO accountDAO;

    @Before
    public void setUp() throws Exception {
        customer = new Customer();
        customerDAO = new CustomerDAO();
        accountDAO = new AccountDAO();
        System.out.println("Running test ...");
    }

    @Test
    public void getCustomerById() {
    }

    @Test
    public void addSavingAccount() {


    }

    @Test
    public void formatBalance() {
    }

    @Test
    public void readFileAddCustomer() {
    }

    @Test
    public void showCustomers() {
    }

    @Test
    public void getAccountByAccountNumber() {
    }
}