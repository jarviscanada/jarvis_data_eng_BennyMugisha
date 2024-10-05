package ca.jrvs.apps.jdbc.dto;

import ca.jrvs.apps.jdbc.util.DataTransferObject;

import java.math.BigDecimal;
import java.util.Date;

public class Order implements DataTransferObject {
    private long orderId;
    private Date creationDate;
    private BigDecimal totalDue;
    private String status;

    private String customerFirstName;
    private String customerLastName;
    private String customerEmail;

    private String salesPersonFirstName;
    private String salesPersonLastName;
    private String salesPersonEmail;

    private String productCode;
    private String productName;
    private int productSize;
    private String productVariety;
    private double productPrice;


    @Override
    public long getId() {
        return orderId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public BigDecimal getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(BigDecimal totalDue) {
        this.totalDue = totalDue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getSalesPersonFirstName() {
        return salesPersonFirstName;
    }

    public void setSalesPersonFirstName(String salesPersonFirstName) {
        this.salesPersonFirstName = salesPersonFirstName;
    }

    public String getSalesPersonLastName() {
        return salesPersonLastName;
    }

    public void setSalesPersonLastName(String salesPersonLastName) {
        this.salesPersonLastName = salesPersonLastName;
    }

    public String getSalesPersonEmail() {
        return salesPersonEmail;
    }

    public void setSalesPersonEmail(String salesPersonEmail) {
        this.salesPersonEmail = salesPersonEmail;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductSize() {
        return productSize;
    }

    public void setProductSize(int productSize) {
        this.productSize = productSize;
    }

    public String getProductVariety() {
        return productVariety;
    }

    public void setProductVariety(String productVariety) {
        this.productVariety = productVariety;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
}
