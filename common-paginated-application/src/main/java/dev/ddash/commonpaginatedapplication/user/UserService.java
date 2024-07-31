package dev.ddash.commonpaginatedapplication.user;


import dev.ddash.commonpaginatedapplication.pagination.PaginatedResponse;
import dev.ddash.commonpaginatedapplication.pagination.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.function.Function;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UserService {

    @Autowired
    private PaginationService paginationService;

    @Autowired
    private UserRepository userRepository;
    public PaginatedResponse<UserDTO> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageable);
        Function<User, UserDTO> converter = user -> new UserDTO(user.getId(), user.getName(), user.getEmail());

        return paginationService.findPaginated(pageable,userPage, converter, this::buildLink);
    }

    private Object buildLink(int page, int size) {
        return methodOn(UserController.class).getAllUsers(page, size);
    }
}