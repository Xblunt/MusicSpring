package dev.MusicSpring.messages;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateSessionMessage {
    Boolean action;
    Double time;
    Long chatId;
    Boolean pause;
    Double currentTimeOnDevice;
}
