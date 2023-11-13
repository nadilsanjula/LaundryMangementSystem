package dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class LaundryEquipmentDTO {
    private String machineId;
    private String machineType;
    private String status;
    private LocalDate nextRepairDate;

}
