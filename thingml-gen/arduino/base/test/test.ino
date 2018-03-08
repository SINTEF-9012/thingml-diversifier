#include <stdint.h>
#include <Arduino.h>
/*****************************************************************************
 * Headers for type : App
 *****************************************************************************/

// Definition of the instance struct:
struct App_Instance {

// Instances of different sessions
bool active;
// Variables for the ID of the ports of the instance
uint16_t id_app;
// Variables for the current instance state
int App_State;
// Variables for the properties of the instance
uint8_t App_id_var;

};
// Declaration of prototypes outgoing messages :
void App_OnEntry(int state, struct App_Instance *_instance);
void App_handle_app_m2(struct App_Instance *_instance, uint8_t a, uint8_t b, uint8_t c);
void App_handle_app_m1(struct App_Instance *_instance, uint8_t a, uint8_t b, uint8_t c, uint8_t d, uint8_t e);
// Declaration of callbacks for incoming messages:
void register_App_send_app_m3_listener(void (*_listener)(struct App_Instance *, uint8_t));
void register_external_App_send_app_m3_listener(void (*_listener)(struct App_Instance *, uint8_t));

// Definition of the states:
#define APP_NULL_WAITFORM2_STATE 0
#define APP_NULL_SENDACK_STATE 1
#define APP_NULL_WAITFORM1_STATE 2
#define APP_STATE 3


/*****************************************************************************
 * Headers for type : Client
 *****************************************************************************/

// Definition of the instance struct:
struct Client_Instance {

// Instances of different sessions
bool active;
// Variables for the ID of the ports of the instance
uint16_t id_app;
// Variables for the current instance state
int Client_State;
// Variables for the properties of the instance
uint8_t Client__a_var;
uint8_t Client__d_var;
uint8_t Client__c_var;
unsigned long Client_counter_var;
unsigned long Client_start_var;
uint8_t Client__e_var;
uint8_t Client__b_var;

};
// Declaration of prototypes outgoing messages :
void Client_OnEntry(int state, struct Client_Instance *_instance);
void Client_handle_app_m3(struct Client_Instance *_instance, uint8_t a);
// Declaration of callbacks for incoming messages:
void register_Client_send_app_m1_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_external_Client_send_app_m1_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_Client_send_app_m2_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t));
void register_external_Client_send_app_m2_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t));

// Definition of the states:
#define CLIENT_STATE 0
#define CLIENT_NULL_ERROR_STATE 1
#define CLIENT_NULL_RUN_STATE 2
#define CLIENT_NULL_STOP_STATE 3



/* Adds and instance to the runtime and returns its id */
uint16_t add_instance(void * instance_struct);
/* Returns the instance with id */
void * instance_by_id(uint16_t id);

/* Returns the number of byte currently in the fifo */
int fifo_byte_length();
/* Returns the number of bytes currently available in the fifo */
int fifo_byte_available();
/* Returns true if the fifo is empty */
int fifo_empty();
/* Return true if the fifo is full */
int fifo_full();
/* Enqueue 1 byte in the fifo if there is space
   returns 1 for sucess and 0 if the fifo was full */
int fifo_enqueue(byte b);
/* Enqueue 1 byte in the fifo without checking for available space
   The caller should have checked that there is enough space */
int _fifo_enqueue(byte b);
/* Dequeue 1 byte in the fifo.
   The caller should check that the fifo is not empty */
byte fifo_dequeue();

#define MAX_INSTANCES 2
#define FIFO_SIZE 256

/*********************************
 * Instance IDs and lookup
 *********************************/

void * instances[MAX_INSTANCES];
uint16_t instances_count = 0;

void * instance_by_id(uint16_t id) {
  return instances[id];
}

uint16_t add_instance(void * instance_struct) {
  instances[instances_count] = instance_struct;
  return instances_count++;
}

/******************************************
 * Simple byte FIFO implementation
 ******************************************/

