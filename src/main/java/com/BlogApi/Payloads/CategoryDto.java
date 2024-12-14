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
	@Size(min = 4 , message = "min size of category Title of 4")
	private String  categoryTitle;
	
	@NotBlank
	@Size(min = 10 , max = 1000 , message = "max size of category Description of 1000")
	private String  categoryDescription;

}
