package com.github.diszexuf.core.repository.specificaction;

import com.github.diszexuf.core.entity.Post;
import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;

public interface PostSpecification {

    static Specification<Post> withFilter(PostFilter filter) {
        Specification<Post> spec = Specification.unrestricted();

        if (filter != null) {
            spec = spec.and(isEquals(filter.getAuthorId(), "author", "id"))
                    .and(contains("tag", filter.getTag()))
                    .and(contains("text", filter.getText()));
        }

        return spec;
    }

    private static <T> Specification<Post> isEquals(T object, String... fieldPath) {
        return ((root, query, criteriaBuilder) -> {
            if (object == null) {
                return criteriaBuilder.conjunction();
            }

            Path<?> rootByPath = root;
            for (String field : fieldPath) {
                rootByPath = rootByPath.get(field);
            }

            return criteriaBuilder.equal(rootByPath, object);
        });
    }

    private static Specification<Post> contains(String fieldName, String keyword) {
        return ((root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.isBlank()) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.like(root.get(fieldName), "%" + keyword + "%");
        });
    }

}
