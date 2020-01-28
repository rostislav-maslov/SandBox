package v1.order;

import com.ub.core.user.service.UserService;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import tech.maslov.sandbox.client.services.ClientApiService;
import tech.maslov.sandbox.client.services.ClientService;
import tech.maslov.sandbox.courier.services.CourierApiService;
import tech.maslov.sandbox.courier.services.CourierService;
import tech.maslov.sandbox.order.api.requests.OrderCourierNextApiRequest;
import tech.maslov.sandbox.order.api.requests.OrderCourierSetApiRequest;
import tech.maslov.sandbox.order.api.requests.OrderCreateApiRequest;
import tech.maslov.sandbox.order.routes.OrderApiRoutes;
import tech.maslov.sandbox.order.services.OrderApiService;
import tech.maslov.sandbox.order.services.OrderService;
import tech.maslov.sandbox.product.services.ProductService;
import tech.maslov.sandbox.restaurant.services.RestaurantService;
import v1.base.BaseFieldResponse;
import v1.client.ClientData;
import v1.courier.CourierData;
import v1.courier.CourierFieldDocs;
import v1.product.ProductData;
import v1.restaurant.RestaurantData;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@ContextConfiguration("/mvc-dispatcher-servlet.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderApiDocumentation {

    @Autowired
    private WebApplicationContext context;

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    private MockMvc mockMvc;


    @Autowired
    private CourierService courierService;
    @Autowired
    private CourierApiService courierApiService;
    @Autowired
    private UserService userService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientApiService clientApiService;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderApiService orderApiService;
    @Autowired
    private ProductService productService;

    private OrderData orderData;
    private RestaurantData restaurantData;
    private ClientData clientData;
    private ProductData productData;
    private CourierData courierData;


    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();

        this.clientData = ClientData.instance(clientService, clientApiService, userService).init();
        this.restaurantData = RestaurantData.instance(restaurantService).init();
        this.productData = ProductData.instance(productService).init();
        this.courierData = CourierData.instance(courierService, courierApiService, userService).init();

        this.orderData = new OrderData(
                restaurantData.rCenter,
                clientData.getRequestData,
                orderService,
                orderApiService,
                productData.productDocs,
                this.courierData.courierDoc.getId())
                .init();
    }


    @Test
    public void createDeliveryTest() throws Exception {
        OrderCreateApiRequest requestObj = orderData.deliveryApiRequest();
        String request = new ObjectMapper().writeValueAsString(requestObj);

        mockMvc.perform(post(OrderApiRoutes.CREATE)
                .accept(MediaType.APPLICATION_JSON)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("order/create/delivery",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        requestFields(OrderFieldDocs.createParams()))
                )
                .andDo(document("order/create/delivery",
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        responseFields(OrderFieldDocs.createResponse()))
                );
    }

    @Test
    public void createPickupTest() throws Exception {
        OrderCreateApiRequest requestObj = orderData.pickupApiRequest();
        String request = new ObjectMapper().writeValueAsString(requestObj);

        mockMvc.perform(post(OrderApiRoutes.CREATE)
                .accept(MediaType.APPLICATION_JSON)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("order/create/pickup",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        requestFields(OrderFieldDocs.createParams()))
                )
                .andDo(document("order/create/pickup",
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        responseFields(OrderFieldDocs.createResponse()))
                );
    }

    @Test
    public void listTest() throws Exception {
        createDeliveryTest();
        createPickupTest();

        mockMvc.perform(get(OrderApiRoutes.ROOT)
                .accept(MediaType.APPLICATION_JSON)
                .param("size", "20")
                .param("skip", "0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("order/list",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        requestParameters(OrderFieldDocs.listParams()))
                )
                .andDo(document("order/list",
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        responseFields(OrderFieldDocs.listResponse()))
                );
    }

    @Test
    public void courierTest() throws Exception {

        mockMvc.perform(get(OrderApiRoutes.COURIER)
                .accept(MediaType.APPLICATION_JSON)
                .param("courierId", this.courierData.courierDoc.getId().toString())
                .param("size", "20")
                .param("skip", "0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("order/courier/list",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        requestParameters(OrderFieldDocs.courierListParams()))
                )
                .andDo(document("order/courier/list",
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        responseFields(OrderFieldDocs.listResponse()))
                );

    }

    @Test
    public void courierEditTest() throws Exception {
        OrderCourierSetApiRequest requestObject = this.orderData.exampleCourierSetRequest();
        String request = new ObjectMapper().writeValueAsString(requestObject);

        mockMvc.perform(post(OrderApiRoutes.COURIER)
                .accept(MediaType.APPLICATION_JSON)

                .content(request)

                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("order/courier/set",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        requestFields(OrderFieldDocs.courierSetParams()))
                )
                .andDo(document("order/courier/set",
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        responseFields(OrderFieldDocs.createResponse()))
                );

    }

    @Test
    public void courierNextTest() throws Exception {
        OrderCourierNextApiRequest requestObject = this.orderData.exampleCourierNextRequest();
        String request = new ObjectMapper().writeValueAsString(requestObject);

        mockMvc.perform(post(OrderApiRoutes.COURIER_NEXT)
                .accept(MediaType.APPLICATION_JSON)

                .content(request)

                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("order/courier/next",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        requestFields(OrderFieldDocs.courierNextParams()))
                )
                .andDo(document("order/courier/next",
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        responseFields(OrderFieldDocs.createResponse()))
                );

    }

    @Test
    public void courierCloseTest() throws Exception {
        OrderCourierNextApiRequest requestObject = this.orderData.exampleCourierNextRequest();
        String request = new ObjectMapper().writeValueAsString(requestObject);

        mockMvc.perform(post(OrderApiRoutes.COURIER_CLOSE_ALL)
                .accept(MediaType.APPLICATION_JSON)

                .content(request)

                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("order/courier/close",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        requestFields(OrderFieldDocs.courierNextParams()))
                )
                .andDo(document("order/courier/close",
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        responseFields(BaseFieldResponse.okResponse()))
                );

    }
}
