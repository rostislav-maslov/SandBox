package tech.maslov.sandbox.product.events;

import tech.maslov.sandbox.product.models.ProductDoc;

public interface IProductEvent {
    public void preSave(ProductDoc doc);
    public void afterSave(ProductDoc doc);

    public Boolean preDelete(ProductDoc doc);
    public void afterDelete(ProductDoc doc);
}
