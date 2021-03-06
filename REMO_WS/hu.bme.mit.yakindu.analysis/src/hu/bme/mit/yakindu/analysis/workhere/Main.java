package hu.bme.mit.yakindu.analysis.workhere;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Transition;
import org.yakindu.sct.model.sgraph.Statechart;

import hu.bme.mit.model2gml.Model2GML;
import hu.bme.mit.yakindu.analysis.modelmanager.ModelManager;

public class Main {
	@Test
	public void test() {
		main(new String[0]);
	}
	
	public static void main(String[] args) {
		ModelManager manager = new ModelManager();
		Model2GML model2gml = new Model2GML();
		
		// Loading model
		EObject root = manager.loadModel("model_input/example.sct");
		
		// Reading model
		Statechart s = (Statechart) root;
		TreeIterator<EObject> iterator = s.eAllContents();
		List<State> drainlist = new ArrayList();
		int i = 0;
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof State) {
				State state = (State) content;
				
				if(state.getOutgoingTransitions().size() == 0)
					drainlist.add(state);
				if(state.getName().equals(""))
					System.out.println("No name, recommended name: " + "state" + i);
				else
					System.out.println(state.getName());
			}
			if(content instanceof Transition) {
				Transition t = (Transition) content;
				System.out.println(t.getSource().getName() + " -> " +  t.getTarget().getName());
			}
			i++;
		}
		
		System.out.println("2.4");
		for(State state : drainlist)
			System.out.println(state.getName());
		
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
}
