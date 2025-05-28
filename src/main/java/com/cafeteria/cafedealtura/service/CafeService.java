package com.cafeteria.cafedealtura.service;

import com.cafeteria.cafedealtura.dto.CafeDTO;
import com.cafeteria.cafedealtura.exception.BadRequestException;
import com.cafeteria.cafedealtura.exception.ResourceNotFoundException;
import com.cafeteria.cafedealtura.model.Cafe;
import com.cafeteria.cafedealtura.repository.CafeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CafeService {

    @Autowired
    private CafeRepository cafeRepository;

    public List<CafeDTO> findAll() {
        return cafeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CafeDTO findById(Long id) {
        Cafe cafe = cafeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Café", "id", id));
        return convertToDTO(cafe);
    }

    @Transactional
    public CafeDTO create(CafeDTO cafeDTO) {
        if (cafeRepository.existsByName(cafeDTO.getName())) {
            throw new BadRequestException("Ya existe un café con ese nombre");
        }
        Cafe cafe = convertToEntity(cafeDTO);
        return convertToDTO(cafeRepository.save(cafe));
    }

    @Transactional
    public CafeDTO update(Long id, CafeDTO cafeDTO) {
        Cafe existingCafe = cafeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Café", "id", id));

        // Verificar si el nuevo nombre ya existe para otro café
        if (!existingCafe.getName().equals(cafeDTO.getName()) &&
                cafeRepository.existsByName(cafeDTO.getName())) {
            throw new BadRequestException("Ya existe un café con ese nombre");
        }

        existingCafe.setName(cafeDTO.getName());
        existingCafe.setDescription(cafeDTO.getDescription());
        existingCafe.setPrice(cafeDTO.getPrice());
        existingCafe.setOrigin(cafeDTO.getOrigin());

        return convertToDTO(cafeRepository.save(existingCafe));
    }

    @Transactional
    public void delete(Long id) {
        if (!cafeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Café", "id", id);
        }
        cafeRepository.deleteById(id);
    }

    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        List<Cafe> cafes = cafeRepository.findAll();

        stats.put("totalCafes", cafes.size());
        stats.put("precioPromedio", cafes.stream()
                .mapToDouble(Cafe::getPrice)
                .average()
                .orElse(0.0));
        stats.put("origenes", cafes.stream()
                .collect(Collectors.groupingBy(
                        Cafe::getOrigin,
                        Collectors.counting())));

        return stats;
    }

    @Transactional
    public CafeDTO updateStock(Long id, int quantity) {
        Cafe cafe = cafeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Café", "id", id));

        if (quantity < 0) {
            throw new BadRequestException("La cantidad no puede ser negativa");
        }

        // Aquí podrías agregar lógica adicional para el manejo del stock
        // Por ejemplo, actualizar un campo de stock en la entidad Cafe

        return convertToDTO(cafeRepository.save(cafe));
    }

    private CafeDTO convertToDTO(Cafe cafe) {
        CafeDTO dto = new CafeDTO();
        dto.setId(cafe.getId());
        dto.setName(cafe.getName());
        dto.setDescription(cafe.getDescription());
        dto.setPrice(cafe.getPrice());
        dto.setOrigin(cafe.getOrigin());
        return dto;
    }

    private Cafe convertToEntity(CafeDTO dto) {
        Cafe cafe = new Cafe();
        cafe.setName(dto.getName());
        cafe.setDescription(dto.getDescription());
        cafe.setPrice(dto.getPrice());
        cafe.setOrigin(dto.getOrigin());
        return cafe;
    }
}
