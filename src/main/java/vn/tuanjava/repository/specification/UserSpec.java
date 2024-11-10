package vn.tuanjava.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import vn.tuanjava.model.User;
import vn.tuanjava.util.Gender;

public class UserSpec {

    public static Specification<User> hasFirstName(String firstName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("firstName"), "%" + firstName + "%");
    }
    public static Specification<User> notEqualGender(Gender gender) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("gender"), gender);
    }
}
