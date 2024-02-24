package Model;

import java.util.ArrayList;

public abstract class Sort {
	
	// Design Pattern #2 - Template Pattern
	
	public abstract ArrayList<Object> getSorted(String sortedBy); // A method that must return a sorted ArrayList of the items based on [sortedBy]
	
	// Template method
	public final void sortItems() {
		getSorted("");
	}
}
