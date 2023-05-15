package com.stc.ruaxe.service.dichvu;

import com.stc.ruaxe.dtos.dichvu.DichVuDto;
import com.stc.ruaxe.entities.DichVu;
import com.stc.ruaxe.entities.embedded.GiaDichVu;
import com.stc.ruaxe.exceptions.InvalidException;
import com.stc.ruaxe.exceptions.NotFoundException;
import com.stc.ruaxe.repositories.DichVuRepository;
import com.stc.ruaxe.utils.PageUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DichVuServiceImpl implements DichVuService{
    public final DichVuRepository dichVuRepository;
    @Override
    public Page<DichVu> filter(String search, int page, int size, String sort, String column) {
        Pageable pageable = PageUtils.createPageable(page, size, sort, column);
        return dichVuRepository.findByMaDichVuContainingOrTenDichVuContainingAllIgnoreCase(search,search, pageable);
    }

    @Override
    public DichVu getDichVuByMaDichVu(String maDichVu) {
        return dichVuRepository.findDichVuByMaDichVu(maDichVu)
                .orElseThrow(()-> new NotFoundException(String.format("User with id %s does not exist",maDichVu)));
    }

    @Override
    public List<DichVu> getAllDichVu(String search) {
        return null;
    }

    @Override
    public List<DichVu> finfAll() {
       return dichVuRepository.findAll();
    }

    @Override
    public DichVu addGiaDV(String maDichVu, GiaDichVu giaDichVu) {
        Optional<DichVu> dichVu= dichVuRepository.findDichVuByMaDichVu(maDichVu);
        if(dichVu==null)
        {
            throw new NotFoundException(String.format("Mã dịch vụ %s không tồn tại",maDichVu));
        }
        dichVu.get().getGiaDichVus().add(giaDichVu);
        return dichVuRepository.save(dichVu.get());

    }

    @Override
    public DichVu create(DichVuDto dto, Principal principal) {
       DichVu dichVu=new DichVu();

        if (ObjectUtils.isEmpty(dto.getMaDichVu())) {
            throw new InvalidException("Mã Dịch Vụ Không được để trống");
        }

        if (ObjectUtils.isEmpty(dto.getTenDichVu())) {
            throw new InvalidException("Tên Dịch Vụ Không được để trống!");
        }
        if (dichVuRepository.existsByMaDichVu(dto.getMaDichVu())) {
            throw new InvalidException(String.format("Mã dịch vụ đã tồn tại",
                    dto.getMaDichVu()));
        }

        dichVu.setMaDichVu(dto.getMaDichVu());
        dichVu.setTenDichVu(dto.getTenDichVu());
        dichVu.setTrangThai(true);
        dichVu.setNoiDung(dto.getNoiDung());
        dichVuRepository.save(dichVu);

        return dichVu;
    }

    @Override
    public DichVu update(String id, DichVuDto dto, Principal principal) {
        Optional<DichVu> dichVu=dichVuRepository.findById(id);
        if(!dichVu.isPresent())
            throw new NotFoundException(String.format("Không tìm thấy dịch vụ có ID %s",id));
        if (!dichVu.get().getMaDichVu().equals(dto.getMaDichVu())){
            // Nếu mã không giống mã cũ thì kiểm tra mã mới đã tồn tại trong database hay chưa
            if (dichVuRepository.existsByMaDichVu(dto.getMaDichVu())){
                throw new InvalidException(String.format("Mã dịch vụ %s đã tồn tại", dto.getMaDichVu()));
            }
            dichVu.get().setMaDichVu(dto.getMaDichVu());
        }
        dichVu.get().setTenDichVu(dto.getTenDichVu());
        dichVu.get().setNoiDung(dto.getNoiDung());
        return dichVuRepository.save(dichVu.get());

    }

    @Override
    public DichVu changeStatus(String maDichVu) {
        Optional<DichVu> dichVu = dichVuRepository.findDichVuByMaDichVu(maDichVu);
        if(!dichVu.isPresent())
            throw new NotFoundException(String.format("Mã Dịch Vụ %s không tồn tại", maDichVu));
        dichVu.get().setTrangThai(!dichVu.get().isTrangThai());
        return dichVuRepository.save(dichVu.get());
    }
}
