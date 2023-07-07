package pt.model.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pt.model.entity.KieuThanhVien;

import java.util.Collection;
import java.util.List;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Integer idPhatTu;
    private String email;
    @JsonIgnoreProperties(value = "phatTuList")
    private KieuThanhVien kieuThanhVien;

    public JwtResponse(String token, Integer idPhatTu, String email, KieuThanhVien kieuThanhVien) {
        this.token = token;
        this.idPhatTu = idPhatTu;
        this.email = email;
        this.kieuThanhVien = kieuThanhVien;
    }
}
