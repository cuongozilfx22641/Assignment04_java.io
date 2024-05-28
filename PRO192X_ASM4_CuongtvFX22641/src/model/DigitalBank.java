package model;

import dao.AccountDAO;
import dao.CustomerDAO;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static dao.CustomerDAO.*;

public class DigitalBank extends Bank {

    public DigitalBank() {
        super();
    }

    // pt tim khach hang theo CCCD
    public Customer getCustomerById(String customerId) {
        for (Customer customer:CustomerDAO.list()){
            if(customerId.equals(customer.getCustomerId())){
                return customer;
            }
        }
        return null;
    }

    // Chuc nang  2 :  them tai khoan ATM va TK tin dung
    public void addSavingAccount(Scanner sc , String customerID) {
        List<Customer> customerList = CustomerDAO.list();
        List<Account> accounts = AccountDAO.list();
        String nameCustemer;
        while (true) {
            // kiem tra cccd nguoi dung nhap co dung yeu cau hay k
            if (!User.isValidCCCD(customerID)){
                System.out.println("Không đúng định dạng CCCD. Vui lòng nhập lại: ");
                customerID = sc.nextLine();
            }
            break;
        }
        boolean check = false;
        for(Customer cus : customerList){
            if(cus.getCustomerId().equals(customerID)){
                check = true;
            }
        }
        // neu cccd chua co trog danh sach khach hangf thi them khach hang moi
        if (check == false) {
            System.out.println("Nhập tên khách hàng");
            nameCustemer = sc.nextLine();
            Customer customer = new Customer();
            // them ten va CCCC nguoi dung nhap
            customer.setCustomerId(customerID);
            customer.setName(nameCustemer);
            CustomerDAO.update(customer);
            System.out.println("Đã thêm khách hàng mới vào danh sách");
        }
        Customer customer = getCustomerById(customerID);
        SavingAccount account;
        String accNumber;
        String accbalance;
        while (true) {
            try {
                System.out.println("Nhập số tài khoản ATM: ");
                accNumber = sc.nextLine();
                // neu account khong hop le thi quang loi
                if (!Account.isValidAccount(accNumber)) {
                    System.out.println("Số tài khoản không hợp lệ");
                }
                // Neu account da ton tai thi quang loi
                if (getCustomerById(customerID).isAccount(accNumber)) {
                    System.out.println("Tài khoản đã tồn tại");
                }
                if (Account.isValidAccount(accNumber) && !getCustomerById(customerID).isAccount(accNumber)) break;
            } catch (Exception e) {
                System.out.println("Vui lòng nhập lại số tài khoản ");
            }
        }
        while (true) {
            try {
                System.out.println("Nhập số dư: ");
                accbalance = sc.nextLine();
                if (Double.parseDouble(accbalance) < 50_000) {
                    System.out.println("Số dư phải > 50_000đ: ");
                }
                if (Double.parseDouble(accbalance) >= 50_000) break;
            } catch (Exception e) {
                System.out.println("Vui lòng nhập lại số dư: ");
            }
        }
        account = new SavingAccount(accNumber, Double.parseDouble(accbalance), customerID);
        customer.aadAccount(account);
        accounts.add(account);
        AccountDAO.save(accounts);
        System.out.println("Đã thêm tài khoản thành công");

    }
    //   Tạo pt định dạnh số dư phân cách hàng nghìn
    public static String formatBalance(double balance) {
        String formatBalance = "";
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        formatBalance += (decimalFormat.format((long) balance) +"đ");
        return formatBalance;
    }

    // Chức năng 2: đọc file thêm danh sách khách hàng
    public void readFileAddCustomer(String filePath) {
        List<Customer> customerList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Phân tách thông tin khách hàng
                String[] customerInfo = line.split(",");

                boolean check = false;
                for (Customer cus : CustomerDAO.list()) {
                    if (cus.getCustomerId().equals(customerInfo[0])) {
                        check = true;
                    }
                }
                if(User.isValidCCCD(customerInfo[0]) && check == false ){
                    System.out.println("Đã thêm khách hàng "+(customerInfo[0])+ " vào danh sách khách hàng");
                    customerList.add(new Customer(customerInfo[1],customerInfo[0]));
                    save(customerList);

                }else{
                    System.out.println("Khách hàng "+customerInfo[0]+ " đã tồn tại hoặc không hợp lệ, thêm khách hàng không thành công");
                }
            }
        } catch (IOException e) {
            System.out.println("Tệp không tồn tại");
        }
    }

    // Chuc nang 1: hien thi khach hang
    public void showCustomers() {
        List<Customer> customers = CustomerDAO.list();
        if (customers.size()>0) {
            // Lap tung khach hang va hien thi ra ngoai
            for (Customer cus : customers) {
                cus.displayInformaion();
            }
        } else {
            System.out.println("Hiện chưa có khách hàng nào trong danh sách.");
        }
    }
    public Account getAccountByAccountNumber(String accountNumber){
        for (Account account : AccountDAO.list()){
            if (accountNumber.equals(account.getAccountNumber())) return account;
        }
        return null;
    }

}
