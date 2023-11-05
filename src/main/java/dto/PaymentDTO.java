package dto;

import java.time.LocalDate;
import java.util.Date;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class PaymentDTO {
    private String paymentId;
    private double amount;
    private LocalDate paymentDate;
    private String orderId;
}
