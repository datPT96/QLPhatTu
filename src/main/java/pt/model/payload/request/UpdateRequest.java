package pt.model.payload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import pt.model.entity.Chua;
import pt.model.entity.KieuThanhVien;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class UpdateRequest {
    private Integer idPhatTu;
    @NotNull
    private String ho;
    @NotNull
    private String tenDem;
    @NotNull
    private String ten;
    @NotNull
    private String phapDanh;
    private String AnhChup;
    @NotNull
    @Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})\\b")
    private String soDienThoai;
    private LocalDate ngaySinh;
    private LocalDate ngayXuatGia;
    private Boolean daHoanTuc;
    private LocalDate ngayHoanTuc;
    private String gioiTinh;
    private KieuThanhVien kieuThanhVien;
    private Integer idChua;
}
