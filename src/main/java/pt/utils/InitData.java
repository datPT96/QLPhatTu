package pt.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pt.model.entity.Chua;
import pt.model.entity.Enum.EGioiTinh;
import pt.model.entity.Enum.EKieuThanhVien;
import pt.model.entity.KieuThanhVien;
import pt.model.entity.PhatTu;
import pt.model.repository.ChuaRepo;
import pt.model.repository.KieuThanhVienRepo;
import pt.model.repository.PhatTuRepo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class InitData implements CommandLineRunner {
    @Autowired
    private KieuThanhVienRepo kieuThanhVienRepo;
    @Autowired
    private ChuaRepo chuaRepo;
    @Autowired
    private PhatTuRepo phatTuRepo;
    @Autowired
    private PasswordEncoder encoder;

    private void initKieuThanhVien() {
        List<KieuThanhVien> lists = new ArrayList<>();
        lists.add(new KieuThanhVien(EKieuThanhVien.ADMIN, "QUAN_LY"));
        lists.add(new KieuThanhVien(EKieuThanhVien.MANAGER, "TRU_TRI"));
        lists.add(new KieuThanhVien(EKieuThanhVien.MEMBER, "PHAT_TU"));
        kieuThanhVienRepo.saveAll(lists);
    }

    private void initChua() {
        var chua = new Chua("CHUA 1", LocalDate.of(1900, 10, 1), "HN", 1);
        chuaRepo.save(chua);
    }

    private void initAdmin() {
        var admin = new PhatTu();
        admin.setKieuThanhVien(kieuThanhVienRepo.findByCode(EKieuThanhVien.ADMIN).get());
        admin.setGioiTinh(EGioiTinh.NAM.getValue());
        admin.setEmail("admin@gmail.com");
        admin.setPassword(encoder.encode("Admin@1234"));
        admin.setSoDienThoai("0355555555");
        admin.setTen("admin");
        phatTuRepo.save(admin);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello World from CommandLineRunner!");
            System.out.println("Executing the task...");
//            initKieuThanhVien();
//            initChua();
//            initAdmin();
    }
}
