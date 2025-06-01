package com.BethsProjects.MyFoodPantryAPI.FoodPantryAPIClasses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.ParameterMode;

import java.util.Map;

@Repository
public class IssuesRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    // ✅ Constructor injection ensures `JdbcTemplate` is available

    public IssuesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void issueFoodParcel(String member_id,
                                String food_parcel_id,
                                String collection_point_id,
                                String date_last_issued, 
                                Integer amount_issued) {
                                StoredProcedureQuery query = entityManager.createStoredProcedureQuery("Sproc_issue_a_FOOD_parcel_IF_one_is_due");

        query.registerStoredProcedureParameter("member_id", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("food_parcel_id", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("collection_point_id", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("date_last_issued", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("amount_issued", Integer.class, ParameterMode.IN);

        query.setParameter("member_id", member_id);
        query.setParameter("food_parcel_id", food_parcel_id);
        query.setParameter("collection_point_id", collection_point_id);
        query.setParameter("date_last_issued", date_last_issued);
        query.setParameter("amount_issued", amount_issued);

        query.execute();
    }

    // added 01 06 2025 to retrieve the date of last issue for a food parcel from MYSQL database
    public String findLastIssueDate(String member_id) {
        String sql = "SELECT date_last_issued FROM members_issued_food_parcels WHERE member_id = ? ORDER BY date_last_issued DESC LIMIT 1";
        
        try {
            Map<String, Object> result = jdbcTemplate.queryForMap(sql, member_id);
            return result.get("date_last_issued").toString(); // Convert Object to String safely

        } catch (EmptyResultDataAccessException e) {
            System.out.println("No previous issue found for memberId: " + member_id);
            return null; // Explicitly return NULL if no previous issue exists
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error executing query: " + e.getMessage());
        }
    }

}


// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.query.Procedure;
// import org.springframework.stereotype.Repository;

// @Repository
// public interface IssuesRepository extends JpaRepository<Void, Long> {
//     @Procedure(procedureName = "Sproc_issue_a_FOOD_parcel_IF_one_is_due")
//     void issueFoodParcel(String member_id, String food_parcel_id, String collection_point_id, String date_last_issued, int amount_issued);
// }
