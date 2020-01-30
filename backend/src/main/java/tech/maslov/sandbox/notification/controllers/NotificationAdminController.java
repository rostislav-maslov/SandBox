package tech.maslov.sandbox.notification.controllers;

import com.mongodb.gridfs.GridFSDBFile;
import tech.maslov.sandbox.notification.models.NotificationDoc;
import tech.maslov.sandbox.notification.routes.NotificationAdminRoutes;
import tech.maslov.sandbox.notification.services.NotificationService;
import tech.maslov.sandbox.notification.views.all.SearchNotificationAdminRequest;
import tech.maslov.sandbox.notification.views.all.SearchNotificationAdminResponse;
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
public class NotificationAdminController {

    @Autowired private NotificationService notificationService;

    private PageHeader defaultPageHeader(String current) {
        PageHeader pageHeader = PageHeader.defaultPageHeader();
        pageHeader.setLinkAdd(NotificationAdminRoutes.ADD);
        pageHeader.getBreadcrumbs().getLinks().add(new BreadcrumbsLink(NotificationAdminRoutes.ALL, "Все Уведомление"));
        pageHeader.getBreadcrumbs().setCurrentPageTitle(current);
        return pageHeader;
    }

    @RequestMapping(value = NotificationAdminRoutes.ADD, method = RequestMethod.GET)
    public String create(Model model) {
        NotificationDoc doc = new NotificationDoc();
        doc.setId(new ObjectId());
        model.addAttribute("doc", doc);
        model.addAttribute("pageHeader", defaultPageHeader("Добавление"));
        return "tech.maslov.sandbox.admin.notification.add";
    }

    @RequestMapping(value = NotificationAdminRoutes.ADD, method = RequestMethod.POST)
    public String create(@ModelAttribute("doc") NotificationDoc doc,
                         RedirectAttributes redirectAttributes) {
        notificationService.save(doc);
        redirectAttributes.addAttribute("id", doc.getId());
        return RouteUtils.redirectTo(NotificationAdminRoutes.EDIT);
    }

    @RequestMapping(value = NotificationAdminRoutes.EDIT, method = RequestMethod.GET)
    public String update(@RequestParam ObjectId id, Model model) {
        NotificationDoc doc = notificationService.findById(id);
        model.addAttribute("doc", doc);
        model.addAttribute("pageHeader", defaultPageHeader("Редактирование"));
        return "tech.maslov.sandbox.admin.notification.edit";
    }

    @RequestMapping(value = NotificationAdminRoutes.EDIT, method = RequestMethod.POST)
    public String update(@ModelAttribute("doc") NotificationDoc doc,
                         RedirectAttributes redirectAttributes) {
        notificationService.save(doc);
        redirectAttributes.addAttribute("id", doc.getId());
        return RouteUtils.redirectTo(NotificationAdminRoutes.EDIT);
    }

    @RequestMapping(value = NotificationAdminRoutes.ALL, method = RequestMethod.GET)
    public String all(@RequestParam(required = false, defaultValue = "0") Integer currentPage,
                      @RequestParam(required = false, defaultValue = "") String query,
                      Model model) {
        SearchNotificationAdminRequest searchRequest = new SearchNotificationAdminRequest(currentPage);
        searchRequest.setQuery(query);
        model.addAttribute("search", notificationService.findAll(searchRequest));
        model.addAttribute("pageHeader", defaultPageHeader("Все"));
        return "tech.maslov.sandbox.admin.notification.all";
    }

    @RequestMapping(value = NotificationAdminRoutes.MODAL_PARENT, method = RequestMethod.GET)
    public String modalResponse(@RequestParam(required = false, defaultValue = "0") Integer currentPage,
                                @RequestParam(required = false, defaultValue = "") String query,
                                Model model) {
        SearchNotificationAdminRequest searchRequest = new SearchNotificationAdminRequest(currentPage);
        searchRequest.setQuery(query);
        model.addAttribute("search", notificationService.findAll(searchRequest));
        return "tech.maslov.sandbox.admin.notification.modal.parent";
    }

    @RequestMapping(value = NotificationAdminRoutes.DELETE, method = RequestMethod.GET)
    public String delete(@RequestParam ObjectId id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("pageHeader", defaultPageHeader("Удаление"));
        return "tech.maslov.sandbox.admin.notification.delete";
    }

    @RequestMapping(value = NotificationAdminRoutes.DELETE, method = RequestMethod.POST)
    public String delete(@RequestParam ObjectId id) {
        notificationService.remove(id);
        return RouteUtils.redirectTo(NotificationAdminRoutes.ALL);
    }
}
