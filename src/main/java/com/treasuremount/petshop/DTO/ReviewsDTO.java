package com.treasuremount.petshop.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ReviewsDTO {
 private Long id;
 private String userName;
 private String vendorName;
 private String productName;
 private String imageUrl;
 private  Integer rating;
 private String comments;
 private Boolean isApproved;
 private Date createdDate;


 public ReviewsDTO(Long id, String userName, String vendorName, String productName, String imageUrl, Integer rating, String comments, Boolean isApproved, Date createdDate) {
  this.id = id;
  this.userName = userName;
  this.vendorName = vendorName;
  this.productName = productName;
  this.imageUrl = imageUrl;
  this.rating = rating;
  this.comments = comments;
  this.isApproved = isApproved;
  this.createdDate = createdDate;
 }


}
