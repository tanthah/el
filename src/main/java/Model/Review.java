package Model;

import jakarta.persistence.*; // Hoặc javax.persistence.* tùy phiên bản Jakarta EE/Java EE
import java.io.Serializable; // Cần Serializable nếu class implement Serializable
import java.time.LocalDateTime;
import java.util.Objects; // Cần import Objects cho equals/hashCode

@Entity
@Table(name = "reviews")
// Đã bỏ @Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class Review implements Serializable { // Giữ lại implements Serializable nếu cần

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Đã bỏ @EqualsAndHashCode.Include
    private Long reviewId; // ID đánh giá

    @Column(nullable = false)
    private Integer rating; // Điểm đánh giá (ví dụ: 1-5)

    @Lob // Cho phép lưu trữ lượng văn bản lớn
    private String comment; // Nội dung bình luận

    @Column(nullable = false)
    private LocalDateTime reviewDate; // Ngày đánh giá

    // Nhiều Review thuộc về một Student
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false) // Khóa ngoại trỏ tới Student (Account)
    private Student student; // Tham chiếu tới Student thực hiện đánh giá

    // Nhiều Review thuộc về một Course
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false) // Khóa ngoại trỏ tới Course
    private Course course; // Tham chiếu tới Course được đánh giá

    // Constructor không tham số (thay thế @NoArgsConstructor)
    public Review() {
    }

    // Constructor với tất cả các trường (thay thế @AllArgsConstructor)
    // Lombok @AllArgsConstructor bao gồm các trường cơ bản và ManyToOne
    public Review(Long reviewId, Integer rating, String comment, LocalDateTime reviewDate, Student student, Course course) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
        this.student = student;
        this.course = course;
    }

     // Constructor không bao gồm ID (khi tạo mới)
      public Review(Integer rating, String comment, LocalDateTime reviewDate, Student student, Course course) {
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
        this.student = student;
        this.course = course;
    }


    // Getters
    public Long getReviewId() {
        return reviewId;
    }

    public Integer getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    // Setters
    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    // Phương thức equals() thủ công (thay thế @EqualsAndHashCode)
    // Dựa trên trường được đánh dấu @EqualsAndHashCode.Include trước đây (reviewId)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(reviewId, review.reviewId);
    }

    // Phương thức hashCode() thủ công (thay thế @EqualsAndHashCode)
    // Dựa trên trường được đánh dấu @EqualsAndHashCode.Include trước đây (reviewId)
    @Override
    public int hashCode() {
        return Objects.hash(reviewId);
    }
}