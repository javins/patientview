package net.frontlinesms.plugins.patientview.ui.administration.people;

import java.util.ArrayList;
import java.util.List;

import net.frontlinesms.plugins.patientview.data.domain.people.User;
import net.frontlinesms.plugins.patientview.data.repository.UserDao;
import net.frontlinesms.ui.UiGeneratorController;

import org.springframework.context.ApplicationContext;

public class UserAdministrationPanelController extends PersonAdministrationPanelController<User>{

	private UserDao userDao;
	public UserAdministrationPanelController(
			UiGeneratorController uiController, ApplicationContext appCon) {
		super(uiController, appCon);
	}

	@Override
	protected List<User> getPeopleForString(String s) {
		if(userDao == null){
			userDao = (UserDao) appCon.getBean("UserDao");
		}
		return new ArrayList<User>(userDao.getUsersByName(s,30));
	}

	@Override
	protected String getPersonType() {
		return "User";
	}

	@Override
	protected void putHeader() {
		advancedTableController.putHeader(User.class, new String[]{"Name","Age","Role"}, new String[]{"getName", "getStringAge","getRoleName"});
	}

	public String getListItemTitle() {
		return "Manage Users";
	}

}