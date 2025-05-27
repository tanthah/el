package Model;


import jakarta.persistence.*; // Hoặc javax.persistence.* tùy phiên bản Jakarta EE/Java EE
import java.io.Serializable; // Cần Serializable nếu class implement Serializable
import java.util.ArrayList;
import java.util.List;
import java.util.Objects; // Cần import Objects cho equals/hashCode nếu thêm thủ công

@Entity
@Table(name = "students")
// Đã bỏ @Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
@PrimaryKeyJoinColumn(name = "account_id") // dùng chung khóa chính với Account
public class Student extends Account implements Serializable { // Giữ lại implements Serializable nếu cần

    // Các danh sách liên kết được khởi tạo ngay tại khai báo
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<Submission> submissions = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<NotificationRequest> notificationRequests = new ArrayList<>();

    // Constructor không tham số (thay thế @NoArgsConstructor)
    public Student() {
        super();
        // Các danh sách đã được khởi tạo ở trên
    }

    // Constructor với các tham số của lớp cha Account
    // (thay thế một phần @AllArgsConstructor)
    public Student(String username, String password, String email, String phone, String avatar, boolean isActive) {
        super(username, password, email, phone, avatar, isActive);
         // Các danh sách đã được khởi tạo ở trên
    }

     // Constructor bao gồm accountId và các tham số của lớp cha Account
    // (thay thế một phần @AllArgsConstructor)
    public Student(Long accountId, String username, String password, String email, String phone, String avatar, boolean isActive) {
        super(accountId, username, password, email, phone, avatar, isActive);
         // Các danh sách đã được khởi tạo ở trên
    }

    // Getters cho các danh sách liên kết (Getters/Setters cho Account fields nằm trong class Account)
    public List<Order> getOrders() {
        return orders;
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public List<NotificationRequest> getNotificationRequests() {
        return notificationRequests;
    }

    // Setters cho các danh sách liên kết thường không được tạo
    // (thay vào đó, sử dụng getters để truy cập và thêm/bớt phần tử)

    // Phương thức equals() thủ công (thay thế @EqualsAndHashCode)
    // Dựa trên trường được đánh dấu @EqualsAndHashCode.Include trước đây (thường là accountId kế thừa)
    // Cần callSuper=true và onlyExplicitlyIncluded=true như Lombok setup
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false; // So sánh phần của lớp cha (dựa trên ID)
        // Student student = (Student) o; // Không cần so sánh thêm trường nào riêng của Student
        return true;
    }

    // Phương thức hashCode() thủ công (thay thế @EqualsAndHashCode)
    // Cần callSuper=true và onlyExplicitlyIncluded=true như Lombok setup
    @Override
    public int hashCode() {
        return super.hashCode(); // Sử dụng hashCode của lớp cha (dựa trên ID)
    }
}