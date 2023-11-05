package model;

import dto.LaundryItemDTO;
import dto.PaymentDTO;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentModel {
    public static boolean save(PaymentDTO paymentDTO) throws SQLException {
        String sql = "INSERT INTO payment(paymentId,amount,paymentDate,orderId) VALUES(?,?,?,?)";
        boolean isSaved = CrudUtil.execute(sql, paymentDTO.getPaymentId(),paymentDTO.getAmount(),paymentDTO.getPaymentDate(),paymentDTO.getOrderId());
        return isSaved;
    }

    public static boolean update(PaymentDTO paymentDTO) throws SQLException {
        String sql = "UPDATE payment set amount=?,paymentDate =?,orderId=? where paymentId=?";
        return CrudUtil.execute(sql, paymentDTO.getAmount(),paymentDTO.getPaymentDate(),paymentDTO.getOrderId(),paymentDTO.getPaymentId());
    }

    public static PaymentDTO search(String paymentId) throws SQLException {
        String sql = "SELECT * FROM payment where paymentId = ?";

        ResultSet resultSet = CrudUtil.execute(sql, paymentId);

        if (resultSet.next()){
            PaymentDTO paymentDTO= new PaymentDTO();
            paymentDTO.setPaymentId(resultSet.getString(1));
            paymentDTO.setAmount(resultSet.getDouble(2));
            paymentDTO.setPaymentDate(resultSet.getDate(3).toLocalDate());
            paymentDTO.setOrderId(resultSet.getString(4));

            return paymentDTO;
        }
        return null;
    }
}
