package com.youssef.gamal.library_magement_system_app.patron;

import com.youssef.gamal.library_magement_system_app.config.CachingConfig;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(cacheNames = CachingConfig.PATRONS_CACHE_NAME, key = "#id")
    public Patron findById(Long id) {
        return patronRepo.findById(id)
                .orElseThrow();
    }

    @Override
    @Cacheable(cacheNames = CachingConfig.PATRONS_CACHE_NAME, key = "#root.methodName")
    public List<Patron> findAll() {
        return patronRepo.findAll();
    }

    @Override
    @CacheEvict(cacheNames = CachingConfig.PATRONS_CACHE_NAME, key = "#id")
    public void deleteById(Long id) {
        Patron patron = patronRepo.findById(id)
                        .orElseThrow();
        patronRepo.deleteById(patron.getId());
    }

    @Override
    @CachePut(cacheNames = CachingConfig.PATRONS_CACHE_NAME, key = "#id")
    public Patron updateById(Long id, Patron updatedPatron) {
        Patron patron = patronRepo.findById(id)
                .orElseThrow();

        patron.setName(updatedPatron.getName());
        patron.setEmail(updatedPatron.getEmail());
        patron.setPhone(updatedPatron.getPhone());
        patron.setAddress(updatedPatron.getAddress());
        patron = patronRepo.save(patron);

        return patron;
    }
}
