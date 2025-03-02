package com.BethsProjects.MyFoodPantryAPI.FoodPantryAPIClasses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;

import java.util.Collections;

import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin(origins = "*")  // This enables CORS for all origins
// @CrossOrigin(origins = {"*", "https://codepen.io", "https://<random_string>.ngrok-free.app"}) // added 15/11/2024 to enable cross origin request and interraction with Javascript

public class MemberController {

    @Autowired
    //private final MemberService memberService;
    private JdbcTemplate jdbcTemplate;
    private MemberService memberService;
    
    public MemberController(MemberService memberService) { 
        this.memberService = memberService;
    }

    // code to handle a GET request to fetch data based on the provided member name and ID, ensuring cross-origin accessibility,
    // and handling potential exceptions gracefully.
    // ORIGINAL CODE WITHOUT POSTCODE - ITS WORKING
    // code to handle a GET request to fetch data based on the provided member name and ID, ensuring cross-origin accessibility,
    // and handling potential exceptions gracefully.

    // http://localhost:8080/api/i/m08
    @GetMapping("/api/{members_name}/{member_id}")
    public List<Map<String, Object>> getData(@PathVariable String members_name, @PathVariable String member_id){
        String query = "SELECT * FROM View_members_due_or_not_due_for_food_parcels " + 
                       "WHERE "+
                       "members_name LIKE ? AND member_id = ? ";
        try {
            return jdbcTemplate.queryForList(query, new Object[] { "%" + members_name + "%", member_id });
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No results found for memberName: " + members_name + " and memberId: " + member_id);
            throw new RuntimeException("No results found for the given member name and ID.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error executing query: " + e.getMessage());
        }
    }    

// }

// BELOW CODE IS WORKING BUT NOT IN USE
// package com.BethsProjects.MyFoodPantryAPI.FoodPantryAPIClasses;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PutMapping;
// // import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.http.HttpHeaders;

// import java.util.Collections;

// import java.util.List;
// import java.util.Map;


// @RestController
// // @CrossOrigin(origins = "*")  // This enables CORS for all origins
// @CrossOrigin(origins = {"*", "https://codepen.io", "https://<random_string>.ngrok-free.app"}) // added 15/11/2024 to enable cross origin request and interraction with Javascript

// public class MemberController {

//     @Autowired
//     private final MemberService memberService;

//     public MemberController(MemberService memberService) { 
//         this.memberService = memberService;
//         }

    // Get member by id - working
    // http://localhost:8080/api/get-user-by-id/M06
    @GetMapping("/api/get-user-by-id/{memberId}") 
    public Map<String, Object> getUserById(@PathVariable String memberId) {
         return memberService.getUserById(memberId);
    }


    // Get member by address id - working
    // http://localhost:8080/api/get-user-by-address-id/A06
    @GetMapping("/api/get-user-by-address-id/{addressId}")
    public Map<String, Object> getUserByAddressId(@PathVariable String addressId) {
         return memberService.getUserByAddressId(addressId);
    }  

    // Get member by post code - working
    // http://localhost:8080/api/get-user-by-postCode/A06
    @GetMapping("/api/get-user-by-postCode/{postCode}")
    public Map<String, Object> getUserByPostCode(@PathVariable String postCode) {
         return memberService.getUserByPostCode(postCode);
    }  


    // Get member by member first name and last name - working
    // http://localhost:8080/api/get-user-by-first-and-last-name/mohamed/seladin
    @GetMapping("/api/get-user-by-first-and-last-name/{firstName}/{lastName}")
    public Map<String, Object> getUserByFirstAndLastName(@PathVariable String firstName, @PathVariable String lastName) {
         return memberService.getUserByFirstAndLastName(firstName, lastName);
    }