byte fifo[FIFO_SIZE];
int fifo_head = 0;
int fifo_tail = 0;

// Returns the number of byte currently in the fifo
int fifo_byte_length() {
  if (fifo_tail >= fifo_head)
    return fifo_tail - fifo_head;
  return fifo_tail + FIFO_SIZE - fifo_head;
}

// Returns the number of bytes currently available in the fifo
int fifo_byte_available() {
  return FIFO_SIZE - 1 - fifo_byte_length();
}

// Returns true if the fifo is empty
int fifo_empty() {
  return fifo_head == fifo_tail;
}

// Return true if the fifo is full
int fifo_full() {
  return fifo_head == ((fifo_tail + 1) % FIFO_SIZE);
}

// Enqueue 1 byte in the fifo if there is space
// returns 1 for sucess and 0 if the fifo was full
int fifo_enqueue(byte b) {
  int new_tail = (fifo_tail + 1) % FIFO_SIZE;
  if (new_tail == fifo_head) return 0; // the fifo is full
  fifo[fifo_tail] = b;
  fifo_tail = new_tail;
  return 1;
}

// Enqueue 1 byte in the fifo without checking for available space
// The caller should have checked that there is enough space
int _fifo_enqueue(byte b) {
  fifo[fifo_tail] = b;
  fifo_tail = (fifo_tail + 1) % FIFO_SIZE;
  return 0; // Dummy added by steffend
}

// Dequeue 1 byte in the fifo.
// The caller should check that the fifo is not empty
byte fifo_dequeue() {
  if (!fifo_empty()) {
    byte result = fifo[fifo_head];
    fifo_head = (fifo_head + 1) % FIFO_SIZE;
    return result;
  }
  return 0;
}

/*****************************************************************************
 * Implementation for type : App
 *****************************************************************************/

// Declaration of prototypes:
//Prototypes: State Machine
void App_OnExit(int state, struct App_Instance *_instance);
//Prototypes: Message Sending
void App_send_app_m3(struct App_Instance *_instance, uint8_t a);
//Prototypes: Function
uint8_t f_App_rnd(struct App_Instance *_instance);
// Declaration of functions:
// Definition of function rnd
uint8_t f_App_rnd(struct App_Instance *_instance) {
return random(256);
}

// Sessions functionss:


// On Entry Actions:
void App_OnEntry(int state, struct App_Instance *_instance) {
switch(state) {
case APP_STATE:{
_instance->App_State = APP_NULL_WAITFORM1_STATE;
App_OnEntry(_instance->App_State, _instance);
break;
}
case APP_NULL_WAITFORM2_STATE:{
break;
}
case APP_NULL_SENDACK_STATE:{
Serial.print("#APP: Come get some app!m3(");
Serial.print(_instance->App_id_var);
Serial.print(")!");
Serial.println();
App_send_app_m3(_instance, _instance->App_id_var);
break;
}
case APP_NULL_WAITFORM1_STATE:{
break;
}
default: break;
}
}

// On Exit Actions:
void App_OnExit(int state, struct App_Instance *_instance) {
switch(state) {
case APP_STATE:{
App_OnExit(_instance->App_State, _instance);
break;}
case APP_NULL_WAITFORM2_STATE:{
break;}
case APP_NULL_SENDACK_STATE:{
break;}
case APP_NULL_WAITFORM1_STATE:{
break;}
default: break;
}
}

