package pt.model.services;


import pt.model.entity.Enum.EKieuThanhVien;
import pt.model.entity.KieuThanhVien;

import java.util.Optional;

public interface IKieuThanhVienService {
    Optional<KieuThanhVien> findByCode(EKieuThanhVien code);
}
