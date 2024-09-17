package com.zkrallah.sanad.service.request;

import java.util.List;

import com.zkrallah.sanad.dtos.CreateRequestDto;
import com.zkrallah.sanad.entity.Request;

public interface RequestService {
    Request createRequest(Long userId, Long lawyerId, CreateRequestDto createRequestDto);

    List<Request> getRequests(Long userId, int type);

    List<Request> getClientRequests(Long lawyerId, int type);
}
