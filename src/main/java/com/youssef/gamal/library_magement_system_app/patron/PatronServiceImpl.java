package com.youssef.gamal.library_magement_system_app.patron;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class PatronServiceImpl implements PatronServiceInterface {


    private final PatronRepo patronRepo;

    @Override
    public Long add(Patron patron) {
        return patronRepo.save(patron).getId();
    }

    @Override
    public Patron findById(Long id) {
        return patronRepo.findById(id)
                .orElseThrow();
    }

    @Override
    public List<Patron> findAll() {
        return patronRepo.findAll();
    }

    @Override
    public void deleteById(Long id) {
        Patron patron = patronRepo.findById(id)
                        .orElseThrow();
        patronRepo.deleteById(patron.getId());
    }

    @Override
    public void updateById(Long id, Patron updatedPatron) {
        Patron patron = patronRepo.findById(id)
                .orElseThrow();

        patron.setName(updatedPatron.getName());
        patron.setEmail(updatedPatron.getEmail());
        patron.setPhone(updatedPatron.getPhone());
        patron.setAddress(updatedPatron.getAddress());
        patronRepo.save(patron);

    }
}
