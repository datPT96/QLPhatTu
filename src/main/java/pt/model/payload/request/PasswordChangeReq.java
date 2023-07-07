package pt.model.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasswordChangeReq {
    @NotNull
    private Integer idPhatTu;
    @NotNull
    private String email;
    @NotNull
    private String oldPassword;
    @NotNull
    private String newPassword;
}
