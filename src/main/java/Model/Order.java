package Model;


import jakarta.persistence.*; // Hoặc javax.persistence.* tùy phiên bản Jakarta EE/Java EE
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects; // Cần import Objects cho equals/hashCode

@Entity
@Table(name = "orders")
// Đã bỏ @Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Đã bỏ @EqualsAndHashCode.Include
    @Column(name = "order_id")
    private Long orderId; // ID đơn hàng

    @Column(nullable = false)
    private LocalDateTime orderDate; // Ngày đặt hàng

    @Column(precision = 10, scale = 2)
    private BigDecimal totalPrice; // Tổng giá trị đơn hàng

    // Nhiều Order thuộc về một Student
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = true) // Khóa ngoại trỏ tới Student (Account)
    private Student student; // Tham chiếu tới Student đặt hàng

    // Nhiều Order thuộc về một Course
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false) // Khóa ngoại trỏ tới Course
    private Course course; // Tham chiếu tới Course được đặt

    // Một Order có một Payment (Order là phía không sở hữu mối quan hệ này)
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    private Payment payment; // Tham chiếu tới Payment liên quan (mappedBy)

    // Constructor không tham số (thay thế @NoArgsConstructor)
    public Order() {
    }

    // Constructor với các trường cơ bản và mối quan hệ ManyToOne
    // (@AllArgsConstructor của Lombok thường bao gồm các trường cơ bản và ManyToOne, không bao gồm mappedBy)
    public Order(Long orderId, LocalDateTime orderDate, BigDecimal totalPrice, Student student, Course course) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.student = student;
        this.course = course;
        // Mối quan hệ mappedBy 'payment' không có trong constructor này
    }

    // Nếu bạn muốn constructor không bao gồm ID (khi tạo mới), bạn cần tự viết
     public Order(LocalDateTime orderDate, BigDecimal totalPrice, Student student, Course course) {
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.student = student;
        this.course = course;
    }

    // Nếu bạn muốn constructor bao gồm cả payment, bạn cần tự viết


    // Getters
    public Long getOrderId() {
        return orderId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public Payment getPayment() {
        return payment;
    }

    // Setters (Setter cho các trường cơ bản và mối quan hệ ManyToOne; Setter cho mappedBy thường không được tạo)
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    // Setter cho payment (mappedBy) thường không được tạo
    // public void setPayment(Payment payment) { this.payment = payment; }


    // Phương thức equals() thủ công (thay thế @EqualsAndHashCode)
    // Dựa trên trường được đánh dấu @EqualsAndHashCode.Include trước đây (orderId)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId);
    }

    // Phương thức hashCode() thủ công (thay thế @EqualsAndHashCode)
    // Dựa trên trường được đánh dấu @EqualsAndHashCode.Include trước đây (orderId)
    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }
}