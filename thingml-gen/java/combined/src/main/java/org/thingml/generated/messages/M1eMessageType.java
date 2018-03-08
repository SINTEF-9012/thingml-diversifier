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

public class M1eMessageType extends EventType {
public M1eMessageType(short code) {super("m1e", code);
}

public M1eMessageType() {
super("m1e", (short) 0x0B);
}

public Event instantiate(final byte r18, final byte r19, final byte r20, final byte r17, final byte e) { return new M1eMessage(this, r18, r19, r20, r17, e); }
public Event instantiate(Map<String, Object> params) {return instantiate((Byte) params.get("r18"), (Byte) params.get("r19"), (Byte) params.get("r20"), (Byte) params.get("r17"), (Byte) params.get("e"));
}

public class M1eMessage extends Event implements java.io.Serializable {

public final byte r18;
public final byte r19;
public final byte r20;
public final byte r17;
public final byte e;
public String toString(){
return "m1e (" + "r18: " + r18 + ", " + "r19: " + r19 + ", " + "r20: " + r20 + ", " + "r17: " + r17 + ", " + "e: " + e + ")";
}

protected M1eMessage(EventType type, final byte r18, final byte r19, final byte r20, final byte r17, final byte e) {
super(type);
this.r18 = r18;
this.r19 = r19;
this.r20 = r20;
this.r17 = r17;
this.e = e;
}
public Event clone() {
return instantiate(this.r18, this.r19, this.r20, this.r17, this.e);
}}

}

