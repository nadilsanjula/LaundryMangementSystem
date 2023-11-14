package dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@ToString
@Setter
@Getter
@AllArgsConstructor

public class CartTM {
    private String itemId;
    private String description;
    private int qty;
    private double unitPrice;
    private double total;
    private Button btn;
}
