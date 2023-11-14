package model;

import db.DBConnection;
import dto.CustomerDTO;
import dto.OrderDTO;
import dto.tm.OrderTM;
import util.CrudUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderModel {
    public static boolean save(OrderDTO orderDTO)throws SQLException {
        String sql = "INSERT INTO orders(orderId,pickupDate,deliverDate,amount,customerID,staffId) VALUES(?,?,?,?,?,?)";
        boolean isSaved = CrudUtil.execute(sql, orderDTO.getOrderId(),orderDTO.getPickupDate(),orderDTO.getDeliveryDate(),orderDTO.getAmount(),orderDTO.getCustomerId(),orderDTO.getStaffid());
        return isSaved;
    }

    public static List<OrderTM> getAll() throws SQLException {
        String sql = "SELECT * FROM orders";
        ResultSet resultSet = CrudUtil.execute(sql);
        List<OrderTM> data = new ArrayList<>();
        while (resultSet.next()) {
            OrderTM orderTM = new OrderTM(
                    resultSet.getString(1),
                    resultSet.getDate(2).toLocalDate(),
                    resultSet.getDate(3).toLocalDate(),
                    resultSet.getDouble(4),
                    resultSet.getString(5),
                    resultSet.getString(6)

            );
            data.add(orderTM);
        }
        return data;
    }

    public static boolean update(OrderDTO orderDTO) throws SQLException {
        String sql = "UPDATE orders set pickupDate=?,deliverDate=?,amount=?,customerId=?,staffId=? WHERE orderId = ?";
        return CrudUtil.execute(sql,orderDTO.getPickupDate(),orderDTO.getDeliveryDate(),orderDTO.getAmount(),orderDTO.getCustomerId(),orderDTO.getStaffid(),orderDTO.getOrderId());
    }

    public static OrderDTO search(String orderId) throws SQLException {
        String sql = "SELECT * FROM orders where orderId = ?";

        ResultSet resultSet = CrudUtil.execute(sql, orderId);

        if (resultSet.next()){
            OrderDTO orderDTO= new OrderDTO();
            orderDTO.setOrderId(resultSet.getString(1));
            orderDTO.setPickupDate(resultSet.getDate(2).toLocalDate());
            orderDTO.setDeliveryDate(resultSet.getDate(3).toLocalDate());
            orderDTO.setAmount(resultSet.getDouble(4));
            orderDTO.setCustomerId(resultSet.getString(5));
            orderDTO.setStaffid(resultSet.getString(6));

            return orderDTO;
        }
        return null;
    }

    public static boolean remove(String orderId) throws SQLException {
        String sql = "DELETE FROM orders WHERE orderId = ?";
        return CrudUtil.execute(sql,orderId);
    }

    public static String generateNextOrderId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT orderId FROM orders ORDER BY orderId DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    private static String splitOrderId(String currentOrderId) {
        if(currentOrderId != null) {
            String[] split = currentOrderId.split("O0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "O00" + id;
        } else {
            return "O001";
        }
    }


    public boolean save(String orderId, LocalDate pickupDate, LocalDate deliverDate, double amount, String customerId, String staffId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO orders VALUES(?, ?, ?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, orderId);
        pstm.setDate(2, Date.valueOf(pickupDate));
        pstm.setDate(3, Date.valueOf(deliverDate));
        pstm.setDouble(4,amount);
        pstm.setString(5, customerId);
        pstm.setString(6, staffId);
        return pstm.executeUpdate() > 0;
    }
}
