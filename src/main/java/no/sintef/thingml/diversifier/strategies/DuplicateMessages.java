package no.sintef.thingml.diversifier.strategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.thingML.ActionBlock;
import org.thingml.xtext.thingML.ConditionalAction;
import org.thingml.xtext.thingML.EventReference;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.FunctionCallExpression;
import org.thingml.xtext.thingML.Handler;
import org.thingml.xtext.thingML.IntegerLiteral;
import org.thingml.xtext.thingML.InternalTransition;
import org.thingml.xtext.thingML.LowerExpression;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.ReceiveMessage;
import org.thingml.xtext.thingML.SendAction;
import org.thingml.xtext.thingML.State;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLFactory;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.Transition;

import no.sintef.thingml.diversifier.Manager;
import no.sintef.thingml.diversifier.Mode;

//TODO: Static/dynamic mode
public class DuplicateMessages extends Strategy {

	private Map<Message, Message> copies = new HashMap<>();

	private Message getCopy(Message m) {//TODO: give code to newly created messages
		Message copy = copies.get(m);
		if (copy != null)
			return copy;
		if(Manager.mode == Mode.STATIC && Manager.rnd.nextBoolean()) {
			copy = m;
		} else {
			copy = EcoreUtil.copy(m);
			copy.setName(m.getName() + "_bis");	
		}
		copies.put(m, copy);
		return copy;
	}

	@Override
	protected void doApply(ThingMLModel model) {
		if (Manager.mode == Mode.STATIC) return; //this transformation has no real visible effect in static mode
		//We need two passes, as the messages may be defined in one thing and then included in other things using them in ports

		//PASS 1: Copy all messages
		final TreeIterator<EObject> it = model.eAllContents();
		while (it.hasNext()) {
			final EObject o = it.next();
			if (o instanceof Message) {
				final Message msg = (Message) o;
				if (!Manager.diversify(msg)) continue;	
				final Thing t = (Thing) msg.eContainer();
				//if (!Manager.diversify(t)) return;
				final Message copy = getCopy(msg);
				t.getMessages().add(copy);
			}
		}

		//PASS 2: Update ports
		final TreeIterator<EObject> it2 = model.eAllContents();
		while (it2.hasNext()) {
			final EObject o = it2.next();
			if (o instanceof Port) {
				final Port port = (Port) o;
				final List<Message> sent = new ArrayList<>();
				sent.addAll(port.getSends());
				for (Message msg : sent) {
					final Message copy = getCopy(msg);
					port.getSends().add(copy);
				}

				final List<Message> received = new ArrayList<>();
				received.addAll(port.getReceives());
				for (Message msg : received) {
					final Message copy = getCopy(msg);
					port.getReceives().add(copy);
				}
			}
		}

		final TreeIterator<EObject> it3 = model.eAllContents();
		while (it3.hasNext()) {
			final EObject o = it3.next();
			if (o instanceof SendAction) {
				final SendAction sa = (SendAction) o;
				duplicateSendAction(sa);
			} else if (o instanceof Handler) {
				final Handler h = (Handler) o;
				duplicateHandler(h);
			}
		}
	}

	private void duplicateSendAction(SendAction sa) {
		System.out.println("Duplicating send action " + sa.getPort().getName() + "!" + sa.getMessage().getName());
		final Message copy = getCopy(sa.getMessage());
		final ConditionalAction ca = ThingMLFactory.eINSTANCE.createConditionalAction();
		final LowerExpression lower = ThingMLFactory.eINSTANCE.createLowerExpression();
		
		final Function rnd = Manager.findRandom(ThingMLHelpers.findContainingThing(sa));
		if (rnd != null) {//fall back to static mode
			final FunctionCallExpression call = ThingMLFactory.eINSTANCE.createFunctionCallExpression();
			call.setFunction(rnd);
			lower.setLhs(call);
		} else {//FIXME: dirty workaround
			final IntegerLiteral threshold = ThingMLFactory.eINSTANCE.createIntegerLiteral();
			threshold.setIntValue(Manager.rnd.nextInt(256));
			lower.setLhs(threshold);
		}
		final IntegerLiteral threshold = ThingMLFactory.eINSTANCE.createIntegerLiteral();
		threshold.setIntValue(Manager.rnd.nextInt(256));	
		lower.setRhs(threshold);
		ca.setCondition(lower);

		final Object parent = sa.eContainer().eGet(sa.eContainingFeature());
		if (parent instanceof EList) {
			EList list = (EList) parent;
			final int index = list.indexOf(sa);
			list.add(index, ca);
		} else {
			final EObject o = sa.eContainer();
			o.eSet(sa.eContainingFeature(), ca);
		}

		final ActionBlock b1 = ThingMLFactory.eINSTANCE.createActionBlock();
		b1.getActions().add(sa);
		ca.setAction(b1);
		final SendAction send2 = EcoreUtil.copy(sa);
		send2.setMessage(copy);
		final ActionBlock b2 = ThingMLFactory.eINSTANCE.createActionBlock();
		b2.getActions().add(send2);
		ca.setElseAction(b2);
	}	

	private void duplicateHandler(Handler h) {
		if (h.getEvent() != null && h.getEvent() instanceof ReceiveMessage) {
			final ReceiveMessage rm = (ReceiveMessage) h.getEvent();
			final Message m = rm.getMessage();
			final Message m2 = getCopy(m);
			final Thing thing = ThingMLHelpers.findContainingThing(h);
			System.out.println("Duplicating handler " + rm.getPort().getName() + "?" + m.getName() + " in thing " + thing.getName());
			Handler h2 = null;
			if (h instanceof Transition) {
				h2 = ThingMLFactory.eINSTANCE.createTransition();
				((State) h.eContainer()).getOutgoing().add((Transition) h2);
				((Transition) h2).setTarget(((Transition) h).getTarget());
			} else {
				h2 = ThingMLFactory.eINSTANCE.createInternalTransition();
				((State) h.eContainer()).getInternal().add((InternalTransition) h2);
			}
			if (h.getName() != null)
				h2.setName(h.getName() + "bis");
			final ReceiveMessage rm2 = ThingMLFactory.eINSTANCE.createReceiveMessage();
			rm2.setName(rm.getName());
			rm2.setPort(rm.getPort());
			rm2.setMessage(m2);
			h2.setEvent(rm2);

			if (h.getGuard() != null) {
				h2.setGuard(EcoreUtil.copy(h.getGuard()));
				for (EventReference ref : ThingMLHelpers.getAllExpressions(h2.getGuard(), EventReference.class)) {
					//System.out.println("Fixing event ref on parameter " + ref.getParameter().getName() + " of message " + m2.getName());
					ref.setReceiveMsg(rm2);
					ref.setParameter(m2.getParameters().get(m.getParameters().indexOf(ref.getParameter())));
				}
			}

			if (h.getAction() != null) {
				h2.setAction(EcoreUtil.copy(h.getAction()));
				for (EventReference ref : ThingMLHelpers.getAllExpressions(h2.getAction(), EventReference.class)) {
					//System.out.println("Fixing event ref on parameter " + ref.getParameter().getName() + " of message " + m2.getName());
					ref.setReceiveMsg(rm2);
					ref.setParameter(m2.getParameters().get(m.getParameters().indexOf(ref.getParameter())));
				}
			}
		}
	}

}
