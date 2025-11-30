package com.FreteGen.FreteGen.dto;


import com.FreteGen.FreteGen.user.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
