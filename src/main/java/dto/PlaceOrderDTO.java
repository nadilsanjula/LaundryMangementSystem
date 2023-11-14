package dto;

import dto.tm.CartTM;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor

public class PlaceOrderDTO {
    private String orderId;
    private LocalDate pickupDate;
    private LocalDate deliverDate;
    private double amount;
    private String customerId;
    private String staffId;
    private List<CartTM> cartTmList = new ArrayList<>();


}
