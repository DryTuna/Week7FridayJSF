/*
 * Name: Duy Tran
 * Date: March 20th, 2015
 */

package resources;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named(value = "staff")
@SessionScoped
public class StaffRegistrationBean implements Serializable{
	private String id;
	private String lastName;
	private String mi;
	private String firstName;
	private String address;
	private String city;
	private String state;
	private String telephone;
	private String email;

	private String status = "Database Not Connected";
	
	private PreparedStatement view, insert, update; 
	private Connection conn;
	
	public StaffRegistrationBean() {
		initializeJdbc();
	}
	
	/** Initialize database connection */
	private void initializeJdbc() {
		try {
			// Explicitly load a MySQL driver
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loaded");

			// Establish a connection
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306", "drytuna", "Pa$$word");
			
			initializeDB(conn.createStatement());

			status = "Database Connected.";
		}
		catch (SQLException ex) {
			System.out.println(ex);
			status = ex.getMessage();
		}
		catch (ClassNotFoundException ex) {
			System.out.println(ex);
			status = ex.getMessage();
		}
	}
	
	/** Create Database */
	public void initializeDB(Statement stmt) throws SQLException{
		stmt.executeUpdate("create database Week7");
		stmt.executeUpdate("use Week7");
		stmt.executeUpdate("create table Staff ("
				+ "id char(9) not null, "
				+ "lastName varchar(15), "
				+ "mi char(1), "
				+ "firstName varchar(15), "
				+ "address varchar(20), "
				+ "city varchar(20), "
				+ "state char(2), "
				+ "telephone char(10), "
				+ "email varchar(40), "
				+ "primary key (id))");
		stmt.executeUpdate("insert into Staff values ("
				+ "'111', 'Doji', '', 'Shuten', '12345 Ayakashi Dr.', 'Tokyo', "
				+ "'GG', '9876540123', 'shutendoji@ayakashi.gg');");
		stmt.executeUpdate("insert into Staff values ("
				+ "'222', 'Doji', '', 'Giri', '23456 Ayakashi Dr.', 'Tokyo', "
				+ "'GG', '6547890123', 'dojigiri@ayakashi.gg');");
		stmt.executeUpdate("insert into Staff values ("
				+ "'333', 'Takeda', '', 'Shingen', '34567 Ayakashi Dr.', 'Tokyo', "
				+ "'GG', '1236540987', 'shingentakeda@ayakashi.gg');");
		stmt.executeUpdate("insert into Staff values ("
				+ "'444', 'Hime', '', 'Kushinada', '45678 Ayakashi Dr.', 'Tokyo', "
				+ "'GG', '3214568790', 'kushinadahime@ayakashi.gg');");
		
		stmt.close();
		System.out.println("Database Created. Table Created.");
	}
	
	/** View Method for view button */
	public void btnView() {
		try {
			view = conn.prepareStatement("select * from Staff where id = ?");
			view.setString(1, getId());
			ResultSet rs = view.executeQuery();
			int count = 0;
			while (rs.next()) {
				setLastName(rs.getString("lastName"));
				setMi(rs.getString("mi"));
				setFirstName(rs.getString("firstName"));
				setAddress(rs.getString("address"));
				setCity(rs.getString("city"));
				setState(rs.getString("state"));
				setTelephone(rs.getString("telephone"));
				setEmail(rs.getString("email"));
				count++;
			}
			rs.close();
			view.close();
			if (count == 0)
				status = "ID-" + getId() + " does not exist.";
			else
				status = "ID-" + getId() + " loaded successfully.";
		}
		catch (SQLException ex) {
			status = ex.getMessage();
		}
	}

	/** Insert Method for insert button */
	public void btnInsert(){
		try {
			insert = conn.prepareStatement("insert into Staff (id, lastName,"
					+ " mi, firstName, address, city, state,"
					+ " telephone, email) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			insert.setString(1, getId());
			insert.setString(2, getLastName());
			insert.setString(3, getMi());
			insert.setString(4, getFirstName());
			insert.setString(5, getAddress());
			insert.setString(6, getCity());
			insert.setString(7, getState());
			insert.setString(8, getTelephone());
			insert.setString(9, getEmail());
			insert.executeUpdate();
			insert.close();
			status = getFirstName() + " " + getLastName()
					+ " is now registered in the database.";
		} 
		catch (Exception ex) {
			status = ex.getMessage();
		}
	}
	
	/** Update Method for update button */
	public void btnUpdate() {
		try {
			view = conn.prepareStatement("select * from Staff where id = ?");
			view.setString(1, getId());
			ResultSet rs = view.executeQuery();

			update = conn.prepareStatement("update Staff set "
					+ " lastName = ?, mi = ?, firstName = ?,"
					+ " address = ?, city = ?, state = ?,"
					+ " telephone = ?, email = ? "
					+ " where id = ?");
			
			int count = 0;
			while (rs.next()) {
				update.setString(1, rs.getString("lastName"));
				update.setString(2, rs.getString("mi"));
				update.setString(3, rs.getString("firstName"));
				update.setString(4, rs.getString("address"));
				update.setString(5, rs.getString("city"));
				update.setString(6, rs.getString("state"));
				update.setString(7, rs.getString("telephone"));
				update.setString(8, rs.getString("email"));
				count++;
			}
			
			if (count > 0) {
				update.setString(9, getId());
				
				if (lastName != null && !lastName.isEmpty())
					update.setString(1, getLastName());
				
				if (mi != null && !mi.isEmpty())
					update.setString(2, getMi());
				
				if (firstName != null && !firstName.isEmpty())
					update.setString(3, getFirstName());
				
				if (address != null && !address.isEmpty())
					update.setString(4, getAddress());
				
				if (city != null && !city.isEmpty())
					update.setString(5, getCity());
				
				if (state != null && !state.isEmpty())
					update.setString(6, getState());
				
				if (telephone != null && !telephone.isEmpty())
					update.setString(7, getTelephone());
				
				if (email != null && !email.isEmpty())
					update.setString(8, getEmail());
				
				update.executeUpdate();
				status = getFirstName() + " " + getLastName()
						+ " is now updated in the database.";
			}
			else
				status = "ID-" + getId() + " does not exist."
					+ " Please use INSERT instead.";
			
			rs.close();
			update.close();
			view.close();
		}
		catch (Exception ex) {
			status = ex.getMessage();
		}
	}
	
	/** Reset Method for reset button */
	public void btnReset() {
		id = null;
		firstName = null;
		mi = null;
		lastName = null;
		address = null;
		city = null;
		state = null;
		telephone = null;
		email = null;
		status = "Database Connected";
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLastName() {
		if (lastName != null && !lastName.isEmpty()) {
			char temp = lastName.toUpperCase().charAt(0);
			return temp + lastName.toLowerCase().substring(1);
		}
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMi() {
		if (mi != null)
			return mi.toUpperCase();
		return mi;
	}

	public void setMi(String mi) {
		this.mi = mi;
	}

	public String getFirstName() {
		if (firstName != null && !firstName.isEmpty()) {
			char temp = firstName.toUpperCase().charAt(0);
			return temp + firstName.toLowerCase().substring(1);
		}
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		if (city != null && !city.isEmpty()) {
			char temp = city.toUpperCase().charAt(0);
			if (city.length() == 1)
				return temp + "";
			return temp + city.toLowerCase().substring(1);
		}
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		if (state != null)
			return state.toUpperCase();
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getStatus() {
		return status;
	}
	
}
