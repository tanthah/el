package Model;


// Import các lớp JPA và Java cần thiết
import jakarta.persistence.*; // Hoặc javax.persistence.* tùy phiên bản Jakarta EE/Java EE
import java.io.Serializable; // Cần Serializable nếu class implement Serializable
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
// Đã bỏ import của Lombok
// import java.util.Objects; // Không cần Objects nếu không tự viết equals/hashCode

// Import enum EStatus (giả định nó nằm trong package model)
import ENum.EStatus; // Sửa import từ ENum.*

@Entity
@Table(name = "submissions")
// Đã bỏ @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Submission implements Serializable { // Giữ lại implements Serializable nếu cần

    @Id // Đánh dấu trường này là khóa chính
    private String submissId; // Mã định danh lần nộp bài (khóa chính)

    // Nhiều Submission liên kết tới một Student
    @ManyToOne(fetch = FetchType.LAZY) // Tải lười biếng
    @JoinColumn(name = "student_id") // Khóa ngoại trỏ tới Student
    private Student student; // Tham chiếu tới Student

    // Nhiều Submission liên kết tới một Assessment
    @ManyToOne(fetch = FetchType.LAZY) // Tải lười biếng
    @JoinColumn(name = "assessment_id") // Khóa ngoại trỏ tới Assessment
    private Assessment assessment; // Tham chiếu tới Assessment

    private double score; // Điểm số

    private LocalDateTime completedAt; // Thời điểm hoàn thành

    @Enumerated(EnumType.STRING) // Lưu enum dưới dạng chuỗi
    private EStatus status; // Trạng thái (PASSED, FAILED, IN_PROGRESS)

    // ElementCollection: Lưu trữ kết quả trả lời của sinh viên
    @ElementCollection
    @CollectionTable(name = "submission_results", joinColumns = @JoinColumn(name = "submission_id"))
    @Column(name = "student_answer")
    private List<String> studentResult = new ArrayList<>(); // Kết quả/đáp án

    // Constructor không tham số (thay thế @NoArgsConstructor)
    public Submission() {
        // Danh sách studentResult đã được khởi tạo ở trên
    }

    // Constructor với tất cả các trường (thay thế @AllArgsConstructor)
    // Lombok @AllArgsConstructor không bao gồm các trường collection được khởi tạo ngay
    public Submission(String submissId, Student student, Assessment assessment, double score, LocalDateTime completedAt, EStatus status) {
        this.submissId = submissId;
        this.student = student;
        this.assessment = assessment;
        this.score = score;
        this.completedAt = completedAt;
        this.status = status;
         // Danh sách studentResult đã được khởi tạo ở trên
    }

     // Constructor không bao gồm ID (khi tạo mới)
     public Submission(Student student, Assessment assessment, double score, LocalDateTime completedAt, EStatus status) {
        this.student = student;
        this.assessment = assessment;
        this.score = score;
        this.completedAt = completedAt;
        this.status = status;
    }


    // Getters
    public String getSubmissId() {
        return submissId;
    }

    public Student getStudent() {
        return student;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public double getScore() {
        return score;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public EStatus getStatus() {
        return status;
    }

    public List<String> getStudentResult() {
        return studentResult;
    }

    // Setters (Setter cho danh sách studentResult thường không được tạo)
    public void setSubmissId(String submissId) {
        this.submissId = submissId;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    // Setter cho danh sách studentResult thường không được tạo

    // Phương thức equals() và hashCode() không được thêm vì không có @EqualsAndHashCode trong code bạn cung cấp
}