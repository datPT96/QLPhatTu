package pt.model.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DuyetDonReq {
    private List<Integer> listDon;
    private Integer nguoiXuLyId;
}
