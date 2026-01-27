package com.ramona.capstone.services;

import com.ramona.capstone.dtos.BrandDto;
import com.ramona.capstone.dtos.BrandRequestDto;
import com.ramona.capstone.entities.Brand;
import com.ramona.capstone.exceptions.ResourceNotFoundException;
import com.ramona.capstone.mappers.BrandMapper;
import com.ramona.capstone.repositories.BrandRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BrandService {
  private final BrandRepository brandRepository;
  private final BrandMapper brandMapper;

  public List<BrandDto> getAllAvailableBrands() {
    return brandRepository.findAll().stream().map(brandMapper::toDto).toList();
  }

  public BrandDto getBrandById(Long id) {
    return brandRepository
        .findById(id)
        .map(brandMapper::toDto)
        .orElseThrow(() -> new ResourceNotFoundException("Brand not found:" + id));
  }

  @Transactional
  public BrandDto createBrand(BrandRequestDto request) {
    if (brandRepository.findByName(request.getName()).isPresent()) {
      throw new ResourceNotFoundException("Brand already exists:" + request.getName());
    }
    Brand brand = brandMapper.toEntity(request);
    return brandMapper.toDto(brandRepository.save(brand));
  }
}
