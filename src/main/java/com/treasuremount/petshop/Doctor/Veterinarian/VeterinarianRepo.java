package com.treasuremount.petshop.Doctor.Veterinarian;

import com.treasuremount.petshop.DTO.VeterinarianCustomDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository

public interface VeterinarianRepo extends JpaRepository<Veterinarian,Long> {

   Veterinarian findByUserId(Long userId);

   @Query("SELECT v FROM Veterinarian v " +
           "LEFT JOIN FETCH v.schedules s " +
           "LEFT JOIN FETCH v.user u " +
           "WHERE (:day IS NULL OR s.daysOfWeek = :day) " +
           "AND (:start IS NULL OR s.startTime >= :start) " +
           "AND (:end IS NULL OR s.startTime <= :end)")
   List<Veterinarian> findVeterinariansBySchedule(
           @Param("day") DayOfWeek day,
           @Param("start") LocalTime start,
           @Param("end") LocalTime end
   );

   @Query(value =
           """
                   SELECT
                      v.id,
                                             u.first_name,
                                             u.last_name,
                                             u.email_id,
                                             u.mobile_number,
                                             MAX(pf.image_url) AS image_url,  -- Aggregate the image_url
                                             v.education,
                                             v.specializations,
                                             v.years_of_experience,
                                             v.city,
                                             v.language,
                                             v.animal_type,
                                             v.about_me
                   FROM user u
                   JOIN veterinarian v ON v.user_id = u.id
                   LEFT JOIN doctor_schedule_config sh ON v.id = sh.veterinarian_id
                   LEFT JOIN profile_image pf ON v.user_id = pf.user_id
                   WHERE
                       (:start IS NULL OR sh.start_time >= :start)
                       AND (:end IS NULL OR sh.start_time <= :end)
                       AND (:day IS NULL OR sh.days_of_week = :day)
                   GROUP BY
                              v.id,
                               u.first_name,
                               u.last_name,
                               u.email_id,
                               u.mobile_number,
                               v.education,
                               v.specializations,
                               v.years_of_experience,
                               v.city,
                               v.language,
                               v.animal_type,
                               v.about_me,
                               CASE WHEN :day IS NOT NULL THEN sh.days_of_week END;
           """,
           nativeQuery = true)
   List<Object[]> getAllCustomDTO(
           @Param("day") String day,
           @Param("start") LocalTime start,
           @Param("end") LocalTime end
   );

   /*
   *  collect all the
   * */


}
