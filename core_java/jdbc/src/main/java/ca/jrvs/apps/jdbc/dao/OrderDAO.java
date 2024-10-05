package ca.jrvs.apps.jdbc.dao;

import ca.jrvs.apps.jdbc.dto.Order;
import ca.jrvs.apps.jdbc.util.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class OrderDAO extends DataAccessObject<Order> {

    private static final String GET_ONE =
            "SELECT" +
            "  c.first_name AS c_first_name, c.last_name AS c_last_name, c.email AS c_email, o.order_id, " +
            "  o.creation_date, o.total_due, o.status, " +
            "  s.first_name AS s_first_name, s.last_name AS s_last_name, s.email AS s_email, " +
            "  ol.quantity," +
            "  p.code, p.name, p.size, p.variety, p.price " +
            "from orders o " +
            "  join customer c on o.customer_id = c.customer_id " +
            "  join salesperson s on o.salesperson_id=s.salesperson_id " +
            "  join order_item ol on ol.order_id=o.order_id " +
            "  join product p on ol.product_id = p.product_id " +
            "where o.order_id = ?;";

    public OrderDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Order findById(long id) {
        Order order = new Order();

        try (PreparedStatement statement = this.connection.prepareStatement(GET_ONE);) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                order.setOrderId(rs.getLong("order_id"));
                order.setCreationDate(rs.getDate("creation_date"));
                order.setTotalDue(rs.getBigDecimal("total_due"));
                order.setStatus(rs.getString("status"));

                order.setCustomerFirstName(rs.getString("c_first_name"));
                order.setCustomerLastName(rs.getString("c_last_name"));
                order.setCustomerEmail(rs.getString("c_email"));

                order.setSalesPersonFirstName(rs.getString("s_first_name"));
                order.setSalesPersonLastName(rs.getString("s_last_name"));
                order.setSalesPersonEmail(rs.getString("s_email"));

                order.setProductCode(rs.getString("code"));
                order.setProductName(rs.getString("name"));
                order.setProductSize(rs.getInt("size"));
                order.setProductVariety(rs.getString("variety"));
                order.setProductPrice(rs.getDouble("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return order;
    }

    @Override
    public List<Order> findAll() {
        return Collections.emptyList();
    }

    @Override
    public Order update(Order dto) {
        return null;
    }

    @Override
    public Order create(Order dto) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
