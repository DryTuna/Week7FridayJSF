package resources;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named(value = "staff")
@RequestScoped
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

	private String status = "Nothing stored";
	
	private Connection conn;
	private PreparedStatement pstmt;
	
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
					"jdbc:mysql://localhost:3306/Week7", "drytuna", "Pa$$word");

			// Create a Statement
			pstmt = conn.prepareStatement("insert into Staff (id, lastName,"
					+ " mi, firstName, address, city, state,"
					+ " telephone, email) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
		}
		catch (Exception ex) {
			System.out.println(ex);
		}
	}
	
	public void btnView() throws SQLException {
		String query = "select * from Staff where id = '" + id +"';";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while (rs.next()) {
			setLastName(rs.getString("lastName"));
			setMi(rs.getString("mi"));
			setFirstName(rs.getString("firstName"));
			setAddress(rs.getString("address"));
			setCity(rs.getString("city"));
			setState(rs.getString("state"));
			setTelephone(rs.getString("telephone"));
			setEmail(rs.getString("email"));
		}
		stmt.close();
	}
	
	public String storeStaff() {
		try {
			pstmt.setString(1, getId());
			pstmt.setString(2, getLastName());
			pstmt.setString(3, getMi());
			pstmt.setString(4, getFirstName());
			pstmt.setString(5, getAddress());
			pstmt.setString(6, getCity());
			pstmt.setString(7, getState());
			pstmt.setString(8, getTelephone());
			pstmt.setString(9, getEmail());
			pstmt.executeUpdate();
			status = getFirstName() + " " + getLastName()
					+ " is now registered in the database.";
		} 
		catch (Exception ex) {
			status = ex.getMessage();
		}
		return "StaffStoredStatus";
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMi() {
		return mi;
	}

	public void setMi(String mi) {
		this.mi = mi;
	}

	public String getFirstName() {
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
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
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
	
}
