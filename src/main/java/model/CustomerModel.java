package model;


import dto.CustomerDTO;
import dto.tm.CustomerTM;
import util.CrudUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModel {

    public static boolean save(CustomerDTO customerDTO) throws SQLException {
        String sql = "INSERT INTO customer(customerId,name,email,address,nic,telNum) VALUES(?,?,?,?,?,?)";
        boolean isSaved = CrudUtil.execute(sql, customerDTO.getCustomerId(),customerDTO.getName(),customerDTO.getEmail(),customerDTO.getAddress(),customerDTO.getNic(),customerDTO.getTelNum());
        return isSaved;
    }


    public static boolean update(CustomerDTO customerDTO) throws SQLException {
        String sql = "UPDATE customer set name=?,address=?,email=?,nic=?,telNum=? WHERE customerId = ?";
        return CrudUtil.execute(sql,customerDTO.getName(),customerDTO.getAddress(),customerDTO.getEmail(),customerDTO.getNic(),customerDTO.getTelNum(),customerDTO.getCustomerId());

    }

    public static boolean remove(String customerId) throws SQLException {
        String sql = "DELETE FROM customer WHERE customerId = ?";
        return CrudUtil.execute(sql,customerId);
    }

    public static CustomerDTO search(String customerId) throws SQLException {
        String sql = "SELECT * FROM customer where customerId = ?";

        ResultSet resultSet = CrudUtil.execute(sql, customerId);

        if (resultSet.next()){
            CustomerDTO customerDTO= new CustomerDTO();
            customerDTO.setCustomerId(resultSet.getString(1));
            customerDTO.setName(resultSet.getString(3));
            customerDTO.setEmail(resultSet.getString(3));
            customerDTO.setAddress(resultSet.getString(4));
            customerDTO.setTelNum(resultSet.getInt(5));
            customerDTO.setNic(resultSet.getString(6));

            return customerDTO;
        }
        return null;
    }

    public static List<CustomerTM> getAll() throws SQLException {
        String sql = "SELECT * FROM customer";
        ResultSet resultSet = CrudUtil.execute(sql);
        List<CustomerTM> data = new ArrayList<>();
        while (resultSet.next()) {
            CustomerTM customerTM = new CustomerTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getInt(5),
                    resultSet.getString(6)

            );
            data.add(customerTM);
        }
        return data;
    }
}
