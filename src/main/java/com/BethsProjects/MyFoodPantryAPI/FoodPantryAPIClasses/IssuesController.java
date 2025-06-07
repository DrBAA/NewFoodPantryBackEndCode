package com.BethsProjects.MyFoodPantryAPI.FoodPantryAPIClasses;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "*")  // This enables CORS for all origins

public class IssuesController {

    private final IssuesService issuesService;

    // Constructor-based dependency injection
    public IssuesController(IssuesService issuesService) {
        this.issuesService = issuesService;
    }


    // CODE TO HANDLE A CREATE REQUEST BY ISSUING A FOOD PARCEL

    // http://localhost:8080/api/issue-food-parcel?member_id=M06&food_parcel_id=FP02&collection_point_id=WAL01
    @PostMapping("/issue-food-parcel")
    public ResponseEntity<String> issueFoodParcel(@RequestParam String member_id,
                                                  @RequestParam String food_parcel_id,
                                                  @RequestParam String collection_point_id,
                                                  @RequestParam(required = false) String date_last_issued,
                                                  @RequestParam(required = false) Integer amount_issued) {
        try {
            // Call the service to issue the food parcel
            issuesService.issueFoodParcel(member_id, food_parcel_id, collection_point_id, date_last_issued, amount_issued);
            return ResponseEntity.ok("Food parcel successfully issued to member ID: " + member_id);
        } catch (IllegalArgumentException ex) {
            // Handle specific validation-related exceptions
            return ResponseEntity.status(400).body("Invalid input: " + ex.getMessage());
        } catch (Exception ex) {
            // Handle general exceptions
            return ResponseEntity.status(500).body("An error occurred: " + ex.getMessage());
        }
    
    }

    // added 01 06 2025 to retrieve the date of last issue for a food parcel from MYSQL database
    @GetMapping("/last-issue-date/{memberId}")
    public ResponseEntity<String> getLastIssueDate(@PathVariable String memberId) {
        String lastIssueDate = issuesService.getLastIssueDate(memberId);
        return ResponseEntity.ok(lastIssueDate); // Returns NULL if no record exists
    }

    // added 07 06 2025 to retrieve stock levels for food parcels from MYSQL database
    @GetMapping("/food-parcel-quantity/{foodParcelId}")
    public ResponseEntity<Integer> getFoodParcelQuantity(@PathVariable String foodParcelId) {
        try {
            // Call the service to retrieve food parcel quantity
            Integer quantity = issuesService.getFoodParcelQuantity(foodParcelId);
            return ResponseEntity.ok(quantity);
        } catch (IllegalArgumentException ex) {
            // Handle specific validation-related exceptions
            return ResponseEntity.status(400).body(null);
        } catch (Exception ex) {
            // Handle general exceptions
            return ResponseEntity.status(500).body(null);
        }
    }

}


    // @PostMapping("/api/issue-food-parcel")
    // public ResponseEntity<String> issueFoodParcel(@RequestParam String member_id,
    //                                               @RequestParam String food_parcel_id,
    //                                               @RequestParam String collection_point_id,
    //                                               @RequestParam(required = false) String date_last_issued,
    //                                               @RequestParam(required = false) Integer amount_issued) {
    //     issuesService.issueFoodParcel(member_id, food_parcel_id, collection_point_id, date_last_issued, amount_issued);
    //     return ResponseEntity.ok("Food parcel successfully issued to member: " + member_id);
    // } 