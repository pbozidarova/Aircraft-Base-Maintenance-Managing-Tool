package managing.tool.e_issue.service.impl;

import lombok.AllArgsConstructor;
import managing.tool.e_issue.model.dto.IssueViewDto;
import managing.tool.e_issue.repository.IssueRepository;
import managing.tool.e_issue.service.IssueService;
import managing.tool.e_maintenance.model.MaintenanceEntity;
import managing.tool.e_maintenance.service.MaintenanceService;
import managing.tool.e_user.model.UserEntity;
import managing.tool.e_user.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class IssueServiceImpl implements IssueService {
    private final IssueRepository issueRepository;
    private final UserService userService;
    private final MaintenanceService maintenanceService;
    private final ModelMapper modelMapper;


    @Override
    public List<IssueViewDto> findAllIssues() {
        return this.issueRepository
                .findAll()
                .stream()
                .map(i -> this.modelMapper.map(i, IssueViewDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<IssueViewDto> findAllIssuesByAuthor(String companyNum) {
        UserEntity userEntity = this.userService.findByCompanyNum(companyNum);

        return this.issueRepository.findAllByAuthor(userEntity)
                .stream()
                .map(i -> this.modelMapper.map(i, IssueViewDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<IssueViewDto> findAllIssuesByMaintenance(String maintenanceNum) {
        MaintenanceEntity maintenanceEntity = this.maintenanceService.findByMaintenanceNum(maintenanceNum);

        return this.issueRepository.findAllByMaintenance(maintenanceEntity)
                .stream()
                .map(i -> this.modelMapper.map(i, IssueViewDto.class))
                .collect(Collectors.toList());
    }
}
