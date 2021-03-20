package managing.tool.e_notification.service.impl;

import lombok.AllArgsConstructor;
import managing.tool.e_notification.model.dto.NotificationViewDto;
import managing.tool.e_notification.repository.NotificationRepository;
import managing.tool.e_notification.service.NotificationService;
import managing.tool.e_maintenance.model.MaintenanceEntity;
import managing.tool.e_maintenance.service.MaintenanceService;
import managing.tool.e_user.model.UserEntity;
import managing.tool.e_user.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository issueRepository;
    private final UserService userService;
    private final MaintenanceService maintenanceService;
    private final ModelMapper modelMapper;


    @Override
    public List<NotificationViewDto> findAllIssues() {
        return this.issueRepository
                .findAll()
                .stream()
                .map(i -> this.modelMapper.map(i, NotificationViewDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationViewDto> findAllIssuesByAuthor(String companyNum) {
        UserEntity userEntity = this.userService.findByCompanyNum(companyNum);

        return this.issueRepository.findAllByAuthor(userEntity)
                .stream()
                .map(i -> this.modelMapper.map(i, NotificationViewDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationViewDto> findAllIssuesByMaintenance(String maintenanceNum) {
        MaintenanceEntity maintenanceEntity = this.maintenanceService.findByMaintenanceNum(maintenanceNum);

        return this.issueRepository.findAllByMaintenance(maintenanceEntity)
                .stream()
                .map(i -> this.modelMapper.map(i, NotificationViewDto.class))
                .collect(Collectors.toList());
    }
}
