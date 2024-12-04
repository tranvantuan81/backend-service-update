package vn.tuanjava.service;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import vn.tuanjava.dto.request.UserRequestDTO;
import vn.tuanjava.dto.response.PageResponse;
import vn.tuanjava.dto.response.UserDetailResponse;
import vn.tuanjava.util.UserStatus;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailsService();

    long saveUser(UserRequestDTO request);

    void updateUser(long userId, UserRequestDTO request);

    void changeStatus(long userId, UserStatus status);

    void deleteUser(long userId);

    UserDetailResponse getUser(long userId);

    PageResponse<?> getAllUsersWithSortBy(int pageNo, int pageSize, String sortBy);

    PageResponse<?> getAllUsersWithSortByMultipleColumns(int pageNo, int pageSize, String... sorts);

    PageResponse<?> getAllUsersAndSearchWithPagingAndSorting(int pageNo, int pageSize, String search, String sortBy);

    PageResponse<?> advanceSearchWithCriteria(int pageNo, int pageSize, String sortBy, String address, String...search);

    PageResponse<?> advanceSearchWithSpecifications(Pageable pageable, String[] user, String[] address);

}
