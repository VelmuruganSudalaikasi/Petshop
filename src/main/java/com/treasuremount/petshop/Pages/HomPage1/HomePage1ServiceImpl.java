package com.treasuremount.petshop.Pages.HomPage1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HomePage1ServiceImpl implements HomePage1Service {

    @Autowired
    private HomePage1Repo repository;

    @Override
    public HomePage1 create(HomePage1 d) {
        try {
            return repository.save(d);

        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<HomePage1> getAll() {

        return repository.findAll();
    }

    @Override
    public HomePage1 getOne(Long id) {
        try {
            return repository.findById(id).orElse(null);

        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public HomePage1 update(HomePage1 d, Long id) {
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


}