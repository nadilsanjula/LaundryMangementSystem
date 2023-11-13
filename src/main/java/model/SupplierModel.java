package model;

import dto.StaffDTO;
import dto.SupplierDTO;
import dto.tm.StaffTM;
import dto.tm.SupplierTM;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierModel {
    public static boolean update(SupplierDTO supplierDTO) throws SQLException {
        String sql = "UPDATE supplier set name=?,email=?,telNum=?,address=? WHERE supplierId = ?";
        return CrudUtil.execute(sql,supplierDTO.getName(),supplierDTO.getEmail(),supplierDTO.getTelnum(),supplierDTO.getAddress(),supplierDTO.getSupplierId());
    }

    public static boolean save(SupplierDTO supplierDTO) throws SQLException {
        String sql = "INSERT INTO supplier(supplierId,name,email,telNum,address) VALUES(?,?,?,?,?)";
        boolean isSaved = CrudUtil.execute(sql, supplierDTO.getSupplierId(),supplierDTO.getName(),supplierDTO.getEmail(),supplierDTO.getTelnum(),supplierDTO.getAddress());
        return isSaved;
    }

    public static boolean remove(String supplierId) throws SQLException {
        String sql = "DELETE FROM supplier WHERE supplierId = ?";
        return CrudUtil.execute(sql,supplierId);
    }

    public static SupplierDTO search(String supplierId) throws SQLException {
        String sql = "SELECT * FROM supplier where supplierId = ?";

        ResultSet resultSet = CrudUtil.execute(sql, supplierId);

        if (resultSet.next()){
            SupplierDTO supplierDTO= new SupplierDTO();
            supplierDTO.setSupplierId(resultSet.getString(1));
            supplierDTO.setName(resultSet.getString(2));
            supplierDTO.setEmail(resultSet.getString(3));
            supplierDTO.setTelnum(resultSet.getInt(4));
            supplierDTO.setAddress(resultSet.getString(5));

            return supplierDTO;
        }
        return null;
    }

    public static List<SupplierTM> getAll() throws SQLException {
        String sql = "SELECT * FROM supplier";
        ResultSet resultSet = CrudUtil.execute(sql);
        List<SupplierTM> data = new ArrayList<>();
        while (resultSet.next()) {
            SupplierTM supplierTM = new SupplierTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getString(5)

            );
            data.add(supplierTM);
        }
        return data;
    }
}
