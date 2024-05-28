package model;
import model.Interface.ReportService;
import model.Interface.Transfer;
import model.Interface.Withdraw;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SavingAccount extends Account implements ReportService, Withdraw, Transfer, Serializable {
    private final static long serialVersionUID = 1L;
    private static final double SAVINGS_ACCOUNT_MAX_WITHDRAW = 5_000_000;

    public SavingAccount(String accountNumber, double balance, String customerId) {
        super(accountNumber, balance, customerId);
    }

    public SavingAccount() {

    }
    // Pt in bien lai giao dich rút tiền cua ATM
    @Override
    public void log(double amount) {
        String rowLine = "+--------------+--------------------+--------------+%n";
        System.out.format(rowLine);
        System.out.printf("%30s%n","BIEN LAI GIAO DICH");
        System.out.printf("NGAY G/D: %28s%n",getCreatedAt());
        System.out.printf("ATM ID: %30s%n","DIGITAL-BANK_ATM 2023");
        System.out.printf("SO TK: %31s%n", this.getAccountNumber());
        System.out.printf("SO TIEN: %29s%n",DigitalBank.formatBalance(amount));
        System.out.printf("SO DU: %31s%n",DigitalBank.formatBalance(this.getBalance()));
        System.out.printf("PHI + VAT: %27s%n","0");
        System.out.format(rowLine);
    }
    // log chuyển tiền

    @Override
    public void log2(double amount,Account accountNumber, Account receivedAccount) {
        String rowLine = "+--------------+--------------------+--------------+%n";
        System.out.format(rowLine);
        System.out.printf("%30s%n","BIEN LAI GIAO DICH");
        System.out.printf("NGAY G/D: %28s%n",getCreatedAt());
        System.out.printf("ATM ID: %30s%n","DIGITAL-BANK_ATM 2023");
        System.out.printf("SO TK: %31s%n", accountNumber.getAccountNumber());
        System.out.printf("SO TK NHẬN: %26s%n", receivedAccount.getAccountNumber());
        System.out.printf("SO TIEN: %29s%n",DigitalBank.formatBalance(amount));
        System.out.printf("SO DU: %31s%n",DigitalBank.formatBalance(accountNumber.getBalance()));
        System.out.printf("PHI + VAT: %27s%n","0");
        System.out.format(rowLine);
    }

    // phuong thuc xu ly nghiep vu rut tien cua ATM
    @Override
    public boolean withdraw(double amount) {
        if(isAccepted(amount)){
            setBalance(getBalance() - amount);
            System.out.println("Rút tiền thành công. Số tiền còn lại trong tài khoản là "+DigitalBank.formatBalance(getBalance()));
        } else {
            System.out.println("Rút tiền không thành công.");
            return false;
        }
        return true;
    }
    // Pt kiem tra co thoa man dieu kien rut tien hay khong
    @Override
    public boolean isAccepted(double amount) {
        double newBalance = getBalance() - amount ;
        if(amount <= 50_000  && amount < (getBalance())-50_000 ){
            System.out.println("Số tền rút tối thiểu là 50.000đ và số dư tối thiểu không < 50.000đ");
            return false;
        }else if (newBalance % 10000 != 0){
            System.out.println("Số tiền rút phải là bội số của 10.000đ");
            return false;
        } else if (isPremium() == false && amount >SAVINGS_ACCOUNT_MAX_WITHDRAW) {
            System.out.println("Tài khoản thường không rút được quá 5.000.000đ");
            return false;
        }
        return true;
    }

    // Pt hien thi date
    public String getCreatedAt(){
        Date createdAt = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String str = format.format(createdAt);
        return str;
    }

    @Override
    public boolean transfers(Account accountNumber, Account receivedAccount, double amount) {
        if (isAccepted(amount)) {
            double newBalanceAcc1 = accountNumber.getBalance() - amount;
            accountNumber.setBalance(newBalanceAcc1);
            double newBalanceAcc2 = receivedAccount.getBalance() + amount;
            receivedAccount.setBalance(newBalanceAcc2);
            log2(amount,accountNumber,receivedAccount);
            return true;
        }else {
            return false;
        }
    }
}
