package com.sa.leanning.CarProject.service.Imp;


import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sa.leanning.CarProject.DTO.NotificationCreateRequest;
import com.sa.leanning.CarProject.DTO.NotificationResponse;
import com.sa.leanning.CarProject.Entities.Notification;
import com.sa.leanning.CarProject.repository.NotificationRepository;
import com.sa.leanning.CarProject.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repo;

    public NotificationServiceImpl(NotificationRepository repo) {
        this.repo = repo;
    }

    @Override
    public NotificationResponse create(NotificationCreateRequest req) {

        Notification n = new Notification();
        n.setFirstName(req.getFirstName());
        n.setLastName(req.getLastName());
        n.setEmail(req.getEmail());
        n.setMobileNo(req.getMobileNo());
        n.setMessage(req.getMessage());
        n.setReadStatus(false);

        return map(repo.save(n));
    }

    @Override
    public List<NotificationResponse> list() {
        return repo.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public long unreadCount() {
        return repo.countByReadStatusFalse();
    }

    @Override
    public void markRead(Long id) {
        Notification n = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        n.setReadStatus(true);
        repo.save(n);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    private NotificationResponse map(Notification n) {
        NotificationResponse r = new NotificationResponse();
        r.setId(n.getId());
        r.setFirstName(n.getFirstName());
        r.setLastName(n.getLastName());
        r.setEmail(n.getEmail());
        r.setMobileNo(n.getMobileNo());
        r.setMessage(n.getMessage());
        r.setReadStatus(n.isReadStatus());
        r.setCreatedAt(n.getCreatedAt());
        r.setCreatedBy(n.getCreatedBy());
        return r;
    }
}
