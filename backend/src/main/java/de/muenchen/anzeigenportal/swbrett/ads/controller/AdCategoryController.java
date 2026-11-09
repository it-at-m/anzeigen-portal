package de.muenchen.anzeigenportal.swbrett.ads.controller;

import de.muenchen.anzeigenportal.swbrett.ads.model.AdCategory;
import de.muenchen.anzeigenportal.swbrett.ads.service.AdCategoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public AdCategory getAdCategory(@PathVariable("id") final long id) {
        return service.getAdCategory(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public AdCategory createAdCategory(@RequestBody final AdCategory adCategory, HttpServletRequest request) {
        return service.saveAdCategory(adCategory);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AdCategory updateAdCategory(@PathVariable("id") long id, @RequestBody final AdCategory adCategory, HttpServletRequest request) {
        return service.saveAdCategory(adCategory);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAd(@PathVariable("id") final long id, HttpServletRequest request) {
        service.deleteAdCategory(id);
    }
}
