package pt.model.controllers;


import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.model.entity.PhatTu;
import pt.model.payload.request.PasswordChangeReq;
import pt.model.payload.request.UpdateRequest;
import pt.model.services.IPhatTuServices;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping(value = "api/v1/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PhatTuControllers {
    @Autowired
    private IPhatTuServices phatTuService;

    private final Gson gson = new GsonBuilder().registerTypeAdapter(
            LocalDate.class, new JsonDeserializer<LocalDate>() {
                @Override
                public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    return LocalDate.parse(json.getAsJsonPrimitive().getAsString());
                }
            }
    ).registerTypeAdapter(
            LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
                @Override
                public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    return LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                }
            }
    ).create();

    @GetMapping(value = "timKiem")
    public Page<PhatTu> timKiemPhatTu(@RequestParam(required = false) String ten, String phapDanh, Integer gioiTinh, Boolean trangThai, Integer page, Integer size) {
        return phatTuService.timKiemPhatTu(ten, phapDanh, gioiTinh, trangThai, page, size);
    }

    @GetMapping(value = "getPhatTu")
    public PhatTu findById(@RequestParam int id) {
        return phatTuService.findById(id);
    }

    @PutMapping(value = "doiMatKhau")
    public ResponseEntity<?> doiMatKhau(@RequestBody String passwordChange) {
        return phatTuService.doiMatKhau(gson.fromJson(passwordChange, PasswordChangeReq.class));
    }

    @PutMapping(value = "suaThongTin")
    public ResponseEntity<?> suaThongTin(@RequestBody String updateRequest) {
        return phatTuService.suaThongTin(gson.fromJson(updateRequest, UpdateRequest.class));
    }

    @DeleteMapping(value = "xoaPhatTu")
    public ResponseEntity<?> xoaPhatTu(@RequestParam Integer idPhatTu) {
        return phatTuService.xoaPhatTu(idPhatTu);
    }
}
