package classes;

public class TodoItem {
	public String action;
	public boolean done = false;
	
	public TodoItem() {
		// TODO Auto-generated constructor stub
	}
	
	public TodoItem(String action) {
		
		this.action = action;
		done = false;
	}

	@Override
	public String toString() {
		String todoItem = "[" + action + "]"; 
		if (done == false) {
			todoItem += " [ ]";
		}
		else {
			todoItem += " [x]";
		}
		return todoItem;
	}
	
	

}
