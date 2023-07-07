package pt.model.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pt.model.entity.DaoTrang;
import pt.model.entity.DonDangKy;
import pt.model.entity.PhatTuDaoTrang;
import pt.model.payload.request.DonDangKyReq;
import pt.model.payload.request.DuyetDonReq;
import pt.model.payload.response.MessageResponse;
import pt.model.repository.DaoTrangRepo;
import pt.model.repository.DonDangKyRepo;
import pt.model.repository.PhatTuDaoTrangRepo;
import pt.model.services.IDonDangKyService;
import pt.utils.ValidatorIO;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DonDangKyServiceImpl implements IDonDangKyService {
    @Autowired
    private ValidatorIO validatorIO;
    @Autowired
    private DaoTrangRepo daoTrangRepo;
    @Autowired
    private DonDangKyRepo donDangKyRepo;
    @Autowired
    private PhatTuDaoTrangRepo phatTuDaoTrangRepo;

    @Override
    public ResponseEntity<?> taoDonDangKy(DonDangKyReq donDangKy) {
        if (!validatorIO.validator(donDangKy)) {
            return ResponseEntity.badRequest().body(new MessageResponse(validatorIO.getViolationList()));
        }
        DaoTrang daoTrangCurr = daoTrangRepo.findById(donDangKy.getDaoTrang().getIdDaoTrang()).orElse(null);
        if (daoTrangCurr == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Dang ky khong thanh cong"));
        } else if (daoTrangCurr.getDaKetThuc()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Dao trang da ket thuc"));
        }
        DonDangKy don = new DonDangKy();
        don.setDaoTrang(donDangKy.getDaoTrang());
        don.setPhatTu(donDangKy.getPhatTu());
        don.setNgayGuiDon(LocalDateTime.now());
        don.setTrangThaiDon(false);
        donDangKyRepo.save(don);
        demNguoiThamGia(daoTrangCurr);
        return ResponseEntity.ok(new MessageResponse("Dang ky thanh cong"));
    }

    @Override
    public ResponseEntity<?> duyetDon(DuyetDonReq duyetDonReq) {
        for (Integer don : duyetDonReq.getListDon()) {
            DonDangKy donDangKy = donDangKyRepo.findById(don).orElse(null);
            if (donDangKy == null) {
                return ResponseEntity.badRequest().body(new MessageResponse("Khong tim thay don"));
            }
            donDangKy.setTrangThaiDon(true);
            donDangKy.setNgayXuLy(LocalDateTime.now());
            donDangKy.setNguoiXuLyId(duyetDonReq.getNguoiXuLyId());
            donDangKyRepo.save(donDangKy);
            var phatTuDaoTrang = PhatTuDaoTrang.builder()
                    .phatTu(donDangKy.getPhatTu())
                    .daoTrang(donDangKy.getDaoTrang())
                    .daThamGia(true)
                    .lyDoKhongThamGia("khong");
            phatTuDaoTrangRepo.save(phatTuDaoTrang.build());
        }
        return ResponseEntity.ok(new MessageResponse("Duyet thanh cong"));
    }

    @Override
    public List<DonDangKy> findByPhatTu(Integer id) {
        return donDangKyRepo.findByPhatTuId(id);
    }

    private void demNguoiThamGia(DaoTrang daoTrang) {
        int dem = 0;
        for (DonDangKy don : daoTrang.getDonDangKyList()) {
            dem++;
        }
        daoTrang.setSoThanhVienThamGia(dem);
        daoTrangRepo.save(daoTrang);
    }
}
