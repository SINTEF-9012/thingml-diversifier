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

public class M2abcMessageType extends EventType {
public M2abcMessageType(short code) {super("m2abc", code);
}

public M2abcMessageType() {
super("m2abc", (short) 0x07);
}

public Event instantiate(final byte a, final byte r10, final byte b, final byte r9, final byte c) { return new M2abcMessage(this, a, r10, b, r9, c); }
public Event instantiate(Map<String, Object> params) {return instantiate((Byte) params.get("a"), (Byte) params.get("r10"), (Byte) params.get("b"), (Byte) params.get("r9"), (Byte) params.get("c"));
}

public class M2abcMessage extends Event implements java.io.Serializable {

public final byte a;
public final byte r10;
public final byte b;
public final byte r9;
public final byte c;
public String toString(){
return "m2abc (" + "a: " + a + ", " + "r10: " + r10 + ", " + "b: " + b + ", " + "r9: " + r9 + ", " + "c: " + c + ")";
}

protected M2abcMessage(EventType type, final byte a, final byte r10, final byte b, final byte r9, final byte c) {
super(type);
this.a = a;
this.r10 = r10;
this.b = b;
this.r9 = r9;
this.c = c;
}
public Event clone() {
return instantiate(this.a, this.r10, this.b, this.r9, this.c);
}}

}
