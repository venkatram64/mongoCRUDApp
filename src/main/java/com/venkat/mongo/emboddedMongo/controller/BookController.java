package com.venkat.mongo.emboddedMongo.controller;

import com.venkat.mongo.emboddedMongo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public List<Object> getAll(){
        return bookService.getAll();
    }

    @PostMapping("/")
    public String create(@RequestBody Map<String, Object> book){
        return bookService.create(book);
    }

    @PutMapping("/")
    public String update(@RequestBody Map<String, Object> book){
        return bookService.update(book);
    }

    @DeleteMapping("/{id}")
    public String delete(@RequestParam("id") String id){
        return bookService.delete(id);
    }

    @GetMapping("/page")
    public Map<String, Object> getAllByPage(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
              @RequestParam(value = "fields", defaultValue = "title, pageCount") String[] fields,
              @RequestParam(value = "sortBy", defaultValue = "id") String sortBy){
        return bookService.getAllByPage(pageNo, pageSize, fields, sortBy);
    }

    @GetMapping("/totalPageCount")
    public Map<String,Object> getTotalPageCount(){
        return bookService.getTotalPageCount();
    }

    @GetMapping("/category")
    public Map<String,Object> getByCategory(@RequestParam(value="category", required = true) String[] categories){
        return bookService.getByCategories(categories);
    }
}
