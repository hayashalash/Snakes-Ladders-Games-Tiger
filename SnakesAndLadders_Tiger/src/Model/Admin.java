package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Admin implements Serializable {

	private static Admin admin = null;
	String password;
	public static String file_ser="Admin.ser";
	private static final long serialVersionUID = 1L;
	
	public static Admin getInstance() {
        if (admin == null) {
            admin = new Admin();
        }
        return admin;
    }
	
	private Admin() {
        // Private constructor to prevent instantiation
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
	
	public static void saveobjectChanges() {
		   
        try
        {
            FileOutputStream file = new FileOutputStream(file_ser); 
            ObjectOutputStream outp = new ObjectOutputStream(file); 
              
            outp.writeObject(admin); 
              
            outp.close(); 
            file.close(); 
                
        } 
          
        catch(IOException ex) 
        {
            return;
        }
		
	}
	
	public static void saveAdminToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(admin);
            System.out.println("Admin saved to file: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Admin loadAdminFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            admin = (Admin) ois.readObject();
            System.out.println("Admin loaded from file: " + filename);
            return admin;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
