package com.ramona.capstone.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CollectionRequestDto {
    @NotBlank(message = "Collection name is required")
    String name;
}
