package Model;

public class PaymentProcessor {
    private PaymentStrategy paymentStrategy;

    // Đặt chiến lược thanh toán
    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    // Xử lý thanh toán
    public void processPayment(Payment payment) {
        if (paymentStrategy != null) {
            paymentStrategy.pay(payment.getAmount(), payment);
        } else {
            throw new IllegalStateException("Payment strategy not set");
        }
    }

    // Tự động chọn chiến lược dựa trên paymentMethod của Payment
public void setStrategyBasedOnMethod(Payment payment) {
    switch (payment.getPaymentMethod()) {
        case BANKING:
            setPaymentStrategy(new BankTransferPayment());
            break;
        case MOMO:
            setPaymentStrategy(new EWalletPayment());
            break;
        default:
            throw new IllegalArgumentException("Unsupported payment method: " + payment.getPaymentMethod());
    }
    
    //VISA, MOMO, BANKING, CASH
}
}