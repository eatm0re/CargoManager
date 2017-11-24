package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.CargoDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Cargo;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.City;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.CargoService;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.util.Loggable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;

@Service
public class CargoServiceImpl extends AbstractService<Cargo, CargoDTO> implements CargoService {

    public CargoServiceImpl() {
        super(Cargo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Loggable
    public long add(CargoDTO cargoDTO) {

        // creating cargo
        Cargo entity = new Cargo();

        // name
        String name = cargoDTO.getName();
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("Cargo name must be specified");
        }
        if (!isLongName(name)) {
            throw new IllegalArgumentException("Wrong cargo name");
        }
        entity.setName(name);

        // weight
        BigDecimal weightKg = cargoDTO.getWeightKg();
        if (weightKg != null) {
            if (weightKg.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Weight must be non-negative");
            }
            entity.setWeightKg(weightKg);
        }

        // location
        if (cargoDTO.getLocation() == null || cargoDTO.getLocation().getName() == null || cargoDTO.getLocation().getName().length() == 0) {
            throw new IllegalArgumentException("City name must be specified");
        }
        String cityName = cargoDTO.getLocation().getName();
        if (!isSimpleName(cityName)) {
            throw new IllegalArgumentException("Wrong city name");
        }
        City city = dao.getCityDAO().selectByName(cityName);
        if (city == null) {
            throw new EntityNotFoundException("City " + cityName + " not found");
        }
        entity.setLocation(city);

        // insert
        dao.getCargoDAO().insert(entity);
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Loggable
    public void change(CargoDTO cargoDTO) {

        // find cargo by ID
        long cargoId = cargoDTO.getId();
        if (cargoId <= 0) {
            throw new IllegalArgumentException("Cargo ID must be positive");
        }
        Cargo cargo = dao.getCargoDAO().selectById(cargoId);
        if (cargo == null) {
            throw new EntityNotFoundException("Cargo #" + cargoId + " not found");
        }

        // name
        String name = cargoDTO.getName();
        if (name != null && name.length() > 0 && !name.equals(cargo.getName())) {
            if (!isLongName(name)) {
                throw new IllegalArgumentException("Wrong cargo name");
            }
            cargo.setName(name);
        }

        // weight
        BigDecimal weightKg = cargoDTO.getWeightKg();
        if (weightKg != null && !weightKg.equals(cargo.getWeightKg())) {
            if (weightKg.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Weight must be non-negative");
            }
            cargo.setWeightKg(weightKg);
        }
    }
}
