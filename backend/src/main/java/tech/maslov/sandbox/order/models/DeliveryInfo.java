package tech.maslov.sandbox.order.models;

import org.bson.types.ObjectId;
import tech.maslov.sandbox.base.models.Point;

public class DeliveryInfo {
    public enum TYPE {
        DELIVERY, PICKUP
    }

    public enum CoordinateAccuracy{
        EXACT, STREET, DISTRICT, CITY, UNDEFINED;
    }

    public static class Address{
        private String city;
        private String street;
        private String house;
        private String porch;
        private String floor;
        private String apartment;
        private Point coordinates = new Point();
        private CoordinateAccuracy coordinateAccuracy = CoordinateAccuracy.UNDEFINED;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getHouse() {
            return house;
        }

        public void setHouse(String house) {
            this.house = house;
        }

        public String getPorch() {
            return porch;
        }

        public void setPorch(String porch) {
            this.porch = porch;
        }

        public String getFloor() {
            return floor;
        }

        public void setFloor(String floor) {
            this.floor = floor;
        }

        public String getApartment() {
            return apartment;
        }

        public void setApartment(String apartment) {
            this.apartment = apartment;
        }

        public Point getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(Point coordinates) {
            this.coordinates = coordinates;
        }

        public CoordinateAccuracy getCoordinateAccuracy() {
            return coordinateAccuracy;
        }

        public void setCoordinateAccuracy(CoordinateAccuracy coordinateAccuracy) {
            this.coordinateAccuracy = coordinateAccuracy;
        }
    }

    private TYPE type;
    private ObjectId restaurantId;
    private Point currentPosition = new Point();
    private Address address = new Address();
    private Double deliveryCost = 0.;

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public ObjectId getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(ObjectId restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Point getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Point currentPosition) {
        this.currentPosition = currentPosition;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Double getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(Double deliveryCost) {
        this.deliveryCost = deliveryCost;
    }
}
