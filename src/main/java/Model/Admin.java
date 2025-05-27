package Model;


import jakarta.persistence.*;
import java.io.Serializable;
// Đã bỏ import của Lombok

@Entity
@Table(name = "admins")
@PrimaryKeyJoinColumn(name = "account_id")
// Đã bỏ @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Admin extends Account implements Serializable {

    // Constructor không tham số (đã có sẵn)
    public Admin() {
        super();
    }

    // Constructors với tham số của lớp cha (đã có sẵn)
    public Admin(String username, String password, String email, String phone, String avatar, boolean isActive) {
        super(username, password, email, phone, avatar, isActive);
    }

    public Admin(Long accountId, String username, String password, String email, String phone, String avatar, boolean isActive) {
        super(accountId, username, password, email, phone, avatar, isActive);
    }

    // Vì Admin không có thuộc tính riêng trong cấu trúc này,
    // không cần thêm getters/setters riêng cho Admin.
    // Getters và Setters cho các thuộc tính kế thừa từ Account nằm trong class Account.
}