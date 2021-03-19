package managing.tool.e_issue.service;

import managing.tool.e_issue.model.dto.IssueViewDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IssueService {

    List<IssueViewDto> findAllIssues();
    List<IssueViewDto> findAllIssuesByAuthor(String companyNum);
    List<IssueViewDto> findAllIssuesByMaintenance(String maintenanceNum);
}