// Event Handlers for incoming messages:
void App_handle_app_m2(struct App_Instance *_instance, uint8_t a, uint8_t b, uint8_t c) {
if(!(_instance->active)) return;
//Region null
uint8_t App_State_event_consumed = 0;
if (_instance->App_State == APP_NULL_WAITFORM2_STATE) {
if (App_State_event_consumed == 0 && 1) {
App_OnExit(APP_NULL_WAITFORM2_STATE, _instance);
_instance->App_State = APP_NULL_SENDACK_STATE;
Serial.print("#APP: Ooh, I needed that app?m2(");
Serial.print(a);
Serial.print(", ");
Serial.print(b);
Serial.print(", ");
Serial.print(c);
Serial.print(")!");
Serial.println();
App_OnEntry(APP_NULL_SENDACK_STATE, _instance);
App_State_event_consumed = 1;
}
}
//End Region null
//End dsregion null
//Session list: 
}
void App_handle_app_m1(struct App_Instance *_instance, uint8_t a, uint8_t b, uint8_t c, uint8_t d, uint8_t e) {
if(!(_instance->active)) return;
//Region null
uint8_t App_State_event_consumed = 0;
if (_instance->App_State == APP_NULL_WAITFORM1_STATE) {
if (App_State_event_consumed == 0 && 1) {
App_OnExit(APP_NULL_WAITFORM1_STATE, _instance);
_instance->App_State = APP_NULL_WAITFORM2_STATE;
_instance->App_id_var = a;
Serial.print("#APP: Ooh, I needed that app?m1(");
Serial.print(a);
Serial.print(", ");
Serial.print(b);
Serial.print(", ");
Serial.print(c);
Serial.print(", ");
Serial.print(d);
Serial.print(", ");
Serial.print(e);
Serial.print(")!");
Serial.println();
App_OnEntry(APP_NULL_WAITFORM2_STATE, _instance);
App_State_event_consumed = 1;
}
}
//End Region null
//End dsregion null
//Session list: 
}
int App_handle_empty_event(struct App_Instance *_instance) {
 uint8_t empty_event_consumed = 0;
if(!(_instance->active)) return 0;
//Region null
if (_instance->App_State == APP_NULL_SENDACK_STATE) {
if (1) {
App_OnExit(APP_NULL_SENDACK_STATE, _instance);
_instance->App_State = APP_NULL_WAITFORM1_STATE;
App_OnEntry(APP_NULL_WAITFORM1_STATE, _instance);
return 1;
}
}
//begin dispatchEmptyToSession
//end dispatchEmptyToSession
return empty_event_consumed;
}

// Observers for outgoing messages:
void (*external_App_send_app_m3_listener)(struct App_Instance *, uint8_t)= 0x0;
void (*App_send_app_m3_listener)(struct App_Instance *, uint8_t)= 0x0;
void register_external_App_send_app_m3_listener(void (*_listener)(struct App_Instance *, uint8_t)){
external_App_send_app_m3_listener = _listener;
}
void register_App_send_app_m3_listener(void (*_listener)(struct App_Instance *, uint8_t)){
App_send_app_m3_listener = _listener;
}
void App_send_app_m3(struct App_Instance *_instance, uint8_t a){
if (App_send_app_m3_listener != 0x0) App_send_app_m3_listener(_instance, a);
if (external_App_send_app_m3_listener != 0x0) external_App_send_app_m3_listener(_instance, a);
;
}



/*****************************************************************************
 * Implementation for type : Client
 *****************************************************************************/

// Declaration of prototypes:
//Prototypes: State Machine
void Client_OnExit(int state, struct Client_Instance *_instance);
//Prototypes: Message Sending
void Client_send_app_m1(struct Client_Instance *_instance, uint8_t a, uint8_t b, uint8_t c, uint8_t d, uint8_t e);
void Client_send_app_m2(struct Client_Instance *_instance, uint8_t a, uint8_t b, uint8_t c);
//Prototypes: Function
uint8_t f_Client_rnd(struct Client_Instance *_instance);
// Declaration of functions:
// Definition of function rnd
uint8_t f_Client_rnd(struct Client_Instance *_instance) {
return random(256);
}

// Sessions functionss:


