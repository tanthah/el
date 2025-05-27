package Model;

import jakarta.persistence.*; // Hoặc javax.persistence.* tùy phiên bản Jakarta EE/Java EE
import java.util.ArrayList;
import java.util.List;
import java.util.Objects; // Cần import Objects nếu thêm equals/hashCode thủ công

@Entity
@Table(name = "assessments")
// Đã bỏ @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Assessment {

    @Id
    private String assessmentId; // Mã định danh bài đánh giá

    private String name; // Tên của bài đánh giá

    @Column(columnDefinition = "TEXT")
    private String description; // Mô tả chi tiết

    private double maxScore; // Điểm tối đa

    private double passingScore; // Điểm sàn

    private double duration; // Thời lượng

    // Danh sách các câu hỏi trong bài đánh giá
    @OneToMany(mappedBy = "assessment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Question> questions = new ArrayList<>();

    // Danh sách các lượt nộp bài cho bài đánh giá này
    @OneToMany(mappedBy = "assessment", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<Submission> submissions = new ArrayList<>();

    // Constructor không tham số (thay thế @NoArgsConstructor)
    public Assessment() {
        // Các danh sách đã được khởi tạo ở trên
    }

    // Constructor với tất cả các trường (thay thế @AllArgsConstructor)
    // Lombok AllArgsConstructor không bao gồm các trường collection được khởi tạo ngay
    public Assessment(String assessmentId, String name, String description, double maxScore, double passingScore, double duration) {
        this.assessmentId = assessmentId;
        this.name = name;
        this.description = description;
        this.maxScore = maxScore;
        this.passingScore = passingScore;
        this.duration = duration;
        // Các danh sách đã được khởi tạo ở trên
    }

    // Nếu bạn muốn constructor bao gồm cả các danh sách, bạn cần tự viết
    // public Assessment(String assessmentId, ..., double duration, List<Question> questions, List<Submission> submissions) {
    //     this(assessmentId, ..., duration); // Gọi constructor trên
    //     this.questions = questions;
    //     this.submissions = submissions;
    // }


    // Getters
    public String getAssessmentId() {
        return assessmentId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getMaxScore() {
        return maxScore;
    }

    public double getPassingScore() {
        return passingScore;
    }

    public double getDuration() {
        return duration;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    // Setters (Setters cho danh sách thường không được tạo)
    public void setAssessmentId(String assessmentId) {
        this.assessmentId = assessmentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMaxScore(double maxScore) {
        this.maxScore = maxScore;
    }

    public void setPassingScore(double passingScore) {
        this.passingScore = passingScore;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    // Setters cho danh sách thường không được tạo
    // public void setQuestions(List<Question> questions) { this.questions = questions; }
    // public void setSubmissions(List<Submission> submissions) { this.submissions = submissions; }


     // Bạn có thể thêm phương thức equals() và hashCode() thủ công nếu cần,
    // dựa trên các trường quan trọng (thường là assessmentId).
    // @Override
    // public boolean equals(Object o) {
    //     if (this == o) return true;
    //     if (o == null || getClass() != o.getClass()) return false;
    //     Assessment that = (Assessment) o;
    //     return Objects.equals(assessmentId, that.assessmentId);
    // }
    //
    // @Override
    // public int hashCode() {
    //     return Objects.hash(assessmentId);
    // }
}