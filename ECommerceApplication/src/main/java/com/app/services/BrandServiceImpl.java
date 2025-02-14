package com.app.services;

import com.app.entites.Brand;
import com.app.entites.Product;
import com.app.exceptions.APIException;
import com.app.exceptions.ResourceNotFoundException;
import com.app.payloads.BrandDTO;
import com.app.payloads.BrandResponse;
import com.app.repositories.BrandRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class BrandServiceImpl implements BrandService {

	@Autowired
	private BrandRepo brandRepo;
	
	@Autowired
	private ProductService productService;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public BrandDTO createBrand(Brand brand) {
		Brand savedBrand = brandRepo.findByBrandName(brand.getBrandName());

		if (savedBrand != null) {
			throw new APIException("Brand with the name '" + brand.getBrandName() + "' already exists !!!");
		}

		savedBrand = brandRepo.save(brand);

		return modelMapper.map(savedBrand, BrandDTO.class);
	}

	@Override
	public BrandResponse getBrand(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
		Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
		
		Page<Brand> pageCategories = brandRepo.findAll(pageDetails);

		List<Brand> categories = pageCategories.getContent();

		if (categories.size() == 0) {
			throw new APIException("No brand is created till now");
		}

		List<BrandDTO> brandDTOs = categories.stream()
				.map(brand -> modelMapper.map(brand, BrandDTO.class)).collect(Collectors.toList());

		BrandResponse brandResponse = new BrandResponse();
		
		brandResponse.setContent(brandDTOs);
		brandResponse.setPageNumber(pageCategories.getNumber());
		brandResponse.setPageSize(pageCategories.getSize());
		brandResponse.setTotalElements(pageCategories.getTotalElements());
		brandResponse.setTotalPages(pageCategories.getTotalPages());
		brandResponse.setLastPage(pageCategories.isLast());
		
		return brandResponse;
	}

	@Override
	public BrandDTO updateBrand(Brand brand, Long brandId) {
		Brand savedBrand = brandRepo.findById(brandId)
				.orElseThrow(() -> new ResourceNotFoundException("Brand", "brandId", brandId));

		brand.setBrandId(brandId);

		savedBrand = brandRepo.save(brand);

		return modelMapper.map(savedBrand, BrandDTO.class);
	}

	@Override
	public String deleteBrand(Long brandId) {
		Brand brand = brandRepo.findById(brandId)
				.orElseThrow(() -> new ResourceNotFoundException("Brand", "brandId", brandId));
		
		List<Product> products = brand.getProducts();

		products.forEach(product -> {
			productService.deleteProduct(product.getProductId());
		});
		
		brandRepo.delete(brand);

		return "Brand with brandId: " + brandId + " deleted successfully !!!";
	}

}
