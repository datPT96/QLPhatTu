package pt.model.serviceimpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.model.entity.Enum.EKieuThanhVien;
import pt.model.entity.KieuThanhVien;
import pt.model.repository.KieuThanhVienRepo;
import pt.model.services.IKieuThanhVienService;

import java.util.Optional;

@Service
public class KieuThanhVienService implements IKieuThanhVienService {
    @Autowired
    private KieuThanhVienRepo kieuThanhVienRepo;
    @Override
    public Optional<KieuThanhVien> findByCode(EKieuThanhVien code) {
        return kieuThanhVienRepo.findByCode(code);
    }
}
