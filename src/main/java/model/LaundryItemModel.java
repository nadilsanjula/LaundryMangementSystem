package model;

import dto.LaundryItemDTO;
import dto.tm.LaundryItemTM;
import util.CrudUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LaundryItemModel {
    public static boolean save(LaundryItemDTO laundryItemDTO) throws SQLException {
        String sql = "INSERT INTO laundryitem(laundryItemId,name,quantityAvailable,description,unitPrice,itemId) VALUES(?,?,?,?,?,?)";
        boolean isSaved = CrudUtil.execute(sql, laundryItemDTO.getLaundryItemId(),laundryItemDTO.getName(),laundryItemDTO.getQtyAvailable(),laundryItemDTO.getDesc(),laundryItemDTO.getUnitePrice(),laundryItemDTO.getItemId());
        return isSaved;
    }

    public static boolean update(LaundryItemDTO laundryItemDTO) throws SQLException{
        String sql = "UPDATE laundryitem set name=?,quantityAvailable =?,description=?,unitPrice=?,itemId=? where laundryItemId=?";
        return CrudUtil.execute(sql,laundryItemDTO.getName(),laundryItemDTO.getQtyAvailable(),laundryItemDTO.getDesc(),laundryItemDTO.getUnitePrice(),laundryItemDTO.getItemId(),laundryItemDTO.getLaundryItemId());
    }

    public static boolean remove(String laundryItemId) throws SQLException {
        String sql = "DELETE FROM laundryitem WHERE laundryItemId = ?";
        return CrudUtil.execute(sql,laundryItemId);
    }

    public static LaundryItemDTO search(String laundryItemId) throws SQLException {
        String sql = "SELECT * FROM laundryitem where laundryItemId = ?";

        ResultSet resultSet = CrudUtil.execute(sql, laundryItemId);

        if (resultSet.next()){
            LaundryItemDTO laundryItemDTO= new LaundryItemDTO();
            laundryItemDTO.setLaundryItemId(resultSet.getString(1));
            laundryItemDTO.setName(resultSet.getString(2));
            laundryItemDTO.setQtyAvailable(resultSet.getInt(3));
            laundryItemDTO.setDesc(resultSet.getString(4));
            laundryItemDTO.setUnitePrice(resultSet.getDouble(5));
            laundryItemDTO.setItemId(resultSet.getString(6));
            return laundryItemDTO;
        }
        return null;
    }

    public static List<LaundryItemTM> getAll() throws SQLException {
        String sql = "SELECT * FROM laundryitem";
        ResultSet resultSet = CrudUtil.execute(sql);
        List<LaundryItemTM> data = new ArrayList<>();
        while (resultSet.next()) {
            LaundryItemTM laundryItemTm = new LaundryItemTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5),
                    resultSet.getString(6)
            );
            data.add(laundryItemTm);
        }
        return data;
    }
}
