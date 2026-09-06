package com.foodapp.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.foodapp.dto.UserDTO;

@FeignClient(name="user-service"
)
public interface UserFeignClient {
    @GetMapping("users/userId/{id}")
    public UserDTO getUserById(@PathVariable Integer id);

}
