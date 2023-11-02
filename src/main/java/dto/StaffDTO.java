package dto;

import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class StaffDTO {
    private String staffId;
    private String name;
    private String email;
    private int telNum;
    private String jobRole;
}
