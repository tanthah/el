package Model;

import jakarta.persistence.*;
import java.io.Serializable;
// Đã bỏ import của Lombok
// import java.util.Objects; // Không cần nếu không tự viết equals/hashCode

@Entity
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.JOINED) // Hoặc SINGLE_TABLE
// Đã bỏ @Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    // Đã bỏ @EqualsAndHashCode.Include (nếu có)
    private Long accountId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password; // Nên được hash trước khi lưu

    @Column(nullable = false, unique = true)
    private String email;
    private String phone;
    private String avatar; // URL or path to avatar
    private boolean isActive = true;

    // Constructors (Đã có sẵn và được giữ nguyên)
    public Account() {
    }

    public Account(String username, String password, String email, String phone, String avatar, boolean isActive) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.avatar = avatar;
        this.isActive = isActive;
    }

    public Account(Long accountId, String username, String password, String email, String phone, String avatar, boolean isActive) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.avatar = avatar;
        this.isActive = isActive;
    }

    // Getters and Setters (Đã có sẵn và được giữ nguyên)
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    // Bạn có thể thêm phương thức equals() và hashCode() thủ công nếu cần,
    // dựa trên các trường quan trọng để so sánh đối tượng (thường là ID).
    // Nếu Lombok @EqualsAndHashCode(onlyExplicitlyIncluded = true) được dùng,
    // logic sẽ chỉ dựa trên các trường có @EqualsAndHashCode.Include (ví dụ: accountId).
}