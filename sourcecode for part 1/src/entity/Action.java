package entity;
public class Action {
	
	public Actions action = null;
	
	public Action() {
		action = null;
	}
	
	public Action(Actions action) {
		this.action = action;
	}
	
	public Actions getAction() {
		return action;
	}
	
	public String getActionStr() {
		
		if (action != null) {
			return action.toString();
		} else {
			return "Wall";
		}
	}
	
	public void setAction(Actions action) {
		this.action = action;
	}
	
	public enum Actions {
		UP("^"),
		DOWN("v"),
		LEFT("<"),
		RIGHT(">");
		
		// Represent the action as a string
		private String actionString;
		
		Actions(String actionString) {
			this.actionString = actionString;
		}
		
		@Override
		public String toString() {
			return actionString;
		}

	}
}
