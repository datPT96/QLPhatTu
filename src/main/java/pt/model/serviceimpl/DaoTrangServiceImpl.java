package pt.model.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pt.model.entity.DaoTrang;
import pt.model.payload.response.MessageResponse;
import pt.model.repository.DaoTrangRepo;
import pt.model.services.IDaoTrangService;
import pt.utils.ValidatorIO;

import java.util.List;

@Service
public class DaoTrangServiceImpl implements IDaoTrangService {
    @Autowired
    private DaoTrangRepo daoTrangRepo;
    @Autowired
    private ValidatorIO validatorIO;

    @Override
    public Page<DaoTrang> danhSachDaoTrang(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<DaoTrang> list = daoTrangRepo.findAll(pageable);
        updateThoiGian();
        return new PageImpl<>(list.getContent(), pageable, list.getTotalElements());
    }


    @Override
    public ResponseEntity<?> taoDaoTrang(DaoTrang daoTrang) {
        if (!validatorIO.validator(daoTrang)) {
            return ResponseEntity.badRequest().body(new MessageResponse(validatorIO.getViolationList()));
        }
        daoTrang.setDaKetThuc(false);
        daoTrang.setSoThanhVienThamGia(0);
        return ResponseEntity.ok(daoTrangRepo.save(daoTrang));
    }

    @Override
    public ResponseEntity<?> suaDaoTrang(DaoTrang daoTrang) {
        DaoTrang daoTrangCurr = daoTrangRepo.findById(daoTrang.getIdDaoTrang()).orElse(null);
        if (daoTrangCurr == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Khong tim thay dao trang"));
        }
        if (validatorIO.validator(daoTrang)) {
            daoTrangCurr.setNoiDung(daoTrang.getNoiDung());
            daoTrangCurr.setNoiToChuc(daoTrang.getNoiToChuc());
            daoTrangCurr.setThoiGianToChuc(daoTrang.getThoiGianToChuc());
            daoTrangCurr.setNguoiChuTri(daoTrang.getNguoiChuTri());
            return ResponseEntity.ok(daoTrangRepo.save(daoTrangCurr));
        }
        return ResponseEntity.badRequest().body(new MessageResponse(validatorIO.getViolationList()));
    }

    private void updateThoiGian() {
        List<DaoTrang> expireds = daoTrangRepo.findByExpiredDate();
        if (expireds.isEmpty()) {
            return;
        }
        for (DaoTrang dt : expireds) {
            dt.setDaKetThuc(true);
            daoTrangRepo.save(dt);
        }
    }
}
