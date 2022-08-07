package com.ciandt.feedfront.controllers.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErroPadrao {

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataDoErro;
    private Integer status;
    private String erro;
    private String caminho;
}
