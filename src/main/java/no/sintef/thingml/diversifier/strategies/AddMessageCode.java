package no.sintef.thingml.diversifier.strategies;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.PlatformAnnotation;
import org.thingml.xtext.thingML.ThingMLFactory;
import org.thingml.xtext.thingML.ThingMLModel;

public class AddMessageCode extends Strategy {
	
	private int code = 0;
	
	@Override
	protected void doApply(ThingMLModel model) {
		final TreeIterator<EObject> it = model.eAllContents();
        while (it.hasNext()) {
            final EObject o = it.next();
            if (o instanceof Message) {
            	final Message m = (Message) o;
                PlatformAnnotation a = null;
                for (PlatformAnnotation annot : m.getAnnotations()) {
                    if (annot.getName().equals("code")) {
                        a = annot;
                        break;
                    }
                }
                if (a == null) {
                    a = ThingMLFactory.eINSTANCE.createPlatformAnnotation();
                    a.setName("code");
                    m.getAnnotations().add(a);
                    //a.setValue(String.format("0x%02X", code));
                    a.setValue(""+code);
                    code++;
                    /*if (code < 127) code++;
                    else code = -128;*/
                }
            }
        }
	}

}
