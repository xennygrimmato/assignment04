package com.devfactory.assignment4.controller;

/**
 * Created by vaibhavtulsyan on 07/07/16.
 */

import com.devfactory.assignment4.model.Product;
import com.devfactory.assignment4.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang.StringUtils.isBlank;

@RequestMapping("/api")
@RestController
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class.getName());

    @Autowired
    ProductRepository productRepo;

    @RequestMapping("/products")
    public ResponseEntity products(@RequestParam(value="name", required = false) String name) {
        List<Product> products = new ArrayList<Product>();
        for(Product p : productRepo.findAll()) {
            if (p.getDeleted() == 0) {
                // if product is not deleted, add to list
                products.add(p);
            }
        }
        return new ResponseEntity(products, HttpStatus.OK);
    }

    @RequestMapping(value="/products/{id}", method=RequestMethod.GET)
    public ResponseEntity<Object> getProduct(@PathVariable int id) {
        Product product = productRepo.findOne(id);

        if(product == null) {
            Map<String,String> detailObject = new HashMap<String,String>();
            detailObject.put("detail", "Not found.");
            return new ResponseEntity<Object>(detailObject, HttpStatus.NOT_FOUND);
        }

        if(product.getDeleted() == 1) {
            Map<String,String> detailObject = new HashMap<String,String>();
            detailObject.put("detail", "Not found.");
            return new ResponseEntity<Object>(detailObject, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Object>(product, HttpStatus.OK);
    }

    @RequestMapping(value="/products", method=RequestMethod.POST)
    public ResponseEntity<Product> products(@RequestBody Product product) {
        try {
            if (isBlank(product.getCode())) {
                //return new ResponseEntity<Product>(product, HttpStatus.BAD_REQUEST);
            }

            Product p = new Product();

            if(!isBlank(product.getName())) p.setName(product.getName());
            if(!isBlank(product.getCode())) p.setCode(product.getCode());
            if(product.getRemaining() == null) p.setRemaining(product.getRemaining());

            productRepo.save(p);
            return new ResponseEntity<Product>(p, HttpStatus.CREATED);
        } catch(Exception e) {
            // Log error
            LOGGER.error(e.getMessage());
        }
        //return new ResponseEntity<Product>(product, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Product>(product, HttpStatus.CREATED);
    }

    @RequestMapping(value="/products/{id}", method=RequestMethod.PUT)
    public ResponseEntity putProduct(@PathVariable int id, @RequestBody Product product) {
        // given values + default values
        try {

            Product p = productRepo.findOne(id);

            if(p == null) {
                Map<String,String> detailObject = new HashMap<String,String>();
                detailObject.put("detail", "Not found.");
                return new ResponseEntity<Object>(detailObject, HttpStatus.NOT_FOUND);
            }

            if(p.getDeleted() == 1) {
                Map<String,String> detailObject = new HashMap<String,String>();
                detailObject.put("detail", "Not found.");
                return new ResponseEntity<Object>(detailObject, HttpStatus.NOT_FOUND);
            }

            if(isBlank(product.getCode())) {
                // code is a compulsory field
                // return BAD_REQUEST if it is not part of request body
                return new ResponseEntity<Object>(null, HttpStatus.NOT_FOUND);
            } else {
                p.setCode(product.getCode());
            }

            if (isBlank(product.getName())) {
                p.setName("");
            } else {
                p.setName(product.getName());
            }

            if (isBlank(product.getDescription())) {
                // description: default value = ""
                p.setDescription("");
            } else {
                p.setDescription(product.getDescription());
            }

            if(product.getDeleted() == null) {
                // deleted: default value = 0
                p.setDeleted(0);
            } else {
                p.setDeleted(product.getDeleted());
            }

            p.setRemaining(product.getRemaining());

            productRepo.save(p);
            return new ResponseEntity<Product>(p, HttpStatus.OK);

        } catch(Exception e) {
            LOGGER.error(e.getMessage());
        }
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

    @RequestMapping(value="/products/{id}", method=RequestMethod.PATCH)
    public ResponseEntity patchProduct(@PathVariable int id, @RequestBody Product product) {
        // update with given values
        try {

            Product p = productRepo.findOne(id);

            if(p == null) {
                Map<String,String> detailObject = new HashMap<String,String>();
                detailObject.put("detail", "Not found.");
                return new ResponseEntity<Object>(detailObject, HttpStatus.NOT_FOUND);
            }

            if(p.getDeleted() == 1) {
                Map<String,String> detailObject = new HashMap<String,String>();
                detailObject.put("detail", "Not found.");
                return new ResponseEntity<Object>(detailObject, HttpStatus.NOT_FOUND);
            }

            if(!isBlank(product.getCode())) {
                p.setCode(product.getCode());
            }

            if(!isBlank(product.getName())) {
                p.setName(product.getName());
            }

            if(!isBlank(product.getDescription())) {
                p.setDescription(product.getDescription());
            }

            if(product.getRemaining() != null) {
                p.setRemaining(product.getRemaining());
            }

            if(product.getDeleted() != null) {
                p.setDeleted(product.getDeleted());
            }

            productRepo.save(p);
            return new ResponseEntity<Product>(p, HttpStatus.OK);

        } catch(Exception e) {
            LOGGER.error(e.getMessage());
        }
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

    @RequestMapping(value="/products/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Object> deleteProduct(@PathVariable int id) {
        try {
            Product p = productRepo.findOne(id);
            if(p == null) {
                Map<String,String> detailObject = new HashMap<String,String>();
                detailObject.put("detail", "Not found.");
                return new ResponseEntity<Object>(detailObject, HttpStatus.NOT_FOUND);
            }
            if(p.getDeleted() == 1) {
                Map<String,String> detailObject = new HashMap<String,String>();
                detailObject.put("detail", "Not found.");
                return new ResponseEntity<Object>(detailObject, HttpStatus.NOT_FOUND);
            }

            p.setDeleted(1);
            productRepo.save(p);
            Map<String, String> empty = new HashMap<String, String>();
            return new ResponseEntity<Object>(empty, HttpStatus.OK);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        Map<String, String> empty = new HashMap<String, String>();
        return new ResponseEntity<Object>(empty, HttpStatus.OK);
    }
}
