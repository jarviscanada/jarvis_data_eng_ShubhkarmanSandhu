package ca.jrvs.apps.jdbc;

public class OrderLines {
  private double quantity;
  private String productCode;
  private String productName;
  private double produceSize;
  private String productVariety;
  private double productPrice;

  public double getQuantity() {
    return quantity;
  }

  public void setQuantity(double quantity) {
    this.quantity = quantity;
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

  public double getProduceSize() {
    return produceSize;
  }

  public void setProduceSize(double produceSize) {
    this.produceSize = produceSize;
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

  @Override
  public String toString() {
    return "OrderLine{" +
        "quantity=" + quantity +
        ", productCode='" + productCode + '\'' +
        ", productName='" + productName + '\'' +
        ", productSize=" + produceSize +
        ", productVariety='" + productVariety + '\'' +
        ", productPrice=" + productPrice +
        '}';
  }

}

