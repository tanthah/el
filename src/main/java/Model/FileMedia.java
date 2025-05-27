package Model;


import jakarta.persistence.*; // Hoặc javax.persistence.* tùy phiên bản Jakarta EE/Java EE
import java.io.Serializable; // Cần Serializable nếu class implement Serializable
import java.util.Objects; // Cần import Objects cho equals/hashCode

@Entity
@Table(name = "file_media")
// Đã bỏ @Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class FileMedia implements Serializable { // Giữ lại implements Serializable nếu cần

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Trường ID nội bộ làm khóa chính

    
    @Column(nullable = false, unique = true)
    private String fileId; // Mã định danh đặc trưng của tệp

    @Column(nullable = false)
    private String fileName; // Tên gốc của tệp

    private String fileType; // Kiểu của tệp

    @Column(nullable = false)
    private String fileUrl; // Đường dẫn (URL hoặc path)

    // Nhiều FileMedia liên kết tới một Lesson
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson; // Tham chiếu tới thực thể Lesson

    // Constructor không tham số (thay thế @NoArgsConstructor)
    public FileMedia() {
    }

    // Constructor với tất cả các trường (thay thế @AllArgsConstructor)
    // Lombok @AllArgsConstructor bao gồm các trường cơ bản và mối quan hệ ManyToOne
    public FileMedia(Long id, String fileId, String fileName, String fileType, String fileUrl, Lesson lesson) {
        this.id = id;
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileUrl = fileUrl;
        this.lesson = lesson;
    }

    // Constructor không bao gồm ID (thường dùng khi tạo đối tượng mới để lưu)
     public FileMedia(String fileId, String fileName, String fileType, String fileUrl, Lesson lesson) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileUrl = fileUrl;
        this.lesson = lesson;
    }


    // Getters
    public Long getId() {
        return id;
    }

    public String getFileId() {
        return fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public Lesson getLesson() {
        return lesson;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    // Phương thức equals() thủ công (thay thế @EqualsAndHashCode)
    // Dựa trên các trường được đánh dấu @EqualsAndHashCode.Include trước đây (id và fileId)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileMedia fileMedia = (FileMedia) o;
        return Objects.equals(id, fileMedia.id) && Objects.equals(fileId, fileMedia.fileId);
    }

    // Phương thức hashCode() thủ công (thay thế @EqualsAndHashCode)
    // Dựa trên các trường được đánh dấu @EqualsAndHashCode.Include trước đây (id và fileId)
    @Override
    public int hashCode() {
        return Objects.hash(id, fileId);
    }
}