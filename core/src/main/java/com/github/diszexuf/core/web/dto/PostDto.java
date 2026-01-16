package com.github.diszexuf.core.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private Long id;

    private String text;

    private String tag;

    private String author;

}
