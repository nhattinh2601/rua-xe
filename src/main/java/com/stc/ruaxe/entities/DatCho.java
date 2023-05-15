package com.stc.ruaxe.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stc.ruaxe.entities.embedded.ThongTinDatCho;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "dat-cho")
public class DatCho {
    @Id
    private String id;


    private String email;

    private List<ThongTinDatCho> thongTinDatChos = new ArrayList<>();


    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm")
    private Date thoiGian;


    private String canDan;


    private String trangThaiDatCho;

    private boolean trangThai = true;
}
