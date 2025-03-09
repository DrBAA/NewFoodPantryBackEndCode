package com.BethsProjects.MyFoodPantryAPI.FoodPantryAPIClasses;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
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