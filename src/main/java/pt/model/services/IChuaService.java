package pt.model.services;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import pt.model.entity.Chua;

import java.util.List;

public interface IChuaService {
    List<Chua> getListChua();
    Page<Chua> layDanhSachChua(int page, int size);
    ResponseEntity<?> themChua(Chua chua);
    ResponseEntity<?> suaChua(Chua chua);
    ResponseEntity<?> xoaChua(Integer id);
}
