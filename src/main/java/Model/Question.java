package Model;


import jakarta.persistence.*; // Hoặc javax.persistence.* tùy phiên bản Jakarta EE/Java EE
import java.util.ArrayList;
import java.util.List;
// Đã bỏ import của Lombok
// import java.util.Objects; // Không cần Objects nếu không tự viết equals/hashCode

@Entity
@Table(name = "questions")
// Đã bỏ @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Khóa chính tự tăng
    private Long id; // ID nội bộ

    private String quesId; // Mã định danh câu hỏi

    @Column(columnDefinition = "TEXT")
    private String questionText; // Nội dung câu hỏi

    // ElementCollection: Lưu trữ danh sách các lựa chọn
    @ElementCollection
    @CollectionTable(name = "question_options", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "option_text")
    private List<String> options = new ArrayList<>(); // Danh sách phương án

    private String correctAnswer; // Đáp án chính xác

    // Nhiều Question liên kết tới một Assessment
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assessment_id") // Khóa ngoại trỏ tới Assessment
    private Assessment assessment; // Tham chiếu tới Assessment

    // Constructor không tham số (thay thế @NoArgsConstructor)
    public Question() {
        // Danh sách options đã được khởi tạo ở trên
    }

    // Constructor với tất cả các trường (thay thế @AllArgsConstructor)
    // Lombok @AllArgsConstructor không bao gồm các trường collection được khởi tạo ngay
    public Question(Long id, String quesId, String questionText, String correctAnswer, Assessment assessment) {
        this.id = id;
        this.quesId = quesId;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.assessment = assessment;
         // Danh sách options đã được khởi tạo ở trên
    }

     // Constructor không bao gồm ID (khi tạo mới)
      public Question(String quesId, String questionText, String correctAnswer, Assessment assessment) {
        this.quesId = quesId;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.assessment = assessment;
    }


    // Getters
    public Long getId() {
        return id;
    }

    public String getQuesId() {
        return quesId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    // Setters (Setter cho danh sách options thường không được tạo)
    public void setId(Long id) {
        this.id = id;
    }

    public void setQuesId(String quesId) {
        this.quesId = quesId;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    // Setter cho danh sách options thường không được tạo

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    // Phương thức equals() và hashCode() không được thêm vì không có @EqualsAndHashCode trong code bạn cung cấp
}