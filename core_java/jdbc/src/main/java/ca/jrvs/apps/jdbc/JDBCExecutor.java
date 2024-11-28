package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.dao.CustomerDAO;
import ca.jrvs.apps.jdbc.dao.OrderDAO;
import ca.jrvs.apps.jdbc.dto.Customer;
import ca.jrvs.apps.jdbc.dto.Order;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCExecutor {

    public static void main(String[] args) {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
                "hplussport", "postgres", "password");

        try {
            Connection connection = dcm.getConnection();

            // order findByID test
            OrderDAO orderDAO = new OrderDAO(connection);
            Order order = orderDAO.findById(1000);
            System.out.println(order.getCustomerFirstName() + " " + order.getCustomerLastName() +
                    " " + order.getSalesPersonFirstName() + " " + order.getSalesPersonLastName());

//            CustomerDAO customerDAO = new CustomerDAO(connection);
            // Delete
//            Customer customer = new Customer();
//            customer.setFirstName("John");
//            customer.setLastName("Adams");
//            customer.setEmail("john.adams@wh.gov");
//            customer.setPhone("(555) 555-9453");
//            customer.setAddress("1234 Main st");
//            customer.setCity("Washington");
//            customer.setState("DC");
//            customer.setZipCode("21212");
//
//            Customer dbCustomer = customerDAO.create(customer);
//            System.out.println(dbCustomer);
//            dbCustomer = customerDAO.findById(dbCustomer.getId());
//            System.out.println(dbCustomer);
//            dbCustomer.setEmail("j.adams@wh.gov");
//            dbCustomer = customerDAO.update(dbCustomer);
//            System.out.println(dbCustomer);
//            customerDAO.delete(dbCustomer.getId());

            // Update
//            Customer customer = customerDAO.findById(10000);
//            System.out.println(customer.getFirstName() + " " + customer.getLastName() +
//                    " " + customer.getEmail());
//            customer.setEmail("gwashington@wh.gov");
//            customer = customerDAO.update(customer);
//            System.out.println(customer.getFirstName() + " " + customer.getLastName() +
//                    " " + customer.getEmail());

            // Create
//            Customer customer = new Customer();
//            customer.setFirstName("George");
//            customer.setLastName("Washington");
//            customer.setEmail("george.wash@wh.gov");
//            customer.setPhone("(555) 555-6543");
//            customer.setAddress("1234 Main st");
//            customer.setCity("Washington");
//            customer.setState("DC");
//            customer.setZipCode("21212");
//            customerDAO.create(customer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
