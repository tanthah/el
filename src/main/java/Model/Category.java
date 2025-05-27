package Model;


import jakarta.persistence.*; // Hoặc javax.persistence.* tùy phiên bản Jakarta EE/Java EE
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects; // Cần import Objects cho equals/hashCode

@Entity
@Table(name = "categories")
// Đã bỏ @Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Đã bỏ @EqualsAndHashCode.Include
    private Long categoryId;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    // Quan hệ: Một Category có nhiều Course
    @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Course> courses = new ArrayList<>(); // Khởi tạo ngay khi khai báo

    // Constructor không tham số (thay thế @NoArgsConstructor)
    public Category() {
        // Danh sách courses đã được khởi tạo ở trên
    }

    // Constructor với các trường cơ bản (thay thế @AllArgsConstructor, thường không bao gồm các collections đã được khởi tạo)
    public Category(Long categoryId, String name, String description) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        // Danh sách courses đã được khởi tạo ở trên
    }

    // Nếu bạn muốn constructor bao gồm cả danh sách courses, bạn cần tự viết
    // public Category(Long categoryId, String name, String description, List<Course> courses) {
    //    this.categoryId = categoryId;
    //    this.name = name;
    //    this.description = description;
    //    this.courses = courses; // Gán danh sách được truyền vào
    // }


    // Getters
    public Long getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Course> getCourses() {
        return courses;
    }

    // Setters (Setters cho danh sách thường không được tạo)
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Setter cho danh sách courses thường không được tạo

    // Phương thức equals() thủ công (thay thế @EqualsAndHashCode)
    // Dựa trên trường được đánh dấu @EqualsAndHashCode.Include trước đây (categoryId)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(categoryId, category.categoryId);
    }

    // Phương thức hashCode() thủ công (thay thế @EqualsAndHashCode)
    // Dựa trên trường được đánh dấu @EqualsAndHashCode.Include trước đây (categoryId)
    @Override
    public int hashCode() {
        return Objects.hash(categoryId);
    }
}