package Model;


import jakarta.persistence.*; // Hoặc javax.persistence.* tùy phiên bản Jakarta EE/Java EE
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects; // Cần import Objects cho equals/hashCode

@Entity
@Table(name = "courses")
// Đã bỏ @Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Đã bỏ @EqualsAndHashCode.Include
    private Long courseId;

    @Column(nullable = false)
    private String title;

    private BigDecimal price;

    private String thumbnail;

    // Nhiều Course thuộc về một Category
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // Nhiều Course được dạy bởi một Teacher
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    // Một Course có một Description
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "description_id", referencedColumnName = "descriptionId")
    private Description description;

    // Một Course có nhiều Chapter
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("chapterOrder ASC")
    private List<Chapter> chapters = new ArrayList<>(); // Khởi tạo ngay

    // Một Course có nhiều Review
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>(); // Khởi tạo ngay

    // Một Course có nhiều Order
    @OneToMany(mappedBy = "course", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>(); // Khởi tạo ngay

    // Constructor không tham số (thay thế @NoArgsConstructor)
    public Course() {
        // Các danh sách đã được khởi tạo ở trên
    }

    // Constructor với các trường cơ bản và mối quan hệ ManyToOne/OneToOne
    // (thay thế @AllArgsConstructor, thường bao gồm các trường cơ bản và ManyToOne/OneToOne, không bao gồm collections đã khởi tạo)
    public Course(Long courseId, String title, BigDecimal price, String thumbnail, Category category, Teacher teacher, Description description) {
        this.courseId = courseId;
        this.title = title;
        this.price = price;
        this.thumbnail = thumbnail;
        this.category = category;
        this.teacher = teacher;
        this.description = description;
        // Các danh sách đã được khởi tạo ở trên
    }

    // Nếu bạn muốn constructor bao gồm cả các danh sách, bạn cần tự viết


    // Getters
    public Long getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public Category getCategory() {
        return category;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Description getDescription() {
        return description;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public List<Order> getOrders() {
        return orders;
    }

    // Setters (Setter cho các trường cơ bản và mối quan hệ ManyToOne/OneToOne; Setter cho danh sách thường không được tạo)
    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void setDescription(Description description) {
        this.description = description;
    }
    public void setChapters(List<Chapter> chapters) {
    this.chapters = chapters;
}
  
    // Setters cho danh sách thường không được tạo


    // Phương thức equals() thủ công (thay thế @EqualsAndHashCode)
    // Dựa trên trường được đánh dấu @EqualsAndHashCode.Include trước đây (courseId)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(courseId, course.courseId);
    }

    // Phương thức hashCode() thủ công (thay thế @EqualsAndHashCode)
    // Dựa trên trường được đánh dấu @EqualsAndHashCode.Include trước đây (courseId)
    @Override
    public int hashCode() {
        return Objects.hash(courseId);
    }
}