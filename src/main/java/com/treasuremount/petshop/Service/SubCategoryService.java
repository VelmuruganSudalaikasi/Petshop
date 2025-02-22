package com.treasuremount.petshop.Service;


import java.util.List;

import com.treasuremount.petshop.DTO.SubCategoryDTO;
import com.treasuremount.petshop.DTO.SubCategoryInfoDTO;
import com.treasuremount.petshop.DTO.SubCategoryInfoImageDTO;
import com.treasuremount.petshop.Entity.SubCategory;

public interface SubCategoryService {

    SubCategory create(SubCategory d);

    List<SubCategory> getAll();

    SubCategory getOne(Long id);

    SubCategory update(SubCategory d, Long id);

    void delete(Long id);

     List<SubCategoryInfoImageDTO> getWithCategoryNameAll();

    List<SubCategoryInfoImageDTO> getSubCategoryByCategoryId(Long categoryId);

}