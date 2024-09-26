package de.muenchen.anzeigenportal.swbrett.ads.controller;

import de.muenchen.intranet.sbrett.swbrett.ads.model.AdCategory;
import de.muenchen.intranet.sbrett.swbrett.ads.service.AdCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class AdCategoryController {

    @Autowired
    private AdCategoryService service;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<AdCategory> getAllAdCategories() {
        return service.getAdCategories();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AdCategory getAdCategory(@PathVariable("id") long id) {
        return service.getAdCategory(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public AdCategory createAdCategory(@RequestBody AdCategory adCategory, HttpServletRequest request) {
        return service.saveAdCategory(adCategory);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AdCategory updateAdCategory(@PathVariable("id") long id, @RequestBody AdCategory adCategory, HttpServletRequest request) {
        return service.saveAdCategory(adCategory);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAd(@PathVariable("id") long id, HttpServletRequest request) {
        service.deleteAdCategory(id);
    }
}
