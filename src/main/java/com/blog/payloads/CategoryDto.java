package com.blog.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	
	private Integer categoryId;
	@NotBlank
	@Size(min = 4,message = "min size for category title is 4")
	private String catTitle;
	@Size(min = 5,message = "min size for category Description is 5")
	private String catDescription;

}
