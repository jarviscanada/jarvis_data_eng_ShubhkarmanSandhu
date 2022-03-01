package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.util.DataAccessObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerDAO extends DataAccessObject<Customer> {


  final Logger logger = LoggerFactory.getLogger(CustomerDAO.class);

  private static final String INSERT = "INSERT INTO customer (first_name, last_name, email, phone, address, city, state, zipcode) VALUES (?,?,?,?,?,?,?,?)";
  private static final String GET_ONE="SELECT customer_id,first_name,last_name,email,phone ,address,city,state,zipcode FROM customer WHERE customer_id=?";
  private static final String UPDATE="UPDATE customer SET first_name=?,last_name=?,email=?,phone=?,address=?,city=?,state=?,zipcode=? WHERE customer_id=?";
  private static final String DELETE="DELETE FROM customer WHERE customer_id=?";
  public CustomerDAO(Connection connection) {
    super(connection);
  }

  @Override
  public Customer findById(long id) {
    Customer customer=new Customer();
    try(PreparedStatement statement=this.connection.prepareStatement(GET_ONE);){
      statement.setLong(1,id);
      ResultSet rs=statement.executeQuery();
      while(rs.next()){
        customer.setId(rs.getLong("customer_id"));
        customer.setFirstName(rs.getString("first_name"));
        customer.setLastName(rs.getString("last_name"));
        customer.setEmail(rs.getString("email"));
        customer.setPhone(rs.getString("phone"));
        customer.setAddress(rs.getString("address"));
        customer.setCity(rs.getString("city"));
        customer.setState(rs.getString("state"));
        customer.setZipCode(rs.getString("zipcode"));
      }
    }
    catch(SQLException e)
    {
      logger.error("Error FindByID: "+e.getMessage());
      throw new RuntimeException("Error in FindByID:",e);
    }
    return customer;
  }

  @Override
  public List<Customer> findAll() {
    return null;
  }

  @Override
  public Customer update(Customer dto) {
    Customer customer=null;
    try{
      this.connection.setAutoCommit(false);
    }
    catch(SQLException e){
      logger.error("Error UPDATE(commit): "+e.getMessage());
      throw new RuntimeException("UPDATE(commit):",e);
    }
    try(PreparedStatement statement= this.connection.prepareStatement(UPDATE);){
      statement.setString(1,dto.getFirstName());
      statement.setString(2,dto.getLastName());
      statement.setString(3,dto.getEmail());
      statement.setString(4,dto.getPhone());
      statement.setString(5,dto.getAddress());
      statement.setString(6,dto.getCity());
      statement.setString(7,dto.getState());
      statement.setString(8,dto.getZipCode());
      statement.setLong(9,dto.getID());
      statement.execute();
      this.connection.commit();
      customer=this.findById(dto.getID());
    }
    catch (SQLException e){
      try{
        this.connection.rollback();
      }
      catch(SQLException e1)
      {
        logger.error("Error UPDATE(RollBack): "+e1.getMessage());
        throw new RuntimeException("UPDATE(RollBack):",e1);

      }
      logger.error("Error UPDATE: "+e.getMessage());
      throw new RuntimeException("UPDATE:",e);
    }

    return customer;
  }

  @Override
  public Customer create(Customer dto) {
    try(PreparedStatement statement=this.connection.prepareStatement(INSERT);){
      statement.setString(1,dto.getFirstName());
      statement.setString(2,dto.getLastName());
      statement.setString(3,dto.getEmail());
      statement.setString(4,dto.getPhone());
      statement.setString(5,dto.getAddress());
      statement.setString(6,dto.getCity());
      statement.setString(7,dto.getState());
      statement.setString(8,dto.getZipCode());
      statement.execute();
      int id=this.getLastVal(CUSTOMER_SEQUENCE);
      return this.findById(id);
    }
    catch (SQLException e){
      logger.error("Error CREATE: "+e.getMessage());
      throw new RuntimeException("CREATE:",e);
    }

  }

  @Override
  public void delete(long id) {
    try(PreparedStatement statement=this.connection.prepareStatement(DELETE);){
      statement.setLong(1,id);
      statement.executeQuery();
    }
    catch (SQLException e){
      logger.error("Error DELETE: "+e.getMessage());
      throw new RuntimeException("DELETE:",e);
    }

  }
}
