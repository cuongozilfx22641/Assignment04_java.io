import dao.AccountDAO;
import dao.CustomerDAO;
import dao.TransactionDao;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Asm4 {
    private static final DigitalBank activeBank = new DigitalBank();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        mainProgram();
    }

    public static List<Customer> getCustomers() {
        return CustomerDAO.list();
    }

    public static void showMenu() {
        final String AUTHOR = "FX22641@";
        final String VERSION = "v2.0.0";
        String rowLine = "+--------------+--------------------+--------------+%n";
        int lengthRowLine = rowLine.length();
        String str = "| %-" + (lengthRowLine - 6) + "s |%n";
        System.out.format(rowLine);
        System.out.format(str, "NGAN HANG SO | " + AUTHOR + "@" + VERSION);
        System.out.format(rowLine);
        System.out.format(str, "1. Danh sách khách hàng");
        System.out.format(str, "2. Nhập danh sách khách hàng");
        System.out.format(str, "3. Thêm tài khoản ATM");
        System.out.format(str, "4. Chuyển tiền");
        System.out.format(str, "5. Rút tiền");
        System.out.format(str, "6. Lịch sử giao dịch");
        System.out.format(str, "0. Thoát");
        System.out.format(rowLine);
    }

    public static void mainProgram() {
        while (true) {
            showMenu();
            try {
                System.out.println("Chức năng: ");
                String choose = sc.nextLine();
                int inputChoose = Integer.parseInt(choose);
                if (inputChoose < 0 || inputChoose > 6) {
                    System.out.println("Vui lòng nhập đúng chức năng: ");
                    choose = sc.nextLine();
                    continue;
                }
                switch (inputChoose) {
                    case 0:
                        System.out.println("Kết thúc chương trình.");
                        return;
                    case 1:
                        activeBank.showCustomers();
                        System.out.println("Kết thúc chức năng 1");
                        break;
                    case 2:
                        System.out.println("Nhập đường dẫn đến tệp: ");
                        String fileName = sc.nextLine();
                        activeBank.readFileAddCustomer(fileName);
                        System.out.println("Kết thúc chức năng 2");
                        break;
                    case 3:
                        System.out.println("Nhập CCCD khách hàng: ");
                        String CCCD = sc.nextLine();
                        activeBank.addSavingAccount(sc,CCCD);
                        System.out.println("Kết thúc chức năng 3");
                        break;
                    case 4:
                        transfers();
                        System.out.println("Kết thúc chức năng 4");
                        break;
                    case 5:
                        drawMoney();
                        System.out.println("Kết thúc chức năng 5");
                        break;
                    case 6:
                        displayInformationTransaction();
                        System.out.println("Kết thúc chức năng 6");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Vui lòng nhập lại chức năng: ");
            }
        }
    }


    // chuc nang 4: chuyển tiền
    public static void transfers() {
        System.out.println("Nhập CCCD khách hàng: ");
        String cccd = sc.nextLine();
        while (true) {
            // kiem tra cccd nguoi dung nhap co dung yeu cau hay k
            if (!User.isValidCCCD(cccd)) {
                System.out.println("Không đúng định dạng CCCD. Nhập lại CCCD: ");
                cccd = sc.nextLine();
            }
            if (User.isValidCCCD(cccd)) break;
        }
        Customer customer = activeBank.getCustomerById(cccd);
        if (customer != null){
            try {
                System.out.println("Nhập số tài khoản chuyển tiền: ");
                String accountNumber = sc.nextLine();
                while (true) {
                    // Kiem tra tài khoan nguoi dung nhap da ton tai chua
                    if (activeBank.getCustomerById(cccd).isAccount(accountNumber)) {
                        break;
                    }
                    System.out.println("Số tài khoản không tồn tại");
                    accountNumber = sc.nextLine();
                }
                Account acc1 =activeBank.getAccountByAccountNumber(accountNumber);
                System.out.println("Nhập số tài khoản nhận tiền: ");
                String receiveAccNumber = sc.nextLine();
                while (true) {
                    // Kiem tra tài khoan nguoi dung nhap da ton tai chua
                    if (activeBank.getAccountByAccountNumber(receiveAccNumber)!=null) {
                        break;
                    }
                    System.out.println("Số tài khoản không tồn tại");
                    receiveAccNumber = sc.nextLine();
                }
                Account acc2 = activeBank.getAccountByAccountNumber(receiveAccNumber);
                System.out.println("Nhập số tiền muốn chuyển: ");
                Double amount = Double.parseDouble(sc.nextLine());
                //System.out.print("Xác nhận thực hiện chuyển " + amount + "đ từ tài khoản [" + accountNumber + "] đến tài khoản [" + receiveAccNumber + "] ");

                SavingAccount trans = new SavingAccount();
                if (trans.transfers(acc1, acc2, amount)){
                    System.out.println("G/D thành công");
                    AccountDAO.update(acc1);
                    AccountDAO.update(acc2);

                   Bill bill = new Bill(cccd, acc1.getAccountNumber(),String.format("%s%s","-", amount), trans.getCreatedAt(), TransationType.TRANSFER);
                   Bill bill2 = new Bill(acc2.getCustomerId(), acc2.getAccountNumber(), String.valueOf(amount), trans.getCreatedAt(), TransationType.TRANSFER);

                   List<Bill> billList = TransactionDao.list();
                   billList.add(bill);
                   billList.add(bill2);
                   TransactionDao.save(billList);

                }else {
                    System.out.println("G/D không thành công");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("Khách hàng không tồn tại");
        }
    }
    // Chuc nang 5: rui tien
    public static void drawMoney() {
        System.out.println("Nhập CCCD của khách hàng: ");
        String CCCD = sc.nextLine();
        while (true) {
            // check nguoi dung nhạp cccd co dung khong
            if (User.isValidCCCD(CCCD)) break;
            System.out.println("Không đúng định dạng CCCD. Nhập lại CCCD: ");
            CCCD = sc.nextLine();
        }

        System.out.println("Nhập số tài khoản cần rút: ");
        String accountNumber = sc.nextLine();
        while (true) {
            // Kiem tra tài khoan nguoi dung nhap da ton tai chua
            if (activeBank.getCustomerById(CCCD).isAccount(accountNumber)) {
                break;
            }
            System.out.println("Số tài khoản không tồn tại.");
            System.out.println("Nhập lại số tài khoản cần rút: ");
            accountNumber = sc.nextLine();
        }
        System.out.println("Nhập số tiền muốn rút: ");
        double amount = Double.parseDouble(sc.nextLine());
        Customer customer1 = new Customer();
        try {
            for (Customer cus : getCustomers()) {
                if (cus.getCustomerId().equals(CCCD)) {
                    customer1 = cus;
                }
            }
            for (Account account : customer1.getAccounts()) {
                if (account.getAccountNumber().equals(accountNumber)) {
                    if (account instanceof SavingAccount) {
                        SavingAccount savingAccount = (SavingAccount) account;
                        boolean checkWithdraw = savingAccount.withdraw(amount);
                        if (checkWithdraw == true) {
                            savingAccount.log(amount);
                            //tao mot list bill de luu lich su giao dich cua tk ATM
                            Bill bill = new Bill(customer1.getCustomerId(), account.getAccountNumber(), String.valueOf(amount), savingAccount.getCreatedAt(),TransationType.WITHDRAW);
                            List<Bill> bills = customer1.getBills();
                            bills.add(bill);
                            customer1.setBills(bills);
                            TransactionDao.save(bills);
                            AccountDAO.update(savingAccount);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Chuc nang 6: hien thi giao dich cua khach hang
    private static void displayInformationTransaction() {
        System.out.println("Nhập CCCD khách hàng: ");
        String CCCD = sc.nextLine();
        while (true) {
            if (User.isValidCCCD(CCCD)) break;
            System.out.println("Không đúng định dạng CCCD. Nhập lại CCCD: ");
            CCCD = sc.nextLine();
        }
        Customer customer1 = activeBank.getCustomerById(CCCD);
        customer1.displayInformaion();
        String GD = "[GD]";
        for (Bill bill : customer1.getBills()) {
            if (CCCD.equals(bill.getCustomerId())){
                System.out.println(String.format("%-4s  %-4s | %8s | %12s | %20s %n", GD, bill.getAcountNumber(),bill.getType() ,DigitalBank.formatBalance(Double.parseDouble(bill.getAmount())), bill.getCreateAt()));
            }
        }
    }
}
