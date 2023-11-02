package model;

import dto.CustomerDTO;
import dto.StaffDTO;
import dto.tm.CustomerTM;
import dto.tm.StaffTM;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffModel {
    public static boolean save(StaffDTO staffDTO) throws SQLException {
        String sql = "INSERT INTO staff(staffId,name,email,telNum,role) VALUES(?,?,?,?,?)";
        boolean isSaved = CrudUtil.execute(sql, staffDTO.getStaffId(),staffDTO.getName(),staffDTO.getEmail(),staffDTO.getTelNum(),staffDTO.getJobRole());
        return isSaved;
    }

    public static boolean update(StaffDTO staffDTO) throws SQLException {
        String sql = "UPDATE staff set name=?,email=?,telNum=?,role=? WHERE staffId = ?";
        return CrudUtil.execute(sql,staffDTO.getName(),staffDTO.getEmail(),staffDTO.getTelNum(),staffDTO.getJobRole(),staffDTO.getStaffId());
    }

    public static StaffDTO search(String staffId) throws SQLException {
        String sql = "SELECT * FROM staff where staffId = ?";

        ResultSet resultSet = CrudUtil.execute(sql, staffId);

        if (resultSet.next()){
            StaffDTO staffDTO= new StaffDTO();
            staffDTO.setStaffId(resultSet.getString(1));
            staffDTO.setName(resultSet.getString(2));
            staffDTO.setEmail(resultSet.getString(3));
            staffDTO.setTelNum(resultSet.getInt(4));
            staffDTO.setJobRole(resultSet.getString(5));

            return staffDTO;
        }
        return null;
    }

    public static boolean remove(String staffId) throws SQLException {
        String sql = "DELETE FROM staff WHERE staffId = ?";
        return CrudUtil.execute(sql,staffId);
    }

    public static List<StaffTM> getAll() throws SQLException {
        String sql = "SELECT * FROM staff";
        ResultSet resultSet = CrudUtil.execute(sql);
        List<StaffTM> data = new ArrayList<>();
        while (resultSet.next()) {
            StaffTM staffTM = new StaffTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getString(5)

            );
            data.add(staffTM);
        }
        return data;
    }
}
