package com.treasuremount.petshop.Testimonial;

import java.util.List;

public interface TestimonialService {

    Testimonial create(Testimonial d);

    List<Testimonial> getAll();

    Testimonial getOneById(Long id);

    Testimonial update(Testimonial d, Long id);

    void delete(Long id);
}
