package tech.maslov.sandbox.restaurant.controllers;

import com.mongodb.gridfs.GridFSDBFile;
import com.ub.core.picture.services.PictureService;
import tech.maslov.sandbox.base.models.Point;
import tech.maslov.sandbox.restaurant.models.RestaurantDoc;
import tech.maslov.sandbox.restaurant.routes.RestaurantAdminRoutes;
import tech.maslov.sandbox.restaurant.services.RestaurantService;
import tech.maslov.sandbox.restaurant.views.all.SearchRestaurantAdminRequest;
import tech.maslov.sandbox.restaurant.views.all.SearchRestaurantAdminResponse;
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
public class RestaurantAdminController {

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private PictureService pictureService;

    private PageHeader defaultPageHeader(String current) {
        PageHeader pageHeader = PageHeader.defaultPageHeader();
        pageHeader.setLinkAdd(RestaurantAdminRoutes.ADD);
        pageHeader.getBreadcrumbs().getLinks().add(new BreadcrumbsLink(RestaurantAdminRoutes.ALL, "Все Рестораны"));
        pageHeader.getBreadcrumbs().setCurrentPageTitle(current);
        return pageHeader;
    }

    @RequestMapping(value = RestaurantAdminRoutes.ADD, method = RequestMethod.GET)
    public String create(Model model) {
        RestaurantDoc doc = new RestaurantDoc();
        doc.setId(new ObjectId());
        model.addAttribute("doc", doc);
        model.addAttribute("pageHeader", defaultPageHeader("Добавление"));
        return "tech.maslov.sandbox.admin.restaurant.add";
    }

    @RequestMapping(value = RestaurantAdminRoutes.ADD, method = RequestMethod.POST)
    public String create(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) MultipartFile picFile,
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false, value = "point.latitude") Double latitude,
            @RequestParam(required = false, value = "point.longitude") Double longitude,
            RedirectAttributes redirectAttributes) {
        RestaurantDoc doc = new RestaurantDoc();

        doc.setTitle(title);
        doc.setDescription(description);
        doc.setPicId(pictureService.saveWithDelete(picFile, null));
        doc.setAvailable(available);
        doc.getPoint().setLongitude(longitude);
        doc.getPoint().setLatitude(latitude);

        restaurantService.save(doc);
        redirectAttributes.addAttribute("id", doc.getId());
        return RouteUtils.redirectTo(RestaurantAdminRoutes.EDIT);
    }

    @RequestMapping(value = RestaurantAdminRoutes.EDIT, method = RequestMethod.GET)
    public String update(@RequestParam ObjectId id, Model model) {
        RestaurantDoc doc = restaurantService.findById(id);
        model.addAttribute("doc", doc);
        model.addAttribute("pageHeader", defaultPageHeader("Редактирование"));
        return "tech.maslov.sandbox.admin.restaurant.edit";
    }

    @RequestMapping(value = RestaurantAdminRoutes.EDIT, method = RequestMethod.POST)
    public String update(
            @RequestParam(required = false) ObjectId id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) MultipartFile picFile,
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false, value = "point.latitude") Double latitude,
            @RequestParam(required = false, value = "point.longitude") Double longitude,

                         RedirectAttributes redirectAttributes) {
        RestaurantDoc doc = restaurantService.findById(id);

        doc.setTitle(title);
        doc.setDescription(description);
        doc.setPicId(pictureService.saveWithDelete(picFile, null));
        doc.setAvailable(available);
        doc.getPoint().setLongitude(longitude);
        doc.getPoint().setLatitude(latitude);

        restaurantService.save(doc);
        redirectAttributes.addAttribute("id", doc.getId());
        return RouteUtils.redirectTo(RestaurantAdminRoutes.EDIT);
    }

    @RequestMapping(value = RestaurantAdminRoutes.ALL, method = RequestMethod.GET)
    public String all(@RequestParam(required = false, defaultValue = "0") Integer currentPage,
                      @RequestParam(required = false, defaultValue = "") String query,
                      Model model) {
        SearchRestaurantAdminRequest searchRequest = new SearchRestaurantAdminRequest(currentPage);
        searchRequest.setQuery(query);
        model.addAttribute("search", restaurantService.findAll(searchRequest));
        model.addAttribute("pageHeader", defaultPageHeader("Все"));
        return "tech.maslov.sandbox.admin.restaurant.all";
    }

    @RequestMapping(value = RestaurantAdminRoutes.MODAL_PARENT, method = RequestMethod.GET)
    public String modalResponse(@RequestParam(required = false, defaultValue = "0") Integer currentPage,
                                @RequestParam(required = false, defaultValue = "") String query,
                                Model model) {
        SearchRestaurantAdminRequest searchRequest = new SearchRestaurantAdminRequest(currentPage);
        searchRequest.setQuery(query);
        model.addAttribute("search", restaurantService.findAll(searchRequest));
        return "tech.maslov.sandbox.admin.restaurant.modal.parent";
    }

    @RequestMapping(value = RestaurantAdminRoutes.DELETE, method = RequestMethod.GET)
    public String delete(@RequestParam ObjectId id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("pageHeader", defaultPageHeader("Удаление"));
        return "tech.maslov.sandbox.admin.restaurant.delete";
    }

    @RequestMapping(value = RestaurantAdminRoutes.DELETE, method = RequestMethod.POST)
    public String delete(@RequestParam ObjectId id) {
        restaurantService.remove(id);
        return RouteUtils.redirectTo(RestaurantAdminRoutes.ALL);
    }
}
