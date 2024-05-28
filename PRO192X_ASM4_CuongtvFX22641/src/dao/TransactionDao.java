package dao;

import model.Bill;
import service.BinaryFileService;

import java.util.List;

public class TransactionDao {
    private static final String FILE_PATH = "store/transactions.dat";

    public static void save(List<Bill> transactions) {
        BinaryFileService.writeFile(FILE_PATH,transactions);
    }

    public static List<Bill> list(){
        return BinaryFileService.readFile(FILE_PATH);
    }
}
