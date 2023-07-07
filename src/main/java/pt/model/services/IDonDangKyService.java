package pt.model.services;

import org.springframework.http.ResponseEntity;
import pt.model.entity.DonDangKy;
import pt.model.entity.PhatTu;
import pt.model.payload.request.DonDangKyReq;
import pt.model.payload.request.DuyetDonReq;

import java.util.List;

public interface IDonDangKyService {
    ResponseEntity<?> taoDonDangKy(DonDangKyReq donDangKy);
    ResponseEntity<?> duyetDon(DuyetDonReq duyetDonReq);
    List<DonDangKy> findByPhatTu(Integer id);
}