// On Entry Actions:
void Client_OnEntry(int state, struct Client_Instance *_instance) {
switch(state) {
case CLIENT_STATE:{
_instance->Client_State = CLIENT_NULL_RUN_STATE;
_instance->Client__a_var = f_Client_rnd(_instance);
_instance->Client__b_var = f_Client_rnd(_instance);
_instance->Client_start_var = millis();
Client_OnEntry(_instance->Client_State, _instance);
break;
}
case CLIENT_NULL_ERROR_STATE:{
Serial.print("#CLI: Heh, heh, heh... what a mess!");
Serial.println();
_instance->active = false;
break;
}
case CLIENT_NULL_RUN_STATE:{
_instance->Client__c_var = f_Client_rnd(_instance) % 100;
_instance->Client__d_var = f_Client_rnd(_instance) % 50;
_instance->Client__e_var = f_Client_rnd(_instance) % 25;
Serial.print("#CLI: Come get some app!m1(");
Serial.print(_instance->Client__a_var);
Serial.print(", ");
Serial.print(_instance->Client__b_var);
Serial.print(", ");
Serial.print(_instance->Client__c_var);
Serial.print(", ");
Serial.print(_instance->Client__d_var);
Serial.print(", ");
Serial.print(_instance->Client__e_var);
Serial.print(")!");
Serial.println();
Client_send_app_m1(_instance, _instance->Client__a_var, _instance->Client__b_var, _instance->Client__c_var, _instance->Client__d_var, _instance->Client__e_var);
Serial.print("#CLI: Come get some app!m2(");
Serial.print(_instance->Client__a_var);
Serial.print(", ");
Serial.print(_instance->Client__b_var);
Serial.print(", ");
Serial.print(_instance->Client__c_var);
Serial.print(")!");
Serial.println();
Client_send_app_m2(_instance, _instance->Client__a_var, _instance->Client__b_var, _instance->Client__c_var);
_instance->Client_counter_var++;
break;
}
case CLIENT_NULL_STOP_STATE:{
Serial.print("#CLI: What are you waitin for? Christmas?");
Serial.println();
;unsigned long duration = millis() - _instance->Client_start_var;
Serial.print("#CLI: took ");
Serial.print(duration);
Serial.print("ms.");
Serial.println();
_instance->active = false;
break;
}
default: break;
}
}

// On Exit Actions:
void Client_OnExit(int state, struct Client_Instance *_instance) {
switch(state) {
case CLIENT_STATE:{
Client_OnExit(_instance->Client_State, _instance);
break;}
case CLIENT_NULL_ERROR_STATE:{
break;}
case CLIENT_NULL_RUN_STATE:{
break;}
case CLIENT_NULL_STOP_STATE:{
break;}
default: break;
}
}

// Event Handlers for incoming messages:
void Client_handle_app_m3(struct Client_Instance *_instance, uint8_t a) {
if(!(_instance->active)) return;
//Region null
uint8_t Client_State_event_consumed = 0;
if (_instance->Client_State == CLIENT_NULL_RUN_STATE) {
if (Client_State_event_consumed == 0 && a == _instance->Client__a_var) {
Client_OnExit(CLIENT_NULL_RUN_STATE, _instance);
_instance->Client_State = CLIENT_NULL_RUN_STATE;
Serial.print("#CLI: Ooh, I needed that app?m3(");
Serial.print(a);
Serial.print(")!");
Serial.println();
Serial.print("#CLI: We meet again, Doctor Jones!");
Serial.println();
Client_OnEntry(CLIENT_NULL_RUN_STATE, _instance);
Client_State_event_consumed = 1;
}
else if (Client_State_event_consumed == 0 && a != _instance->Client__a_var) {
Client_OnExit(CLIENT_NULL_RUN_STATE, _instance);
_instance->Client_State = CLIENT_NULL_ERROR_STATE;
Serial.print("#CLI: Ooh, I needed that app?m3(");
Serial.print(a);
Serial.print(")!");
Serial.println();
Serial.print("#CLI: Damn, you re ugly.");
Serial.println();
Client_OnEntry(CLIENT_NULL_ERROR_STATE, _instance);
Client_State_event_consumed = 1;
}
}
//End Region null
//End dsregion null
//Session list: 
}
int Client_handle_empty_event(struct Client_Instance *_instance) {
 uint8_t empty_event_consumed = 0;
if(!(_instance->active)) return 0;
//Region null
if (_instance->Client_State == CLIENT_NULL_RUN_STATE) {
if (_instance->Client_counter_var == 100) {
Client_OnExit(CLIENT_NULL_RUN_STATE, _instance);
_instance->Client_State = CLIENT_NULL_STOP_STATE;
Client_OnEntry(CLIENT_NULL_STOP_STATE, _instance);
return 1;
}
}
//begin dispatchEmptyToSession
//end dispatchEmptyToSession
return empty_event_consumed;
}

