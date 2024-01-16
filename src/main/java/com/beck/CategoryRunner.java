package com.beck;

import com.beck.dao.CategoryDao;
import com.beck.model.Category;

public class CategoryRunner {
    public static CategoryDao categoryDao = CategoryDao.getInstance();

    public static void main(String[] args) {

//        saveCategory(new Category("Fruits"));
        deleteCategory(4);



    }

    public static Category saveCategory(Category category) {
        category.setName(category.getName());
        categoryDao.save(category);
        return category;
    }

    public static void deleteCategory(Integer id){
        categoryDao.deleteById(id);
    }

}
