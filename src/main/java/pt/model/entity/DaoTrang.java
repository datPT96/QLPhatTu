package pt.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "DaoTrang")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DaoTrang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer idDaoTrang;
    @Column(name = "NoiToChuc", columnDefinition = "TEXT")
    private String noiToChuc;
    @Column(name = "SoThanhVienThamGia")
    private Integer soThanhVienThamGia;
    @JoinColumn(name = "NguoiChuTriId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = {"chuTriDaoTrangs"})
    private PhatTu nguoiChuTri;
    @Column(name = "ThoiGianToChuc", columnDefinition = "datetime(6)")
    private LocalDateTime thoiGianToChuc;
    @Column(name = "NoiDung", columnDefinition = "TEXT")
    private String noiDung;
    @Column(name = "DaKetThuc", columnDefinition = "BIT")
    private Boolean daKetThuc;
    @OneToMany(mappedBy = "daoTrang")
    @JsonIgnoreProperties(value = "daoTrang")
    private List<DonDangKy> donDangKyList;
    @OneToMany(mappedBy = "daoTrang")
    @JsonIgnoreProperties(value = "daoTrang")
    private List<PhatTuDaoTrang> phatTuDaoTrangList;
}
