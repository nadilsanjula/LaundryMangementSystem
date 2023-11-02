package dto;

import java.time.LocalDate;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class OrderDTO {
    private String orderId;
    private LocalDate pickupDate;
    private LocalDate deliveryDate;
    private double amount;
    private String status;
    private String customerId;
    private String staffid;

}
