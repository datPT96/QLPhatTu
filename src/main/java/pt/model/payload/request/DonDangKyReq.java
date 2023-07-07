package pt.model.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import pt.model.entity.DaoTrang;
import pt.model.entity.PhatTu;

@Data
@AllArgsConstructor
public class DonDangKyReq {
    private DaoTrang daoTrang;
    private PhatTu phatTu;
}
