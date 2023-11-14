package dto.tm;

import java.time.LocalDate;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class OrderTM {
    private String orderId;
    private LocalDate pickupDate;
    private LocalDate deliveryDate;
    private double amount;
    private String customerId;
    private String staffid;
}
