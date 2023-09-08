package com.selflearning.blogging.bloggingapplicationapi.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto
{
    private int categoryId;

    @NotBlank
    @Size(min = 4, message = "Title needs to contain at least 4 letters")
    private String categoryTitle;

    @NotBlank
    @Size(min = 10, message = "Description needs to contain at least 10 letters")
    private String categoryDescription;
}
