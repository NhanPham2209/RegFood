package com.example.regfood.service.imp;

import java.util.List;

import com.example.regfood.dto.CategoryDTO;
import com.example.regfood.entities.Category;

public interface CategoryServiceImp {
	List<CategoryDTO> getAllCategories();
	boolean addCategory(CategoryDTO categoryDTO);
	boolean editCategory(int Id,CategoryDTO categoryDTO);
	boolean deleteCategory(int categoryId);
	CategoryDTO getCategoryById(int categoryId);
}
