package pt.model.serviceimpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pt.model.entity.Chua;
import pt.model.entity.Enum.EGioiTinh;
import pt.model.entity.PhatTu;
import pt.model.payload.request.PasswordChangeReq;
import pt.model.payload.request.UpdateRequest;
import pt.model.payload.response.MessageResponse;
import pt.model.repository.ChuaRepo;
import pt.model.repository.KieuThanhVienRepo;
import pt.model.repository.PhatTuRepo;
import pt.model.services.IPhatTuServices;
import pt.utils.ValidatorIO;

import java.time.LocalDateTime;
import java.util.Collections;

import static pt.model.entity.Enum.EGioiTinh.*;

@Service
public class PhatTuServiceImpl implements IPhatTuServices {
    @Autowired
    private PhatTuRepo phatTuRepo;
    @Autowired
    private KieuThanhVienRepo kieuThanhVienRepo;
    @Autowired
    private ChuaRepo chuaRepo;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private ValidatorIO validatorIO;
//    @Autowired
//    private ModelMapper modelMapper;

//    @Autowired
//    JwtTokenProvider jwtTokenProvider;

    @Override
    public PageImpl<PhatTu> timKiemPhatTu(String ten, String phapDanh, Integer gioiTinh, Boolean trangThai, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<PhatTu> list = phatTuRepo.timKiemPhatTu(ten, phapDanh, gioiTinh, trangThai, pageable);
        return new PageImpl<>(list.getContent(), pageable, list.getTotalElements());
    }

    @Override
    public PhatTu findById(Integer id) {
        return phatTuRepo.findById(id).orElse(null);
    }

//    @Override
//    public boolean existByEmail(String email) {
//        return phatTuRepo.existByEmail(email);
//    }

//    @Override
//    public PhatTu saveOrUpdate(PhatTu phatTu) {
//        return phatTuRepo.save(phatTu);
//    }

    @Override
    public ResponseEntity<?> doiMatKhau(PasswordChangeReq request) {
        if (!validatorIO.validator(request)) {
            return ResponseEntity.badRequest().body(new MessageResponse(validatorIO.getViolationList()));
        }
        PhatTu phatTu = phatTuRepo.findById(request.getIdPhatTu()).orElseThrow(() -> new RuntimeException("Khong tim thay phat tu"));
        if (!encoder.matches(request.getOldPassword(), phatTu.getPassword())) {
            return ResponseEntity.badRequest().body(new MessageResponse(Collections.singletonList("Mat khau cu khong dung")));
        }
        if (!phatTu.getEmail().equalsIgnoreCase(request.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse(Collections.singletonList("Sai thong tin email")));
        }
        phatTu.setPassword(encoder.encode(request.getNewPassword()));
        phatTuRepo.save(phatTu);
        return ResponseEntity.ok(new MessageResponse(Collections.singletonList("Doi thanh cong")));
    }

    @Override
    public ResponseEntity<?> suaThongTin(UpdateRequest request) {
        PhatTu phatTu = phatTuRepo.findById(request.getIdPhatTu()).orElse(null);
        Chua chua = chuaRepo.findById(request.getIdChua()).orElse(null);
        if (phatTu != null) {
            phatTu.setHo(request.getHo());
            phatTu.setTenDem(request.getTenDem());
            phatTu.setTen(request.getTen());
            phatTu.setPhapDanh(request.getPhapDanh());
            phatTu.setAnhChup(request.getAnhChup());
            phatTu.setSoDienThoai(request.getSoDienThoai());
            phatTu.setNgaySinh(request.getNgaySinh());
            phatTu.setNgayXuatGia(request.getNgayXuatGia());
            phatTu.setNgayHoanTuc(request.getNgayHoanTuc());
            phatTu.setDaHoanTuc(request.getDaHoanTuc());
            switch (request.getGioiTinh()) {
                case "nam" -> phatTu.setGioiTinh(NAM.getValue());
                case "nu" -> phatTu.setGioiTinh(NU.getValue());
                default -> phatTu.setGioiTinh(OTHER.getValue());
            }
//            phatTu.setKieuThanhVien(request.getKieuThanhVien());
            phatTu.setChua(chua);
            phatTu.setNgayCapNhat(LocalDateTime.now());
            phatTuRepo.save(phatTu);
            return ResponseEntity.ok(new MessageResponse(Collections.singletonList("Sua thanh cong")));
        }
        return ResponseEntity.badRequest().body(new MessageResponse(Collections.singletonList("Khong tim thay phat tu")));
    }

    @Override
    public ResponseEntity<?> xoaPhatTu(Integer id) {
        PhatTu pt = phatTuRepo.findById(id).orElse(null);
        if (pt == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("khong tim thay phat tu"));
        }
        pt.setDaHoanTuc(true);
        phatTuRepo.save(pt);
        return ResponseEntity.ok(new MessageResponse("Xoa thanh cong"));
    }

}
