package datn.be.repository;

import datn.be.model.UserView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserViewRepository extends JpaRepository<UserView, Long> {
    @Query("SELECT uv FROM UserView uv")
    Page<UserView> getLists(Pageable pageable);

    @Query(value = " SELECT id, name, email, created_at AS joinedDate  FROM users  " +
            " ORDER BY created_at DESC LIMIT :page_size", nativeQuery = true)
    List<Object[]> getNewUser(@Param("page_size") int page_size);
}
