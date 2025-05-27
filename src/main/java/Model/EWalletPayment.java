package Model;

import ENum.PaymentStatus;
import java.math.BigDecimal;

public class EWalletPayment implements PaymentStrategy {
    @Override
    public void pay(BigDecimal amount, Payment payment) {
        System.out.println("Processing payment of " + amount + " via E-Wallet");
        // Logic xử lý thanh toán qua E-Wallet
        payment.setStatus(PaymentStatus.PAID); // Cập nhật trạng thái thanh toán
    }
}