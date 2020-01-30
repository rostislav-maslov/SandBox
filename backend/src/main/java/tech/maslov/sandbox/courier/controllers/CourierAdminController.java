package tech.maslov.sandbox.courier.controllers;

import com.ub.core.base.utils.RouteUtils;
import com.ub.core.base.views.breadcrumbs.BreadcrumbsLink;
import com.ub.core.base.views.pageHeader.PageHeader;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tech.maslov.sandbox.courier.models.CourierDoc;
import tech.maslov.sandbox.courier.routes.CourierAdminRoutes;
import tech.maslov.sandbox.courier.services.CourierService;
import tech.maslov.sandbox.courier.views.all.SearchCourierAdminRequest;
import tech.maslov.sandbox.order.models.OrderDoc;
import tech.maslov.sandbox.order.services.OrderService;

import java.util.List;

@Controller
public class CourierAdminController {

    @Autowired
    private CourierService courierService;
    @Autowired
    private OrderService orderService;

    private PageHeader defaultPageHeader(String current) {
        PageHeader pageHeader = PageHeader.defaultPageHeader();
        pageHeader.setLinkAdd(CourierAdminRoutes.ADD);
        pageHeader.getBreadcrumbs().getLinks().add(new BreadcrumbsLink(CourierAdminRoutes.ALL, "Все Курьер"));
        pageHeader.getBreadcrumbs().setCurrentPageTitle(current);
        return pageHeader;
    }

    @RequestMapping(value = CourierAdminRoutes.ADD, method = RequestMethod.GET)
    public String create(Model model) {
        CourierDoc doc = new CourierDoc();
        doc.setId(new ObjectId());
        model.addAttribute("doc", doc);
        model.addAttribute("pageHeader", defaultPageHeader("Добавление"));
        return "tech.maslov.sandbox.admin.courier.add";
    }

    @RequestMapping(value = CourierAdminRoutes.ADD, method = RequestMethod.POST)
    public String create(@ModelAttribute("doc") CourierDoc doc,
                         RedirectAttributes redirectAttributes) {
        courierService.save(doc);
        redirectAttributes.addAttribute("id", doc.getId());
        return RouteUtils.redirectTo(CourierAdminRoutes.EDIT);
    }

    @RequestMapping(value = CourierAdminRoutes.EDIT, method = RequestMethod.GET)
    public String update(@RequestParam ObjectId id, Model model) {
        CourierDoc doc = courierService.findById(id);

        List<OrderDoc> orderDocs = orderService.findByCourier(id);

        model.addAttribute("doc", doc);
        model.addAttribute("orders", orderDocs);
        model.addAttribute("pageHeader", defaultPageHeader("Редактирование"));
        return "tech.maslov.sandbox.admin.courier.edit";
    }

    @RequestMapping(value = CourierAdminRoutes.EDIT, method = RequestMethod.POST)
    public String update(@ModelAttribute("doc") CourierDoc doc,
                         RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("id", doc.getId());
        return RouteUtils.redirectTo(CourierAdminRoutes.EDIT);
    }

    @RequestMapping(value = CourierAdminRoutes.ALL, method = RequestMethod.GET)
    public String all(@RequestParam(required = false, defaultValue = "0") Integer currentPage,
                      @RequestParam(required = false, defaultValue = "") String query,
                      Model model) {
        SearchCourierAdminRequest searchRequest = new SearchCourierAdminRequest(currentPage);
        searchRequest.setQuery(query);
        model.addAttribute("search", courierService.findAll(searchRequest));
        model.addAttribute("pageHeader", defaultPageHeader("Все"));
        return "tech.maslov.sandbox.admin.courier.all";
    }

    @RequestMapping(value = CourierAdminRoutes.MODAL_PARENT, method = RequestMethod.GET)
    public String modalResponse(@RequestParam(required = false, defaultValue = "0") Integer currentPage,
                                @RequestParam(required = false, defaultValue = "") String query,
                                Model model) {
        SearchCourierAdminRequest searchRequest = new SearchCourierAdminRequest(currentPage);
        searchRequest.setQuery(query);
        model.addAttribute("search", courierService.findAll(searchRequest));
        return "tech.maslov.sandbox.admin.courier.modal.parent";
    }

    @RequestMapping(value = CourierAdminRoutes.DELETE, method = RequestMethod.GET)
    public String delete(@RequestParam ObjectId id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("pageHeader", defaultPageHeader("Удаление"));
        return "tech.maslov.sandbox.admin.courier.delete";
    }

    @RequestMapping(value = CourierAdminRoutes.DELETE, method = RequestMethod.POST)
    public String delete(@RequestParam ObjectId id) {
        courierService.remove(id);
        return RouteUtils.redirectTo(CourierAdminRoutes.ALL);
    }
}
