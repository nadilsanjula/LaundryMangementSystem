package dto;

import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class CustomerDTO {
    private String customerId;
    private String name;
    private String email;
    private String address;
    private int telNum;
    private String nic;
}
