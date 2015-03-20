package resources;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
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

	private String status = "Nothing stored";
	
	private PreparedStatement view, insert, update;
	
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
			status = "Database Connected.";

			// Create Statements
			view = conn.prepareStatement("select * from Staff where id = ?");
			
			insert = conn.prepareStatement("insert into Staff (id, lastName,"
					+ " mi, firstName, address, city, state,"
					+ " telephone, email) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
			update = conn.prepareStatement("update Staff set "
					+ " lastName = ?, mi = ?, firstName = ?,"
					+ " address = ?, city = ?, state = ?,"
					+ " telephone = ?, email = ? "
					+ " where id = ?");
			
		}
		catch (Exception ex) {
			System.out.println(ex);
			status = ex.getMessage();
		}
	}
	
	public void btnView() throws SQLException {
		try {
			view.setString(1, getId());
			ResultSet rs = view.executeQuery();
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
			status = "Staff ID: " + getId() + " loaded successfully.";
		}
		catch (SQLException ex) {
			status = ex.getMessage();
		}
	}

	public void btnInsert() {
		try {
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
			status = getFirstName() + " " + getLastName()
					+ " is now registered in the database.";
		} 
		catch (Exception ex) {
			status = ex.getMessage();
		}
	}
	
	public void btnUpdate() {
		try {
			view.setString(1, getId());
			ResultSet rs = view.executeQuery();
			while (rs.next()) {
				update.setString(1, rs.getString("lastName"));
				update.setString(2, rs.getString("mi"));
				update.setString(3, rs.getString("firstName"));
				update.setString(4, rs.getString("address"));
				update.setString(5, rs.getString("city"));
				update.setString(6, rs.getString("state"));
				update.setString(7, rs.getString("telephone"));
				update.setString(8, rs.getString("email"));
			}
			
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
			
			System.out.println(update.toString());
			update.executeUpdate();
			status = getFirstName() + " " + getLastName()
					+ " is now updated in the database.";
		}
		catch (Exception ex) {
			status = ex.getMessage();
		}
	}
	
	public void btnReset() {
		id = "";
		firstName = "";
		mi = "";
		lastName = "";
		address = "";
		city = "";
		state = "";
		telephone = "";
		email = "";
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
	
	public String getStatus() {
		return status;
	}
	
}
