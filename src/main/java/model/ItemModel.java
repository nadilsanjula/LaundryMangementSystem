package model;

import db.DBConnection;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.tm.CartTM;
import dto.tm.ItemTM;
import dto.tm.StaffTM;
import util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemModel {
    public static boolean save(ItemDTO itemDTO) throws SQLException {
        String sql = "INSERT INTO item(itemId,name,quantity,unitPrice,description,orderId) VALUES(?,?,?,?,?,?)";
        boolean isSaved = CrudUtil.execute(sql, itemDTO.getItemId(),itemDTO.getName(),itemDTO.getQty(),itemDTO.getUnitPrice(),itemDTO.getDescription(),itemDTO.getOrderId());
        return isSaved;
    }

    public static boolean update(ItemDTO itemDTO) throws SQLException {
        String sql = "UPDATE item set name=?,description=?,quantity=?,unitPrice=?,orderId=? WHERE itemId = ?";
        return CrudUtil.execute(sql,itemDTO.getName(),itemDTO.getDescription(),itemDTO.getQty(),itemDTO.getUnitPrice(),itemDTO.getOrderId(),itemDTO.getItemId());
    }

    public static boolean remove(String itemId) throws SQLException {
        String sql = "DELETE FROM item WHERE itemId = ?";
        return CrudUtil.execute(sql,itemId);
    }

    public static ItemDTO search(String itemId) throws SQLException {
        String sql = "SELECT * FROM item where itemId = ?";

        ResultSet resultSet = CrudUtil.execute(sql, itemId);

        if (resultSet.next()){
            ItemDTO itemDTO= new ItemDTO();
            itemDTO.setItemId(resultSet.getString(1));
            itemDTO.setName(resultSet.getString(2));
            itemDTO.setDescription(resultSet.getString(3));
            itemDTO.setQty(resultSet.getInt(4));
            itemDTO.setUnitPrice(resultSet.getDouble(5));
            itemDTO.setOrderId(resultSet.getString(6));

            return itemDTO;
        }
        return null;
    }

    public static List<ItemTM> getAll() throws SQLException {
        String sql = "SELECT * FROM item";
        ResultSet resultSet = CrudUtil.execute(sql);
        List<ItemTM> data = new ArrayList<>();
        while (resultSet.next()) {
            ItemTM itemTM = new ItemTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getDouble(5),
                    resultSet.getString(6)
            );
            data.add(itemTM);
        }
        return data;
    }

    public boolean updateItem(List<CartTM> cartTmList) throws SQLException {
        for(CartTM tm : cartTmList) {
            System.out.println("Item: " + tm);
            if(!updateQty(tm.getItemId(), tm.getQty())) {
                return false;
            }
        }
        return true;
    }

    public boolean updateQty(String code, int qty) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE item SET quantity = quantity - ? WHERE ItemId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setInt(1, qty);
        pstm.setString(2, code);

        return pstm.executeUpdate() > 0; //false
    }
}
