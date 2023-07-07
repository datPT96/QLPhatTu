package pt.model.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pt.model.entity.Chua;
import pt.model.entity.PhatTu;
import pt.model.payload.response.MessageResponse;
import pt.model.repository.ChuaRepo;
import pt.model.repository.PhatTuRepo;
import pt.model.services.IChuaService;
import pt.utils.ValidatorIO;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChuaService implements IChuaService {
    @Autowired
    private ChuaRepo chuaRepo;

    @Autowired
    private PhatTuRepo phatTuRepo;

    @Autowired
    private ValidatorIO validatorIO;

    @Override
    public List<Chua> getListChua() {
        return chuaRepo.findAll();
    }

    @Override
    public Page<Chua> layDanhSachChua(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Chua> listChua = chuaRepo.findAll(pageable);
        return new PageImpl<>(listChua.getContent(), pageable, listChua.getTotalElements());
    }

    @Override
    public ResponseEntity<?> themChua(Chua chua) {
        if (!validatorIO.validator(chua)) {
            return ResponseEntity.badRequest().body(new MessageResponse(validatorIO.getViolationList()));
        }
        chua.setCapNhat(LocalDateTime.now());
        chuaRepo.save(chua);
        return ResponseEntity.ok(new MessageResponse("Them thanh cong"));
    }

    @Override
    public ResponseEntity<?> suaChua(Chua chua) {
        Chua chuaCurr = chuaRepo.findById(chua.getIdChua()).orElse(null);
        if (chuaCurr == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Chua khong ton tai"));
        }
        if (validatorIO.validator(chua)) {
            chuaCurr.setTenChua(chua.getTenChua());
            chuaCurr.setDiaChi(chua.getDiaChi());
            chuaCurr.setNgayThanhLap(chua.getNgayThanhLap());
            chuaCurr.setTruTriId(chua.getTruTriId());
            chuaCurr.setCapNhat(LocalDateTime.now());
            return ResponseEntity.ok(chuaRepo.save(chuaCurr));
        }
        return ResponseEntity.badRequest().body(new MessageResponse(validatorIO.getViolationList()));
    }

    @Override
    public ResponseEntity<?> xoaChua(Integer id) {
        Chua chuaDelete = chuaRepo.findById(id).orElse(null);
        if (chuaDelete == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Chua khong ton tai"));
        }
        for (PhatTu pt: chuaDelete.getPhatTuList()) {
            pt.setChua(null);
            phatTuRepo.save(pt);
        }
        chuaRepo.delete(chuaDelete);
        return ResponseEntity.ok(new MessageResponse("Xoa thanh cong"));
    }
}
