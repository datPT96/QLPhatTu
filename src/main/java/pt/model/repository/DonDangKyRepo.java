package pt.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.model.entity.DonDangKy;
import pt.model.entity.PhatTu;

import java.util.List;

@Repository
public interface DonDangKyRepo extends JpaRepository<DonDangKy, Integer> {
    @Query("select dk from DonDangKy dk where dk.phatTu.idPhatTu = :id ")
    List<DonDangKy> findByPhatTuId(@Param("id")Integer id);
}
