package kalmi;

import jason.asSemantics.*;
import jason.asSyntax.*;
import kalmi.AI.Controller;
import kalmi.CommonTypes.Perception;
import kalmi.JasonLayer.Commands.Command;


public class main extends DefaultInternalAction{
	private static final long serialVersionUID = -6271003270174978200L;	
	static Controller controller = new Controller();
	
	public main() {
		
	}

	public Object execute(TransitionSystem ts, final Unifier un, final Term[] arg) throws Exception {
		
		Agent agent = ts.getAg();		
		Perception perception = new Perception(agent.getBB());
		
		Command command  = controller.process(perception);
		
		ActionExec action = command.toAction();
		ts.getUserAgArch().act(action, null);
		
		return true;
	}
}