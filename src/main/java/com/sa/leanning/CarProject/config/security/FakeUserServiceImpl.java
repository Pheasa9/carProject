package com.sa.leanning.CarProject.config.security;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class FakeUserServiceImpl implements UserService {
	@Autowired
	private PasswordEncoder pencode;

	@Override
	public Optional<AuthUser> loadUserByUsername(String username) {
		List<AuthUser> users = List.of(
				new AuthUser("SaAdmin", pencode.encode("Sa123"), RoleEnum.ADMIN.getGrantedAuthorities(), true, true,
						true, true),
				new AuthUser("SaSale", pencode.encode("Sa123"), RoleEnum.SALE.getGrantedAuthorities(), true, true, true,
						true));
		return users.stream().filter(user -> user.getUsername().equals(username)).findFirst();
	}

}
