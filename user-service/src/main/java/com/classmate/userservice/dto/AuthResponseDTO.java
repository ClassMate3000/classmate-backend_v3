package com.classmate.userservice.dto;

// Added firstName and lastName so the frontend can display user initials
public record AuthResponseDTO(Long userId, String token, String firstName, String lastName) { }
