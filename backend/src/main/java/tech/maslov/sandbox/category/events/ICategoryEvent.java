package tech.maslov.sandbox.category.events;

import tech.maslov.sandbox.category.models.CategoryDoc;

public interface ICategoryEvent {
    public void preSave(CategoryDoc doc);
    public void afterSave(CategoryDoc doc);

    public Boolean preDelete(CategoryDoc doc);
    public void afterDelete(CategoryDoc doc);
}
