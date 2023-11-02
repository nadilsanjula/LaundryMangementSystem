package dto;

import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class ItemDTO {
    private String itemId;
    private String name;
    private String description;
    private int qty;
    private double unitPrice;
    private String orderId;
}
