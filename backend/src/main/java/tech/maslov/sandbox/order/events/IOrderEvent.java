package tech.maslov.sandbox.order.events;

import tech.maslov.sandbox.order.models.OrderDoc;

public interface IOrderEvent {
    public void preSave(OrderDoc doc);
    public void afterSave(OrderDoc doc);

    public Boolean preDelete(OrderDoc doc);
    public void afterDelete(OrderDoc doc);
}
