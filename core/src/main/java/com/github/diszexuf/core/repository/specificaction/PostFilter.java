package com.github.diszexuf.core.repository.specificaction;

import lombok.Data;

@Data
public class PostFilter {

    private String tag;

    private Long authorId;

    private String text;

    private Integer pageSize = 10;

    private Integer pageNumber = 0;
}
