package Model;


// Import các lớp JPA và Java cần thiết
import jakarta.persistence.*; // Hoặc javax.persistence.* tùy phiên bản Jakarta EE/Java EE
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects; // Cần import Objects cho equals/hashCode
import java.util.Set;

// Import enum ScheduleDay (giả định nó nằm trong package model)
import ENum.ScheduleDay; // Sửa import từ ENum.* thành model.ScheduleDay

@Entity
@Table(name = "descriptions")
// Đã bỏ @Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class Description implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Đã bỏ @EqualsAndHashCode.Include
    private Long descriptionId;

    @Lob // Cho phép lưu trữ lượng văn bản lớn
    private String content;

    // Một Description gắn với một Course (Course là chủ sở hữu mối quan hệ này)
    @OneToOne(mappedBy = "description", fetch = FetchType.LAZY)
    private Course course; // Trường này không có setter trong lớp sở hữu mối quan hệ

    // Mô tả áp dụng cho các ngày (ví dụ: MONDAY, WEDNESDAY)
    @ElementCollection(targetClass = ScheduleDay.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "description_schedule", joinColumns = @JoinColumn(name = "description_id"))
    @Enumerated(EnumType.STRING) // Lưu enum dưới dạng chuỗi
    @Column(name = "schedule_day")
    private Set<ScheduleDay> applicableDays = new HashSet<>(); // Khởi tạo ngay

    // Constructor không tham số (thay thế @NoArgsConstructor)
    public Description() {
        // Tập hợp applicableDays đã được khởi tạo ở trên
    }

    // Constructor với các trường cơ bản
    // (@AllArgsConstructor của Lombok thường không bao gồm mappedBy fields và collections đã khởi tạo)
    public Description(Long descriptionId, String content) {
        this.descriptionId = descriptionId;
        this.content = content;
        // Các mối quan hệ mappedBy và collections đã khởi tạo không có trong constructor này
    }

    // Nếu bạn muốn constructor bao gồm cả applicableDays, bạn cần tự viết


    // Getters
    public Long getDescriptionId() {
        return descriptionId;
    }

    public String getContent() {
        return content;
    }

    public Course getCourse() {
        return course;
    }

    public Set<ScheduleDay> getApplicableDays() {
        return applicableDays;
    }

    // Setters (Setters cho mappedBy relationship và collections thường không được tạo)
    public void setDescriptionId(Long descriptionId) {
        this.descriptionId = descriptionId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // Setter cho course (mappedBy) và applicableDays (collection) thường không được tạo


    // Phương thức equals() thủ công (thay thế @EqualsAndHashCode)
    // Dựa trên trường được đánh dấu @EqualsAndHashCode.Include trước đây (descriptionId)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Description that = (Description) o;
        return Objects.equals(descriptionId, that.descriptionId);
    }

    // Phương thức hashCode() thủ công (thay thế @EqualsAndHashCode)
    // Dựa trên trường được đánh dấu @EqualsAndHashCode.Include trước đây (descriptionId)
    @Override
    public int hashCode() {
        return Objects.hash(descriptionId);
    }
}