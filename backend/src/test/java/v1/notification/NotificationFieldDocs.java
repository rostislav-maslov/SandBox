package v1.notification;

import org.bson.types.ObjectId;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.restdocs.request.RequestDocumentation;
import tech.maslov.sandbox.notification.models.NotificationDoc;
import tech.maslov.sandbox.order.models.DeliveryInfo;
import tech.maslov.sandbox.order.models.PaymentInfo;
import v1.base.BaseFieldResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NotificationFieldDocs {

    public static FieldDescriptor[] createParams() {
        FieldDescriptor[] response = {
                PayloadDocumentation
                        .fieldWithPath("clientId")
                        .description("ID клиента, которому отсылаем сообщение"),
                PayloadDocumentation
                        .fieldWithPath("orderId")
                        .optional()
                        .description("ID заказа, если сообщение связано с заказов"),
                PayloadDocumentation
                        .fieldWithPath("title")
                        .description("Заголовок push уведомления"),
                PayloadDocumentation
                        .fieldWithPath("description")
                        .description("Описание push уведомления"),
        };

        return response;
    }

    public static FieldDescriptor[] notificationResponse(String prefix) {
        FieldDescriptor[] response = {

                PayloadDocumentation.fieldWithPath(prefix + "id")
                        .type(ObjectId.class)
                        .description("ID уведомления"),

                PayloadDocumentation.fieldWithPath(prefix + "clientId")
                        .description("ID гостя"),
                PayloadDocumentation.fieldWithPath(prefix + "orderId")
                        .optional()
                        .description("ID заказа"),
                PayloadDocumentation.fieldWithPath(prefix + "title")
                        .description("Заголовок уведомления"),
                PayloadDocumentation.fieldWithPath(prefix + "description")
                        .description("Описание пуш уведомления"),

                PayloadDocumentation.fieldWithPath(prefix + "readStatus")
                        .description("Статус прочтения: " + NotificationDoc.STATUS.NOT_READ + " " + NotificationDoc.STATUS.READ),

        };

        return response;
    }

    public static FieldDescriptor[] createResponse() {
        List<FieldDescriptor> response = new ArrayList<>();

        response.add(PayloadDocumentation.fieldWithPath("result").description("Результат ответа"));
        response.addAll(Arrays.asList(notificationResponse("result.")));
        response.addAll(Arrays.asList(BaseFieldResponse.error()));

        return response.toArray(new FieldDescriptor[response.size()]);
    }

    public static ParameterDescriptor[] listParams() {
        ParameterDescriptor[] response = {
                RequestDocumentation
                        .parameterWithName("clientId")
                        .description("Сколько записей нужно пропустить, прежде чем отдать выдачу"),
        };

        return response;
    }

    public static FieldDescriptor[] listResponse() {
        List<FieldDescriptor> response = new ArrayList<>();

        response.add(PayloadDocumentation.fieldWithPath("result").description("Результат ответа"));
        response.addAll(Arrays.asList(notificationResponse("result[].")));
        response.addAll(Arrays.asList(BaseFieldResponse.error()));

        return response.toArray(new FieldDescriptor[response.size()]);
    }
}
