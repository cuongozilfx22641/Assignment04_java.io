package model.Interface;
import model.Account;

public interface Transfer {
    boolean transfers(Account accountNumber, Account receivedAccount, double amount);

}
