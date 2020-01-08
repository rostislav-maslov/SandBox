package tech.maslov.sandbox.category.controllers;

import com.mongodb.gridfs.GridFSDBFile;
import com.ub.core.base.utils.ObjectIdUtils;
import com.ub.core.picture.services.PictureService;
import tech.maslov.sandbox.category.models.CategoryDoc;
import tech.maslov.sandbox.category.routes.CategoryAdminRoutes;
import tech.maslov.sandbox.category.services.CategoryService;
import tech.maslov.sandbox.category.views.all.SearchCategoryAdminRequest;
import tech.maslov.sandbox.category.views.all.SearchCategoryAdminResponse;
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
public class CategoryAdminController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PictureService pictureService;

    private PageHeader defaultPageHeader(String current) {
        PageHeader pageHeader = PageHeader.defaultPageHeader();
        pageHeader.setLinkAdd(CategoryAdminRoutes.ADD);
        pageHeader.getBreadcrumbs().getLinks().add(new BreadcrumbsLink(CategoryAdminRoutes.ALL, "Все Категории"));
        pageHeader.getBreadcrumbs().setCurrentPageTitle(current);
        return pageHeader;
    }

    @RequestMapping(value = CategoryAdminRoutes.ADD, method = RequestMethod.GET)
    public String create(Model model) {
        CategoryDoc doc = new CategoryDoc();
        model.addAttribute("doc", doc);
        model.addAttribute("pageHeader", defaultPageHeader("Добавление"));
        model.addAttribute("categories", categoryService.findHashMap());
        return "tech.maslov.sandbox.admin.category.add";
    }

    @RequestMapping(value = CategoryAdminRoutes.ADD, method = RequestMethod.POST)
    public String create(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String parentId,
            @RequestParam Integer sortNum,
            @RequestParam Boolean available,

            @RequestParam(required = false) MultipartFile picFile,

            RedirectAttributes redirectAttributes) {
        CategoryDoc categoryDoc = new CategoryDoc();

        categoryDoc.setTitle(title);
        categoryDoc.setDescription(description);
        categoryDoc.setParentId(ObjectIdUtils.tryParseString(parentId));
        categoryDoc.setSortNum(sortNum);
        categoryDoc.setAvailable(available);

        categoryDoc.setPicId(pictureService.saveWithDelete(picFile, categoryDoc.getPicId()));

        categoryService.save(categoryDoc);
        redirectAttributes.addAttribute("id", categoryDoc.getId());
        return RouteUtils.redirectTo(CategoryAdminRoutes.EDIT);
    }

    @RequestMapping(value = CategoryAdminRoutes.EDIT, method = RequestMethod.GET)
    public String update(@RequestParam ObjectId id, Model model) {
        CategoryDoc doc = categoryService.findById(id);
        model.addAttribute("doc", doc);
        model.addAttribute("pageHeader", defaultPageHeader("Редактирование"));
        model.addAttribute("categories", categoryService.findHashMap(id));
        return "tech.maslov.sandbox.admin.category.edit";
    }

    @RequestMapping(value = CategoryAdminRoutes.EDIT, method = RequestMethod.POST)
    public String update(

            @RequestParam ObjectId id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String parentId,
            @RequestParam Integer sortNum,
            @RequestParam Boolean available,
            @RequestParam(required = false) MultipartFile picFile,

            RedirectAttributes redirectAttributes) {
        CategoryDoc categoryDoc = categoryService.findById(id);

        categoryDoc.setTitle(title);
        categoryDoc.setDescription(description);
        categoryDoc.setParentId(ObjectIdUtils.tryParseString(parentId));
        categoryDoc.setSortNum(sortNum);
        categoryDoc.setAvailable(available);

        categoryDoc.setPicId(pictureService.saveWithDelete(picFile, categoryDoc.getPicId()));

        categoryService.save(categoryDoc);
        redirectAttributes.addAttribute("id", categoryDoc.getId());
        return RouteUtils.redirectTo(CategoryAdminRoutes.EDIT);
    }

    @RequestMapping(value = CategoryAdminRoutes.ALL, method = RequestMethod.GET)
    public String all(@RequestParam(required = false, defaultValue = "0") Integer currentPage,
                      @RequestParam(required = false, defaultValue = "") String query,
                      Model model) {
        SearchCategoryAdminRequest searchRequest = new SearchCategoryAdminRequest(currentPage);
        searchRequest.setQuery(query);
        model.addAttribute("search", categoryService.findAll(searchRequest));
        model.addAttribute("pageHeader", defaultPageHeader("Все"));
        model.addAttribute("categories", categoryService.findHashMap());
        return "tech.maslov.sandbox.admin.category.all";
    }

    @RequestMapping(value = CategoryAdminRoutes.MODAL_PARENT, method = RequestMethod.GET)
    public String modalResponse(@RequestParam(required = false, defaultValue = "0") Integer currentPage,
                                @RequestParam(required = false, defaultValue = "") String query,
                                Model model) {
        SearchCategoryAdminRequest searchRequest = new SearchCategoryAdminRequest(currentPage);
        searchRequest.setQuery(query);
        model.addAttribute("search", categoryService.findAll(searchRequest));
        return "tech.maslov.sandbox.admin.category.modal.parent";
    }

    @RequestMapping(value = CategoryAdminRoutes.DELETE, method = RequestMethod.GET)
    public String delete(@RequestParam ObjectId id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("pageHeader", defaultPageHeader("Удаление"));
        return "tech.maslov.sandbox.admin.category.delete";
    }

    @RequestMapping(value = CategoryAdminRoutes.DELETE, method = RequestMethod.POST)
    public String delete(@RequestParam ObjectId id) {
        categoryService.remove(id);
        return RouteUtils.redirectTo(CategoryAdminRoutes.ALL);
    }
}
