package v1.order;

import org.bson.types.ObjectId;
import tech.maslov.sandbox.base.models.Point;
import tech.maslov.sandbox.client.models.ClientDoc;
import tech.maslov.sandbox.order.api.requests.OrderCourierNextApiRequest;
import tech.maslov.sandbox.order.api.requests.OrderCourierSetApiRequest;
import tech.maslov.sandbox.order.api.requests.OrderCreateApiRequest;
import tech.maslov.sandbox.order.api.responses.OrderApiResponse;
import tech.maslov.sandbox.order.models.DeliveryInfo;
import tech.maslov.sandbox.order.models.OrderDoc;
import tech.maslov.sandbox.order.models.PaymentInfo;
import tech.maslov.sandbox.order.services.OrderApiService;
import tech.maslov.sandbox.order.services.OrderService;
import tech.maslov.sandbox.product.models.ProductDoc;
import tech.maslov.sandbox.restaurant.models.RestaurantDoc;

import java.util.List;
import java.util.Random;

public class OrderData {
    private RestaurantDoc restaurantDoc;
    private ClientDoc clientDoc;
    private OrderService orderService;
    private OrderApiService orderApiService;
    private List<ProductDoc> productDocs;
    private ObjectId courierId;

    private OrderDoc orderDelivery;
    private OrderDoc orderDeliveryCourier;
    private OrderDoc orderPickUp;

    public OrderData(RestaurantDoc restaurantDoc, ClientDoc clientDoc, OrderService orderService, OrderApiService orderApiService, List<ProductDoc> productDocs, ObjectId courierId) {
        this.restaurantDoc = restaurantDoc;
        this.clientDoc = clientDoc;
        this.orderService = orderService;
        this.productDocs = productDocs;
        this.orderApiService = orderApiService;
        this.courierId = courierId;
    }

    public OrderData init(){
        this.orderDelivery = orderService.findById(new ObjectId(orderApiService.create(deliveryApiRequest()).getId()));
        this.orderPickUp = orderService.findById(new ObjectId(orderApiService.create(pickupApiRequest()).getId()));

        this.orderDeliveryCourier = orderService.findById(new ObjectId(orderApiService.create(deliveryApiRequest()).getId()));
        this.orderDeliveryCourier.getDeliveryInfo().setCourierId(this.courierId);
        orderService.save(this.orderDeliveryCourier);

        return this;
    }

    public OrderCreateApiRequest deliveryApiRequest(){
        OrderCreateApiRequest request = new OrderCreateApiRequest();

        request.setOrderInfo(exampleOrderInfo());
        request.setPaymentType(examplePaymentInfo());
        request.setDeliveryInfo(exampleOrderDelivery());
        request.setBasket(exampleOrderBasket());
        request.setClientInfo(exampleOrderClientInfo());

        return request;
    }

    public OrderCreateApiRequest pickupApiRequest(){
        OrderCreateApiRequest request = new OrderCreateApiRequest();

        request.setOrderInfo(exampleOrderInfo());
        request.setPaymentType(examplePaymentInfo());
        request.setDeliveryInfo(exampleOrderPickup());
        request.setBasket(exampleOrderBasket());
        request.setClientInfo(exampleOrderClientInfo());

        return request;
    }

    private OrderCreateApiRequest.ClientInfo exampleOrderClientInfo(){
        ClientDoc clientDoc = this.clientDoc;

        OrderCreateApiRequest.ClientInfo clientInfo = new OrderCreateApiRequest.ClientInfo();
        clientInfo.setClientId(clientDoc.getId());
        clientInfo.setFirstName(clientDoc.getFirstName());
        clientInfo.setPhoneNumber(clientDoc.getPhoneNumber());

        return clientInfo;
    }

    private OrderCreateApiRequest.Basket exampleOrderBasket(){
        List<ProductDoc> productDocs = this.productDocs;
        OrderCreateApiRequest.Basket basket = new OrderCreateApiRequest.Basket();

        for(ProductDoc productDoc: productDocs){
            OrderCreateApiRequest.Product product = new OrderCreateApiRequest.Product();
            product.setCount(new Random().nextInt(2) + 1);
            product.setProductId(new ObjectId(productDoc.getId().toString()));
            basket.getProducts().add(product);
        }

        return basket;
    }

    private OrderCreateApiRequest.DeliveryInfo exampleOrderDelivery(){
        OrderCreateApiRequest.DeliveryInfo deliveryInfo = new OrderCreateApiRequest.DeliveryInfo();

        deliveryInfo.setType(DeliveryInfo.TYPE.DELIVERY);
        deliveryInfo.setRestaurantId(this.restaurantDoc.getId());

        OrderCreateApiRequest.Address address = new OrderCreateApiRequest.Address();
        address.setCity("Волгоград");
        address.setStreet("Университетский проспект");
        address.setHouse("100к1");
        address.setPorch("");
        address.setFloor("1");
        address.setApartment("1");

        Point point = new Point();
        point.setLatitude(48.642232);
        point.setLongitude(44.424269);
        address.setCoordinates(point);
        address.setCoordinateAccuracy(DeliveryInfo.CoordinateAccuracy.EXACT);

        deliveryInfo.setAddress(address);

        return deliveryInfo;
    }

    private OrderCreateApiRequest.DeliveryInfo exampleOrderPickup(){
        OrderCreateApiRequest.DeliveryInfo deliveryInfo = new OrderCreateApiRequest.DeliveryInfo();

        deliveryInfo.setType(DeliveryInfo.TYPE.PICKUP);
        deliveryInfo.setRestaurantId(this.restaurantDoc.getId());

        deliveryInfo.setAddress(null);

        return deliveryInfo;
    }

    private OrderCreateApiRequest.PaymentInfo examplePaymentInfo(){
        OrderCreateApiRequest.PaymentInfo paymentInfo = new OrderCreateApiRequest.PaymentInfo();
        paymentInfo.setChangeWith(5000.);
        paymentInfo.setType(PaymentInfo.TYPE.CASH);

        return paymentInfo;
    }

    private OrderCreateApiRequest.OrderInfo exampleOrderInfo(){
        OrderCreateApiRequest.OrderInfo orderInfo = new OrderCreateApiRequest.OrderInfo();

        orderInfo.setComment("Тестовый заказ для документации");

        return orderInfo;
    }

    public OrderCourierSetApiRequest exampleCourierSetRequest(){
        OrderCourierSetApiRequest request = new OrderCourierSetApiRequest();

        request.setCourierId(this.courierId);
        request.setCurrentPosition(new Point(48.01, 44.0));
        request.setStatus(DeliveryInfo.STATUS.ON_THE_WAY);
        request.setId(this.orderDelivery.getId());

        return request;
    }

    public OrderCourierNextApiRequest exampleCourierNextRequest(){
        OrderCourierNextApiRequest requestObject = new OrderCourierNextApiRequest();
        requestObject.setCourierId(this.courierId);
        return  requestObject;
    }
}
