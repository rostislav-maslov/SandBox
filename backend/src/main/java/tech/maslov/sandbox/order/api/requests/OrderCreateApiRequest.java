package tech.maslov.sandbox.order.api.requests;

import org.bson.types.ObjectId;
import tech.maslov.sandbox.base.models.Point;

import java.util.ArrayList;
import java.util.List;

public class OrderCreateApiRequest {
    public static class Product {
        private ObjectId productId;
        private Integer count;

        public ObjectId getProductId() {
            return productId;
        }

        public void setProductId(ObjectId productId) {
            this.productId = productId;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }

    public static class Basket {
        private List<Product> products = new ArrayList<>();

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }
    }

    public static class ClientInfo {
        private ObjectId clientId;
        private String firstName;
        private Long phoneNumber;

        public ObjectId getClientId() {
            return clientId;
        }

        public void setClientId(ObjectId clientId) {
            this.clientId = clientId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public Long getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(Long phoneNumber) {
            this.phoneNumber = phoneNumber;
        }


    }

    public static class Address{
        private String city;
        private String street;
        private String house;
        private String porch;
        private String floor;
        private String apartment;
        private Point coordinates = new Point();
        private tech.maslov.sandbox.order.models.DeliveryInfo.CoordinateAccuracy coordinateAccuracy = tech.maslov.sandbox.order.models.DeliveryInfo.CoordinateAccuracy.UNDEFINED;

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

        public tech.maslov.sandbox.order.models.DeliveryInfo.CoordinateAccuracy getCoordinateAccuracy() {
            return coordinateAccuracy;
        }

        public void setCoordinateAccuracy(tech.maslov.sandbox.order.models.DeliveryInfo.CoordinateAccuracy coordinateAccuracy) {
            this.coordinateAccuracy = coordinateAccuracy;
        }
    }

    public static class DeliveryInfo {

        private tech.maslov.sandbox.order.models.DeliveryInfo.TYPE type;
        private ObjectId restaurantId;
        private Address address = new Address();

        public tech.maslov.sandbox.order.models.DeliveryInfo.TYPE getType() {
            return type;
        }

        public void setType(tech.maslov.sandbox.order.models.DeliveryInfo.TYPE type) {
            this.type = type;
        }

        public ObjectId getRestaurantId() {
            return restaurantId;
        }

        public void setRestaurantId(ObjectId restaurantId) {
            this.restaurantId = restaurantId;
        }


        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

    }

    public static class OrderInfo {
        private String comment;

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }

    public static class PaymentInfo {

        private tech.maslov.sandbox.order.models.PaymentInfo.TYPE type;
        private Double changeWith = 0.;

        public tech.maslov.sandbox.order.models.PaymentInfo.TYPE getType() {
            return type;
        }

        public void setType(tech.maslov.sandbox.order.models.PaymentInfo.TYPE type) {
            this.type = type;
        }

        public Double getChangeWith() {
            return changeWith;
        }

        public void setChangeWith(Double changeWith) {
            this.changeWith = changeWith;
        }
    }

    private ClientInfo clientInfo = new ClientInfo();
    private Basket basket = new Basket();
    private DeliveryInfo deliveryInfo = new DeliveryInfo();
    private PaymentInfo paymentType = new PaymentInfo();
    private OrderInfo orderInfo = new OrderInfo();

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public DeliveryInfo getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public PaymentInfo getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentInfo paymentType) {
        this.paymentType = paymentType;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }


}
