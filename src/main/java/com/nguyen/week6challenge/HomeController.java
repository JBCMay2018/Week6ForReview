package com.nguyen.week6challenge;

import com.cloudinary.utils.ObjectUtils;
import com.nguyen.week6challenge.config.CloudinaryConfig;
import com.nguyen.week6challenge.model.Car;
import com.nguyen.week6challenge.model.Category;
import com.nguyen.week6challenge.repository.CarRepository;
import com.nguyen.week6challenge.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    CloudinaryConfig cloudc;

    @Autowired
    CarRepository carRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @RequestMapping("/")
    public String indexPage(Model model) {
        model.addAttribute("cars", carRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "index";
    }

    /* ************************************************
    *
    *               CAR OBJECT
    *
    * *************************************************/

    @GetMapping("/addcar")
    public String loadCarForm(Model model) {
        model.addAttribute("car", new Car());
        model.addAttribute("categories", categoryRepository.findAll());
        return "carform";
    }

    @PostMapping("/addcar")
    public String processCarForm(@Valid @ModelAttribute("car") Car car, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()){
            return "carform";
        }
        try {
            Map uploadResult =  cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
            car.setPictureUrl(uploadResult.get("url").toString());
            carRepository.save(car);
        } catch (IOException e){
            e.printStackTrace();
            return "redirect:/add";
        }
        return "redirect:/";
    }

    @RequestMapping("/detailcar/{id}")
    public String showCar(@PathVariable("id") long id, Model model) {
        Car car = carRepository.findById(id).get();
        Category category = categoryRepository.findById(car.getCategory_id()).get();
        model.addAttribute("car", car);
        model.addAttribute("carCategory", category);
        return "showcar";
    }

    // DOES NOT WORK!!!
    @RequestMapping("/updatecar/{id}")
    public String updateCar(@PathVariable("id") long id, Model model) {
        model.addAttribute("car", carRepository.findById(id).get());
        model.addAttribute("categories", categoryRepository.findAll());
        return "carform";
    }

    @RequestMapping("/deletecar/{id}")
    public String deleteCar(@PathVariable("id") long id) {
        carRepository.deleteById(id);
        return "redirect:/";
    }


    /* ************************************************
    *
    *               CATEGORY OBJECT
    *
    * *************************************************/

    @GetMapping("/addcategory")
    public String loadCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "categoryform";
    }

    @PostMapping("/addcategory")
    public String processCategoryForm(@Valid @ModelAttribute("category") Category category, BindingResult result) {
        if (result.hasErrors()) {
            return "categoryform";
        }
        categoryRepository.save(category);
        return "redirect:/";
    }

    @RequestMapping("/detailcategory/{id}")
    public String showCategory(@PathVariable("id") long id, Model model) {
        model.addAttribute("category", categoryRepository.findById(id).get());
        return "showcategory";
    }

    @RequestMapping("/updatecategory/{id}")
    public String updateCategory(@PathVariable("id") long id, Model model) {
        model.addAttribute("category", categoryRepository.findById(id));
        return "categoryform";
    }

    @RequestMapping("/deletecategory/{id}")
    public String deleteCategory(@PathVariable("id") long id) {
        categoryRepository.deleteById(id);
        return "redirect:/";
    }


}
