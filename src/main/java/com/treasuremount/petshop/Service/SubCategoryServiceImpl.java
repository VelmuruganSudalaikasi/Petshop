package com.treasuremount.petshop.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.treasuremount.petshop.DTO.SubCategoryInfoDTO;
import com.treasuremount.petshop.DTO.SubCategoryInfoImageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.treasuremount.petshop.Entity.Category;
import com.treasuremount.petshop.Entity.SubCategory;
import com.treasuremount.petshop.Repository.SubCategoryRepository;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {

    @Autowired
    private SubCategoryRepository repository;

    @Override
    public SubCategory create(SubCategory d) {
        try {
            return repository.save(d);

        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<SubCategory> getAll() {

        return repository.findAll();
    }

    @Override
    public List<SubCategoryInfoImageDTO> getWithCategoryNameAll() {

        return repository.findAllWithCategoryName();
    }

    @Override
    public SubCategory getOne(Long id) {
        try {
            return repository.findById(id).orElse(null);

        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public SubCategory update(SubCategory d, Long id) {
        try {

            if (repository.existsById(id)) {
                d.setId(id);
                return repository.saveAndFlush(d);
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<SubCategoryInfoImageDTO> getSubCategoryByCategoryId(Long categoryId) {
        try {
            List<SubCategoryInfoImageDTO> all = repository.getAllSubCategory(categoryId);

    return all;

        } catch (Exception ex) {
            return Collections.emptyList();
        }
    }

    public List<SubCategoryInfoDTO> convertToSubCategoryInfoDTO(List<SubCategory> all){
        List<SubCategoryInfoDTO> response = new ArrayList<>();
        for (SubCategory subCategory : all) {
            Category category = subCategory.getCategory();
            SubCategoryInfoDTO subCategoryDTO = new SubCategoryInfoDTO(
                    subCategory.getId(),
                    subCategory.getName(),
                    subCategory.getCategoryId(),
                    category.getName(),
                    subCategory.getActiveStatus()
            );
            response.add(subCategoryDTO);
        }
        return response;
    }
}