package dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor

public class SupplierTM {
    private String supplierId;
    private String name;
    private String email;
    private int telnum;
    private String address;
}
