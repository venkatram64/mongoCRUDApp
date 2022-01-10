package com.venkat.mongo.emboddedMongo.service;

import com.venkat.mongo.emboddedMongo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Object> getAll() {
        return bookRepository.getAll();
    }

    public String create(Map<String, Object> book) {
        return bookRepository.create(book);
    }

    public String update(Map<String, Object> book) {
        return bookRepository.update(book);
    }

    public String delete(String id) {
        return bookRepository.delete(id);
    }

    public Map<String,Object> getAllByPage(int pageNo, int pageSize, String[] fields, String sortBy) {
        Map<String, Object> response = new HashMap<>();
        List<Object> books = bookRepository.getAllByPage(pageNo, pageSize, fields, sortBy);
        long count = bookRepository.countDocuments();
        response.put("data", books);
        response.put("No. of Element", count);
        response.put("No of Pages", Math.ceil(count/pageSize));
        return response;
    }

    public Map<String, Object> getTotalPageCount() {
        Map<String, Object> response = new HashMap<>();
        response.put("Total No. of Pages", bookRepository.countPages());
        return response;
    }

    public Map<String, Object> getByCategories(String[] categories) {
        Map<String, Object> response = new HashMap<>();
        List<Object> books = bookRepository.getByCategories(categories);
        response.put("data", books);
        response.put("size of books", books.size());
        return response;
    }
}
