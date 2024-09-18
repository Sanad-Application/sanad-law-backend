package com.zkrallah.sanad.service.request;

import java.util.List;

import com.zkrallah.sanad.dtos.CreateRequestDto;
import com.zkrallah.sanad.entity.Request;

public interface RequestService {
    Request createRequest(Long userId, Long lawyerId, CreateRequestDto createRequestDto);

    Request getRequest(Long requestId);

    Request updateRequestStatus(Long requestId, int type);

    List<Request> getRequests(Long userId, int type);

    List<Request> getClientRequests(String authHeader, int type);
}
