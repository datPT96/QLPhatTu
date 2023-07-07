package pt.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pt.model.entity.DaoTrang;
import pt.model.entity.PhatTu;

import java.util.List;

@Repository
public interface DaoTrangRepo extends JpaRepository<DaoTrang, Integer> {
//    @Query("select dt from DaoTrang dt")
//    Page<DaoTrang> findAll(Pageable pageable);

    @Query("select d from DaoTrang d where d.thoiGianToChuc < CURRENT_TIMESTAMP and d.daKetThuc = false")
    List<DaoTrang> findByExpiredDate();
//    List<DaoTrang> findByNguoiChuTri(PhatTu phatTu);
}
