package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.util.DataTransferObject;

import java.util.ArrayList;
import java.util.List;

public class Order implements DataTransferObject {

  private long id;
  private String customerFirstName;
  private String customerLastName;
  private String customerEmail;
  private String creationDate;
  private double totalDue;
  private String status;
  private String salesPersonFirstName;
  private String salesPersonLastName;
  private String salesPersonEmail;
  private ArrayList<OrderLines> orderLines = new ArrayList<>();

  @Override
  public long getID() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public String getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(String creationDate) {
    this.creationDate = creationDate;
  }

  public double getTotalDue() {
    return totalDue;
  }

  public void setTotalDue(double totalDue) {
    this.totalDue = totalDue;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
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

  public void addOrderLine(OrderLines od){
    this.orderLines.add(od);
  }

  public void setOrderLines(List<OrderLines> od){
    for (OrderLines ordrlns:od){
      this.orderLines.add(ordrlns);
    }
  }

  public List<OrderLines> getOrderLines(){
    return orderLines;
  }

  @Override
  public String toString() {
    return "Order{" +
        "id=" + id +
        ", customerFirstName='" + customerFirstName + '\'' +
        ", customerLastLane='" + customerLastName + '\'' +
        ", customerEmail='" + customerEmail + '\'' +
        ", creationDate=" + creationDate +
        ", totalDue=" + totalDue +
        ", status='" + status + '\'' +
        ", salespersonFirstName='" + salesPersonFirstName + '\'' +
        ", salespersonLastName='" + salesPersonLastName + '\'' +
        ", salespersonEmail='" + salesPersonEmail + '\'' +
        ", orderLines=" + orderLines +
        '}';
  }

}