package dto.tm;

import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class LaundryItemTM {
    private String laundryItemId;
    private String name;
    private int qtyAvailable;
    private String desc;
    private double unitePrice;
    private String itemId;
}
