package tech.maslov.sandbox.order.controllers;

import com.mongodb.gridfs.GridFSDBFile;
import tech.maslov.sandbox.order.models.OrderDoc;
import tech.maslov.sandbox.order.routes.OrderAdminRoutes;
import tech.maslov.sandbox.order.services.OrderService;
import tech.maslov.sandbox.order.views.all.SearchOrderAdminRequest;
import tech.maslov.sandbox.order.views.all.SearchOrderAdminResponse;
import com.ub.core.base.utils.RouteUtils;
import com.ub.core.file.services.FileService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.ub.core.base.views.breadcrumbs.BreadcrumbsLink;
import com.ub.core.base.views.pageHeader.PageHeader;

@Controller
public class OrderAdminController {

    @Autowired private OrderService orderService;

    private PageHeader defaultPageHeader(String current) {
        PageHeader pageHeader = PageHeader.defaultPageHeader();
        pageHeader.setLinkAdd(OrderAdminRoutes.ADD);
        pageHeader.getBreadcrumbs().getLinks().add(new BreadcrumbsLink(OrderAdminRoutes.ALL, "Все Заказ"));
        pageHeader.getBreadcrumbs().setCurrentPageTitle(current);
        return pageHeader;
    }

    @RequestMapping(value = OrderAdminRoutes.ADD, method = RequestMethod.GET)
    public String create(Model model) {
        OrderDoc doc = new OrderDoc();
        doc.setId(new ObjectId());
        model.addAttribute("doc", doc);
        model.addAttribute("pageHeader", defaultPageHeader("Добавление"));
        return "tech.maslov.sandbox.admin.order.add";
    }

    @RequestMapping(value = OrderAdminRoutes.ADD, method = RequestMethod.POST)
    public String create(@ModelAttribute("doc") OrderDoc doc,
                         RedirectAttributes redirectAttributes) {
        orderService.save(doc);
        redirectAttributes.addAttribute("id", doc.getId());
        return RouteUtils.redirectTo(OrderAdminRoutes.EDIT);
    }

    @RequestMapping(value = OrderAdminRoutes.EDIT, method = RequestMethod.GET)
    public String update(@RequestParam ObjectId id, Model model) {
        OrderDoc doc = orderService.findById(id);
        model.addAttribute("doc", doc);
        model.addAttribute("pageHeader", defaultPageHeader("Редактирование"));
        return "tech.maslov.sandbox.admin.order.edit";
    }

    @RequestMapping(value = OrderAdminRoutes.EDIT, method = RequestMethod.POST)
    public String update(@ModelAttribute("doc") OrderDoc doc,
                         RedirectAttributes redirectAttributes) {
        orderService.save(doc);
        redirectAttributes.addAttribute("id", doc.getId());
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