    // GET member by member last name and member ID - working
    // http://localhost:8080/api/get-user-by-lastName-and-id/seladin/M06
    @GetMapping("/api/get-user-by-lastName-and-id/{memberLastName}/{memberId}")
    public Map<String, Object> getUserByLastNameAndId(@PathVariable String memberLastName, @PathVariable String memberId) {
        return memberService.getUserByLastNameAndId(memberLastName, memberId);
    }


    // select a member from a MySQL View by member name and member id OR member name and postCode to check their eligibility for a food parcel 
    // GET http://localhost:8080/api/check-member-eligibility-by-name-Id-and-postcode/Mohamed/M06/B9 5XU    
    @GetMapping("/api/check-member-eligibility-by-name-Id-and-postcode/{memberName}/{memberId}/{postCode}")
    public ResponseEntity<List<Map<String, Object>>> checkEligibilityForAFoodParcel(@PathVariable String memberName, 
             @PathVariable String memberId, @PathVariable String postCode){
                List<Map<String, Object>> results = memberService.checkEligibilityForAFoodParcelByNameIdAndPostCode(memberName, memberId, postCode);
            if (results.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else {
                return new ResponseEntity<>(results, HttpStatus.OK);
            }
    } 
 
    
    // GET a member by member's first or last name and member id to check their eligibility for a food parcel - working
    // GET http://localhost:8080/api/check-member-eligibility-by-name-and-Id/Mohamed/M06
    @GetMapping(value = "/api/check-member-eligibility-by-name-and-Id/{memberName}/{memberId}", produces = "application/json")
    public ResponseEntity<List<Map<String, Object>>> checkEligibilityForAFoodParcelByNameAndId(@PathVariable String memberName,
                                                                                    @PathVariable String memberId) {
        try {
            // Log incoming request
            System.out.println("Request received for memberName: " + memberName + ", memberId: " + memberId);
            
            List<Map<String, Object>> results = memberService.checkEligibilityForAFoodParcelByNameAndId(memberName, memberId);
            
            // Log response
            System.out.println("Response: " + results);
            
            return ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                                .body(results);
        } catch (Exception e) {
            // Log error
            System.err.println("Error occurred: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                                .body(Collections.singletonList(Collections.singletonMap("error", e.getMessage())));
        }
    }


   // select a member from a MySQL View by member name and postCode to check their eligibility for a food parcel 
    // http://localhost:8080/api/check-member-eligibility-by-name-and-post-code/Mohamed/B9%205XU
    @GetMapping(value = "/api/check-member-eligibility-by-name-and-post-code/{memberName}/{postCode}", produces = "application/json")
    public ResponseEntity<List<Map<String, Object>>> checkEligibilityForAFoodParcelByNameAndPostCode(@PathVariable String memberName,
                                                                                    @PathVariable String postCode) {
        try {
            // Log incoming request
            System.out.println("Request received for memberName: " + memberName + ", memberId: " + postCode);
            
            List<Map<String, Object>> results = memberService.checkEligibilityForAFoodParcelByNameAndPostCode(memberName, postCode);
            
            // Log response
            System.out.println("Response: " + results);
            
            return ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                                .body(results);
        } catch (Exception e) {
            // Log error
            System.err.println("Error occurred: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                                .body(Collections.singletonList(Collections.singletonMap("error", e.getMessage())));
        }
    }    
  

    // Update member's main contact number - working
    // PUT http://localhost:8080/api/update-contact-number/mohamed/seladin/M06/582-343-9130 - old number
    // PUT http://localhost:8080/api/update-contact-number/mohamed/seladin/M06/582-343-9131 - updated number
    @PutMapping("/api/update-contact-number/{memberFirstName}/{memberLastName}/{memberId}/{newContactNumber}")
    public int updateMainContactNumber(@PathVariable String memberFirstName,
                                       @PathVariable String memberLastName,
                                       @PathVariable String memberId,
                                       @PathVariable String newContactNumber) {
        return memberService.updateMainContactNumber(memberFirstName, memberLastName, memberId, newContactNumber);
    }


// }


 



}




// to run the spring boot application
// ./mvnw spring-boot:run
