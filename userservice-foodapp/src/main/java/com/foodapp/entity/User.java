package com.foodapp.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userId")
	@SequenceGenerator(name = "userId", sequenceName = "userId", initialValue = 100, allocationSize = 1)

	@Schema(description = "Unique User ID", example = "101")
	private Integer userId;

	@NotBlank(message = "Name is required")
	@Schema(description = "User Full Name", example = "Khushbu Sharma")
	private String name;

	@Column(unique = true)
	@NotBlank(message = "Email cannot be leave empty")
	@Email(message = "Invalid email format")
	@Schema(description = "User Email Address", example = "abc@gmail.com")
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Email must contain a valid domain")
	private String email;

	@Column(unique = true)
	@NotNull
	@Pattern(regexp = "^[0-9]{10}$", message = "Phone number must contain 10 digits")
	@Schema(description = "10 Digit Mobile Number", example = "987XXXXXXX")
	private String phonenumber;

	@NotBlank(message = "Address is required")
	@Schema(description = "User Residential Address", example = "Patiala, Punjab")
	private String address;

}
