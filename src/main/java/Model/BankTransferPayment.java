package Model;

import ENum.PaymentStatus;
import java.math.BigDecimal;

public class BankTransferPayment implements PaymentStrategy {
    @Override
    public void pay(BigDecimal amount, Payment payment) {
        System.out.println("Processing payment of " + amount + " via Bank Transfer");
        // Logic xử lý thanh toán qua Bank Transfer
        payment.setStatus(PaymentStatus.PAID); // Cập nhật trạng thái thanh toán
    }
}