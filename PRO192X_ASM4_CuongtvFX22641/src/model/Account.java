package model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class Account implements Serializable {
    private final static long serialVersionUID = 1L;
    private String accountNumber;
    protected double balance;

    private String customerId;
    private List<Bill> bills;


    // contructor
    public Account() {
    }
    // Contructor
    public Account(String accountNumber, double balance,String customerId) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.customerId = customerId;
        this.bills = new ArrayList<>();
    }
    // Get and Set
    public String getAccountNumber() {
        return accountNumber;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public String getCustomerId() {
        return customerId;
    }

    // kiem tra so tai khoan co hop le khong
    public static boolean isValidAccount(String accountNumber){
        if(accountNumber.length() !=6){
            return false;
        }
        return true;
    }
    // Check xem co phai vip khong
    public boolean isPremium(){
        boolean vip = false;
        if(this.getBalance()>= 10_000_000){
            vip = true;
        }
        return vip;
    }


    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    // Phuong thuc toString
    @Override
    public String toString() {
        return ("|    "+ this.getAccountNumber()+"|       |"+this.getBalance());
    }

    //Tạo pt định dạnh số dư phân cách hàng nghìn
    public static String formatBalance(double balance) {
        String formatBalance = "";
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        formatBalance += (decimalFormat.format((long) balance) +"vnd");
        return formatBalance;
    }
}
