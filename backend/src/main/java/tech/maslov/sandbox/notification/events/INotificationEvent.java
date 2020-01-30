package tech.maslov.sandbox.notification.events;

import tech.maslov.sandbox.notification.models.NotificationDoc;

public interface INotificationEvent {
    public void preSave(NotificationDoc doc);
    public void afterSave(NotificationDoc doc);

    public Boolean preDelete(NotificationDoc doc);
    public void afterDelete(NotificationDoc doc);
}
