package v1.order;

import org.bson.types.ObjectId;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.restdocs.request.RequestDocumentation;
import tech.maslov.sandbox.order.models.DeliveryInfo;
import tech.maslov.sandbox.order.models.PaymentInfo;
import v1.base.BaseFieldResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderFieldDocs {

    public static FieldDescriptor[] createParams() {
        FieldDescriptor[] response = {
                PayloadDocumentation
                        .fieldWithPath("clientInfo")
                        .description("Информация о госте"),
                PayloadDocumentation
                        .fieldWithPath("clientInfo.clientId")
                        .description("ID гостя"),
                PayloadDocumentation
                        .fieldWithPath("clientInfo.firstName")
                        .description("Имя"),
                PayloadDocumentation
                        .fieldWithPath("clientInfo.phoneNumber")
                        .type(Long.class)
                        .description("Телефон"),

                PayloadDocumentation
                        .fieldWithPath("basket")
                        .description("Корзина товаров"),
                PayloadDocumentation
                        .fieldWithPath("basket.products")
                        .type(List.class)
                        .description("Продукты"),
                PayloadDocumentation
                        .fieldWithPath("basket.products[].productId")
                        .type(ObjectId.class)
                        .description("ID продукта"),
                PayloadDocumentation
                        .fieldWithPath("basket.products[].count")
                        .type(Integer.class)
                        .description("Количество"),

                PayloadDocumentation
                        .fieldWithPath("deliveryInfo")
                        .description("Информация о доставке/самовывозе товара"),
                PayloadDocumentation
                        .fieldWithPath("deliveryInfo.type")
                        .type(DeliveryInfo.TYPE.class)
                        .description("Тип доставки: " + DeliveryInfo.TYPE.DELIVERY + " или " + DeliveryInfo.TYPE.PICKUP),
                PayloadDocumentation
                        .fieldWithPath("deliveryInfo.restaurantId")
                        .type(ObjectId.class)
                        .description("ID ресторана в случае самовывоза. Null или пустое поле если Delivery"),
                PayloadDocumentation
                        .fieldWithPath("deliveryInfo.address")
                        .optional()
                        .type(Object.class)
                        .description("Адрес"),
                PayloadDocumentation
                        .fieldWithPath("deliveryInfo.address.city")
                        .optional()
                        .type(String.class)
                        .description("Город"),
                PayloadDocumentation
                        .fieldWithPath("deliveryInfo.address.street")
                        .optional()
                        .type(String.class)
                        .description("Улица"),
                PayloadDocumentation
                        .fieldWithPath("deliveryInfo.address.house")
                        .optional()
                        .type(String.class)
                        .description("Дом"),
                PayloadDocumentation
                        .fieldWithPath("deliveryInfo.address.porch")
                        .optional()
                        .type(String.class)
                        .description("подъезд"),
                PayloadDocumentation
                        .fieldWithPath("deliveryInfo.address.floor")
                        .optional()
                        .type(String.class)
                        .description("этаж"),
                PayloadDocumentation
                        .fieldWithPath("deliveryInfo.address.apartment")
                        .optional()
                        .type(String.class)
                        .description("квартира"),
                PayloadDocumentation
                        .fieldWithPath("deliveryInfo.address.coordinates")
                        .optional()
                        .type(String.class)
                        .description("координаты"),
                PayloadDocumentation
                        .fieldWithPath("deliveryInfo.address.coordinates.longitude")
                        .optional()
                        .type(String.class)
                        .description("долгота"),
                PayloadDocumentation
                        .fieldWithPath("deliveryInfo.address.coordinates.latitude")
                        .optional()
                        .type(String.class)
                        .description("широта"),
                PayloadDocumentation
                        .fieldWithPath("deliveryInfo.address.coordinateAccuracy")
                        .optional()
                        .type(DeliveryInfo.CoordinateAccuracy.class)
                        .description(
                                DeliveryInfo.CoordinateAccuracy.EXACT + " - точные координаты," +
                                DeliveryInfo.CoordinateAccuracy.STREET + " - точность до улицы," +
                                DeliveryInfo.CoordinateAccuracy.DISTRICT + " - точность до района," +
                                DeliveryInfo.CoordinateAccuracy.CITY + " - точность до города," +
                                DeliveryInfo.CoordinateAccuracy.UNDEFINED + " - координаты не определены,"
                ),

                PayloadDocumentation
                        .fieldWithPath("paymentType")
                        .description("Информация об оплате"),
                PayloadDocumentation
                        .fieldWithPath("paymentType.type")
                        .type(PaymentInfo.TYPE.class)
                        .description("Тип оплаты: " +
                            PaymentInfo.TYPE.CASH + ", " +
                            PaymentInfo.TYPE.ONLINE + ", " +
                            PaymentInfo.TYPE.CARD_OFFLINE + ", "
                ),
                PayloadDocumentation
                        .fieldWithPath("paymentType.changeWith")
                        .type(Double.class)
                        .description("Сдача с"),

                PayloadDocumentation
                        .fieldWithPath("orderInfo")
                        .description("Информация о заказе"),
                PayloadDocumentation
                        .fieldWithPath("orderInfo.comment")
                        .description("Комментарий к заказу"),

        };

        return response;
    }

    public static FieldDescriptor[] orderResponse(String prefix) {
        FieldDescriptor[] response = {

                PayloadDocumentation.fieldWithPath(prefix + "id")
                        .type(ObjectId.class)
                        .description("ID заказа"),

                PayloadDocumentation.fieldWithPath(prefix + "clientInfo")
                        .description("Информация о клиенте"),
                PayloadDocumentation.fieldWithPath(prefix + "clientInfo.clientId")
                        .description("ID гостя"),
                PayloadDocumentation.fieldWithPath(prefix + "clientInfo.firstName")
                        .description("Имя"),
                PayloadDocumentation.fieldWithPath(prefix + "clientInfo.phoneNumber")
                        .description("Телефон"),

                PayloadDocumentation.fieldWithPath(prefix + "basket")
                        .description("Корзина"),
                PayloadDocumentation.fieldWithPath(prefix + "basket.totalPrice")
                        .description("Сумма товаров"),
                PayloadDocumentation.fieldWithPath(prefix + "basket.products")
                        .description("Товары"),
                PayloadDocumentation.fieldWithPath(prefix + "basket.products[].id")
                        .description("ID позиции внутри корзины"),
                PayloadDocumentation.fieldWithPath(prefix + "basket.products[].productId")
                        .description("ID товара"),
                PayloadDocumentation.fieldWithPath(prefix + "basket.products[].product")
                        .description("Информация о товаре"),
                PayloadDocumentation.fieldWithPath(prefix + "basket.products[].count")
                        .description("Количество товара"),
                PayloadDocumentation.fieldWithPath(prefix + "basket.products[].totalPrice")
                        .description("Сумма по конкретному товару"),

                PayloadDocumentation.fieldWithPath(prefix + "deliveryInfo")
                        .description("Доставка"),
                PayloadDocumentation.fieldWithPath(prefix + "deliveryInfo.type")
                        .description("Тип доставки"),
                PayloadDocumentation.fieldWithPath(prefix + "deliveryInfo.restaurantId")
                        .optional()
                        .description("ID ресторана, который готовит блюдо"),
                PayloadDocumentation.fieldWithPath(prefix + "deliveryInfo.currentPosition")
                        .optional()
                        .description("Текущая позиция заказа"),
                PayloadDocumentation.fieldWithPath(prefix + "deliveryInfo.address")
                        .optional()
                        .description("Адрес доставки"),
                PayloadDocumentation.fieldWithPath(prefix + "deliveryInfo.deliveryCost")
                        .optional()
                        .description("Стоиомость доставки"),
                PayloadDocumentation.fieldWithPath(prefix + "deliveryInfo.courierId")
                        .optional()
                        .description("ID курьера"),

                PayloadDocumentation.fieldWithPath(prefix + "paymentInfo")
                        .description("Оплата"),
                PayloadDocumentation.fieldWithPath(prefix + "paymentInfo.type")
                        .description("Тип оплаты"),
                PayloadDocumentation.fieldWithPath(prefix + "paymentInfo.status")
                        .description("Статус оплаты"),
                PayloadDocumentation.fieldWithPath(prefix + "paymentInfo.changeWith")
                        .description("Сдача с"),
                PayloadDocumentation.fieldWithPath(prefix + "paymentInfo.basketCost")
                        .description("Сумма товаров"),
                PayloadDocumentation.fieldWithPath(prefix + "paymentInfo.deliveryCost")
                        .description("Сумма доставки"),
                PayloadDocumentation.fieldWithPath(prefix + "paymentInfo.discountCost")
                        .description("Сумма скидки"),
                PayloadDocumentation.fieldWithPath(prefix + "paymentInfo.total")
                        .description("Всего"),
                PayloadDocumentation.fieldWithPath(prefix + "paymentInfo.payLink")
                        .description("Ссылка для онлайн оплаты"),

                PayloadDocumentation.fieldWithPath(prefix + "orderInfo")
                        .description("Информация о заказе"),
                PayloadDocumentation.fieldWithPath(prefix + "orderInfo.comment")
                        .description("Комментарий"),

              };

        return response;
    }

    public static FieldDescriptor[] createResponse() {
        List<FieldDescriptor> response = new ArrayList<>();

        response.add(PayloadDocumentation.fieldWithPath("result").description("Результат ответа"));
        response.addAll(Arrays.asList(orderResponse("result.")));
        response.addAll(Arrays.asList(BaseFieldResponse.error()));

        return response.toArray(new FieldDescriptor[response.size()]);
    }

    public static FieldDescriptor[] courierSetParams() {
        List<FieldDescriptor> response = new ArrayList<>();

        response.add(PayloadDocumentation.fieldWithPath("id").description("ID заказа"));
        response.add(PayloadDocumentation.fieldWithPath("courierId").description("ID курьера"));
        response.add(PayloadDocumentation.fieldWithPath("currentPosition").description("Текущая позиция курьера если есть"));
        response.add(PayloadDocumentation.fieldWithPath("currentPosition.longitude").description("longitude"));
        response.add(PayloadDocumentation.fieldWithPath("currentPosition.latitude").description("latitude"));
        response.add(PayloadDocumentation.fieldWithPath("status").description("Статус заказа. В случае если заказ еще на доставки, отсылаем " + DeliveryInfo.STATUS.ON_THE_WAY+". В случае если заказ доставлен, отсылаем - " + DeliveryInfo.STATUS.DELIVERED));

        return response.toArray(new FieldDescriptor[response.size()]);
    }

    public static FieldDescriptor[] courierNextParams() {
        List<FieldDescriptor> response = new ArrayList<>();

        response.add(PayloadDocumentation.fieldWithPath("courierId").description("ID курьера"));

        return response.toArray(new FieldDescriptor[response.size()]);
    }

    public static ParameterDescriptor[] courierListParams() {
        ParameterDescriptor[] response = {
                RequestDocumentation
                        .parameterWithName("skip")
                        .description("Сколько записей нужно пропустить, прежде чем отдать выдачу"),
                RequestDocumentation
                        .parameterWithName("size")
                        .description("Сколько записей нужно отдать за раз"),
                RequestDocumentation
                        .parameterWithName("courierId")
                        .description("ID Курьера")
        };

        return response;
    }

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
        List<FieldDescriptor> response = new ArrayList<>();

        response.add(PayloadDocumentation.fieldWithPath("result").description("Результат ответа"));

        response.addAll(Arrays.asList(orderResponse("result.items[].")));
        response.add( PayloadDocumentation.fieldWithPath("result.total")
                .description("Общее количество"));

        response.addAll(Arrays.asList(BaseFieldResponse.error()));

        return response.toArray(new FieldDescriptor[response.size()]);
    }
}
