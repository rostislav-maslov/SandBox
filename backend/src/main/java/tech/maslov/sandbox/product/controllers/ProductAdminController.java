package tech.maslov.sandbox.product.controllers;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.ub.core.base.utils.RouteUtils;
import com.ub.core.base.views.breadcrumbs.BreadcrumbsLink;
import com.ub.core.base.views.pageHeader.PageHeader;
import com.ub.core.picture.services.PictureService;
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
import tech.maslov.sandbox.category.services.CategoryService;
import tech.maslov.sandbox.product.models.ProductDoc;
import tech.maslov.sandbox.product.routes.ProductAdminRoutes;
import tech.maslov.sandbox.product.services.ProductService;
import tech.maslov.sandbox.product.views.all.SearchProductAdminRequest;

@Controller
public class ProductAdminController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PictureService pictureService;

    private PageHeader defaultPageHeader(String current) {
        PageHeader pageHeader = PageHeader.defaultPageHeader();
        pageHeader.setLinkAdd(ProductAdminRoutes.ADD);
        pageHeader.getBreadcrumbs().getLinks().add(new BreadcrumbsLink(ProductAdminRoutes.ALL, "Все Товары"));
        pageHeader.getBreadcrumbs().setCurrentPageTitle(current);
        return pageHeader;
    }

    @RequestMapping(value = ProductAdminRoutes.ADD, method = RequestMethod.GET)
    public String create(Model model) {
        ProductDoc doc = new ProductDoc();
        doc.setId(new ObjectId());
        model.addAttribute("doc", doc);
        model.addAttribute("pageHeader", defaultPageHeader("Добавление"));
        model.addAttribute("categories", categoryService.findHashMap());
        return "tech.maslov.sandbox.admin.product.add";
    }

    @RequestMapping(value = ProductAdminRoutes.ADD, method = RequestMethod.POST)
    public String create(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) ObjectId categoryId,
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) MultipartFile picFile,
            RedirectAttributes redirectAttributes) {

        ProductDoc doc = new ProductDoc();

        doc.setTitle(title);
        doc.setDescription(description);
        doc.setCategoryId(categoryId);
        doc.setAvailable(available);
        doc.setPrice(price);
        doc.setPicId(pictureService.saveWithDelete(picFile, null));

        productService.save(doc);
        redirectAttributes.addAttribute("id", doc.getId());
        return RouteUtils.redirectTo(ProductAdminRoutes.EDIT);
    }

    @RequestMapping(value = ProductAdminRoutes.EDIT, method = RequestMethod.GET)
    public String update(@RequestParam ObjectId id, Model model) {
        ProductDoc doc = productService.findById(id);
        model.addAttribute("doc", doc);
        model.addAttribute("pageHeader", defaultPageHeader("Редактирование"));
        model.addAttribute("categories", categoryService.findHashMap());
        return "tech.maslov.sandbox.admin.product.edit";
    }

    @RequestMapping(value = ProductAdminRoutes.EDIT, method = RequestMethod.POST)
    public String update(
            @RequestParam(required = false) ObjectId id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) ObjectId categoryId,
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) MultipartFile picFile,
            RedirectAttributes redirectAttributes) {
        ProductDoc doc = productService.findById(id);

        doc.setId(id);
        doc.setTitle(title);
        doc.setDescription(description);
        doc.setCategoryId(categoryId);
        doc.setAvailable(available);
        doc.setPrice(price);
        doc.setPicId(pictureService.saveWithDelete(picFile, doc.getPicId()));

        productService.save(doc);
        redirectAttributes.addAttribute("id", doc.getId());

        return RouteUtils.redirectTo(ProductAdminRoutes.EDIT);
    }

    @RequestMapping(value = ProductAdminRoutes.ALL, method = RequestMethod.GET)
    public String all(@RequestParam(required = false, defaultValue = "0") Integer currentPage,
                      @RequestParam(required = false, defaultValue = "") String query,
                      Model model) {
        SearchProductAdminRequest searchRequest = new SearchProductAdminRequest(currentPage);
        searchRequest.setQuery(query);
        model.addAttribute("search", productService.findAll(searchRequest));
        model.addAttribute("pageHeader", defaultPageHeader("Все"));
        model.addAttribute("categories", categoryService.findHashMap());
        return "tech.maslov.sandbox.admin.product.all";
    }

    @RequestMapping(value = ProductAdminRoutes.MODAL_PARENT, method = RequestMethod.GET)
    public String modalResponse(@RequestParam(required = false, defaultValue = "0") Integer currentPage,
                                @RequestParam(required = false, defaultValue = "") String query,
                                Model model) {
        SearchProductAdminRequest searchRequest = new SearchProductAdminRequest(currentPage);
        searchRequest.setQuery(query);
        model.addAttribute("search", productService.findAll(searchRequest));
        return "tech.maslov.sandbox.admin.product.modal.parent";
    }

    @RequestMapping(value = ProductAdminRoutes.DELETE, method = RequestMethod.GET)
    public String delete(@RequestParam ObjectId id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("pageHeader", defaultPageHeader("Удаление"));
        return "tech.maslov.sandbox.admin.product.delete";
    }

    @RequestMapping(value = ProductAdminRoutes.DELETE, method = RequestMethod.POST)
    public String delete(@RequestParam ObjectId id) {
        productService.remove(id);
        return RouteUtils.redirectTo(ProductAdminRoutes.ALL);
    }
}
