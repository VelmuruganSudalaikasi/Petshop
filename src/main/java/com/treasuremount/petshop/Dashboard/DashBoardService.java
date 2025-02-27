package com.treasuremount.petshop.Dashboard;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Service
public class DashBoardService {

    @Autowired
    private DashBoardRepo dashboardRepository;

    public List<Map<String, Map<String, Map<String, Object>>>> getOrderStatistics(String strStartDate, String strEndDate, Long vendorUserId) {
        // Fetch raw results from the database
        List<Object[]> rawData = dashboardRepository.findOrderStatisticsByDateRange(strStartDate, strEndDate, vendorUserId);

        // Map to store the results grouped by date
        Map<LocalDate, Map<String, Map<String, Object>>> groupedData = new HashMap<>();

        for (Object[] row : rawData) {
            LocalDate date = ((java.sql.Date) row[0]).toLocalDate();
            String statusName = null;
            BigDecimal totalAmountBigDecimal = (BigDecimal) row[2]; // Assuming it's BigDecimal

            // Handle potential type issues:
            if (row[1] != null) {
                statusName = (String) row[1]; // Extract status_name from the correct column
            }

            // Handle potential BigDecimal to Long conversion for totalAmount
            Long totalAmount = totalAmountBigDecimal != null ? totalAmountBigDecimal.longValue() : 0L;
            Long productCount= (Long)row[3];

            // Create the inner map for status and totalAmount
            Map<String, Object> statusMap = new HashMap<>();
            statusMap.put("totalAmount", totalAmount);
            statusMap.put("productCount", productCount);

            // Add to the date-level grouped data
            groupedData.computeIfAbsent(date, k -> new HashMap<>()).put(statusName, statusMap);
        }

        // Convert to the desired response format
        List<Map<String, Map<String, Map<String, Object>>>> response = new ArrayList<>();
        groupedData.forEach((date, statusMap) -> {
            Map<String, Map<String, Map<String, Object>>> dateMap = new HashMap<>();
            dateMap.put(date.toString(), statusMap);
            response.add(dateMap);
        });


        return response;
    }

    public Map<String, Map<String, Long>> getOrderStatusTotals(String strStartDate, String strEndDate, Long vendorUserId) {
        // Define the possible statuses
        List<String> statuses = Arrays.asList(
                "Pending", "Confirmed", "Packaging", "Shipped",
                "Out for Delivery", "Delivered", "Cancelled",
                "Returned", "Refunded");

        // Fetch raw results from the repository
        List<Object[]> rawData = dashboardRepository.findOrderStatusByDateRange(strStartDate, strEndDate, vendorUserId);

        // Map to store aggregated results for each status
        Map<String, Map<String, Long>> statusTotals = new HashMap<>();

        // Initialize all statuses with default values
        statuses.forEach(status -> {
            statusTotals.put(status, createStatusMap(0L, 0L));
        });

        // Process each row of data from the query results
        for (Object[] row : rawData) {
            String statusName = (String) row[1]; // Extract status name

            // Extract total amount (BigDecimal to Long)
            BigDecimal totalAmountBigDecimal = (BigDecimal) row[2];
            Long totalAmount = totalAmountBigDecimal != null ? totalAmountBigDecimal.longValue() : 0L;

            Long productCount = (Long) row[3]; // Number of products for this status

            // Aggregate totals for each status
            if (statusTotals.containsKey(statusName)) {
                Map<String, Long> currentStatus = statusTotals.get(statusName);
                currentStatus.put("totalAmount", currentStatus.get("totalAmount") + totalAmount);
                currentStatus.put("productCount", currentStatus.get("productCount") + productCount);
            }
        }

        return statusTotals;
    }

    // Helper method to create a status map with default values (totalAmount and productCount)
    private Map<String, Long> createStatusMap(Long totalAmount, Long productCount) {
        Map<String, Long> statusMap = new HashMap<>();
        statusMap.put("totalAmount", totalAmount);
        statusMap.put("productCount", productCount);
        return statusMap;
    }


    public DashBoardDTO getDashboardData(Long vendorUserid) {
        // Get total users
        Long totalUsers = dashboardRepository.getTotalUsers(vendorUserid);
        Long totalVendor=    dashboardRepository.getTotalVendor(vendorUserid);


        // Get product, vendor, review, and order data
        Map<String, Object> data = dashboardRepository.getProductVendorReviewData(vendorUserid);

        // Map the data to DTO
        return new DashBoardDTO(
                totalUsers,
                ((Number) data.get("totalOrders")).longValue(),
                totalVendor,
                ((Number) data.get("totalReviews")).longValue(),
                ((Number) data.get("productCount")).longValue()
        );
    }

    public List<VendorSalesDTO> getTopSales(Long limit,Long orderStatusId){
      List<VendorSalesDTO>  vendorSalesDTOS=  dashboardRepository.getTopVendorBySales(limit,orderStatusId);
        return  vendorSalesDTOS;
    }

   /*  {
    "2025-02-10": {
      "Pending": {
        "totalAmount": 707,
        "productCount": 1
      },
      "Refunded": {
        "totalAmount": 1544,
        "productCount": 4
      }
    }
  }
  */


        public List<Map<String, Map<String, Map<String, Object>>>> getAppointmentStatistics(
                Long doctorId, String startDate, String endDate) {

            List<Object[]> response = dashboardRepository.findAppointmentStatisticsByDateRange(startDate, endDate, doctorId);
            List<Map<String, Map<String, Map<String, Object>>>> formattedResponse = new ArrayList<>();

            Map<LocalDate, Map<String, Map<String, Object>>> groupedData = new HashMap<>();

            for (Object[] obj : response) {
                LocalDate date = ((java.sql.Date) obj[2]).toLocalDate();
                Long count = (Long) obj[0];
                String status = (String) obj[1];

                // Ensure the date entry exists
                groupedData.putIfAbsent(date, new HashMap<>());
                Map<String, Map<String, Object>> statusMap = groupedData.get(date);

                // Ensure the status entry exists
                statusMap.putIfAbsent(status, new HashMap<>());
                Map<String, Object> countMap = statusMap.get(status);

                // Store count
                countMap.put("Count", count);
            }

            // Convert groupedData to List<Map<String, Map<String, Map<String, Object>>>>
            for (Map.Entry<LocalDate, Map<String, Map<String, Object>>> entry : groupedData.entrySet()) {
                Map<String, Map<String, Map<String, Object>>> dateEntry = new HashMap<>();
                dateEntry.put(entry.getKey().toString(), entry.getValue());
                formattedResponse.add(dateEntry);
            }

            return formattedResponse;
        }




}

