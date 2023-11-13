package model;

import dto.LaundryEquipmentDTO;
import dto.OrderDTO;
import dto.tm.LaundryEquipmentTM;
import dto.tm.OrderTM;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LaundryEquipmentModel {
    public static boolean save(LaundryEquipmentDTO laundryEquipmentDTO) throws SQLException {
        String sql = "INSERT INTO laundryEquipment(machineId,machineType,status,nextRepairDate) VALUES(?,?,?,?)";
        boolean isSaved = CrudUtil.execute(sql, laundryEquipmentDTO.getMachineId(),laundryEquipmentDTO.getMachineType(),laundryEquipmentDTO.getStatus(),laundryEquipmentDTO.getNextRepairDate());
        return isSaved;
    }

    public static boolean update(LaundryEquipmentDTO laundryEquipmentDTO) throws SQLException {
        String sql = "UPDATE laundryEquipment set machineType=?,status=?,nextRepairDate=? WHERE machineId = ?";
        return CrudUtil.execute(sql,laundryEquipmentDTO.getMachineType(),laundryEquipmentDTO.getStatus(),laundryEquipmentDTO.getNextRepairDate(),laundryEquipmentDTO.getMachineId());
    }

    public static boolean remove(String machineId) throws SQLException {
        String sql = "DELETE FROM laundryEquipment WHERE machineId = ?";
        return CrudUtil.execute(sql,machineId);
    }

    public static LaundryEquipmentDTO search(String machineId) throws SQLException {
        String sql = "SELECT * FROM laundryEquipment where machineId = ?";

        ResultSet resultSet = CrudUtil.execute(sql, machineId);

        if (resultSet.next()){
            LaundryEquipmentDTO laundryEquipmentDTO= new LaundryEquipmentDTO();
            laundryEquipmentDTO.setMachineId(resultSet.getString(1));
            laundryEquipmentDTO.setMachineType(resultSet.getString(2));
            laundryEquipmentDTO.setStatus(resultSet.getString(3));
            laundryEquipmentDTO.setNextRepairDate(resultSet.getDate(4).toLocalDate());

            return laundryEquipmentDTO;
        }
        return null;
    }

    public static List<LaundryEquipmentTM> getAll() throws SQLException {
        String sql = "SELECT * FROM laundryEquipment";
        ResultSet resultSet = CrudUtil.execute(sql);
        List<LaundryEquipmentTM> data = new ArrayList<>();
        while (resultSet.next()) {
            LaundryEquipmentTM laundryEquipmentTM = new LaundryEquipmentTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4)

            );
            data.add(laundryEquipmentTM);
        }
        return data;
    }
}
