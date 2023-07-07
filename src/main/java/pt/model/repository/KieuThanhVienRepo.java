package pt.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.model.entity.Enum.EKieuThanhVien;
import pt.model.entity.KieuThanhVien;

import java.util.Optional;

@Repository
public interface KieuThanhVienRepo extends JpaRepository<KieuThanhVien, Integer> {
    Optional<KieuThanhVien> findByCode(EKieuThanhVien code);
}
