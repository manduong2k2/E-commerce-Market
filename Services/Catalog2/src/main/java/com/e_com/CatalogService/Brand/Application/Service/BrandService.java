package com.e_com.CatalogService.Brand.Application.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.e_com.CatalogService.Brand.Application.DTO.Request.CreateBrandRequest;
import com.e_com.CatalogService.Brand.Application.DTO.Request.UpdateBrandRequest;
import com.e_com.CatalogService.Brand.Application.DTO.Response.BrandResponse;
import com.e_com.CatalogService.Brand.Domain.Contract.IBrandRepository;
import com.e_com.CatalogService.Brand.Domain.Contract.IBrandService;
import com.e_com.CatalogService.Brand.Domain.Model.Brand;
import com.e_com.CatalogService.Shared.Domain.Contract.IEventPublisher;
import com.e_com.CatalogService.Shared.Infrastructure.Event.EventOptions;

import jakarta.transaction.Transactional;

@Service
public class BrandService implements IBrandService {
    @Autowired
    public IBrandRepository brandRepository;
    @Autowired
    public IEventPublisher eventPublisher; 

    public List<BrandResponse> getAllBrands() {
        return brandRepository.findAll().stream()
                .map(BrandResponse::new)
                .toList();
    }

    public BrandResponse getBrand(UUID brandId) {
        return brandRepository.findById(brandId)
                .map(BrandResponse::new)
                .orElseThrow(() -> new RuntimeException("Brand not found"));
    }

    @Transactional
    public BrandResponse createBrand(CreateBrandRequest request) {
        Brand brand = new Brand(
                null,
                request.getName(),
                request.getImage(),
                request.getDescription()
        );

        brand = brandRepository.save(brand);

        publishDomainEvents(brand, "brand.created");

        return new BrandResponse(brand);
    }

    @Transactional
    public BrandResponse updateBrand(UUID brandId, UpdateBrandRequest request) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        brand.setName(request.getName());
        brand.setDescription(request.getDescription());

        brand = brandRepository.save(brand);

        publishDomainEvents(brand, "brand.updated");
        
        return new BrandResponse(brand);
    }

    @Transactional
    public void deleteBrand(UUID brandId) {
        brandRepository.delete(brandId);
    }

    @Async
    private void publishDomainEvents(Brand brand, String queue) {
        brand.getDomainEvents()
                .forEach(event -> eventPublisher.publish(event, new EventOptions(queue, false)));

        brand.clearDomainEvents();
    }
}
