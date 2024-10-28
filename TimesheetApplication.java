package ru.gb.timesheet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.gb.timesheet.model.*;
import ru.gb.timesheet.repository.*;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class TimesheetApplication {

	private static final String[] firstNames = {"Ivan", "Anton", "Boris"};
	private static final String[] lastNames = {"Sidorov", "Ivanov", "Petrov"};

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(TimesheetApplication.class, args);

		UserRepository userRepository = ctx.getBean(UserRepository.class);
		User admin = new User();
		admin.setLogin("admin");
		admin.setPassword("$2a$12$zhb31f/FV2PrE58v5zh/kussQNtekz1ka4iiYWwWuFdLectj2lMRe");

		User user = new User();
		user.setLogin("user");
		user.setPassword("$2a$12$KsPW54qGJiW9jynO8sXQPuvXf9b2ZtcujJOAqwTo6x3zjNQ5tDjs2");

		admin = userRepository.save(admin);
		user = userRepository.save(user);

		UserRoleRepository userRoleRepository = ctx.getBean(UserRoleRepository.class);


		UserRole adminAdminRole = new UserRole();
		adminAdminRole.setUserId(admin.getUserId());
		adminAdminRole.setRoleName("admin");
		userRoleRepository.save(adminAdminRole);

		UserRole adminUserRole = new UserRole();
		adminUserRole.setUserId(admin.getUserId());
		adminUserRole.setRoleName("user");
		userRoleRepository.save(adminUserRole);

		UserRole userUserRole = new UserRole();
		userUserRole.setUserId(user.getUserId());
		userUserRole.setRoleName("user");
		userRoleRepository.save(userUserRole);

		User anonymous = new User();
		anonymous.setLogin("anon");
		anonymous.setPassword("$2a$12$iCejSCKMo4HdDEdIbMpsVuhmhMaWNqiFCAeGtB4qqNEp9roxL3VPe");
		anonymous = userRepository.save(anonymous);

		User rest = new User();
		rest.setLogin("rest");
		rest.setPassword("$2a$12$IorVAanxpbXJM1PrIc/I1eDt03GyEcwc07mv0VvlzsayQQbuEMV6q");
		rest = userRepository.save(rest);

		UserRole restRestRole = new UserRole();
		restRestRole.setUserId(rest.getUserId());
		restRestRole.setRoleName("rest");
		userRoleRepository.save(restRestRole);

		ProjectRepository projectRepository = ctx.getBean(ProjectRepository.class);
		for (int i = 1; i <= 5; i++) {
			Project project = new Project();
			project.setProjectName("Project #" + i);
			projectRepository.save(project);
		}

		EmployeeRepository employeeRepository = ctx.getBean(EmployeeRepository.class);
		for (int i = 1; i <= 3; i++) {
			Employee employee = new Employee();
			employee.setFirstName(firstNames[i-1]);
			employee.setLastName(lastNames[i-1]);
			employeeRepository.save(employee);
		}

		TimesheetRepository timesheetRepository = ctx.getBean(TimesheetRepository.class);

		LocalDate createdAt = LocalDate.now();

		for (int i = 1; i <= 10; i++) {
			createdAt = createdAt.plusDays(1);

			Timesheet timesheet = new Timesheet();
			timesheet.setProjectId(ThreadLocalRandom.current().nextLong(1, 6));
			timesheet.setCreatedAt(createdAt);
			timesheet.setMinutes(ThreadLocalRandom.current().nextInt(100, 1000));
			timesheet.setEmployeeId(ThreadLocalRandom.current().nextLong(1, 6));

			timesheetRepository.save(timesheet);
		}
	}

}