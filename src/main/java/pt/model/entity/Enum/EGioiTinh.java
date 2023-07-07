package pt.model.entity.Enum;

public enum EGioiTinh {
    NAM(1), NU(2), OTHER(3);

    private final Integer value;

    EGioiTinh(Integer value){
        this.value = value;
    }
    public Integer getValue(){
        return value;
    }
}
