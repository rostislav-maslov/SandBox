package tech.maslov.sandbox.restaurant.models;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import com.ub.core.base.models.BaseModel;
import tech.maslov.sandbox.base.models.Point;


import javax.persistence.Id;

@Document
public class RestaurantDoc extends BaseModel {
    @Id
    private ObjectId id;
    private String title;
	private String description;
	private ObjectId picId;
	private Boolean available = true;
	private Point point = new Point();
	
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ObjectId getPicId() {
        return picId;
    }

    public void setPicId(ObjectId picId) {
        this.picId = picId;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

}
