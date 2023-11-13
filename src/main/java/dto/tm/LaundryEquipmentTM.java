package dto.tm;

import lombok.*;

import java.util.Date;
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class LaundryEquipmentTM {
    private String machineId;
    private String machineType;
    private String status;
    private Date nextRepairDate;
}
