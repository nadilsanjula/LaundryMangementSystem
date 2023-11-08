package dto.tm;

import java.time.LocalDate;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class PaymemtTM {
    private String paymentId;
    private double amount;
    private LocalDate paymentDate;
    private String orderId;
}
