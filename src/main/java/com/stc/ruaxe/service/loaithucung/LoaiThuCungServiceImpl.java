package com.stc.ruaxe.service.loaithucung;

import com.stc.ruaxe.dtos.loaithucung.LoaiThuCungDto;
import com.stc.ruaxe.entities.LoaiThuCung;
import com.stc.ruaxe.exceptions.InvalidException;
import com.stc.ruaxe.exceptions.NotFoundException;
import com.stc.ruaxe.repositories.LoaiThuCungRepository;
import com.stc.ruaxe.utils.PageUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class LoaiThuCungServiceImpl implements LoaiThuCungService{
    public final LoaiThuCungRepository loaiThuCungRepository;
    @Override
    public Page<LoaiThuCung> filter(String search, int page, int size, String sort, String column) {
        Pageable pageable = PageUtils.createPageable(page, size, sort, column);
        return loaiThuCungRepository.findByMaLoaiThuCungContainingOrTenLoaiThuCungContainingAllIgnoreCase(search,search, pageable);
    }

    @Override
    public LoaiThuCung getLoaiThuCungByMaLoaiThuCung(String maLoaiThuCung) {
        return loaiThuCungRepository.findLoaiThuCungByMaLoaiThuCung(maLoaiThuCung);
    }

    @Override
    public List<LoaiThuCung> getAllLoaiThuCung(String search) {
        return null;
    }

    @Override
    public List<LoaiThuCung> finfAll() {
        return loaiThuCungRepository.findAll();
    }

    @Override
    public LoaiThuCung create(LoaiThuCungDto dto) {
        LoaiThuCung loaiThuCung = new LoaiThuCung();

        if (ObjectUtils.isEmpty(dto.getMaLoaiThuCung())) {
            throw new InvalidException("Mã Loại Không được để trống");
        }
        if (ObjectUtils.isEmpty(dto.getTenLoaiThuCung())) {
            throw new InvalidException("Tên loại thú cưng không được để trống");
        }
        if (loaiThuCungRepository.existsByMaLoaiThuCung(dto.getMaLoaiThuCung())) {
            throw new InvalidException(String.format("Mã loại thú cưng đã tồn tại, vui lòng nhập mã khác",
                    dto.getMaLoaiThuCung()));
        }
        loaiThuCung.setMaLoaiThuCung(dto.getMaLoaiThuCung());
        loaiThuCung.setTenLoaiThuCung(dto.getTenLoaiThuCung());
        loaiThuCung.setTrangThai(true);
        loaiThuCungRepository.save(loaiThuCung);

        return loaiThuCung;
    }

    @Override
    public LoaiThuCung update(String id, LoaiThuCungDto dto) {
        Optional<LoaiThuCung> loaiThuCung=loaiThuCungRepository.findById(id);
        if(!loaiThuCung.isPresent())
            throw new NotFoundException(String.format("ID %s Không tồn tại",id));
        if (!loaiThuCung.get().getMaLoaiThuCung().equals(dto.getMaLoaiThuCung())){
            // Nếu mã không giống mã cũ thì kiểm tra mã mới đã tồn tại trong database hay chưa
            if (loaiThuCungRepository.existsByMaLoaiThuCung(dto.getMaLoaiThuCung())){
                throw new InvalidException(String.format("Mã loại thú cưng %s đã tồn tại", dto.getMaLoaiThuCung()));
            }
        }
        loaiThuCung.get().setTenLoaiThuCung(dto.getTenLoaiThuCung());
        loaiThuCung.get().setMaLoaiThuCung(dto.getMaLoaiThuCung());
        return loaiThuCungRepository.save(loaiThuCung.get());
    }


    @Override
    public LoaiThuCung changeStatus(String maLoaiThuCung) {
        LoaiThuCung loaiThuCung = loaiThuCungRepository.findLoaiThuCungByMaLoaiThuCung(maLoaiThuCung);
        if(loaiThuCung==null)
            throw new NotFoundException(String.format("Mã loại thú Cưng %s không tồn tại", maLoaiThuCung));
        loaiThuCung.setTrangThai(!loaiThuCung.isTrangThai());
        return loaiThuCungRepository.save(loaiThuCung);
    }
}
