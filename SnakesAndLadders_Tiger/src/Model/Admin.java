package Model;

public class Admin {

	private static Admin admin = null;
	String password = "admin123";
	
	public static Admin getInstance() {
		if (admin == null) {

			admin = new Admin();
			return admin;
		}
		return admin;
	}

	public boolean checkPassword(String enteredPassword) {
		if (enteredPassword.equals(this.password))
			return true;
		return false;
	}
	
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
