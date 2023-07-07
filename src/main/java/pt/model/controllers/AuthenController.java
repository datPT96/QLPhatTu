package pt.model.controllers;


import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.model.payload.request.SigninRequest;
import pt.model.payload.request.SignupRequest;
import pt.model.services.IAuthenticationService;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping(value = "api/v1/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthenController {
    @Autowired
    private IAuthenticationService authenticationService;

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

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody String signUpRequest) {
        return authenticationService.signupPhatTU(gson.fromJson(signUpRequest, SignupRequest.class));
    }
    @PostMapping("/signin")
    public ResponseEntity<?> signinUser(@RequestBody String signRequest){
        return authenticationService.signinPhatTu(gson.fromJson(signRequest, SigninRequest.class));
    }
}
