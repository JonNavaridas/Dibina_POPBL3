package usuarios;

public class User {

	String id;
	String name;
	Integer password;
	
	public User(String id, String name, int password) {
		this.id = id;
		this.name = name;
		this.password = password;
	}
	
	public String getName() {
		return name;
	}
	
	public String getID() {
		return id;
	}
	
	public int getContraseña() {
		return password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
	}
}