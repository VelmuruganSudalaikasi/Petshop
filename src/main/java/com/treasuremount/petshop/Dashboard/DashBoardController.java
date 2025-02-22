package com.treasuremount.petshop.Dashboard;

import com.treasuremount.petshop.Entity.Reviews;
import org.hibernate.grammars.hql.HqlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/public/dashBoard")
public class DashBoardController {


        @Autowired
        private DashBoardService dashboardService;

        @GetMapping("/order-statistics")
        public List<Map<String, Map<String, Map<String, Object>>>> getOrderStatistics(
                @RequestParam(defaultValue = "0", required = false) Long vendorUserId,
                @RequestParam(required = false) String startDate,
                @RequestParam(required = false) String endDate) {


            return dashboardService.getOrderStatistics(startDate, endDate,vendorUserId);
        }

    @GetMapping("/status/totals")
    public Map<String, Map<String, Long>> getOrderStatusTotals(
            @RequestParam(defaultValue = "0", required = false) Long vendorUserId,
            @RequestParam(defaultValue = "0",required = false) String startDate,
            @RequestParam(defaultValue = "0",required = false) String endDate) {

        return dashboardService.getOrderStatusTotals(startDate, endDate, vendorUserId);
    }


    @GetMapping("/TotalCount")
    public ResponseEntity<DashBoardDTO> getAllStatic(
            @RequestParam(defaultValue = "0") Long vendorUserid
    ) {
        DashBoardDTO dashboardData = dashboardService.getDashboardData(vendorUserid);
        return ResponseEntity.ok(dashboardData);


    }

    // DashBoard vendor dto needs what

    /*
         VendorName
         productCount
         shopName
         ImageUrl
         email

    * */

    @GetMapping("/TopSalesVendor")
    public ResponseEntity<List<VendorSalesDTO>> getTopSales(
            @RequestParam(value = "limit",defaultValue = "5",required = false) Long limit,
            @RequestParam(value="OrderStatusId",defaultValue = "6",required = false) Long orderStatusId
    ) {
        List<VendorSalesDTO> dashboardData = dashboardService.getTopSales(limit,orderStatusId);

return ResponseEntity.ok(dashboardData);

    }

    @GetMapping("/appointment-statistics")
    public ResponseEntity<List<Map<String, Map<String, Map<String, Object>>>>> getDoctorAppointment(
            @RequestParam(defaultValue = "0", required = false) Long veterinarianId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    ) {
        return ResponseEntity.ok(dashboardService.getAppointmentStatistics(veterinarianId, startDate,endDate));



    }



}
