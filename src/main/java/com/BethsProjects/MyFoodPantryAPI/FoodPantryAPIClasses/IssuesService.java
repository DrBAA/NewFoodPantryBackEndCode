package com.BethsProjects.MyFoodPantryAPI.FoodPantryAPIClasses;

import org.springframework.stereotype.Service;

@Service
public class IssuesService {

    private final IssuesRepository repository;

    // Constructor-based dependency injection
    public IssuesService(IssuesRepository repository) {
        this.repository = repository;
    }

    public void issueFoodParcel(String member_id, String food_parcel_id, String collection_point_id, String date_last_issued, Integer amount_issued){
        repository.issueFoodParcel(member_id, food_parcel_id, collection_point_id, date_last_issued, amount_issued);
    }
}



// import org.springframework.stereotype.Service;

// @Service
// public class IssuesService {

//     private final IssuesRepository issuesRepository;

//     // Constructor-based dependency injection
//     public IssuesService(IssuesRepository issuesRepository) {
//         this.issuesRepository = issuesRepository;
//     }

//     public void issueFoodParcel(String member_id, String food_parcel_id, String collection_point_id, String date_last_issued, Integer amount_issued) {
//         issuesRepository.issueFoodParcel(member_id, food_parcel_id, collection_point_id, date_last_issued, amount_issued);
//     }
// }

