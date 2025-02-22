
package com.treasuremount.petshop.Dashboard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class DashBoardDTO {
private Long totalUsers;
private Long totalOrders;
private Long totalVendors;
private Long totalReviews;
private Long totalProducts;

    public DashBoardDTO(Long totalUsers, Long totalOrders, Long totalVendors, Long totalReviews, Long totalProducts) {
        this.totalUsers = totalUsers;
        this.totalOrders = totalOrders;
        this.totalVendors = totalVendors;
        this.totalReviews = totalReviews;
        this.totalProducts = totalProducts;
    }
}






