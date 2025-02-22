package com.treasuremount.petshop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogDTO {

    private Long id;
    private Long userId;
    private String imageUrl;
    private Long CategoryId;
    private String categoryName;
    private String heading;
    private Boolean activeStatus;
    private String shortDescription;
    private String Description;
    private Date createdDate;
    private Date modifiedDate;


}
