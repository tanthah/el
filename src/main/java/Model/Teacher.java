package Model;

import jakarta.persistence.*; // Hoặc javax.persistence.* tùy phiên bản Jakarta EE/Java EE
import java.io.Serializable; // Cần Serializable nếu class implement Serializable
import java.util.ArrayList;
import java.util.List;
import java.util.Objects; // Cần import Objects cho equals/hashCode nếu thêm thủ công

@Entity
@Table(name = "teachers")
// Đã bỏ @Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
@PrimaryKeyJoinColumn(name = "account_id") // Dùng chung khóa chính từ Account
public class Teacher extends Account implements Serializable { // Giữ lại implements Serializable nếu cần

    @Column(nullable = false)
    private String name; // Tên giáo viên

    private String bio; // Tiểu sử

    private String qualifications; // Bằng cấp, trình độ

    // Quan hệ: Một Teacher dạy nhiều Course
    @OneToMany(mappedBy = "teacher", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Course> courses = new ArrayList<>(); // Danh sách các khóa học dạy bởi giáo viên này

    // Constructor không tham số (thay thế @NoArgsConstructor)
    public Teacher() {
        super(); // Gọi constructor không tham số của lớp cha
        // Danh sách courses đã được khởi tạo ở trên
    }

    // Constructor với các trường riêng của Teacher và gọi constructor không tham số của lớp cha
    public Teacher(String name, String bio, String qualifications) {
        super(); // Gọi constructor không tham số của lớp cha
        this.name = name;
        this.bio = bio;
        this.qualifications = qualifications;
         // Danh sách courses đã được khởi tạo ở trên
    }


    // Constructor với các trường của lớp cha Account và các trường riêng của Teacher
    // (thay thế một phần @AllArgsConstructor)
    public Teacher(String username, String password, String email, String phone, String avatar, boolean isActive, String name, String bio, String qualifications) {
        super(username, password, email, phone, avatar, isActive); // Gọi constructor của lớp cha
        this.name = name;
        this.bio = bio;
        this.qualifications = qualifications;
         // Danh sách courses đã được khởi tạo ở trên
    }

    // Constructor bao gồm ID của lớp cha và các trường của lớp cha + riêng của Teacher
    // (thay thế một phần @AllArgsConstructor)
    public Teacher(Long accountId, String username, String password, String email, String phone, String avatar, boolean isActive, String name, String bio, String qualifications) {
         super(accountId, username, password, email, phone, avatar, isActive); // Gọi constructor của lớp cha
        this.name = name;
        this.bio = bio;
        this.qualifications = qualifications;
         // Danh sách courses đã được khởi tạo ở trên
    }

    // Getters cho các trường riêng của Teacher và danh sách courses
    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public String getQualifications() {
        return qualifications;
    }

    public List<Course> getCourses() {
        return courses;
    }

    // Setters cho các trường riêng của Teacher (Setter cho danh sách courses thường không được tạo)
    public void setName(String name) {
        this.name = name;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    // Setter cho danh sách courses thường không được tạo


    // Phương thức equals() thủ công (thay thế @EqualsAndHashCode)
    // Dựa trên callSuper=true và onlyExplicitlyIncluded=true, chỉ so sánh dựa trên ID của lớp cha Account
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false; // So sánh phần của lớp cha (dựa trên ID Account)
        // Teacher teacher = (Teacher) o; // Không cần so sánh thêm trường nào riêng của Teacher vì không có Include ở đây
        return true;
    }
   

    // Phương thức hashCode() thủ công (thay thế @EqualsAndHashCode)
    // Dựa trên callSuper=true và onlyExplicitlyIncluded=true, chỉ sử dụng hashCode của lớp cha (dựa trên ID Account)
    @Override
    public int hashCode() {
        return super.hashCode(); // Sử dụng hashCode của lớp cha
    }
}