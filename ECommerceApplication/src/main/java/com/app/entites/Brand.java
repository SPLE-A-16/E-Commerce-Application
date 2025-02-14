package com.app.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "brand")
@NoArgsConstructor
@AllArgsConstructor
public class Brand {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long brandId;

	@NotBlank
	@Size(min = 5, message = "Brand name must contain atleast 5 characters")
	private String brandName;

	@OneToMany(mappedBy = "brand", cascade =  CascadeType.ALL )
	private List<Product> products;

}
