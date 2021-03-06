package v1.product;

import org.bson.types.ObjectId;
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
import tech.maslov.sandbox.category.models.CategoryDoc;
import tech.maslov.sandbox.category.routes.CategoryApiRoutes;
import tech.maslov.sandbox.category.services.CategoryApiService;
import tech.maslov.sandbox.category.services.CategoryService;
import tech.maslov.sandbox.product.api.response.ProductApiResponse;
import tech.maslov.sandbox.product.models.ProductDoc;
import tech.maslov.sandbox.product.routes.ProductApiRoutes;
import tech.maslov.sandbox.product.services.ProductApiService;
import tech.maslov.sandbox.product.services.ProductService;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@ContextConfiguration("/mvc-dispatcher-servlet.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductApiDocumentation {

    @Autowired
    private WebApplicationContext context;

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductApiService productApiService;


    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();

        ProductData.instance(productService).init();
    }

    @Test
    public void testAll() throws Exception {
        mockMvc.perform(get(ProductApiRoutes.ALL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("product/all",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        requestParameters(
                        ))
                )
                .andDo(document("product/all",
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        responseFields(
                                fieldWithPath("result")
                                        .description("Результат ответа"),
                                fieldWithPath("result.total")
                                        .optional()
                                        .type(Long.class)
                                        .description("Сколько всего элементов в коллекции"),
                                fieldWithPath("result.items[].id")
                                        .optional()
                                        .type(ObjectId.class)
                                        .description("ID категории"),
                                fieldWithPath("result.items[].title")
                                        .optional()
                                        .type(String.class)
                                        .description("Название категории"),
                                fieldWithPath("result.items[].description")
                                        .optional()
                                        .type(String.class)
                                        .description("Описание категории"),
                                fieldWithPath("result.items[].picId")
                                        .optional()
                                        .type(ObjectId.class)
                                        .description("ID картинки категории; /pics/{picId}"),
                                fieldWithPath("result.items[].categoryId")
                                        .optional()
                                        .type(ObjectId.class)
                                        .description("ID категории"),
                                fieldWithPath("result.items[].price")
                                        .optional()
                                        .type(Double.class)
                                        .description("Цена товара"),
                                fieldWithPath("result.items[].available")
                                        .optional()
                                        .type(Boolean.class)
                                        .description("Активный товар или нет"),


                                fieldWithPath("error")
                                        .description("Инфомрация об ошибке запроса"),
                                fieldWithPath("error.success")
                                        .description("Флаг успешного запроса"),
                                fieldWithPath("error.code")
                                        .description("Код ошибки"),
                                fieldWithPath("error.message")
                                        .optional().type(String.class)
                                        .description("Сообщение об ошибке")
                        )));
    }


}
