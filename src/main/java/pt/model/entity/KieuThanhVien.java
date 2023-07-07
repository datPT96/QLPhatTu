package pt.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.Hibernate;
import pt.model.entity.Enum.EKieuThanhVien;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "KieuThanhVien")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class KieuThanhVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer idKieuThanhVien;
    @Column(name = "Code", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private EKieuThanhVien code;
    @Column(name = "TenKieu", nullable = false)
    @NotNull
    private String tenKieu;
    @OneToMany(mappedBy = "kieuThanhVien")
    @JsonIgnoreProperties(value = "kieuThanhVien")
    @ToString.Exclude
    private List<PhatTu> phatTuList;

    public KieuThanhVien(EKieuThanhVien code, String tenKieu) {
        this.code = code;
        this.tenKieu = tenKieu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        KieuThanhVien that = (KieuThanhVien) o;
        return idKieuThanhVien != null && Objects.equals(idKieuThanhVien, that.idKieuThanhVien);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
