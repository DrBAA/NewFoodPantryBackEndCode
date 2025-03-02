package com.BethsProjects.MyFoodPantryAPI.FoodPantryAPIClasses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
  

@Service
public class MemberService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    // Get member by member ID - working
    public Map<String, Object> getUserById(String memberId) {
         String query = "SELECT * FROM members_personal_details WHERE member_id = ?";
         try { return jdbcTemplate.queryForMap(query, new Object[] { memberId });
         }
         catch (Exception e) { e.printStackTrace();
            throw new RuntimeException("Error executing query: " + e.getMessage());
         }
    }

    // Get member by address ID - working    
    public Map<String, Object> getUserByAddressId(String addressId) {
        String query = "SELECT * FROM members_personal_details WHERE address_id = ?";
        try { return jdbcTemplate.queryForMap(query, new Object[] { addressId });
        }
        catch (Exception e) { e.printStackTrace();
           throw new RuntimeException("Error executing query: " + e.getMessage());
        }
    }


    // Get member by postCode - working
    public Map<String, Object> getUserByPostCode(String postCode) {
        String query = "SELECT * FROM members_address WHERE post_code LIKE ?";
        try { return jdbcTemplate.queryForMap(query, new Object[] { postCode });
        }
        catch (Exception e) { e.printStackTrace();
           throw new RuntimeException("Error executing query: " + e.getMessage());
        }
   }



    // Get member by member first name and last name - working    
    public Map<String, Object> getUserByFirstAndLastName(String firstName, String lastName) {
        String query = "SELECT * FROM members_personal_details WHERE first_name = ? AND last_name = ?";
        try { return jdbcTemplate.queryForMap(query, new Object[] { firstName, lastName });
        }
        catch (Exception e) { e.printStackTrace();
           throw new RuntimeException("Error executing query: " + e.getMessage());
        }
    }

    // GET member by member last name and member ID - working 
    public Map<String, Object> getUserByLastNameAndId(String memberLastName, String memberId) {
         String query = "SELECT * FROM members_personal_details WHERE last_name = ? AND member_id = ?";
         try { return jdbcTemplate.queryForMap(query, memberLastName, memberId);
         }
         catch (EmptyResultDataAccessException e) {
            Map<String, Object> emptyResult = new HashMap<>();
            emptyResult.put("message", "No user found with the given name and ID.");
            return emptyResult;
         }
         catch (Exception e) { e.printStackTrace();
              throw new RuntimeException("Error executing query: " + e.getMessage());
         }
    }


    // select a member from a view by member name and member id to check their eligibility for a food parcel 
    public List<Map<String, Object>> checkEligibilityForAFoodParcelByNameAndId(String memberName, String memberId) {
        String query = "SELECT * FROM View_members_due_or_not_due_for_food_parcels " + 
                       "WHERE "+
                       "members_name LIKE ? AND member_id = ? ";
        try {
            return jdbcTemplate.queryForList(query, new Object[] { "%" + memberName + "%", memberId });
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No results found for memberName: " + memberName + " and memberId: " + memberId);
            throw new RuntimeException("No results found for the given member name and ID.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error executing query: " + e.getMessage());
        }
    } 

    // select a member from a view by member name and member post code to check their eligibility for a food parcel 
    public List<Map<String, Object>> checkEligibilityForAFoodParcelByNameAndPostCode(String memberName, String postCode) {
        String query = "SELECT * FROM View_members_due_or_not_due_for_food_parcels " + 
                       "WHERE "+
                       "members_name LIKE ? AND post_code LIKE ? ";
        try {
            return jdbcTemplate.queryForList(query, new Object[] { "%" + memberName + "%", "%" + postCode + "%" });
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No results found for memberName: " + memberName + " and memberId: " + postCode);
            throw new RuntimeException("No results found for the given member name and postcode");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error executing query: " + e.getMessage());
        }
    }


    // select a member from a view by member name and member id OR member name and postCode to check their eligibility for a food parcel - working
    public List<Map<String, Object>> checkEligibilityForAFoodParcelByNameIdAndPostCode(String memberName, String memberId, String postCode) {
        String query = "SELECT * FROM View_members_due_or_not_due_for_food_parcels " + 
                       "WHERE (members_name LIKE ? AND member_id LIKE ?) " +
                        "OR post_code LIKE ?";
        try {
            return jdbcTemplate.queryForList(query, new Object[] { "%" + memberName + "%", memberId + "%", postCode });
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No results found for memberName: " + memberName + " and memberId: " + memberId  + " and postCode: " + postCode);
            throw new RuntimeException("No results found for the given member name and ID.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error executing query: " + e.getMessage());
        }
    } 

    // Update member's maincontact number - working    
    public int updateMainContactNumber(String memberFirstName, String memberLastName, String memberId, String newContactNumber) {
        String query = "UPDATE members_personal_details SET main_contact_number = ? WHERE first_name = ? AND last_name = ? AND member_id = ?";
        return jdbcTemplate.update(query, newContactNumber, memberFirstName, memberLastName, memberId);
   }    
    


}




// BELOW CODE NOT FULL PROOF OF SQL INJECTIONS as it's currently constructed using string concatenation with user-supplied inputs, which can potentially make it vulnerable to SQL injection attacks.
// To make the query more secure, you should use prepared statements with parameterized queries as in the above code.
// In Spring's JdbcTemplate, this can be done by passing the parameters separately from the SQL string.
// The below Code: Passes the parameters directly within the method call: jdbcTemplate.queryForList(query, "%" + memberFirstName + "%", memberId).
// Although it uses placeholders (?) in the SQL query, it concatenates the parameters directly within the method call, which could still potentially expose the code to SQL injection risks if not handled properly.

// Revised Code above: Uses an Object array to pass parameters: jdbcTemplate.queryForList(query, new Object[] { "%" + memberFirstName + "%", memberId }).
// Uses parameterized queries by passing parameters in a separate array, which ensures that the database engine treats them as data, not executable code. This effectively mitigates the risk of SQL injection attacks.

// @Service
// public class MemberService {

//     @Autowired
//     private JdbcTemplate jdbcTemplate;

//     public List<Map<String, Object>> checkEligibilityForAFoodParcel(String memberFirstName, String memberId) {
//         String query = "SELECT member_id, members_name, post_code, support_issued,  collection_point_location, date_last_issued, next_issue_due_date, days_since_last_issue, IF((date_last_issued IS NULL) OR (days_since_last_issue >= 7), 'YES', 'NO') AS Issue_a_food_parcel_or_Not FROM View_members_issued_with_food_parcels WHERE members_name LIKE ? AND member_id = ?";
//         return jdbcTemplate.queryForList(query, "%" + memberFirstName + "%", memberId);
//     }
    
// }  
