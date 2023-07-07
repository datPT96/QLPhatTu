package pt.model.services;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import pt.model.entity.DaoTrang;
import pt.model.payload.request.DonDangKyReq;

public interface IDaoTrangService {
    Page<DaoTrang> danhSachDaoTrang(Integer page, Integer size);
    ResponseEntity<?> taoDaoTrang(DaoTrang daoTrang);
    ResponseEntity<?> suaDaoTrang(DaoTrang daoTrang);
}
