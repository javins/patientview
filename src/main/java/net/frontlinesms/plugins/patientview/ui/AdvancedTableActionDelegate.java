package net.frontlinesms.plugins.patientview.ui;


public interface AdvancedTableActionDelegate {
	public void selectionChanged(Object selectedObject);
	public void doubleClickAction(Object selectedObject);
	public void resultsChanged();
}
