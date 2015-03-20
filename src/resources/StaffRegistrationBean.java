package resources;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named(value = "staffRegistration")
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

	private String status = "Nothing stored";
	
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
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/Week7", "drytuna", "Pa$$word");

			// Create a Statement
			pstmt = conn.prepareStatement("insert into Address (lastName,"
					+ " firstName, mi, telephone, email, street, city, "
					+ "state, zip) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
		} catch (Exception ex) {
			System.out.println(ex);
		}
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
