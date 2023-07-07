package pt.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Chua")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Chua {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer idChua;
    @Column(name = "TenChua", nullable = false)
    @NotNull
    private String tenChua;
    @Column(name = "NgayThanhLap", nullable = false, columnDefinition = "date")
    @NotNull
    private LocalDate ngayThanhLap;
    @Column(name = "DiaChi")
    private String diaChi;
    @Column(name = "TruTriId", nullable = false)
    @NotNull
    private Integer truTriId;
    @Column(name = "CapNhat", columnDefinition = "datetime(6)")
    private LocalDateTime capNhat;
    @OneToMany(mappedBy = "chua")
    @JsonIgnoreProperties(value = {"chua"})
    @ToString.Exclude
    private List<PhatTu> phatTuList;

    public Chua(String tenChua, LocalDate ngayThanhLap, String diaChi, Integer truTriId) {
        this.tenChua = tenChua;
        this.ngayThanhLap = ngayThanhLap;
        this.diaChi = diaChi;
        this.truTriId = truTriId;
        this.capNhat = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Chua chua = (Chua) o;
        return idChua != null && Objects.equals(idChua, chua.idChua);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
