package tech.maslov.sandbox.order.services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import tech.maslov.sandbox.base.api.response.ListApiResponse;
import tech.maslov.sandbox.base.models.Point;
import tech.maslov.sandbox.client.models.ClientDoc;
import tech.maslov.sandbox.client.services.ClientApiService;
import tech.maslov.sandbox.order.api.requests.OrderCourierNextApiRequest;
import tech.maslov.sandbox.order.api.requests.OrderCourierSetApiRequest;
import tech.maslov.sandbox.order.api.requests.OrderCreateApiRequest;
import tech.maslov.sandbox.order.api.responses.OrderApiResponse;
import tech.maslov.sandbox.order.models.*;
import tech.maslov.sandbox.order.routes.OrderApiRoutes;
import tech.maslov.sandbox.product.api.response.ProductApiResponse;
import tech.maslov.sandbox.product.models.ProductDoc;
import tech.maslov.sandbox.product.services.ProductApiService;
import tech.maslov.sandbox.product.services.ProductService;
import tech.maslov.sandbox.restaurant.models.RestaurantDoc;
import tech.maslov.sandbox.restaurant.services.RestaurantService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class OrderApiService {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private ProductApiService productApiService;
    @Autowired
    private ClientApiService clientApiService;

    private ClientInfo transform(OrderCreateApiRequest.ClientInfo request) {
        ClientInfo clientInfo = new ClientInfo();

        clientInfo.setClientId(request.getClientId());
        clientInfo.setFirstName(request.getFirstName());
        clientInfo.setPhoneNumber(request.getPhoneNumber());

        return clientInfo;
    }

    private Basket transform(OrderCreateApiRequest.Basket request) {
        Basket basket = new Basket();

        basket.setTotalPrice(0.0);
        for (OrderCreateApiRequest.Product productRequest : request.getProducts()) {
            ProductDoc productDoc = productService.findById(productRequest.getProductId());
            if (productDoc == null) continue;

            Basket.Product product = new Basket.Product();
            product.setProductId(productRequest.getProductId());
            product.setProductDoc(productDoc);
            product.setCount(productRequest.getCount());
            product.setTotalPrice(productDoc.getPrice() * productRequest.getCount());

            basket.getProducts().add(product);
            basket.setTotalPrice(basket.getTotalPrice() + product.getTotalPrice());
        }

        return basket;
    }

    private DeliveryInfo.Address transform(OrderCreateApiRequest.Address request) {
        if (request == null) return null;

        DeliveryInfo.Address address = new DeliveryInfo.Address();

        address.setCity(request.getCity());
        address.setStreet(request.getStreet());
        address.setHouse(request.getHouse());
        address.setPorch(request.getPorch());
        address.setFloor(request.getFloor());
        address.setApartment(request.getApartment());
        address.setCoordinates(request.getCoordinates());
        address.setCoordinateAccuracy(request.getCoordinateAccuracy());

        return address;
    }

    private DeliveryInfo transform(OrderCreateApiRequest.DeliveryInfo request) {
        DeliveryInfo deliveryInfo = new DeliveryInfo();

        deliveryInfo.setType(request.getType());
        deliveryInfo.setRestaurantId(request.getRestaurantId());
        deliveryInfo.setCurrentPosition(new Point());
        deliveryInfo.setAddress(transform(request.getAddress()));
        deliveryInfo.setDeliveryCost(0.);

        if (request.getRestaurantId() != null) {
            RestaurantDoc restaurantDoc = restaurantService.findById(request.getRestaurantId());
            deliveryInfo.setCurrentPosition(restaurantDoc.getPoint());
        }

        return deliveryInfo;
    }

    private PaymentInfo transform(OrderCreateApiRequest.PaymentInfo request, Basket basket, DeliveryInfo delivery) {
        PaymentInfo paymentInfo = new PaymentInfo();

        paymentInfo.setType(request.getType());
        paymentInfo.setStatus(PaymentInfo.STATUS.NOT_PAYED);

        paymentInfo.setChangeWith(request.getChangeWith());

        paymentInfo.setBasketCost(basket.getTotalPrice());
        paymentInfo.setDeliveryCost(delivery.getDeliveryCost());
        paymentInfo.setDiscountCost(0.);

        paymentInfo.setTotal(
                paymentInfo.getBasketCost() +
                        paymentInfo.getDeliveryCost() -
                        paymentInfo.getDiscountCost()
        );

        return paymentInfo;
    }

    private OrderInfo transform(OrderCreateApiRequest.OrderInfo request) {
        OrderInfo orderInfo = new OrderInfo();

        orderInfo.setComment(request.getComment());

        return orderInfo;
    }

    private OrderDoc transform(OrderCreateApiRequest request) {
        OrderDoc orderDoc = new OrderDoc();

        ClientInfo clientInfo = transform(request.getClientInfo());
        Basket basket = transform(request.getBasket());
        DeliveryInfo deliveryInfo = transform(request.getDeliveryInfo());
        OrderInfo orderInfo = transform(request.getOrderInfo());
        PaymentInfo paymentInfo = transform(request.getPaymentType(), basket, deliveryInfo);

        orderDoc.setClientInfo(clientInfo);
        orderDoc.setBasket(basket);
        orderDoc.setDeliveryInfo(deliveryInfo);
        orderDoc.setOrderInfo(orderInfo);
        orderDoc.setPaymentInfo(paymentInfo);

        return orderDoc;
    }

    private OrderApiResponse.ClientInfo transform(ClientInfo clientInfo) {
        OrderApiResponse.ClientInfo response = new OrderApiResponse.ClientInfo();
        response.setClientId(clientInfo.getClientId() != null ? clientInfo.getClientId().toString() : null);
        response.setFirstName(clientInfo.getFirstName());
        response.setPhoneNumber(clientInfo.getPhoneNumber());
        return response;
    }

    private OrderApiResponse.Basket transform(Basket basket) {
        OrderApiResponse.Basket response = new OrderApiResponse.Basket();
        response.setTotalPrice(basket.getTotalPrice());

        for (Basket.Product product : basket.getProducts()) {
            OrderApiResponse.Product productResponse = new OrderApiResponse.Product();
            productResponse.setId(product.getId().toString());
            productResponse.setProductId(product.getProductId().toString());
            productResponse.setCount(product.getCount());
            productResponse.setTotalPrice(product.getTotalPrice());
            productResponse.setProduct(productApiService.transform(product.getProductDoc()));

            response.getProducts().add(productResponse);
        }

        return response;
    }

    private OrderApiResponse.Address transform(DeliveryInfo.Address address) {
        OrderApiResponse.Address response = new OrderApiResponse.Address();
        response.setCity(address.getCity());
        response.setStreet(address.getStreet());
        response.setHouse(address.getHouse());
        response.setPorch(address.getPorch());
        response.setFloor(address.getFloor());
        response.setApartment(address.getApartment());
        response.setCoordinates(address.getCoordinates());
        response.setCoordinateAccuracy(address.getCoordinateAccuracy());
        return response;
    }

    private OrderApiResponse.DeliveryInfo transform(DeliveryInfo deliveryInfo) {
        OrderApiResponse.DeliveryInfo response = new OrderApiResponse.DeliveryInfo();

        response.setType(deliveryInfo.getType());
        response.setRestaurantId(deliveryInfo.getRestaurantId() != null ? deliveryInfo.getRestaurantId().toString() : null);
        response.setCurrentPosition(deliveryInfo.getCurrentPosition());
        response.setAddress(deliveryInfo.getAddress());
        response.setDeliveryCost(deliveryInfo.getDeliveryCost());
        response.setCourierId(deliveryInfo.getCourierId() != null ? deliveryInfo.getCourierId().toString() : null);

        return response;
    }

    private OrderApiResponse.PaymentInfo transform(ObjectId orderId, PaymentInfo paymentInfo) {
        OrderApiResponse.PaymentInfo response = new OrderApiResponse.PaymentInfo();

        response.setType(paymentInfo.getType());
        response.setStatus(paymentInfo.getStatus());

        response.setChangeWith(paymentInfo.getChangeWith());

        response.setBasketCost(paymentInfo.getBasketCost());
        response.setDeliveryCost(paymentInfo.getDeliveryCost());
        response.setDiscountCost(paymentInfo.getDiscountCost());

        response.setTotal(paymentInfo.getTotal());

        if (PaymentInfo.TYPE.ONLINE.equals(paymentInfo.getType()) && PaymentInfo.STATUS.NOT_PAYED.equals(paymentInfo.getStatus())) {
            response.setPayLink(OrderApiRoutes.PAY + "?orderId=" + orderId.toString());
        }

        return response;
    }

    private OrderApiResponse.OrderInfo transform(OrderInfo orderInfo) {
        OrderApiResponse.OrderInfo response = new OrderApiResponse.OrderInfo();

        response.setComment(orderInfo.getComment());

        return response;
    }

    private OrderApiResponse transform(OrderDoc doc) {
        OrderApiResponse response = new OrderApiResponse();

        response.setId(doc.getId().toString());
        response.setNumber(doc.getNumber());

        response.setClientInfo(transform(doc.getClientInfo()));
        response.setBasket(transform(doc.getBasket()));
        response.setDeliveryInfo(transform(doc.getDeliveryInfo()));
        response.setPaymentInfo(transform(doc.getId(), doc.getPaymentInfo()));
        response.setOrderInfo(transform(doc.getOrderInfo()));

        return response;
    }

    public OrderApiResponse create(OrderCreateApiRequest request) {
        OrderDoc orderDoc = transform(request);

        orderService.save(orderDoc);

        return transform(orderDoc);
    }

    private ListApiResponse<OrderApiResponse> transform(List<OrderDoc> orderDocs, Long count) {
        ListApiResponse<OrderApiResponse> response = new ListApiResponse<>();
        response.setItems(new ArrayList<OrderApiResponse>());
        response.setTotal(count);

        for (OrderDoc orderDoc : orderDocs) {
            response.getItems().add(transform(orderDoc));
        }

        return response;
    }

    public ListApiResponse<OrderApiResponse> list(Integer size, Integer skip) {
        List<OrderDoc> orderDocs = orderService.findAll(size, skip);
        Long count = orderService.count(new Query());

        return transform(orderDocs, count);
    }

    public OrderApiResponse courierSet(OrderCourierSetApiRequest request) {
        OrderDoc orderDoc = orderService.findById(request.getId());
        orderDoc.getDeliveryInfo().setCourierId(request.getCourierId());
        orderDoc.getDeliveryInfo().setStatus(request.getStatus());
        orderDoc.getDeliveryInfo().setCurrentPosition(request.getCurrentPosition());

        orderService.save(orderDoc);

        return transform(orderDoc);
    }

    public OrderApiResponse courierNext(OrderCourierNextApiRequest request) {
        OrderDoc orderDoc = orderService.findNextOrderForCourier();
        if (orderDoc == null) orderDoc = exampleOrder();

        orderDoc.getDeliveryInfo().setCourierId(request.getCourierId());
        orderDoc.getDeliveryInfo().setStatus(DeliveryInfo.STATUS.ON_THE_WAY);
        orderDoc.getDeliveryInfo().setCurrentPosition(orderDoc.getDeliveryInfo().getCurrentPosition());

        orderService.save(orderDoc);

        return transform(orderDoc);
    }

    public ListApiResponse<OrderApiResponse> courierOrders(ObjectId courierId, Integer size, Integer skip) {
        List<OrderDoc> orderDocs = orderService.findAll(courierId, size, skip);
        Long count = orderService.count(courierId);

        return transform(orderDocs, count);
    }


    private OrderCreateApiRequest.ClientInfo exampleOrderClientInfo() {
        ClientDoc clientDoc = clientApiService.exampleClient();

        OrderCreateApiRequest.ClientInfo clientInfo = new OrderCreateApiRequest.ClientInfo();
        clientInfo.setClientId(clientDoc.getId());
        clientInfo.setFirstName(clientDoc.getFirstName());
        clientInfo.setPhoneNumber(clientDoc.getPhoneNumber());

        return clientInfo;
    }

    private OrderCreateApiRequest.Basket exmapleOrderBasket() {
        List<ProductApiResponse> allProducts = productApiService.all();
        OrderCreateApiRequest.Basket basket = new OrderCreateApiRequest.Basket();

        for (Integer i = 0; i < 5; i++) {
            Integer index = new Random().nextInt(allProducts.size());
            ProductApiResponse prResponse = allProducts.get(index);
            OrderCreateApiRequest.Product product = new OrderCreateApiRequest.Product();
            product.setCount(new Random().nextInt(2) + 1);
            product.setProductId(new ObjectId(prResponse.getId()));
            basket.getProducts().add(product);
        }

        return basket;
    }

    private OrderCreateApiRequest.DeliveryInfo exampleOrderDelivery() {
        OrderCreateApiRequest.DeliveryInfo deliveryInfo = new OrderCreateApiRequest.DeliveryInfo();

        deliveryInfo.setType(DeliveryInfo.TYPE.DELIVERY);
        deliveryInfo.setRestaurantId(restaurantService.example().getId());

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

    private OrderCreateApiRequest.PaymentInfo examplePaymentInfo() {
        OrderCreateApiRequest.PaymentInfo paymentInfo = new OrderCreateApiRequest.PaymentInfo();
        paymentInfo.setChangeWith(5000.);
        paymentInfo.setType(PaymentInfo.TYPE.CASH);

        return paymentInfo;
    }

    private OrderCreateApiRequest.OrderInfo exampleOrderInfo() {
        OrderCreateApiRequest.OrderInfo orderInfo = new OrderCreateApiRequest.OrderInfo();

        orderInfo.setComment("Тестовый заказ для курьера");

        return orderInfo;
    }

    public OrderDoc exampleOrder() {
        OrderCreateApiRequest request = new OrderCreateApiRequest();
        request.setClientInfo(exampleOrderClientInfo());
        request.setBasket(exmapleOrderBasket());
        request.setDeliveryInfo(exampleOrderDelivery());
        request.setPaymentType(examplePaymentInfo());
        request.setOrderInfo(exampleOrderInfo());

        OrderApiResponse orderApiResponse = create(request);
        return orderService.findById(new ObjectId(orderApiResponse.getId()));
    }

    public void closeAll(OrderCourierNextApiRequest request) {
        List<OrderDoc> orderDocs = orderService.findByCourier(request.getCourierId());

        for (OrderDoc orderDoc : orderDocs) {
            orderDoc.getDeliveryInfo().setStatus(DeliveryInfo.STATUS.CLOSED);
            orderService.save(orderDoc);
        }

        return;
    }
}
