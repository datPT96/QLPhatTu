package pt.model.controllers;

import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pt.model.entity.Chua;
import pt.model.services.IChuaService;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChuaController {
    @Autowired
    private IChuaService chuaService;

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
    )
            .create();

    @GetMapping(value = "listChua")
    @PreAuthorize("permitAll()")
    public List<Chua> getAll(){
        return chuaService.getListChua();
    }

    @GetMapping(value = "danhSachChua")
    public Page<Chua> danhSachChua(@RequestParam int page, int size) {
        return chuaService.layDanhSachChua(page, size);
    }

    @PostMapping(value = "themChua")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<?> themChua(@RequestBody String chua) {
        return chuaService.themChua(gson.fromJson(chua, Chua.class));
    }

    @PutMapping(value = "suaThongTinChua")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<?> suaChua(@RequestBody String chua) {
        return chuaService.suaChua(gson.fromJson(chua, Chua.class));
    }

    @DeleteMapping(value = "xoaChua")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<?> xoaChua(@RequestParam Integer idChua) {
        return chuaService.xoaChua(idChua);
    }
}
