package pt.model.services;


import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import pt.model.entity.PhatTu;
import pt.model.payload.request.PasswordChangeReq;
import pt.model.payload.request.UpdateRequest;

public interface IPhatTuServices {
    Page<PhatTu> timKiemPhatTu(String ten, String phapDanh, Integer gioiTinh, Boolean trangThai, Integer page, Integer size);

    PhatTu findById(Integer id);

    //    boolean existByEmail(String email);
//    PhatTu saveOrUpdate(PhatTu phatTu);
    ResponseEntity<?> doiMatKhau(PasswordChangeReq request);

    ResponseEntity<?> suaThongTin(UpdateRequest request);

    ResponseEntity<?> xoaPhatTu(Integer id);
}
