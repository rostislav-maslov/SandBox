package v1.client;

import org.bson.types.ObjectId;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.restdocs.request.RequestPartDescriptor;
import tech.maslov.sandbox.client.models.Sex;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;


public class ClientFieldDocs {
    public static ParameterDescriptor[] listParams() {
        ParameterDescriptor[] response = {
                RequestDocumentation
                        .parameterWithName("skip")
                        .description("Сколько записей нужно пропустить, прежде чем отдать выдачу"),
                RequestDocumentation
                        .parameterWithName("size")
                        .description("Сколько записей нужно отдать за раз")
        };

        return response;
    }

    public static FieldDescriptor[] listResponse() {
        FieldDescriptor[] response = {

                fieldWithPath("result")
                        .description("Результат ответа"),
                fieldWithPath("result.total")
                        .optional()
                        .type(Long.class)
                        .description("Сколько всего элементов в коллекции"),
                fieldWithPath("result.items[].id")
                        .optional()
                        .type(ObjectId.class)
                        .description("ID клиента/гостя"),
                fieldWithPath("result.items[].firstName")
                        .optional()
                        .type(String.class)
                        .description("Имя"),
                fieldWithPath("result.items[].lastName")
                        .optional()
                        .type(String.class)
                        .description("Фамилия"),
                fieldWithPath("result.items[].phoneNumber")
                        .optional()
                        .type(Long.class)
                        .description("Номер телефона"),
                fieldWithPath("result.items[].bDate")
                        .optional()
                        .type(String.class)
                        .description("Дата рождения в формате yyyy-MM-dd"),
                fieldWithPath("result.items[].sex")
                        .type(String.class)
                        .description("Пол: " + Sex.NOT_SET + ", " + Sex.MALE + ", " + Sex.FEMALE + ", "),



                fieldWithPath("error")
                        .description("Инфомрация об ошибке запроса"),
                fieldWithPath("error.success")
                        .description("Флаг успешного запроса"),
                fieldWithPath("error.code")
                        .description("Код ошибки"),
                fieldWithPath("error.message")
                        .optional().type(String.class)
                        .description("Сообщение об ошибке")

        };

        return response;
    }

    public static RequestPartDescriptor[] getParams() {
        RequestPartDescriptor[] response = {
                RequestDocumentation
                        .partWithName("clientId")
                        .description("Client ID - айди клиента, которого мы хотим найти")
        };

        return response;
    }
    public static FieldDescriptor[] getResponse() {
        FieldDescriptor[] response = {

                PayloadDocumentation.fieldWithPath("result")
                        .description("Результат ответа"),
                PayloadDocumentation.fieldWithPath("result.id")
                        .type(ObjectId.class)
                        .description("ID Пользователя"),
                PayloadDocumentation.fieldWithPath("result.firstName")
                        .type(String.class)
                        .description("Имя"),
                PayloadDocumentation.fieldWithPath("result.lastName")
                        .type(String.class)
                        .description("Фамилия"),
                PayloadDocumentation.fieldWithPath("result.bDate")
                        .type(String.class)
                        .description("Дата Рождения: в формате yyyy-MM-dd"),
                PayloadDocumentation.fieldWithPath("result.sex")
                        .type(String.class)
                        .description("Пол: " + Sex.NOT_SET + ", " + Sex.MALE + ", " + Sex.FEMALE + ", "),
                PayloadDocumentation.fieldWithPath("result.phoneNumber")
                        .type(Long.class)
                        .description("Номер телефона. Только цифры"),

                PayloadDocumentation.fieldWithPath("error")
                        .description("Инфомрация об ошибке запроса"),
                PayloadDocumentation.fieldWithPath("error.success")
                        .description("Флаг успешного запроса"),
                PayloadDocumentation.fieldWithPath("error.code")
                        .description("Код ошибки"),
                PayloadDocumentation.fieldWithPath("error.message")
                        .optional().type(String.class)
                        .description("Сообщение об ошибке")};

        return response;
    }

    public static FieldDescriptor[] loginParams() {
        FieldDescriptor[] response = {
                PayloadDocumentation
                        .fieldWithPath("phoneNumber")
                        .description("Номер телефона. Только цифры"),
                PayloadDocumentation
                        .fieldWithPath("code")
                        .description("Код \"SMS\" - всегда последние 4 цифры номера телефона"),
        };

        return response;
    }

    public static FieldDescriptor[] loginResponse() {
        FieldDescriptor[] response = {

                PayloadDocumentation.fieldWithPath("result")
                        .description("Результат ответа"),
                PayloadDocumentation.fieldWithPath("result.id")
                        .type(ObjectId.class)
                        .description("ID Пользователя"),
                PayloadDocumentation.fieldWithPath("result.accessToken")
                        .type(String.class)
                        .description("Ключ доступа, который передается в X-Auth-Token в запросах"),

                PayloadDocumentation.fieldWithPath("error")
                        .description("Инфомрация об ошибке запроса"),
                PayloadDocumentation.fieldWithPath("error.success")
                        .description("Флаг успешного запроса"),
                PayloadDocumentation.fieldWithPath("error.code")
                        .description("Код ошибки"),
                PayloadDocumentation.fieldWithPath("error.message")
                        .optional().type(String.class)
                        .description("Сообщение об ошибке")};

        return response;
    }
}
