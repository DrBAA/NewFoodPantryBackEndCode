package com.BethsProjects.MyFoodPantryAPI.FoodPantryAPIClasses;

import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.ParameterMode;

@Repository
public class IssuesRepository {

    @PersistenceContext
    private EntityManager entityManager;

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
}


// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.query.Procedure;
// import org.springframework.stereotype.Repository;

// @Repository
// public interface IssuesRepository extends JpaRepository<Void, Long> {
//     @Procedure(procedureName = "Sproc_issue_a_FOOD_parcel_IF_one_is_due")
//     void issueFoodParcel(String member_id, String food_parcel_id, String collection_point_id, String date_last_issued, int amount_issued);
// }