// Observers for outgoing messages:
void (*external_Client_send_app_m1_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void (*Client_send_app_m1_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void register_external_Client_send_app_m1_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
external_Client_send_app_m1_listener = _listener;
}
void register_Client_send_app_m1_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
Client_send_app_m1_listener = _listener;
}
void Client_send_app_m1(struct Client_Instance *_instance, uint8_t a, uint8_t b, uint8_t c, uint8_t d, uint8_t e){
if (Client_send_app_m1_listener != 0x0) Client_send_app_m1_listener(_instance, a, b, c, d, e);
if (external_Client_send_app_m1_listener != 0x0) external_Client_send_app_m1_listener(_instance, a, b, c, d, e);
;
}
void (*external_Client_send_app_m2_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t)= 0x0;
void (*Client_send_app_m2_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t)= 0x0;
void register_external_Client_send_app_m2_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t)){
external_Client_send_app_m2_listener = _listener;
}
void register_Client_send_app_m2_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t)){
Client_send_app_m2_listener = _listener;
}
void Client_send_app_m2(struct Client_Instance *_instance, uint8_t a, uint8_t b, uint8_t c){
if (Client_send_app_m2_listener != 0x0) Client_send_app_m2_listener(_instance, a, b, c);
if (external_Client_send_app_m2_listener != 0x0) external_Client_send_app_m2_listener(_instance, a, b, c);
;
}






/*****************************************************************************
 * Definitions for configuration : test
 *****************************************************************************/

//Declaration of instance variables
//Instance client1
// Variables for the properties of the instance
struct Client_Instance client1_var;
// Variables for the sessions of the instance
//Instance app
// Variables for the properties of the instance
struct App_Instance app_var;
// Variables for the sessions of the instance


