package pt.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.model.entity.PhatTu;

import java.util.Optional;

@Repository
public interface PhatTuRepo extends JpaRepository<PhatTu, Integer> {
    @Query("SELECT phattu FROM PhatTu phattu WHERE (:ten IS NULL OR phattu.ten LIKE CONCAT('%',:ten,'%'))" +
            "AND (:phapDanh IS NULL OR phattu.phapDanh LIKE CONCAT('%',:phapDanh,'%'))" +
            "AND (:gioiTinh IS NULL OR phattu.gioiTinh = :gioiTinh)" +
            "AND (:trangThai IS NULL OR phattu.daHoanTuc = :trangThai)"
    )
    Page<PhatTu> timKiemPhatTu(@Param("ten") String ten,
                               @Param("phapDanh") String phapDanh,
                               @Param("gioiTinh") Integer gioiTinh,
                               @Param("trangThai") Boolean trangThai,
                               Pageable pageable);
    Optional<PhatTu> findByEmail(String email);
    @Query("Select case when (count(pt) > 0) then true else false end from PhatTu pt where pt.email like :email")
    Boolean existByEmail(@Param("email") String email);
}
