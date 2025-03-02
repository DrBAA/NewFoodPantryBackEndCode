package com.BethsProjects.MyFoodPantryAPI.FoodPantryAPIClasses;

// import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


// configuring CORS at the global level to allow all origins
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
            }
        };
    }
}




// Here's the CORS configuration you'll need to make sure requests from Live Server are allowed:
// @Configuration
// public class WebConfig implements WebMvcConfigurer {

//     @Bean
//     public WebMvcConfigurer corsConfigurer() {
//         return new WebMvcConfigurer() {
//             @Override
//             public void addCorsMappings(CorsRegistry registry) {
//                 registry.addMapping("/api/**")
//                         .allowedOrigins( 
//                 "http://localhost:5500",
//                            "http://127.0.0.1:5500",
//                            "https://codepen.io",
//                            "https://0d2a-81-102-212-160.ngrok-free.app")
//                         .allowedMethods("GET", "POST", "PUT", "DELETE")
//                         .allowedHeaders("*")
//                         .allowCredentials(true);
//             }
//         };
//     }
// }







// REQUESTS FROM CODE PEN

/*
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOriginPatterns("http://localhost:8080", "https://codepen.io")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}

*/