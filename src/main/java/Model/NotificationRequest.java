package Model;


import jakarta.persistence.*; // Hoặc javax.persistence.* tùy phiên bản Jakarta EE/Java EE
import java.io.Serializable; // Cần Serializable nếu class implement Serializable
import java.time.LocalDateTime;
import java.util.Objects; // Cần import Objects cho equals/hashCode

@Entity
@Table(name = "notification_requests")
// Đã bỏ @Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class NotificationRequest implements Serializable { // Giữ lại implements Serializable nếu cần

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Đã bỏ @EqualsAndHashCode.Include
    private Long requestId; // ID nội bộ tự tăng

    @Column(nullable = false)
    private String title; // Tiêu đề thông báo

    @Column(nullable = false, length = 1000)
    private String message; // Nội dung thông báo

    private LocalDateTime sentDate; // Thời gian gửi

    // Mỗi notification request thuộc về 1 student
    @ManyToOne(fetch = FetchType.LAZY)
    // Đã sửa JoinColumn để tham chiếu khóa chính của Student (account_id)
    @JoinColumn(name = "student_account_id", referencedColumnName = "account_id", nullable = false)
    private Student student; // Tham chiếu tới Student

    // Constructor không tham số (thay thế @NoArgsConstructor)
    public NotificationRequest() {
    }

    // Constructor với tất cả các trường (thay thế @AllArgsConstructor)
    public NotificationRequest(Long requestId, String title, String message, LocalDateTime sentDate, Student student) {
        this.requestId = requestId;
        this.title = title;
        this.message = message;
        this.sentDate = sentDate;
        this.student = student;
    }

     // Constructor không bao gồm ID (thường dùng khi tạo đối tượng mới để lưu)
     public NotificationRequest(String title, String message, LocalDateTime sentDate, Student student) {
        this.title = title;
        this.message = message;
        this.sentDate = sentDate;
        this.student = student;
    }


    // Getters
    public Long getRequestId() {
        return requestId;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getSentDate() {
        return sentDate;
    }

    public Student getStudent() {
        return student;
    }

    // Setters
    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSentDate(LocalDateTime sentDate) {
        this.sentDate = sentDate;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    // Phương thức equals() thủ công (thay thế @EqualsAndHashCode)
    // Dựa trên trường được đánh dấu @EqualsAndHashCode.Include trước đây (requestId)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationRequest that = (NotificationRequest) o;
        return Objects.equals(requestId, that.requestId);
    }

    // Phương thức hashCode() thủ công (thay thế @EqualsAndHashCode)
    // Dựa trên trường được đánh dấu @EqualsAndHashCode.Include trước đây (requestId)
    @Override
    public int hashCode() {
        return Objects.hash(requestId);
    }
}