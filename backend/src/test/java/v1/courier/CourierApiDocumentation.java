package v1.courier;

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
import tech.maslov.sandbox.courier.api.request.CourierLoginApiRequest;
import tech.maslov.sandbox.courier.routes.CourierApiRoutes;
import tech.maslov.sandbox.courier.services.CourierApiService;
import tech.maslov.sandbox.courier.services.CourierService;

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
public class CourierApiDocumentation {

    @Autowired
    private WebApplicationContext context;

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    private MockMvc mockMvc;
    private CourierData courierData;

    @Autowired
    private CourierService courierService;
    @Autowired
    private CourierApiService courierApiService;
    @Autowired
    private UserService userService;


    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();

        courierData = CourierData.instance(courierService, courierApiService, userService).init();
    }


    @Test
    public void login() throws Exception {
        CourierLoginApiRequest requestObject = new CourierLoginApiRequest();
        requestObject.setPhoneNumber(79677742027l);
        requestObject.setCode(2027);
        requestObject.setFirstName("Иван");
        requestObject.setLastName("Иванов");

        String request = new ObjectMapper().writeValueAsString(requestObject);

        mockMvc.perform(post(CourierApiRoutes.LOGIN)
                .accept(MediaType.APPLICATION_JSON)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("courier/login",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        requestFields(CourierFieldDocs.loginParams()))
                )
                .andDo(document("courier/login",
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        responseFields(
                                CourierFieldDocs.loginResponse()
                        ))
                );
    }

    @Test
    public void getTest() throws Exception {

        mockMvc.perform(
                get(CourierApiRoutes.GET.replaceAll("\\{courierId}", courierData.courierDoc.getId().toString()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("courier/get",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint())
                ))
                .andDo(document("courier/get",
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        responseFields(
                                CourierFieldDocs.getResponse()
                        ))
                );
    }

    @Test
    public void listTest() throws Exception {

        mockMvc.perform(
                get(CourierApiRoutes.ROOT)
                        .param("size", "50")
                        .param("skip", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("courier/list",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        requestParameters(
                                CourierFieldDocs.listParams()
                        )
                ))
                .andDo(document("courier/list",
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        responseFields(
                                CourierFieldDocs.listResponse()
                        ))
                );
    }


}
