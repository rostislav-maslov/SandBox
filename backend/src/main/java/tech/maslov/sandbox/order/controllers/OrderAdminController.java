package tech.maslov.sandbox.order.controllers;

import com.ub.core.base.utils.RouteUtils;
import com.ub.core.base.views.breadcrumbs.BreadcrumbsLink;
import com.ub.core.base.views.pageHeader.PageHeader;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tech.maslov.sandbox.courier.models.CourierDoc;
import tech.maslov.sandbox.courier.services.CourierService;
import tech.maslov.sandbox.order.models.DeliveryInfo;
import tech.maslov.sandbox.order.models.OrderDoc;
import tech.maslov.sandbox.order.routes.OrderAdminRoutes;
import tech.maslov.sandbox.order.services.OrderService;
import tech.maslov.sandbox.order.views.all.SearchOrderAdminRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OrderAdminController {

    @Autowired
    private OrderService orderService;

    @Autowired private CourierService courierService;

    private PageHeader defaultPageHeader(String current) {
        PageHeader pageHeader = PageHeader.defaultPageHeader();
        pageHeader.setLinkAdd(OrderAdminRoutes.ADD);
        pageHeader.getBreadcrumbs().getLinks().add(new BreadcrumbsLink(OrderAdminRoutes.ALL, "Все Заказ"));
        pageHeader.getBreadcrumbs().setCurrentPageTitle(current);
        return pageHeader;
    }

    @RequestMapping(value = OrderAdminRoutes.EDIT, method = RequestMethod.GET)
    public String update(@RequestParam ObjectId id, Model model) {
        OrderDoc doc = orderService.findById(id);

        Map<ObjectId, CourierDoc> couriers = new HashMap<>();
        List<CourierDoc> courierDocList =courierService.findAll(1000, 0);

        for(CourierDoc courierDoc : courierDocList){
            couriers.put(courierDoc.getId(), courierDoc);
        }


        model.addAttribute("doc", doc);
        model.addAttribute("couriers", couriers);
        model.addAttribute("pageHeader", defaultPageHeader("Редактирование"));
        return "tech.maslov.sandbox.admin.order.edit";
    }

    @RequestMapping(value = OrderAdminRoutes.EDIT, method = RequestMethod.POST)
    public String update(@RequestParam ObjectId id,
                         @RequestParam(required = false) DeliveryInfo.STATUS status,
                         @RequestParam(required = false) ObjectId courierId,
                         RedirectAttributes ra) {
        OrderDoc doc = orderService.findById(id);

        if (status != null)
            doc.getDeliveryInfo().setStatus(status);
        if (courierId != null)
            doc.getDeliveryInfo().setCourierId(courierId);

        orderService.save(doc);

        ra.addAttribute("id", id);
        return RouteUtils.redirectTo(OrderAdminRoutes.EDIT);
    }

    @RequestMapping(value = OrderAdminRoutes.ALL, method = RequestMethod.GET)
    public String all(@RequestParam(required = false, defaultValue = "0") Integer currentPage,
                      @RequestParam(required = false, defaultValue = "") String query,
                      Model model) {
        SearchOrderAdminRequest searchRequest = new SearchOrderAdminRequest(currentPage);
        searchRequest.setQuery(query);
        model.addAttribute("search", orderService.findAll(searchRequest));
        model.addAttribute("pageHeader", defaultPageHeader("Все"));
        return "tech.maslov.sandbox.admin.order.all";
    }

    @RequestMapping(value = OrderAdminRoutes.MODAL_PARENT, method = RequestMethod.GET)
    public String modalResponse(@RequestParam(required = false, defaultValue = "0") Integer currentPage,
                                @RequestParam(required = false, defaultValue = "") String query,
                                Model model) {
        SearchOrderAdminRequest searchRequest = new SearchOrderAdminRequest(currentPage);
        searchRequest.setQuery(query);
        model.addAttribute("search", orderService.findAll(searchRequest));
        return "tech.maslov.sandbox.admin.order.modal.parent";
    }

    @RequestMapping(value = OrderAdminRoutes.DELETE, method = RequestMethod.GET)
    public String delete(@RequestParam ObjectId id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("pageHeader", defaultPageHeader("Удаление"));
        return "tech.maslov.sandbox.admin.order.delete";
    }

    @RequestMapping(value = OrderAdminRoutes.DELETE, method = RequestMethod.POST)
    public String delete(@RequestParam ObjectId id) {
        orderService.remove(id);
        return RouteUtils.redirectTo(OrderAdminRoutes.ALL);
    }
}
