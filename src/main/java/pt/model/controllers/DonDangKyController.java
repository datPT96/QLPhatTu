package pt.model.controllers;

import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pt.model.entity.DonDangKy;
import pt.model.payload.request.DonDangKyReq;
import pt.model.payload.request.DuyetDonReq;
import pt.model.services.IDonDangKyService;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DonDangKyController {
    @Autowired
    private IDonDangKyService donDangKyService;

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

    @PostMapping(value = "dangKyDaoTrang")
    public ResponseEntity<?> taoDonDangKy(@RequestBody String donDangKy) {
        return donDangKyService.taoDonDangKy(gson.fromJson(donDangKy, DonDangKyReq.class));
    }

    @PutMapping(value = "duyetDon")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<?> duyetDon(@RequestBody DuyetDonReq duyetReq) {
        return donDangKyService.duyetDon(duyetReq);
    }

    @GetMapping(value = "donDangKyTheoPhatTu")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public List<DonDangKy> findByPhatTu(@RequestParam Integer idPhatTu) {
        return donDangKyService.findByPhatTu(idPhatTu);
    }
}
