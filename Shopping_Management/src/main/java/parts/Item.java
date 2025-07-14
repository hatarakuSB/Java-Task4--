package parts;

import lombok.Getter;

@Getter
public class Item {
	    private String name;
	    private int price;
	    private String location;
	    private String date;

	    public Item(String name, int price, String location, String date) {
	        this.name = name;
	        this.price = price;
	        this.location = location;
	        this.date = date;
	    }
}
