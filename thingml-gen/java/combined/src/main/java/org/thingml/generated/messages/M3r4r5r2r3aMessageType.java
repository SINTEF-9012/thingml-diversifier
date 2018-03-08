/**
 * File generated by the ThingML IDE
 * /!\Do not edit this file/!\
 * In case of a bug in the generated code,
 * please submit an issue on our GitHub
 **/

package org.thingml.generated.messages;

import no.sintef.jasm.*;
import no.sintef.jasm.ext.*;

import java.util.*;
import java.nio.*;

public class M3r4r5r2r3aMessageType extends EventType {
public M3r4r5r2r3aMessageType(short code) {super("m3r4r5r2r3a", code);
}

public M3r4r5r2r3aMessageType() {
super("m3r4r5r2r3a", (short) 0x09);
}

public Event instantiate(final byte r4, final byte r5, final byte r2, final byte r3, final byte a) { return new M3r4r5r2r3aMessage(this, r4, r5, r2, r3, a); }
public Event instantiate(Map<String, Object> params) {return instantiate((Byte) params.get("r4"), (Byte) params.get("r5"), (Byte) params.get("r2"), (Byte) params.get("r3"), (Byte) params.get("a"));
}

public class M3r4r5r2r3aMessage extends Event implements java.io.Serializable {

public final byte r4;
public final byte r5;
public final byte r2;
public final byte r3;
public final byte a;
public String toString(){
return "m3r4r5r2r3a (" + "r4: " + r4 + ", " + "r5: " + r5 + ", " + "r2: " + r2 + ", " + "r3: " + r3 + ", " + "a: " + a + ")";
}

protected M3r4r5r2r3aMessage(EventType type, final byte r4, final byte r5, final byte r2, final byte r3, final byte a) {
super(type);
this.r4 = r4;
this.r5 = r5;
this.r2 = r2;
this.r3 = r3;
this.a = a;
}
public Event clone() {
return instantiate(this.r4, this.r5, this.r2, this.r3, this.a);
}}

}

