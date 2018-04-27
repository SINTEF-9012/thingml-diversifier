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

public class M3bis_MessageType extends EventType {
public M3bis_MessageType(short code) {super("m3bis_", code);
}

public M3bis_MessageType() {
super("m3bis_", (short) 0x0E);
}

public Event instantiate(final byte r29, final byte r28, final byte r30, final byte r27, final byte r26) { return new M3bis_Message(this, r29, r28, r30, r27, r26); }
public Event instantiate(Map<String, Object> params) {return instantiate((Byte) params.get("r29"), (Byte) params.get("r28"), (Byte) params.get("r30"), (Byte) params.get("r27"), (Byte) params.get("r26"));
}

public class M3bis_Message extends Event implements java.io.Serializable {

public final byte r29;
public final byte r28;
public final byte r30;
public final byte r27;
public final byte r26;
public String toString(){
return "m3bis_ (" + "r29: " + r29 + ", " + "r28: " + r28 + ", " + "r30: " + r30 + ", " + "r27: " + r27 + ", " + "r26: " + r26 + ")";
}

protected M3bis_Message(EventType type, final byte r29, final byte r28, final byte r30, final byte r27, final byte r26) {
super(type);
this.r29 = r29;
this.r28 = r28;
this.r30 = r30;
this.r27 = r27;
this.r26 = r26;
}
public Event clone() {
return instantiate(this.r29, this.r28, this.r30, this.r27, this.r26);
}}

}
