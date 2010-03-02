package net.frontlinesms.plugins.patientview.ui.detailview;

import static net.frontlinesms.ui.i18n.InternationalisationUtils.getI18NString;

import java.util.HashMap;

import net.frontlinesms.plugins.patientview.data.domain.people.Patient;
import net.frontlinesms.plugins.patientview.ui.personpanel.CommunityHealthWorkerPanel;
import net.frontlinesms.plugins.patientview.ui.personpanel.PatientPanel;
import net.frontlinesms.plugins.patientview.ui.personpanel.PersonAttributePanel;
import net.frontlinesms.ui.UiGeneratorController;

import org.springframework.context.ApplicationContext;

import thinlet.Thinlet;

public class PatientDetailViewPanelController implements DetailViewPanelController<Patient> {

	private static final String EDIT_PATIENT_ATTRIBUTES = "detailview.buttons.edit.patient.attributes";
	private static final String SAVE_PATIENT_ATTRIBUTES = "detailview.buttons.save";
	private static final String CANCEL = "detailview.buttons.cancel";
	private Object mainPanel;
	private UiGeneratorController uiController;
	private ApplicationContext appCon;
	private boolean inEditingMode;
	
	private PatientPanel currentPatientPanel;
	private CommunityHealthWorkerPanel currentCHWPanel;
	private PersonAttributePanel currentAttributePanel;
	
	public PatientDetailViewPanelController(UiGeneratorController uiController,ApplicationContext appCon){
		this.uiController = uiController;
		this.appCon = appCon;
		inEditingMode=false;
	}
	public Class getEntityClass() {
		return Patient.class;
	}

	public HashMap<String, String> getFurtherOptions() {
		return null;
	}

	public Object getPanel() {
		return mainPanel;
	}

	public void viewWillAppear(Patient p) {
		inEditingMode=false;
		mainPanel = uiController.create("panel");
		uiController.setWeight(mainPanel, 1, 1);
		uiController.setColumns(mainPanel, 1);
		currentPatientPanel = new PatientPanel(uiController,appCon,p);
		currentCHWPanel = new CommunityHealthWorkerPanel(uiController,appCon,p.getChw());
		currentAttributePanel = new PersonAttributePanel(uiController,appCon,p);
		uiController.add(mainPanel, currentPatientPanel.getMainPanel());
		uiController.add(mainPanel, currentCHWPanel.getMainPanel());
		uiController.add(mainPanel, currentAttributePanel.getMainPanel());
		uiController.add(mainPanel,getBottomButtons());
	}
	
	private Object getBottomButtons(){
		Object buttonPanel = uiController.create("panel");
		uiController.setName(buttonPanel, "buttonPanel");
		uiController.setColumns(buttonPanel, 3);
		Object leftButton = uiController.createButton(!inEditingMode?getI18NString(EDIT_PATIENT_ATTRIBUTES):getI18NString(SAVE_PATIENT_ATTRIBUTES));
		if(inEditingMode){
			uiController.setAction(leftButton, "saveButtonClicked", null, this);
		}else{
			uiController.setAction(leftButton, "editButtonClicked", null, this);
		}
		uiController.setHAlign(leftButton, Thinlet.LEFT);
		uiController.setVAlign(leftButton, Thinlet.BOTTOM);
		uiController.add(buttonPanel,leftButton);
		if(inEditingMode){
			Object spacerLabel = uiController.createLabel("");
			uiController.setWeight(spacerLabel, 1, 0);
			uiController.add(buttonPanel,spacerLabel);
			Object rightButton = uiController.createButton(getI18NString(CANCEL));
			uiController.setHAlign(rightButton, Thinlet.RIGHT);
			uiController.setVAlign(rightButton, Thinlet.BOTTOM);
			uiController.setAction(rightButton, "cancelButtonClicked", null, this);
			uiController.add(buttonPanel, rightButton);
		}
		uiController.setWeight(buttonPanel, 1, 1);
		uiController.setVAlign(buttonPanel, Thinlet.BOTTOM);
		return buttonPanel;
	}

	public void editButtonClicked(){
		inEditingMode=true;
		currentAttributePanel.switchToEditingPanel();
		uiController.remove(uiController.find(mainPanel,"buttonPanel"));
		uiController.add(mainPanel,getBottomButtons());
	}
	
	public void saveButtonClicked(){
		inEditingMode=false;
		currentAttributePanel.stopEditingWithSave();
		uiController.remove(uiController.find(mainPanel,"buttonPanel"));
		uiController.add(mainPanel,getBottomButtons());
	}
	
	public void cancelButtonClicked(){
		inEditingMode=false;
		currentAttributePanel.stopEditingWithoutSave();
		uiController.remove(uiController.find(mainPanel,"buttonPanel"));
		uiController.add(mainPanel,getBottomButtons());
	}
	public void viewWillDisappear() {/* do nothing */}

}