package datn.be.auth.repository;

import datn.be.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query(value = " SELECT id, name, email, created_at AS joinedDate  FROM users  " +
            " ORDER BY created_at DESC LIMIT :page_size", nativeQuery = true)
    List<Object[]> getNewUser(@Param("page_size") int page_size);
}

