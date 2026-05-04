package org.example.ecommerce.Services;


import lombok.RequiredArgsConstructor;
import org.example.ecommerce.Model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryService {

    ArrayList<Category> categories=new ArrayList<>();


    public ArrayList<Category> getCategories() {
        return categories;
    }

    public boolean addCategory(Category category) {
        for(Category c:categories){
            if(c.getId().equals(category.getId()))
                return false;
        }
        categories.add(category);
        return true;
    }

    public boolean removeCategory(String id) {
        for (Category category : categories) {
            if(category.getId().equals(id)){
                categories.remove(category);
                return true;
            }
        }
        return false;
    }

    public boolean updateCategory(String id,Category category) {
        for (Category category1 : categories) {
            if(category1.getId().equals(id)){
                categories.set(categories.indexOf(category1),category);
                return true;
            }
        }
        return false;
    }

    public Category getCategoryByName(String name) {
        for (Category category : categories) {
            if(category.getName().equalsIgnoreCase(name)){
                return category;
            }
        }
        return null;
    }

}
