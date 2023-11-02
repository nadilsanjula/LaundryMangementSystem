package dto.tm;

import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class StaffTM {
    private String staffId;
    private String name;
    private String email;
    private int telNum;
    private String jobRole;
}
