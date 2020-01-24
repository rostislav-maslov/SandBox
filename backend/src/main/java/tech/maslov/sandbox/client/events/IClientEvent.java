package tech.maslov.sandbox.client.events;

import tech.maslov.sandbox.client.models.ClientDoc;

public interface IClientEvent {
    public void preSave(ClientDoc doc);
    public void afterSave(ClientDoc doc);

    public Boolean preDelete(ClientDoc doc);
    public void afterDelete(ClientDoc doc);
}
