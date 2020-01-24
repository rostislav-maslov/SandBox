package v1.client;

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
import tech.maslov.sandbox.client.api.request.ClientLoginApiRequest;
import tech.maslov.sandbox.client.api.response.ClientApiResponse;
import tech.maslov.sandbox.client.routes.ClientApiRoutes;
import tech.maslov.sandbox.client.services.ClientApiService;
import tech.maslov.sandbox.client.services.ClientService;

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
public class ClientApiDocumentation {

    @Autowired
    private WebApplicationContext context;

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    private MockMvc mockMvc;
    private ClientData clientData;

    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientApiService clientApiService;
    @Autowired
    private UserService userService;


    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();

        clientData = ClientData.instance(clientService, clientApiService, userService).init();
    }


    @Test
    public void login() throws Exception {
        ClientLoginApiRequest requestObject = new ClientLoginApiRequest();
        requestObject.setPhoneNumber(79677742027l);
        requestObject.setCode(2027);

        String request = new ObjectMapper().writeValueAsString(requestObject);

        mockMvc.perform(post(ClientApiRoutes.LOGIN)
                .accept(MediaType.APPLICATION_JSON)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("client/login",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        requestFields(ClientFieldDocs.loginParams()))
                )
                .andDo(document("client/login",
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        responseFields(
                                ClientFieldDocs.loginResponse()
                        ))
                );
    }

    @Test
    public void getTest() throws Exception {

        mockMvc.perform(
                get(ClientApiRoutes.GET.replaceAll("\\{clientId}", clientData.getRequestData.getId().toString()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("client/get",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint())
                ))
                .andDo(document("client/get",
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        responseFields(
                                ClientFieldDocs.getResponse()
                        ))
                );
    }

    @Test
    public void listTest() throws Exception {

        mockMvc.perform(
                get(ClientApiRoutes.ROOT)
                        .param("size", "50")
                        .param("skip", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("client/list",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        requestParameters(
                                ClientFieldDocs.listParams()
                        )
                ))
                .andDo(document("client/list",
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        responseFields(
                                ClientFieldDocs.listResponse()
                        ))
                );
    }


}
