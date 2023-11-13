package dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor

public class SupplierDTO {
    private String supplierId;
    private String name;
    private String email;
    private int telnum;
    private String address;
}
