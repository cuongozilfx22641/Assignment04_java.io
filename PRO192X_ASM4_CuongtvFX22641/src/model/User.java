package model;

import java.io.Serializable;

public class User implements Serializable {
    private final static long serialVersionUID = 1L;
    private String name;
    private String customerId;

    public User(){

    }
    // Check ma cccd giong asm01
    public static boolean isValidCCCD(String CccdNumber){
        boolean cccd = false;
        String[][] hometown = {{"001", "Ha Noi"},{"002", "Ha Giang"}, {"004", "Cao Bang"},
                {"006", "Bac Can"}, {"008", "Tuyen Quang"}, {"010", "Lao Cai"}, {"011", "Dien Bien"},
                {"012", "Lai Chau"}, {"014", "Son La"}, {"015", "Yen Bai"}, {"017", "Hoa Binh"},
                {"019", "Thai Nguyen"}, {"020", "Lang Son"}, {"022", "Quang Ninh"}, {"024", "Bac Giang"},
                {"025", "Phu Tho"}, {"026", "Vinh Phuc"}, {"027", "Bac Ninh"}, {"030", "Hai Duong"},
                {"031", "Hai Phong"}, {"033", "Hung Yen"}, {"034", "Thai Binh"}, {"035", "Ha Nam"},
                {"036", "Nam Dinh"}, {"037", "Ninh Binh"}, {"038", "Thanh Hoa"}, {"040", "Nghe An"},
                {"042", "Ha Tinh"},{"044", "Quang Binh"}, {"045", "Quang Tri"}, {"046", "Thua Thien Hue"},
                {"048", "Da Nang"}, {"049", "Quang Nam"}, {"051", "Quang Ngai"}, {"052", "Binh Dinh"},
                {"054", "Phu Yen"}, {"056", "Khanh Hoa"}, {"058", "Ninh Thuan"}, {"060", "Binh Thuan"},
                {"062", "Kom Tum"}, {"064", "Gia Lai"}, {"066", "Dak Lak"}, {"067", "Dak Nong"},
                {"068", "Lam Dong"}, {"070", "Binh Phuoc"}, {"072", "Tay Ninh"}, {"074", "Binh Duong"},
                {"075", "Dong Nai"}, {"077", "Ba Ria - Vung Tau"}, {"079", "Ho Chi Minh"}, {"080", "Long An"},
                {"082", "Tien Giang"}, {"083", "Ben Tre"}, {"084", "Tra Vinh"}, {"086", "Vinh Long"},
                {"087", "Dong Thap"}, {"089", "An Giang"}, {"091", "Kien Giang"}, {"092", "Can Tho"},
                {"093", "Hau Giang"}, {"094", "Soc Trang"}, {"095", "Bac lieu"}, {"096", "Ca Mau"}};

        int lengthCccd = hometown.length;
        try{
            // Kiem tra cccd co phai 1 chuoi hay khong:
            long longcccd = Long.parseLong(CccdNumber);
            // Kiem tra do dai cua ma cccd:
            if ((CccdNumber.length() !=12)){
                System.out.println(1/0);
            }
            // Vong lap qua homtown xem co khop hay khong neu khop thi tra cccd ve true vaf thoat vong lap:
            for (int i=0; i<lengthCccd;i++){
                if((CccdNumber.substring(0,3).equals(hometown[i][0]))){
                    cccd = true;
                    break;
                }
            }
        }
        catch (Exception e){
            System.out.println("CCCD không hợp lệ.");
        }
        return cccd;
    }

    // Contructor
    public User(String name, String customerId) {
        this.name = name;
        this.customerId = customerId;
    }

    // set and get
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        if(isValidCCCD(customerId)){
            this.customerId = customerId;
        } else {
            System.out.println("So CCCD khong chinh xac:");
        }
    }
}
