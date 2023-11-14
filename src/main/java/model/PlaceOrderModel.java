package model;

import db.DBConnection;
import dto.ItemDTO;
import dto.PlaceOrderDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class PlaceOrderModel {
    private OrderModel orderModel = new OrderModel();
    private ItemModel itemModel = new ItemModel();

    public boolean placeOrder(PlaceOrderDTO placeOrderDto) throws SQLException {
        System.out.println(placeOrderDto);

        String orderId = placeOrderDto.getOrderId();
        LocalDate pickupDate = placeOrderDto.getPickupDate();
        LocalDate deliverDate = placeOrderDto.getDeliverDate();
        double amount = placeOrderDto.getAmount();
        String customerId = placeOrderDto.getCustomerId();
        String staffId = placeOrderDto.getStaffId();


        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isOrderSaved = orderModel.save(orderId,pickupDate,deliverDate,amount, customerId,staffId );
            if (isOrderSaved) {
                boolean isUpdated = itemModel.updateItem(placeOrderDto.getCartTmList());
                if (isUpdated) {
                        connection.commit();
                    }
                }
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
        return true;
    }
}
