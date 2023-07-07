package pt.model.serviceimpl;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pt.model.entity.Chua;
import pt.model.entity.Enum.EKieuThanhVien;
import pt.model.entity.KieuThanhVien;
import pt.model.entity.PhatTu;
import pt.model.payload.request.SigninRequest;
import pt.model.payload.request.SignupRequest;
import pt.model.payload.response.JwtResponse;
import pt.model.payload.response.MessageResponse;
import pt.model.repository.ChuaRepo;
import pt.model.repository.KieuThanhVienRepo;
import pt.model.repository.PhatTuRepo;
import pt.model.services.IAuthenticationService;
import pt.security.jwt.JwtProvider;
import pt.security.services.PhatTuDetails;

import java.time.LocalDate;
import java.util.Collections;

import static pt.model.entity.Enum.EGioiTinh.*;

@Service
public class AuthenticationService implements IAuthenticationService {
    private final PhatTuRepo phatTuRepo;
    private final KieuThanhVienRepo kieuThanhVienRepo;
    private final ChuaRepo chuaRepo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtTokenProvider;

    public AuthenticationService(PhatTuRepo phatTuRepo, KieuThanhVienRepo kieuThanhVienRepo, ChuaRepo chuaRepo, PasswordEncoder encoder, AuthenticationManager authenticationManager, JwtProvider jwtTokenProvider) {
        this.phatTuRepo = phatTuRepo;
        this.kieuThanhVienRepo = kieuThanhVienRepo;
        this.chuaRepo = chuaRepo;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    //    public boolean existByEmail(String email) {
//        return phatTuRepo.existByEmail(email);
//    }
    @Override
    public ResponseEntity<?> signupPhatTU(SignupRequest request) {
        if (phatTuRepo.existByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse(Collections.singletonList("Email da dang ki")));
        }
        KieuThanhVien kieuThanhVien = kieuThanhVienRepo.findByCode(EKieuThanhVien.MEMBER).orElse(null);

        PhatTu phatTu = new PhatTu();
        phatTu.setHo(request.getHo());
        phatTu.setTenDem(request.getTenDem());
        phatTu.setTen(request.getTen());
        phatTu.setPhapDanh(request.getPhapDanh());
        phatTu.setNgaySinh(request.getNgaySinh());
        switch (request.getGioiTinh()) {
            case "nam" -> phatTu.setGioiTinh(NAM.getValue());
            case "nu" -> phatTu.setGioiTinh(NU.getValue());
            default -> phatTu.setGioiTinh(OTHER.getValue());
        }
        phatTu.setSoDienThoai(request.getSoDienThoai());
        phatTu.setEmail(request.getEmail());
        phatTu.setPassword(encoder.encode(request.getPassword()));
        phatTu.setAnhChup(request.getAnhChup());
        phatTu.setDaHoanTuc(false);
        phatTu.setNgayXuatGia(LocalDate.now());
        phatTu.setKieuThanhVien(kieuThanhVien);
        phatTuRepo.save(phatTu);
        return ResponseEntity.ok(new MessageResponse(Collections.singletonList("Dang ky thanh caong")));
    }

    @Override
    public ResponseEntity<?> signinPhatTu(SigninRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.genarateToken(authentication);
        PhatTuDetails phatTu = (PhatTuDetails) authentication.getPrincipal();
        String code = phatTu.getAuthorities().iterator().next().toString();
        KieuThanhVien kieuThanhVien = kieuThanhVienRepo.findByCode(EKieuThanhVien.valueOf(code)).orElse(null);
        return ResponseEntity.ok(new JwtResponse(token, phatTu.getId(), phatTu.getUsername(), kieuThanhVien));
    }
}
