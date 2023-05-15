package com.stc.ruaxe.service.datcho;

import com.stc.ruaxe.dtos.datcho.DatChoDto;
import com.stc.ruaxe.entities.DatCho;
import com.stc.ruaxe.entities.embedded.ThongTinDatCho;
import com.stc.ruaxe.exceptions.InvalidException;
import com.stc.ruaxe.exceptions.NotFoundException;
import com.stc.ruaxe.repositories.DatChoRepository;
import com.stc.ruaxe.utils.EnumTrangThaiDatCho;
import com.stc.ruaxe.utils.PageUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DatChoServiceImpl implements DatChoService{
    public final DatChoRepository datChoRepository;


    @Override
    public Page<DatCho> filter(String search, int page, int size, String sort, String column) {
        Pageable pageable = PageUtils.createPageable(page, size, sort, column);
        return datChoRepository.findByIdContainingOrEmailContainingAllIgnoreCase(search,search, pageable);
    }

    @Override
    public DatCho getDatChoByMaDatCho(String maDatCho) {
        return null;
    }

    @Override
    public Optional<DatCho> findById(String Id) {
        return datChoRepository.findById(Id);
    }

    @Override
    public List<DatCho> getAllDatCho(String search) {
        return null;
    }

    @Override
    public List<DatCho> finfAll() {
        return datChoRepository.findAll();
    }

    @Override
    public DatCho addThongTinDatCho(String id, ThongTinDatCho thongTinDatCho) {
        Optional<DatCho> datCho=datChoRepository.findById(id);
       // Optional<DichVu> dichVu= dichVuRepository.findDichVuByMaDichVu(maDichVu);
        if(!datCho.isPresent())
        {
            throw new NotFoundException(String.format("Id  %s không tồn tại",id));
        }
        datCho.get().getThongTinDatChos().add(thongTinDatCho);
        return datChoRepository.save(datCho.get());
    }

    @Override
    public DatCho create(DatChoDto dto) {

        DatCho datCho=new DatCho();

        if (ObjectUtils.isEmpty(dto.getEmail())) {
            throw new InvalidException("Email Không được để trống");
        }

        if (ObjectUtils.isEmpty(dto.getThoigian())) {
            throw new InvalidException("Thoi gian3 Không được để trống!");
        }
        datCho.setEmail(dto.getEmail());
        datCho.setCanDan(dto.getCanDan());
        datCho.setThoiGian(dto.getThoigian());
        datCho.setTrangThaiDatCho(EnumTrangThaiDatCho.DAT_CHO.name());
        datCho.setTrangThai(true);

        datChoRepository.save(datCho);

        return datCho;
    }

    @Override
    public DatCho update(String id, DatChoDto dto) {

        Optional<DatCho> datCho=datChoRepository.findById(id);
        if(!datCho.isPresent())
            throw new NotFoundException(String.format("Không tìm thấy DatCho có ID %s",id));

        datCho.get().setEmail(dto.getEmail());
        datCho.get().setCanDan(dto.getCanDan());
        datCho.get().setThoiGian(dto.getThoigian());
        return datChoRepository.save(datCho.get());
    }


    @Override
    public DatCho changeStatus(String id) {
        Optional<DatCho> datCho = datChoRepository.findById(id);
        if(!datCho.isPresent())
            throw new NotFoundException(String.format("Mã dat cho %s không tồn tại",id));
        datCho.get().setTrangThai(!datCho.get().isTrangThai());
        return datChoRepository.save(datCho.get());
    }
}
