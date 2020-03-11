package no.sintef.thingml.diversifier.strategies;

import java.util.List;

import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.ActionHelper;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.ActionBlock;
import org.thingml.xtext.thingML.ByteLiteral;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.RequiredPort;
import org.thingml.xtext.thingML.SendAction;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLFactory;
import org.thingml.xtext.thingML.ThingMLModel;

import no.sintef.thingml.diversifier.Manager;

/**
 * 
 * FIXME: Generate better names, especially to avoid conflicts when we apply this strategy multiple time. As of now, it should work if applied once in static mode and once in dynamic mode
 *
 */
public class AddFakeNews extends Strategy {
	
	public AddFakeNews(Manager manager) {
		super(manager);
	}

	public AddFakeNews(Manager manager, int i) {
		super(manager, i);
	}

	private static int counter = 0;


	@Override
	protected void doApply(ThingMLModel model) {
		for(Thing t : ThingMLHelpers.allThings(model)) {
			if (t.getBehaviour() == null) continue;
			if (!Manager.diversify(t)) continue;
			if (AnnotatedElementHelper.hasFlag(t, "stl")) continue;
			if (manager.rnd.nextInt(10)>=probability) continue;
			Message fakeNews = null;
			RequiredPort znn = null;
			for(Message msg : t.getMessages()) {
				if (msg.getName().startsWith("fakeNews")) {
					fakeNews = msg;
					break;
				}
			}
			
			final List<ActionBlock> allBlocks = ActionHelper.getAllContainedActions(t.getBehaviour(), ActionBlock.class);
			if (allBlocks.isEmpty()) continue;
			
			if (fakeNews == null) {
				fakeNews = ThingMLFactory.eINSTANCE.createMessage();
				fakeNews.setName("fakeNews" + (counter++));
				
				znn = ThingMLFactory.eINSTANCE.createRequiredPort();
				znn.setOptional(true);
				znn.setName("znn" + (counter++));
				znn.getSends().add(fakeNews);
				
				t.getMessages().add(fakeNews);
				t.getPorts().add(znn);
			} else {
				for(Port p : t.getPorts()) {
					if (p.getName().startsWith("znn")) {
						znn = (RequiredPort)p;
						break;
					}
				}
			}
			
			final SendAction sa = ThingMLFactory.eINSTANCE.createSendAction();
			sa.setMessage(fakeNews);
			sa.setPort(znn);
			for(Parameter p : fakeNews.getParameters()) {
				final ByteLiteral b = ThingMLFactory.eINSTANCE.createByteLiteral();
				b.setByteValue((byte)manager.rnd.nextInt(128));
				sa.getParameters().add(b);
			}
			final ActionBlock b = allBlocks.get(manager.rnd.nextInt(allBlocks.size()));
			b.getActions().add(manager.rnd.nextInt(b.getActions().size()), sa);
			
		}
                
	}

}
