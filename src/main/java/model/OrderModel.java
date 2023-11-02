package model;

import dto.CustomerDTO;
import dto.OrderDTO;
import dto.tm.OrderTM;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderModel {
    public static boolean save(OrderDTO orderDTO)throws SQLException {
        String sql = "INSERT INTO orders(orderId,pickupDate,deliverDate,amount,status,customerID,staffId) VALUES(?,?,?,?,?,?,?)";
        boolean isSaved = CrudUtil.execute(sql, orderDTO.getOrderId(),orderDTO.getPickupDate(),orderDTO.getDeliveryDate(),orderDTO.getAmount(),orderDTO.getStatus(),orderDTO.getCustomerId(),orderDTO.getStaffid());
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
                    resultSet.getString(6),
                    resultSet.getString(7)

            );
            data.add(orderTM);
        }
        return data;
    }

    public static boolean update(OrderDTO orderDTO) throws SQLException {
        String sql = "UPDATE orders set pickupDate=?,deliverDate=?,amount=?,status=?,customerId=?,staffId=? WHERE orderId = ?";
        return CrudUtil.execute(sql,orderDTO.getPickupDate(),orderDTO.getDeliveryDate(),orderDTO.getAmount(),orderDTO.getStatus(),orderDTO.getCustomerId(),orderDTO.getStaffid(),orderDTO.getOrderId());
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
            orderDTO.setStatus(resultSet.getString(5));
            orderDTO.setCustomerId(resultSet.getString(6));
            orderDTO.setStaffid(resultSet.getString(7));

            return orderDTO;
        }
        return null;
    }

    public static boolean remove(String orderId) throws SQLException {
        String sql = "DELETE FROM orders WHERE orderId = ?";
        return CrudUtil.execute(sql,orderId);
    }
}
