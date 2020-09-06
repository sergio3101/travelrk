package ru.flystar.travelrk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.flystar.travelrk.domain.persistents.CustomerInfo;
import ru.flystar.travelrk.domain.persistents.Region;
import ru.flystar.travelrk.repositories.RegionRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 01.11.2017.
 */
@Service
public class RegionService {
    private final RegionRepository regionRepository;

    @Autowired
    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public Region getRegionByName(String name) {
        return regionRepository.findByName(name);
    }

    public Region getRegionById(int id) {
        return regionRepository.findOne(id);
    }

    public List<Region> getRegionList() {
        return regionRepository.findByOrderByViewNameAsc();
    }

    @Transactional
    public Region addRegion(Region region) {
        return regionRepository.saveAndFlush(region);
    }

    @Transactional
    public boolean removeRegionById(int id) throws DataIntegrityViolationException {
        regionRepository.delete(id);
        return !regionRepository.exists(id);
    }
}
