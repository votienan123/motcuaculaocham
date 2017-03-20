package vn.toancauxanh.gg.model;

public class PropertyChangeObject {
	private String propertyName = "";
	private String oldValue = "";
	private String newValue = "";
		
	public PropertyChangeObject(String propertyName, String oldValue, String newValue) {
		super();
		this.propertyName = propertyName;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	@Override
	public int hashCode() {
		return 1;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Integer) {
			return false;
		}
		PropertyChangeObject obj2 = (PropertyChangeObject) obj;
		if (obj2 != null) {
			if (getPropertyName()
					.equals(obj2.getPropertyName()) 
					&& getOldValue().equals(obj2.getOldValue()) 
					&& getNewValue().equals(obj2.getNewValue())) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return "propertyNam: " + getPropertyName() + " ;oldValue: " + getOldValue() + " ;newValue: " + getNewValue();
	}
}
