package com.BlogApi.Payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

    private Integer categoryId;

    @NotBlank
    @Size(min = 4, message = "The minimum size for the category title is 4 characters")
    private String categoryTitle;

    @NotBlank
    @Size(min = 10, max = 1000, message = "The category description must be between 10 and 1000 characters")
    private String categoryDescription;

    // Constructor with all arguments to support deserialization
    public CategoryDto(Integer categoryId, String categoryTitle, String categoryDescription) {
        this.categoryId = categoryId;
        this.categoryTitle = categoryTitle;
        this.categoryDescription = categoryDescription;
    }
}


//package com.BlogApi.Payloads;
//
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@NoArgsConstructor
//@Getter
//@Setter
//public class CategoryDto {
//    private Integer categoryId;
//    
//    @NotBlank
//    @Size(min = 4, message = "The minimum size for the category title is 4 characters")
//    private String categoryTitle;
//
//    @NotBlank
//    @Size(min = 10, max = 1000, message = "The category description must be between 10 and 1000 characters")
//    private String categoryDescription;
//}
//
//
////package com.BlogApi.Payloads;
////
////import jakarta.validation.constraints.NotBlank;
////import jakarta.validation.constraints.Size;
////import lombok.Getter;
////import lombok.NoArgsConstructor;
////import lombok.Setter;
////
////@NoArgsConstructor
////@Getter
////@Setter
////
////public class CategoryDto {
////	
////	private Integer categoryId;
////	
////	@NotBlank
////	@Size(min = 4 , message = "min size of category Title of 4")
////	private String  categoryTitle;
////	
////	@NotBlank
////	@Size(min = 10 , max = 1000 , message = "max size of category Description of 1000")
////	private String  categoryDescription;
////
////}
