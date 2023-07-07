package pt.model.controllers;

import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pt.model.entity.DaoTrang;
import pt.model.payload.request.DonDangKyReq;
import pt.model.services.IDaoTrangService;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping(value = "api/v1/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DaoTrangController {
    @Autowired
    private IDaoTrangService daoTrangService;

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

    @GetMapping(value = "danhSachDaoTrang")
    public Page<DaoTrang> findAll(@RequestParam Integer page, Integer size) {
        return daoTrangService.danhSachDaoTrang(page, size);
    }

    @PostMapping(value = "themDaoTrang")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<?> taoDaoTrang(@RequestBody String daoTrang) {
        return daoTrangService.taoDaoTrang(gson.fromJson(daoTrang, DaoTrang.class));
    }

    @PutMapping(value = "capNhatDaoTrang")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<?> capNhatDaoTrang(@RequestBody String daoTrang) {
        return daoTrangService.suaDaoTrang(gson.fromJson(daoTrang, DaoTrang.class));
    }


}
