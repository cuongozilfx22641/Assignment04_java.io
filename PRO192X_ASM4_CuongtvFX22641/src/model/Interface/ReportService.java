package model.Interface;

import model.Account;

public interface ReportService {
    void log(double amount);
    void log2(double amount, Account accountNumber, Account receivedAccount);
}
