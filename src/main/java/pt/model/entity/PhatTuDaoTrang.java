package pt.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PhatTuDaoTrang")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhatTuDaoTrang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer idPhatTuDaoTrang;
    @JoinColumn(name = "PhatTuId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = "phatTuDaoTrangList")
    @JsonBackReference
    private PhatTu phatTu;
    @JoinColumn(name = "DaoTrangId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = "phatTuDaoTrangList")
    @JsonBackReference
    private DaoTrang daoTrang;
    @Column(name = "DaThamGia", columnDefinition = "BIT")
    private Boolean daThamGia;
    @Column(name = "LyDoKhongThamGia", columnDefinition = "TEXT")
    private String lyDoKhongThamGia;
}