// Enqueue of messages Client::app::m2
void enqueue_Client_send_app_m2(struct Client_Instance *_instance, uint8_t a, uint8_t b, uint8_t c){
if ( fifo_byte_available() > 7 ) {

_fifo_enqueue( (1 >> 8) & 0xFF );
_fifo_enqueue( 1 & 0xFF );

// ID of the source port of the instance
_fifo_enqueue( (_instance->id_app >> 8) & 0xFF );
_fifo_enqueue( _instance->id_app & 0xFF );

// parameter a
union u_a_t {
uint8_t p;
byte bytebuffer[1];
} u_a;
u_a.p = a;
_fifo_enqueue(u_a.bytebuffer[0] & 0xFF );

// parameter b
union u_b_t {
uint8_t p;
byte bytebuffer[1];
} u_b;
u_b.p = b;
_fifo_enqueue(u_b.bytebuffer[0] & 0xFF );

// parameter c
union u_c_t {
uint8_t p;
byte bytebuffer[1];
} u_c;
u_c.p = c;
_fifo_enqueue(u_c.bytebuffer[0] & 0xFF );
}
}
// Enqueue of messages Client::app::m1
void enqueue_Client_send_app_m1(struct Client_Instance *_instance, uint8_t a, uint8_t b, uint8_t c, uint8_t d, uint8_t e){
if ( fifo_byte_available() > 9 ) {

_fifo_enqueue( (2 >> 8) & 0xFF );
_fifo_enqueue( 2 & 0xFF );

// ID of the source port of the instance
_fifo_enqueue( (_instance->id_app >> 8) & 0xFF );
_fifo_enqueue( _instance->id_app & 0xFF );

// parameter a
union u_a_t {
uint8_t p;
byte bytebuffer[1];
} u_a;
u_a.p = a;
_fifo_enqueue(u_a.bytebuffer[0] & 0xFF );

// parameter b
union u_b_t {
uint8_t p;
byte bytebuffer[1];
} u_b;
u_b.p = b;
_fifo_enqueue(u_b.bytebuffer[0] & 0xFF );

// parameter c
union u_c_t {
uint8_t p;
byte bytebuffer[1];
} u_c;
u_c.p = c;
_fifo_enqueue(u_c.bytebuffer[0] & 0xFF );

// parameter d
union u_d_t {
uint8_t p;
byte bytebuffer[1];
} u_d;
u_d.p = d;
_fifo_enqueue(u_d.bytebuffer[0] & 0xFF );

// parameter e
union u_e_t {
uint8_t p;
byte bytebuffer[1];
} u_e;
u_e.p = e;
_fifo_enqueue(u_e.bytebuffer[0] & 0xFF );
}
}
// Enqueue of messages App::app::m3
void enqueue_App_send_app_m3(struct App_Instance *_instance, uint8_t a){
if ( fifo_byte_available() > 5 ) {

_fifo_enqueue( (3 >> 8) & 0xFF );
_fifo_enqueue( 3 & 0xFF );

// ID of the source port of the instance
_fifo_enqueue( (_instance->id_app >> 8) & 0xFF );
_fifo_enqueue( _instance->id_app & 0xFF );

// parameter a
union u_a_t {
uint8_t p;
byte bytebuffer[1];
} u_a;
u_a.p = a;
_fifo_enqueue(u_a.bytebuffer[0] & 0xFF );
}
}


//New dispatcher for messages
void dispatch_m2(uint16_t sender, uint8_t param_a, uint8_t param_b, uint8_t param_c) {
if (sender == client1_var.id_app) {
App_handle_app_m2(&app_var, param_a, param_b, param_c);

}

}


//New dispatcher for messages
void dispatch_m1(uint16_t sender, uint8_t param_a, uint8_t param_b, uint8_t param_c, uint8_t param_d, uint8_t param_e) {
if (sender == client1_var.id_app) {
App_handle_app_m1(&app_var, param_a, param_b, param_c, param_d, param_e);

}

}


//New dispatcher for messages
void dispatch_m3(uint16_t sender, uint8_t param_a) {
if (sender == app_var.id_app) {
Client_handle_app_m3(&client1_var, param_a);

}

}


