package Model;

import java.math.BigDecimal;

public interface PaymentStrategy {
    void pay(BigDecimal amount, Payment payment);
}