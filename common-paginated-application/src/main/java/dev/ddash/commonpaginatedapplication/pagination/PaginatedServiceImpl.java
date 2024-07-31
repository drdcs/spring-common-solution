package dev.ddash.commonpaginatedapplication.pagination;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
public class PaginatedServiceImpl implements PaginationService{

    @Override
    public <T, U> PaginatedResponse<U> findPaginated(
            Pageable pageable,
            Page<? extends T> page,
            Function<T, U> converter,
            BiFunction<Integer, Integer, Object> linkBuilder) {

        PaginatedResponse<U> response = new PaginatedResponse<>(
                page.getContent().stream().map(converter).collect(Collectors.toList()),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
        addPaginationLinks(response, page, pageable, linkBuilder);
        return response;
    }

    private <U> void addPaginationLinks(PaginatedResponse<U> response, Page<?> page, Pageable pageable, BiFunction<Integer, Integer, Object> linkBuilder) {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        if (page.hasNext()) {
            response.add(linkTo(linkBuilder.apply(pageNumber + 1, pageSize)).withRel("next"));
        }

        if (page.hasPrevious()) {
            response.add(linkTo(linkBuilder.apply(pageNumber - 1, pageSize)).withRel("prev"));
        }

        response.add(linkTo(linkBuilder.apply(pageNumber, pageSize)).withSelfRel());
    }

}

