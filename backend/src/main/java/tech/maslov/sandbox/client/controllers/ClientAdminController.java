package tech.maslov.sandbox.client.controllers;

import com.mongodb.gridfs.GridFSDBFile;
import tech.maslov.sandbox.client.models.ClientDoc;
import tech.maslov.sandbox.client.routes.ClientAdminRoutes;
import tech.maslov.sandbox.client.services.ClientService;
import tech.maslov.sandbox.client.views.all.SearchClientAdminRequest;
import tech.maslov.sandbox.client.views.all.SearchClientAdminResponse;
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
public class ClientAdminController {

    @Autowired private ClientService clientService;

    private PageHeader defaultPageHeader(String current) {
        PageHeader pageHeader = PageHeader.defaultPageHeader();
        pageHeader.setLinkAdd(ClientAdminRoutes.ADD);
        pageHeader.getBreadcrumbs().getLinks().add(new BreadcrumbsLink(ClientAdminRoutes.ALL, "Все Клиент"));
        pageHeader.getBreadcrumbs().setCurrentPageTitle(current);
        return pageHeader;
    }

//    @RequestMapping(value = ClientAdminRoutes.ADD, method = RequestMethod.GET)
//    public String create(Model model) {
//        ClientDoc doc = new ClientDoc();
//        doc.setId(new ObjectId());
//        model.addAttribute("doc", doc);
//        model.addAttribute("pageHeader", defaultPageHeader("Добавление"));
//        return "tech.maslov.sandbox.admin.client.add";
//    }
//
//    @RequestMapping(value = ClientAdminRoutes.ADD, method = RequestMethod.POST)
//    public String create(@ModelAttribute("doc") ClientDoc doc,
//                         RedirectAttributes redirectAttributes) {
//        clientService.save(doc);
//        redirectAttributes.addAttribute("id", doc.getId());
//        return RouteUtils.redirectTo(ClientAdminRoutes.EDIT);
//    }

    @RequestMapping(value = ClientAdminRoutes.EDIT, method = RequestMethod.GET)
    public String update(@RequestParam ObjectId id, Model model) {
        ClientDoc doc = clientService.findById(id);
        model.addAttribute("doc", doc);
        model.addAttribute("pageHeader", defaultPageHeader("Редактирование"));
        return "tech.maslov.sandbox.admin.client.edit";
    }

//    @RequestMapping(value = ClientAdminRoutes.EDIT, method = RequestMethod.POST)
//    public String update(@ModelAttribute("doc") ClientDoc doc,
//                         RedirectAttributes redirectAttributes) {
//        clientService.save(doc);
//        redirectAttributes.addAttribute("id", doc.getId());
//        return RouteUtils.redirectTo(ClientAdminRoutes.EDIT);
//    }

    @RequestMapping(value = ClientAdminRoutes.ALL, method = RequestMethod.GET)
    public String all(@RequestParam(required = false, defaultValue = "0") Integer currentPage,
                      @RequestParam(required = false, defaultValue = "") String query,
                      Model model) {
        SearchClientAdminRequest searchRequest = new SearchClientAdminRequest(currentPage);
        searchRequest.setQuery(query);
        model.addAttribute("search", clientService.findAll(searchRequest));
        model.addAttribute("pageHeader", defaultPageHeader("Все"));
        return "tech.maslov.sandbox.admin.client.all";
    }

    @RequestMapping(value = ClientAdminRoutes.MODAL_PARENT, method = RequestMethod.GET)
    public String modalResponse(@RequestParam(required = false, defaultValue = "0") Integer currentPage,
                                @RequestParam(required = false, defaultValue = "") String query,
                                Model model) {
        SearchClientAdminRequest searchRequest = new SearchClientAdminRequest(currentPage);
        searchRequest.setQuery(query);
        model.addAttribute("search", clientService.findAll(searchRequest));
        return "tech.maslov.sandbox.admin.client.modal.parent";
    }

    @RequestMapping(value = ClientAdminRoutes.DELETE, method = RequestMethod.GET)
    public String delete(@RequestParam ObjectId id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("pageHeader", defaultPageHeader("Удаление"));
        return "tech.maslov.sandbox.admin.client.delete";
    }

    @RequestMapping(value = ClientAdminRoutes.DELETE, method = RequestMethod.POST)
    public String delete(@RequestParam ObjectId id) {
        clientService.remove(id);
        return RouteUtils.redirectTo(ClientAdminRoutes.ALL);
    }
}
