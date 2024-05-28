package model;

import dao.AccountDAO;
import dao.TransactionDao;

import java.io.Serializable;
import java.util.List;

public class Customer extends User implements Serializable {
    private final static long serialVersionUID = 1L;
    private List<Account> accounts ;
    private List<Bill> bills;

    public List<Bill> getBills() {
        return TransactionDao.list();
    }
    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    public Customer(){
        super();
        this.accounts = AccountDAO.list();
    }

    public Customer(String name, String customerId) {
        super(name, customerId);
        this.accounts = AccountDAO.list();
        this.bills = TransactionDao.list();
    }

    // get cua account
    public List<Account> getAccounts() {
        return AccountDAO.list();
    }

    // check xem cos phai vip khong
    public boolean isPremium(){
        boolean vip=false;
        for (Account vipCheck:getAccounts()){
            if(vipCheck.isPremium()){
                vip =true;
                break;
            }
        }
        return vip;
    }

    // kiem tra xem account da ton tai chua
    public boolean isAccount(String stk){
        boolean isStk = false;
        for (Account account:AccountDAO.list()){
            if(stk.equals(account.getAccountNumber())){
                isStk = true;
                break;
            }
        }return isStk;
    }

    // thêm 1 account
    public void aadAccount(Account newAccount){
        if(!isAccount(newAccount.getAccountNumber())){
            this.accounts.add(newAccount);
        }
        else {
            System.out.println("So tai khoan da ton tai");
        }
    }

    // tinh tong so du cua tat ca cac tai khoan
    public double getBalance(){
        double balance= 0;
        for (Account acc:AccountDAO.list()){
            if (this.getCustomerId().equals(acc.getCustomerId())){
                balance+= acc.getBalance();
            }
        }
        return balance;
    }

    public boolean isPremiumAcc(){
        return getBalance() >= 10_000_000;
    }

    // tra ve thong tin khach hang
    public void displayInformaion() {
        try {
            String status = (this.isPremiumAcc()) ? "Premium" : "Normal";
            System.out.printf("%-12s | %-10s | %-10s | %16s %n", this.getCustomerId(), this.getName(), status, Account.formatBalance(getBalance()));
            List<Account> accountList = AccountDAO.list();
            if (accountList.size() > 0) {
                int i = 1;
                for (Account acc : accountList) {
                    if (this.getCustomerId().equals(acc.getCustomerId())){
                        String type = "Saving";
                        // hien thi thong tin khach hang
                        System.out.printf("%-4s  %4s | %-10s | %29s %n", i, acc.getAccountNumber(), type, Account.formatBalance(acc.getBalance()));
                        i++;
                    }
                }
            } else {
                System.out.println("Không có tài khoản");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
