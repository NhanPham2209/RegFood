package com.example.regfood.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.regfood.dto.CategoryDTO;
import com.example.regfood.entities.Category;
import com.example.regfood.repository.CategoryRepository;
import com.example.regfood.service.imp.CategoryServiceImp;
@Service
public class CategoryService implements CategoryServiceImp{
	@Autowired 
	CategoryRepository categoryRepository;
	@Override
	public List<CategoryDTO> getAllCategories() {
		List<Category> listCategories = categoryRepository.findAll();
		List<CategoryDTO> listCategoryDTOs = new ArrayList<>();
		for (Category category : listCategories) {
			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setId(category.getId());
			categoryDTO.setCategoryName(category.getCategoryName());
			categoryDTO.setStatus(category.getStatus());
			categoryDTO.setCreateDate(category.getCreatedDate());
			listCategoryDTOs.add(categoryDTO);
			
		}
		return listCategoryDTOs;
	}

	@Override
	public boolean addCategory(CategoryDTO categoryDTO) {
		Category category = new Category();
		category.setCategoryName(categoryDTO.getCategoryName());
		category.setStatus(categoryDTO.getStatus());
		Date currentDate = new Date();
		Timestamp timestamp = new Timestamp(currentDate.getTime());
		category.setCreatedDate(timestamp);
		try {
			categoryRepository.save(category);
			return true;
		} catch (Exception e) {
			return false;
		}
	}



	@Override
	public boolean deleteCategory(int categoryId) {
		
		try {
			categoryRepository.deleteCategory(categoryId);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean editCategory(int Id, CategoryDTO categoryDTO) {
		Category category = categoryRepository.findCategoryById(Id);
		category.setCategoryName(categoryDTO.getCategoryName());
		category.setStatus(categoryDTO.getStatus());
		 
		try {
			categoryRepository.save(category);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public CategoryDTO getCategoryById(int categoryId) {
		Category category = categoryRepository.findCategoryById(categoryId);
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(categoryId);
		categoryDTO.setCategoryName(category.getCategoryName());
		categoryDTO.setStatus(category.getStatus());
		categoryDTO.setCreateDate(category.getCreatedDate());
		return categoryDTO;
	}

}
