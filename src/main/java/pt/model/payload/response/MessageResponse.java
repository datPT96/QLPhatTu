package pt.model.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
public class MessageResponse {
    private String message;
    private List<String> messages;

    public MessageResponse(String message){
        this.message = message;
    }

    public MessageResponse(List<String> messages){
        this.messages = messages;
    }
}
