package tech.maslov.sandbox.courier.events;

import tech.maslov.sandbox.courier.models.CourierDoc;

public interface ICourierEvent {
    public void preSave(CourierDoc doc);
    public void afterSave(CourierDoc doc);

    public Boolean preDelete(CourierDoc doc);
    public void afterDelete(CourierDoc doc);
}
