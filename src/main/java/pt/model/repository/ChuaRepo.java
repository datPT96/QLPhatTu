package pt.model.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import pt.model.entity.Chua;

@Repository
public interface ChuaRepo extends JpaRepository<Chua, Integer> {
    Page<Chua> findAll(Pageable pageable);

}
