package com.zkrallah.sanad.service.request;

import com.zkrallah.sanad.dtos.CreateRequestDto;
import com.zkrallah.sanad.entity.Request;

public interface RequestService {
    Request createRequest(Long userId, Long lawyerId, CreateRequestDto createRequestDto);
}
