package domain.model;

import java.util.Base64;

public class Field {
	
	private String type;
	private String fieldName;
	private String desc;
	private String fieldValue;

	public Field() {
	}

	public Field(String type, String fieldName, String desc, String fieldValue) {
        this.type = type;
        this.fieldName = fieldName;
        this.desc = desc;
        this.fieldValue = fieldValue;
    }

    public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	
	public String fetchDecodedValue() {
		String decodedString = new String(Base64.getDecoder().decode(fieldValue));
		return decodedString;
	}
	
	public String fetchEncodedValue() {
		String encodedString = Base64.getEncoder().encodeToString(fieldValue.getBytes());
		return encodedString;
	}
	
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	
}