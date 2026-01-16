package com.github.diszexuf.core.web.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostRequest {

    @Size(max = 120, message = "Максимальный размер текста - 120 символов")
    private String text;

    @Pattern(regexp = "^#.*", message = "Тег должен начинаться с '#'")
    private String tag;
}
