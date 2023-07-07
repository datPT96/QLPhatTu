package pt.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.model.entity.PhatTuDaoTrang;

@Repository
public interface PhatTuDaoTrangRepo extends JpaRepository<PhatTuDaoTrang, Integer> {
}