int processMessageQueue() {
if (fifo_empty()) return 0; // return 0 if there is nothing to do

uint8_t mbufi = 0;

// Read the code of the next port/message in the queue
uint16_t code = fifo_dequeue() << 8;

code += fifo_dequeue();

// Switch to call the appropriate handler
switch(code) {
case 1:{
byte mbuf[7 - 2];
while (mbufi < (7 - 2)) mbuf[mbufi++] = fifo_dequeue();
uint8_t mbufi_m2 = 2;
union u_m2_a_t {
uint8_t p;
byte bytebuffer[1];
} u_m2_a;
u_m2_a.bytebuffer[0] = mbuf[mbufi_m2 + 0];
mbufi_m2 += 1;
union u_m2_b_t {
uint8_t p;
byte bytebuffer[1];
} u_m2_b;
u_m2_b.bytebuffer[0] = mbuf[mbufi_m2 + 0];
mbufi_m2 += 1;
union u_m2_c_t {
uint8_t p;
byte bytebuffer[1];
} u_m2_c;
u_m2_c.bytebuffer[0] = mbuf[mbufi_m2 + 0];
mbufi_m2 += 1;
dispatch_m2((mbuf[0] << 8) + mbuf[1] /* instance port*/,
 u_m2_a.p /* a */ ,
 u_m2_b.p /* b */ ,
 u_m2_c.p /* c */ );
break;
}
case 2:{
byte mbuf[9 - 2];
while (mbufi < (9 - 2)) mbuf[mbufi++] = fifo_dequeue();
uint8_t mbufi_m1 = 2;
union u_m1_a_t {
uint8_t p;
byte bytebuffer[1];
} u_m1_a;
u_m1_a.bytebuffer[0] = mbuf[mbufi_m1 + 0];
mbufi_m1 += 1;
union u_m1_b_t {
uint8_t p;
byte bytebuffer[1];
} u_m1_b;
u_m1_b.bytebuffer[0] = mbuf[mbufi_m1 + 0];
mbufi_m1 += 1;
union u_m1_c_t {
uint8_t p;
byte bytebuffer[1];
} u_m1_c;
u_m1_c.bytebuffer[0] = mbuf[mbufi_m1 + 0];
mbufi_m1 += 1;
union u_m1_d_t {
uint8_t p;
byte bytebuffer[1];
} u_m1_d;
u_m1_d.bytebuffer[0] = mbuf[mbufi_m1 + 0];
mbufi_m1 += 1;
union u_m1_e_t {
uint8_t p;
byte bytebuffer[1];
} u_m1_e;
u_m1_e.bytebuffer[0] = mbuf[mbufi_m1 + 0];
mbufi_m1 += 1;
dispatch_m1((mbuf[0] << 8) + mbuf[1] /* instance port*/,
 u_m1_a.p /* a */ ,
 u_m1_b.p /* b */ ,
 u_m1_c.p /* c */ ,
 u_m1_d.p /* d */ ,
 u_m1_e.p /* e */ );
break;
}
case 3:{
byte mbuf[5 - 2];
while (mbufi < (5 - 2)) mbuf[mbufi++] = fifo_dequeue();
uint8_t mbufi_m3 = 2;
union u_m3_a_t {
uint8_t p;
byte bytebuffer[1];
} u_m3_a;
u_m3_a.bytebuffer[0] = mbuf[mbufi_m3 + 0];
mbufi_m3 += 1;
dispatch_m3((mbuf[0] << 8) + mbuf[1] /* instance port*/,
 u_m3_a.p /* a */ );
break;
}
}
return 1;
}


//external Message enqueue

void initialize_configuration_test() {
// Initialize connectors
register_Client_send_app_m1_listener(&enqueue_Client_send_app_m1);
register_Client_send_app_m2_listener(&enqueue_Client_send_app_m2);
register_App_send_app_m3_listener(&enqueue_App_send_app_m3);


// Network Initialization
// End Network Initialization

// Init the ID, state variables and properties for instance app
app_var.active = true;
app_var.id_app = add_instance( (void*) &app_var);
app_var.App_State = APP_NULL_WAITFORM1_STATE;

App_OnEntry(APP_STATE, &app_var);
// Init the ID, state variables and properties for instance client1
client1_var.active = true;
client1_var.id_app = add_instance( (void*) &client1_var);
client1_var.Client_State = CLIENT_NULL_RUN_STATE;
client1_var.Client_counter_var = 0;

Client_OnEntry(CLIENT_STATE, &client1_var);
}




void setup() {
Serial.begin(115200);
initialize_configuration_test();

}

void loop() {

// Network Listener// End Network Listener

int emptyEventConsumed = 1;
while (emptyEventConsumed != 0) {
emptyEventConsumed = 0;
emptyEventConsumed += Client_handle_empty_event(&client1_var);
emptyEventConsumed += App_handle_empty_event(&app_var);
}

    processMessageQueue();
}
