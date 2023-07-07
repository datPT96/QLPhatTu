package pt.model.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    @NotNull
    private String email;
    @NotNull
    private String password;
    private String ho;
    private String tenDem;
    @NotNull
    private String ten;
    private String phapDanh;
    private String soDienThoai;
    private LocalDate ngaySinh;
    @NotNull
    private String gioiTinh;
    private String anhChup;
}
