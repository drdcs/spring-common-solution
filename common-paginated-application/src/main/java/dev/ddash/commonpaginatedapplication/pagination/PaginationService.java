package dev.ddash.commonpaginatedapplication.pagination;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface PaginationService {
    <T, U> PaginatedResponse<U> findPaginated(
            Pageable pageable,
            Page<? extends T> page,
            Function<T, U> converter,
            BiFunction<Integer, Integer, Object> linkBuilder);
}
