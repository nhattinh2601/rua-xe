package com.stc.ruaxe.dtos.dichvu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DichVuDto {
    private String maDichVu;

    private String tenDichVu;

    private String noiDung;

    //private List<String> giaDichVus = new ArrayList<>();
}
