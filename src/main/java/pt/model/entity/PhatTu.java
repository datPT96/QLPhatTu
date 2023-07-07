package pt.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static pt.model.entity.Enum.EGioiTinh.NAM;

@Entity
@Table(name = "PhatTu")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PhatTu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer idPhatTu;
    @Column(name = "Ho" ,columnDefinition = "TEXT")
    private String ho;
    @Column(name = "TenDem", columnDefinition = "TEXT")
    private String tenDem;
    @Column(name = "Ten", columnDefinition = "TEXT")
    private String ten;
    @Column(name = "PhapDanh", columnDefinition = "TEXT")
    private String phapDanh;
    @Column(name = "AnhChup", columnDefinition = "TEXT")
    private String AnhChup;
    @Column(name = "SoDienThoai")
    @NotNull
    @Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})\\b")
    private String soDienThoai;
    @Column(name = "Email", columnDefinition = "TEXT")
    @NotNull
    @Pattern(regexp = "^[a-z][a-z0-9_\\.]{2,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$")
    private String email;
    @Column(name = "MatKhau", columnDefinition = "TEXT")
    @NotNull
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Mật khẩu phải có tối thiểu 8 ký tự có cả chữ hoa chữ thường và ký tự đặc biệt")
    @JsonIgnore
    private String password;
    @Column(name = "NgaySinh", columnDefinition = "date")
    private LocalDate ngaySinh;
    @Column(name = "NgayXuatGia", columnDefinition = "date")
    private LocalDate ngayXuatGia;
    @Column(name = "DaHoanTuc", columnDefinition = "BIT")
    private Boolean daHoanTuc;
    @Column(name = "NgayHoanTuc", columnDefinition = "date")
    private LocalDate ngayHoanTuc;
    @Column(name = "GioiTinh", columnDefinition = "INT")
    private Integer gioiTinh = NAM.getValue();
    @JoinColumn(name = "KieuThanhVienId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = "phatTuList")
    private KieuThanhVien kieuThanhVien;
    @Column(name = "NgayCapNhat", columnDefinition = "datetime(6)")
    private LocalDateTime ngayCapNhat = LocalDateTime.now();
    @JoinColumn(name = "ChuaId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = {"phatTuList"})
    private Chua chua;
    @OneToMany(mappedBy = "nguoiChuTri")
    @JsonIgnoreProperties(value = {"phatTuDaoTrangList", "nguoiChuTri", "donDangKyList"})
    @ToString.Exclude
    private List<DaoTrang> chuTriDaoTrangs;
    @OneToMany(mappedBy = "phatTu")
    @JsonIgnoreProperties(value = "phatTu")
    @ToString.Exclude
    private List<PhatTuDaoTrang> phatTuDaoTrangList;
    @OneToMany(mappedBy = "phatTu")
    @JsonIgnoreProperties(value = {"phatTu"})
    @JsonManagedReference
    @ToString.Exclude
    private List<DonDangKy> donDangKyList;

    public PhatTu(String ho, String tenDem, String ten, String phapDanh, String anhChup, String soDienThoai, String email, String password, LocalDate ngaySinh, LocalDate ngayXuatGia, Integer gioiTinh, Chua chua) {
        this.ho = ho;
        this.tenDem = tenDem;
        this.ten = ten;
        this.phapDanh = phapDanh;
        this.AnhChup = anhChup;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.password = password;
        this.ngaySinh = ngaySinh;
        this.ngayXuatGia = ngayXuatGia;
        this.gioiTinh = gioiTinh;
        this.chua = chua;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PhatTu phatTu = (PhatTu) o;
        return idPhatTu != null && Objects.equals(idPhatTu, phatTu.idPhatTu);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
