package tech.maslov.sandbox.client.models;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import com.ub.core.base.models.BaseModel;


import javax.persistence.Id;

@Document
public class ClientDoc extends BaseModel {
    @Id
    private ObjectId id;
    private String firstName = "";
	private String lastName = "";
	private ObjectId userId;
	private String bDate= null;
	private Sex sex = Sex.NOT_SET;
	private Long phoneNumber;
	
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public String getBDate() {
        return bDate;
    }

    public void setBDate(String bDate) {
        this.bDate = bDate;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getbDate() {
        return bDate;
    }

    public void setbDate(String bDate) {
        this.bDate = bDate;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
