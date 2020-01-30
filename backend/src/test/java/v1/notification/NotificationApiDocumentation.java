package v1.notification;

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
import tech.maslov.sandbox.notification.api.requests.NotificationCreateRequest;
import tech.maslov.sandbox.notification.routes.NotificationApiRoutes;
import tech.maslov.sandbox.notification.services.NotificationService;

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
public class NotificationApiDocumentation {
    @Autowired
    private WebApplicationContext context;

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    private MockMvc mockMvc;

    private NotificationData notificationData;
    @Autowired private NotificationService notificationService;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();

        this.notificationData = NotificationData.instance(notificationService).init();
    }


    @Test
    public void createTest() throws Exception {
        NotificationCreateRequest requestObj = notificationData.request();
        String request = new ObjectMapper().writeValueAsString(requestObj);

        mockMvc.perform(post(NotificationApiRoutes.ROOT)
                .accept(MediaType.APPLICATION_JSON)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("notification/create",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        requestFields(NotificationFieldDocs.createParams()))
                )
                .andDo(document("notification/create",
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        responseFields(NotificationFieldDocs.createResponse()))
                );
    }

    @Test
    public void list() throws Exception {
        mockMvc.perform(get(NotificationApiRoutes.ROOT)
                .accept(MediaType.APPLICATION_JSON)
                .param("clientId", notificationData.clientId.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("notification/list",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        requestParameters(NotificationFieldDocs.listParams()))
                )
                .andDo(document("notification/list",
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        responseFields(NotificationFieldDocs.listResponse()))
                );
    }



}
