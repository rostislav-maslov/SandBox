package v1;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
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

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@ContextConfiguration("/mvc-dispatcher-servlet.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CategoryApiDocumentation {

    @Autowired
    private WebApplicationContext context;

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    private MockMvc mockMvc;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryApiService categoryApiService;


    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();

        initCategories();
    }

    public void initCategories() {
        initCategory("Роллы", "Вкусные Роллы", null, 0);
        initCategory("Суши", "Вкусные Суши", null, 0);
        CategoryDoc categoryDoc = initCategory("Еще", "Остальные блюда", null, 0);
        initCategory("Десерт", "Вкусные Десерты", categoryDoc.getId(), 0);
        initCategory("Салаты", "Вкусные Салаты", categoryDoc.getId(), 0);
    }

    public CategoryDoc initCategory(String title, String description, ObjectId parentId, Integer sortNumber) {
        CategoryDoc categoryDoc = new CategoryDoc();
        categoryDoc.setTitle(title);
        categoryDoc.setDescription(description);
        categoryDoc.setParentId(parentId);
        categoryDoc.setSortNum(sortNumber);
        categoryDoc.setPicId(new ObjectId());
        return categoryService.save(categoryDoc);
    }

    @Test
    public void testStreetSuggestions() throws Exception {
        mockMvc.perform(get(CategoryApiRoutes.ALL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("category/all",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        requestParameters(
                        ))
                )
                .andDo(document("category/all",
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
                                fieldWithPath("result.items[].parentId")
                                        .optional()
                                        .type(ObjectId.class)
                                        .description("ID родительской категории"),
                                fieldWithPath("result.items[].sortNum")
                                        .optional()
                                        .type(Integer.class)
                                        .description("Порядковый номер"),
                                fieldWithPath("result.items[].available")
                                        .optional()
                                        .type(Boolean.class)
                                        .description("Активная категория или нет"),


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
