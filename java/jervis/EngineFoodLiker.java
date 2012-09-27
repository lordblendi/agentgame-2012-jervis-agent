package kalmi;

import jason.stdlib.concat;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;


public class EngineFoodLiker implements RecommendationEngine {

		@SuppressWarnings("serial")
		List<Rectangle> boundPerAgent = new ArrayList<Rectangle>(){{
		
			
			add(new Rectangle(0,  0, 39, 21));
			
			add(new Rectangle(0,  21, 21, 39));
			
			add(new Rectangle(21, 39, 39, 21));
			
			add(new Rectangle(59-21+1, 0, 21, 39));
			
			add(new Rectangle(10, 10, 40, 40));
					
		}};
	
	public boolean okForAgent(Point food, Agent agent){
		//ArrayList<Rectangle> bounds = boundListPerAgent.get(agent.id-1);
		Rectangle r = boundPerAgent.get(agent.id-1);
		boolean inBound = false;
		//for (Rectangle r : bounds) {
			if(r.contains(food))
				inBound = true;
		//}
		return inBound;
		//return true;
	} 
		
	public Set<MyDir> getRecommendation(State state, int myId) {
		EnumSet<MyDir> result = EnumSet.noneOf(MyDir.class);
		if(state.foods.size() == 0)
			return result;
		
		Agent agent = state.agents.get(myId);
		
		Point closestFood = null;
		int closestDistance = Integer.MAX_VALUE;
		for (Point food : state.foods) {
			
			if(!okForAgent(food, agent))
				continue;
			
			
			
			int closestToAgent = -1;
			int closestDistanceToAgent = Integer.MAX_VALUE;
			
			for (Entry<Integer, Agent> entry : state.agents.entrySet()) {
				
				if(!okForAgent(food, entry.getValue()))
					continue;
				
				Agent a = entry.getValue();
				int d = Math.abs(food.x - a.position.x) + Math.abs(food.y - a.position.y);
				if(closestDistanceToAgent > d){
					closestDistanceToAgent = d;
					closestToAgent = entry.getKey();
				}
			}
			
			if(closestToAgent != myId) continue;
			
			 
			if(closestDistanceToAgent < closestDistance){
				closestDistance = closestDistanceToAgent;
				closestFood = food;
			}
		}
		
		if(closestFood == null) return result;
		
		if(agent.position.x > closestFood.x)
			result.add(MyDir.left);
		else if (agent.position.x < closestFood.x)
			result.add(MyDir.right);
		
		if(agent.position.y < closestFood.y)
			result.add(MyDir.down);
		else if (agent.position.y > closestFood.y)
			result.add(MyDir.up);
		
		return result;
			
	}

}

