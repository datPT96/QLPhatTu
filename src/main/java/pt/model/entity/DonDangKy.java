package pt.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@Table(name = "DonDangKy")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonDangKy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer idDon;
    @JoinColumn(name = "PhatTuId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = {"donDangKyList", "phatTuDaoTrangList", "chuTriDaoTrangs"})
    @JsonBackReference
    private PhatTu phatTu;
    @Column(name = "TrangThaiDon", columnDefinition = "BIT")
    private Boolean trangThaiDon;
    @Column(name = "NgayGuiDon", columnDefinition = "datetime(6)")
    private LocalDateTime ngayGuiDon;
    @Column(name = "NgayXuLy", columnDefinition = "datetime(6)")
    private LocalDateTime ngayXuLy;
    @Column(name = "NguoiXuLyId")
    private Integer nguoiXuLyId;
    @JoinColumn(name = "DaoTrangId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = {"donDangKyList", "phatTuDaoTrangList"})
    private DaoTrang daoTrang;
}
