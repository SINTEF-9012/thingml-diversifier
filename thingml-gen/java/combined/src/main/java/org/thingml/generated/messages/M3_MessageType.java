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

public class M3_MessageType extends EventType {
public M3_MessageType(short code) {super("m3_", code);
}

public M3_MessageType() {
super("m3_", (short) 0x08);
}

public Event instantiate(final byte r13, final byte r15, final byte r12, final byte r14, final byte r11) { return new M3_Message(this, r13, r15, r12, r14, r11); }
public Event instantiate(Map<String, Object> params) {return instantiate((Byte) params.get("r13"), (Byte) params.get("r15"), (Byte) params.get("r12"), (Byte) params.get("r14"), (Byte) params.get("r11"));
}

public class M3_Message extends Event implements java.io.Serializable {

public final byte r13;
public final byte r15;
public final byte r12;
public final byte r14;
public final byte r11;
public String toString(){
return "m3_ (" + "r13: " + r13 + ", " + "r15: " + r15 + ", " + "r12: " + r12 + ", " + "r14: " + r14 + ", " + "r11: " + r11 + ")";
}

protected M3_Message(EventType type, final byte r13, final byte r15, final byte r12, final byte r14, final byte r11) {
super(type);
this.r13 = r13;
this.r15 = r15;
this.r12 = r12;
this.r14 = r14;
this.r11 = r11;
}
public Event clone() {
return instantiate(this.r13, this.r15, this.r12, this.r14, this.r11);
}}

}

