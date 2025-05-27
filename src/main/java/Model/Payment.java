package Model;


// Import các lớp JPA và Java cần thiết
import jakarta.persistence.*; // Hoặc javax.persistence.* tùy phiên bản Jakarta EE/Java EE
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects; // Cần import Objects cho equals/hashCode

// Import các enum (giả định chúng nằm trong package model)
import ENum.PaymentMethod; // Sửa import từ ENum.*
import ENum.PaymentStatus; // Sửa import từ ENum.*


@Entity
@Table(name = "payments")
// Đã bỏ @Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class Payment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Đã bỏ @EqualsAndHashCode.Include
    private Long paymentId; // ID thanh toán

    @Column(nullable = false)
    private BigDecimal amount; // Số tiền

    @Column(nullable = false)
    private LocalDateTime paymentDate; // Ngày thanh toán

    @Enumerated(EnumType.STRING) // Lưu enum dưới dạng chuỗi
    @Column(nullable = false)
    private PaymentMethod paymentMethod; // Phương thức thanh toán

    @Enumerated(EnumType.STRING) // Lưu enum dưới dạng chuỗi
    @Column(nullable = false)
    private PaymentStatus status; // Trạng thái thanh toán

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true) // Khóa ngoại trỏ tới Order (Payment là phía sở hữu)
    private Order order; // Tham chiếu tới Order liên quan

    // Constructor không tham số (thay thế @NoArgsConstructor)
    public Payment() {
         // Có thể gán giá trị mặc định tại đây nếu cần
         // this.status = PaymentStatus.PENDING; // Hoặc gán trực tiếp ở khai báo trường
    }

    // Constructor với tất cả các trường (thay thế @AllArgsConstructor)
    public Payment(Long paymentId, BigDecimal amount, LocalDateTime paymentDate, PaymentMethod paymentMethod, PaymentStatus status, Order order) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.order = order;
    }

     // Constructor không bao gồm ID (thường dùng khi tạo đối tượng mới để lưu)
      public Payment(BigDecimal amount, LocalDateTime paymentDate, PaymentMethod paymentMethod, PaymentStatus status, Order order) {
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.order = order;
    }


    // Constructor tiện dụng để gán mặc định status (đã có sẵn)
    public Payment(BigDecimal amount, LocalDateTime paymentDate, PaymentMethod paymentMethod, Order order) {
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.status = PaymentStatus.PENDING; // Gán mặc định
        this.order = order;
    }


    // Getters
    public Long getPaymentId() {
        return paymentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public Order getOrder() {
        return order;
    }

    // Setters
    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public void setOrder(Order order) {
        this.order = order;
    }


    // Phương thức equals() thủ công (thay thế @EqualsAndHashCode)
    // Dựa trên trường được đánh dấu @EqualsAndHashCode.Include trước đây (paymentId)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(paymentId, payment.paymentId);
    }

    // Phương thức hashCode() thủ công (thay thế @EqualsAndHashCode)
    // Dựa trên trường được đánh dấu @EqualsAndHashCode.Include trước đây (paymentId)
    @Override
    public int hashCode() {
        return Objects.hash(paymentId);
    }
}