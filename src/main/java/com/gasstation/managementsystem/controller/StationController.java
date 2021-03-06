package com.gasstation.managementsystem.controller;

import com.gasstation.managementsystem.exception.custom.CustomBadRequestException;
import com.gasstation.managementsystem.exception.custom.CustomNotFoundException;
import com.gasstation.managementsystem.model.dto.station.StationDTO;
import com.gasstation.managementsystem.model.dto.station.StationDTOCreate;
import com.gasstation.managementsystem.model.dto.station.StationDTOUpdate;
import com.gasstation.managementsystem.service.StationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;


@RestController
@RequestMapping("/api/v1")
@CrossOrigin
@Tag(name = "Station", description = "API for station")
@RequiredArgsConstructor
public class StationController {
    private final StationService stationService;

    @Operation(summary = "View All Station")
    @GetMapping("/stations")
    public HashMap<String, Object> getAll(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                                          @RequestParam(name = "pageSize", required = false) Integer pageSize,
                                          Principal principal) {
        if (pageSize != null) {
            return stationService.findAll(PageRequest.of(pageIndex - 1, pageSize), principal);
        }
        return stationService.findAll(principal);
    }

    @Operation(summary = "Find Station by id")
    @GetMapping("/stations/{id}")
    public StationDTO getOne(@PathVariable(name = "id") Integer id) throws CustomNotFoundException {
        return stationService.findById(id);
    }

    @Operation(summary = "Create new Station")
    @PostMapping("/stations")
    public StationDTO create(@Valid @RequestBody StationDTOCreate stationDTOCreate) throws CustomBadRequestException {
        return stationService.create(stationDTOCreate);
    }

    @Operation(summary = "Update Station by id")
    @PutMapping("/stations/{id}")
    public StationDTO update(@PathVariable(name = "id") Integer id, @Valid @RequestBody StationDTOUpdate stationDTOUpdate) throws CustomBadRequestException, CustomNotFoundException {
        return stationService.update(id, stationDTOUpdate);
    }

    @Operation(summary = "Delete Station by id")
    @DeleteMapping("/stations/{id}")
    public StationDTO delete(@PathVariable(name = "id") Integer id) throws CustomNotFoundException {
        return stationService.delete(id);
    }
}
