package entity;
import entity.Utility;
import entity.Action;

public class ActionUtility implements Comparable<ActionUtility> {
	
	public Action action = new Action();
	public Utility utility = new Utility();
	
	public ActionUtility() {
		action.setAction(null);
		utility.setUtility(0);
	}
	
	public ActionUtility(Action.Actions action, double utility) {
		this.action.setAction(action);
		this.utility.setUtility(utility);
	}

	@Override
	public int compareTo(ActionUtility arg0) {
		// TODO Auto-generated method stub
		//compare utility values
		return ((Double) arg0.utility.getUtility()).compareTo(utility.getUtility());
	}
	
}
