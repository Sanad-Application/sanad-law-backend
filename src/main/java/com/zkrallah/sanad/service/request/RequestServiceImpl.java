package com.zkrallah.sanad.service.request;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zkrallah.sanad.dtos.CreateRequestDto;
import com.zkrallah.sanad.entity.Lawyer;
import com.zkrallah.sanad.entity.Request;
import com.zkrallah.sanad.entity.User;
import com.zkrallah.sanad.repository.RequestRepository;
import com.zkrallah.sanad.service.lawyer.LawyerService;
import com.zkrallah.sanad.service.user.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final UserService userService;
    private final LawyerService lawyerService;

    @Override
    @Transactional
    public Request createRequest(Long userId, Long lawyerId, CreateRequestDto createRequestDto) {
        User user = userService.getUserById(userId);
        Lawyer lawyer = lawyerService.getLawyer(lawyerId);

        Request request = new Request();
        Date now = new Date();

        request.setType(createRequestDto.getType());
        request.setTag(createRequestDto.getTag());
        request.setTitle(createRequestDto.getTitle());
        request.setDescription(createRequestDto.getDescription());
        request.setKeywords(createRequestDto.getKeywords());
        request.setAttachment(createRequestDto.getAttachment());
        request.setTimestamp(new Timestamp(now.getTime()));
        request.setStatus("WAITING");

        request.setLawyer(lawyer);
        request.setUser(user);

        return requestRepository.save(request);
    }

    @Override
    public Request getRequest(Long requestId) {
        return requestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Could not get request."));
    }

    @Override
    @Transactional
    public Request updateRequestStatus(Long requestId, int type) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Could not get request."));
        switch (type) {
            case 1 -> request.setStatus("APPROVED");
            case 2 -> request.setStatus("REJECTED");
            default -> throw new IllegalArgumentException("Not a valid type");
        }

        return request;
    }

    @Override
    public List<Request> getRequests(Long userId, int type) {
        User user = userService.getUserById(userId);

        return user.getRequests().stream()
                .filter(it -> switch (type) {
                    case 1 -> it.getStatus().equals("WAITING");
                    case 2 -> it.getStatus().equals("APPROVED");
                    case 3 -> it.getStatus().equals("REJECTED");
                    default -> true;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Request> getClientRequests(String authHeader, int type) {
        User user = userService.getUserByJwt(authHeader);
        Lawyer lawyer = user.getLawyer();

        if (lawyer == null) {
            throw new IllegalArgumentException("User is not a lawyer");
        }

        return lawyer.getClientRequests().stream()
                .filter(it -> switch (type) {
                    case 1 -> it.getStatus().equals("WAITING");
                    case 2 -> it.getStatus().equals("APPROVED");
                    case 3 -> it.getStatus().equals("REJECTED");
                    default -> true;
                })
                .collect(Collectors.toList());
    }
}
