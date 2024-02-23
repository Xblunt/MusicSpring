package dev.MusicSpring.db.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Time;
@Data
@AllArgsConstructor
public class SessionDTO {
    private Long id;
    private Double time;
    private Boolean action;
    private Boolean pause;
    private Double currentTimeOnDevice;
}
