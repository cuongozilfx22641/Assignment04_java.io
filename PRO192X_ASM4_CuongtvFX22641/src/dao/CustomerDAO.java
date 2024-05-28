package dao;

import model.Customer;
import service.BinaryFileService;

import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private static final String FILE_PATH = "store/customers.dat";

    // luu vao file
    public static void save(List<Customer> customers) {
        BinaryFileService.writeFile(FILE_PATH, customers);
    }

    // doc file
    public static List<Customer> list(){
        return BinaryFileService.readFile(FILE_PATH);
    }

    public static void update(Customer editCus) {
        List<Customer> customers = list();
        boolean hasExist = customers.stream().anyMatch(account -> account.getCustomerId().equals(editCus.getCustomerId()));

        List<Customer> updateCustomer;
        if(!hasExist){
            updateCustomer = new ArrayList<>(customers);
            updateCustomer.add(editCus);
        }else {
            updateCustomer = new ArrayList<>();

            for (Customer cus : customers){
                if (cus.getCustomerId().equals(editCus.getCustomerId())){
                    updateCustomer.add(editCus);
                }else {
                    updateCustomer.add(cus);
                }
            }
        }
        save(updateCustomer);
    }
}
