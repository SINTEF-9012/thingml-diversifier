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
uint16_t id_diversified;
// Variables for the current instance state
int App_State;
int App_null_generate_m2_from_m2r0r1_and_m2abc_State;
int App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_State;
int App_null_generate_m1_from_m1abcd_and_m1e_State;
int App_null_generate_m1_from_m1abcd_and_m1e_INIT_State;
int App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_State;
int App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_State;
int App_null_generate_m1bis_from_m1bisabcd_and_m1bise_State;
int App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_State;
// Variables for the properties of the instance
uint8_t App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_b_var;
uint8_t App_null_generate_m1_from_m1abcd_and_m1e_INIT_a_var;
uint8_t App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_c_var;
uint8_t App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_r0_var;
uint8_t App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_b_var;
uint8_t App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_c_var;
uint8_t App_null_generate_m1_from_m1abcd_and_m1e_INIT_e_var;
uint8_t App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_b_var;
uint8_t App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_a_var;
uint8_t App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_d_var;
uint8_t App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_r1_var;
uint8_t App_null_generate_m1_from_m1abcd_and_m1e_INIT_c_var;
uint8_t App_id_var;
uint8_t App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_a_var;
uint8_t App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_a_var;
uint8_t App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_c_var;
uint8_t App_null_generate_m1_from_m1abcd_and_m1e_INIT_d_var;
uint8_t App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_r1_var;
uint8_t App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_e_var;
uint8_t App_null_generate_m1_from_m1abcd_and_m1e_INIT_b_var;
uint8_t App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_r0_var;

};
// Declaration of prototypes outgoing messages :
void App_OnEntry(int state, struct App_Instance *_instance);
void App_handle_diversified_m2(struct App_Instance *_instance, uint8_t r0, uint8_t r1, uint8_t a, uint8_t b, uint8_t c);
void App_handle_diversified_m1(struct App_Instance *_instance, uint8_t a, uint8_t b, uint8_t c, uint8_t d, uint8_t e);
void App_handle_diversified_m1bis(struct App_Instance *_instance, uint8_t a, uint8_t b, uint8_t c, uint8_t d, uint8_t e);
void App_handle_diversified_m2bis(struct App_Instance *_instance, uint8_t r0, uint8_t r1, uint8_t a, uint8_t b, uint8_t c);
void App_handle_app_m1bise(struct App_Instance *_instance, uint8_t r32, uint8_t r35, uint8_t r33, uint8_t r34, uint8_t e);
void App_handle_app_m2bisc(struct App_Instance *_instance, uint8_t r24, uint8_t r22, uint8_t r23, uint8_t r25, uint8_t c);
void App_handle_app_m1e(struct App_Instance *_instance, uint8_t r18, uint8_t r19, uint8_t r20, uint8_t r17, uint8_t e);
void App_handle_app_m2r0r1(struct App_Instance *_instance, uint8_t r6, uint8_t r0, uint8_t r7, uint8_t r8, uint8_t r1);
void App_handle_app_m1bisabcd(struct App_Instance *_instance, uint8_t a, uint8_t r31, uint8_t b, uint8_t c, uint8_t d);
void App_handle_app_m2abc(struct App_Instance *_instance, uint8_t a, uint8_t r10, uint8_t b, uint8_t r9, uint8_t c);
void App_handle_app_m1abcd(struct App_Instance *_instance, uint8_t a, uint8_t b, uint8_t c, uint8_t r16, uint8_t d);
void App_handle_app_m2bisr0r1ab(struct App_Instance *_instance, uint8_t r21, uint8_t r0, uint8_t r1, uint8_t a, uint8_t b);
// Declaration of callbacks for incoming messages:
void register_App_send_app_m3__listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_external_App_send_app_m3__listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_App_send_app_m3r4r5r2r3a_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_external_App_send_app_m3r4r5r2r3a_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_App_send_app_m3bis__listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_external_App_send_app_m3bis__listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_App_send_app_m3bisr4r5r2r3a_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_external_App_send_app_m3bisr4r5r2r3a_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_App_send_diversified_m2_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_external_App_send_diversified_m2_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_App_send_diversified_m1abcd_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_external_App_send_diversified_m1abcd_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_App_send_diversified_m1e_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_external_App_send_diversified_m1e_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_App_send_diversified_m1_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_external_App_send_diversified_m1_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_App_send_diversified_m2bisr0r1ab_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_external_App_send_diversified_m2bisr0r1ab_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_App_send_diversified_m2bisc_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_external_App_send_diversified_m2bisc_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_App_send_diversified_m2bis_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_external_App_send_diversified_m2bis_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_App_send_diversified_m1bisabcd_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_external_App_send_diversified_m1bisabcd_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_App_send_diversified_m1bise_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_external_App_send_diversified_m1bise_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_App_send_diversified_m1bis_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_external_App_send_diversified_m1bis_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));

// Definition of the states:
#define APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_S2_STATE 0
#define APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_S2_STATE 1
#define APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_STATE 2
#define APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_S1_STATE 3
#define APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_S2_STATE 4
#define APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_S4_STATE 5
#define APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_S4_STATE 6
#define APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_STATE 7
#define APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_S3_STATE 8
#define APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_S1_STATE 9
#define APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_S3_STATE 10
#define APP_STATE 11
#define APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_S3_STATE 12
#define APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_S2_STATE 13
#define APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_STATE 14
#define APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_S4_STATE 15
#define APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_S4_STATE 16
#define APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_S1_STATE 17
#define APP_NULL_WAITFORM2_STATE 18
#define APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_S1_STATE 19
#define APP_NULL_WAITFORM1_STATE 20
#define APP_NULL_SENDACK_STATE 21
#define APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_STATE 22
#define APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_S3_STATE 23


/*****************************************************************************
 * Headers for type : Client
 *****************************************************************************/

// Definition of the instance struct:
struct Client_Instance {

// Instances of different sessions
bool active;
// Variables for the ID of the ports of the instance
uint16_t id_app;
uint16_t id_diversified;
// Variables for the current instance state
int Client_State;
int Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_State;
int Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_State;
int Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_State;
int Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_State;
// Variables for the properties of the instance
uint16_t Client_start_var;
uint8_t Client__d_var;
uint8_t Client__e_var;
uint8_t Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r4_var;
uint8_t Client__c_var;
uint8_t Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r3_var;
uint8_t Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_a_var;
uint8_t Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r2_var;
uint8_t Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_a_var;
uint8_t Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r5_var;
uint8_t Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r4_var;
uint8_t Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r2_var;
uint8_t Client__a_var;
uint16_t Client_counter_var;
uint8_t Client__b_var;
uint8_t Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r5_var;
uint8_t Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r3_var;

};
// Declaration of prototypes outgoing messages :
void Client_OnEntry(int state, struct Client_Instance *_instance);
void Client_handle_app_m3r4r5r2r3a(struct Client_Instance *_instance, uint8_t r4, uint8_t r5, uint8_t r2, uint8_t r3, uint8_t a);
void Client_handle_app_m3bis_(struct Client_Instance *_instance, uint8_t r29, uint8_t r28, uint8_t r30, uint8_t r27, uint8_t r26);
void Client_handle_app_m3_(struct Client_Instance *_instance, uint8_t r13, uint8_t r15, uint8_t r12, uint8_t r14, uint8_t r11);
void Client_handle_app_m3bisr4r5r2r3a(struct Client_Instance *_instance, uint8_t r4, uint8_t r5, uint8_t r2, uint8_t r3, uint8_t a);
void Client_handle_diversified_m3bis(struct Client_Instance *_instance, uint8_t r4, uint8_t r5, uint8_t r2, uint8_t r3, uint8_t a);
void Client_handle_diversified_m3(struct Client_Instance *_instance, uint8_t r4, uint8_t r5, uint8_t r2, uint8_t r3, uint8_t a);
// Declaration of callbacks for incoming messages:
void register_Client_send_app_m2r0r1_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_external_Client_send_app_m2r0r1_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_Client_send_app_m2abc_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_external_Client_send_app_m2abc_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_Client_send_app_m1abcd_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_external_Client_send_app_m1abcd_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_Client_send_app_m1e_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_external_Client_send_app_m1e_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_Client_send_app_m2bisr0r1ab_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_external_Client_send_app_m2bisr0r1ab_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_Client_send_app_m2bisc_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_external_Client_send_app_m2bisc_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_Client_send_app_m1bisabcd_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_external_Client_send_app_m1bisabcd_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_Client_send_app_m1bise_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_external_Client_send_app_m1bise_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_Client_send_diversified_m3_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_external_Client_send_diversified_m3_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_Client_send_diversified_m3bis__listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_external_Client_send_diversified_m3bis__listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_Client_send_diversified_m3bisr4r5r2r3a_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_external_Client_send_diversified_m3bisr4r5r2r3a_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_Client_send_diversified_m3bis_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));
void register_external_Client_send_diversified_m3bis_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t));

// Definition of the states:
#define CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_S3_STATE 0
#define CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_S1_STATE 1
#define CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_S4_STATE 2
#define CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_S1_STATE 3
#define CLIENT_STATE 4
#define CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_S4_STATE 5
#define CLIENT_NULL_ERROR_STATE 6
#define CLIENT_NULL_STOP_STATE 7
#define CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_S3_STATE 8
#define CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_STATE 9
#define CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_S2_STATE 10
#define CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_S2_STATE 11
#define CLIENT_NULL_RUN_STATE 12
#define CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_STATE 13



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

#define MAX_INSTANCES 4
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
void App_send_app_m3_(struct App_Instance *_instance, uint8_t r13, uint8_t r15, uint8_t r12, uint8_t r14, uint8_t r11);
void App_send_app_m3r4r5r2r3a(struct App_Instance *_instance, uint8_t r4, uint8_t r5, uint8_t r2, uint8_t r3, uint8_t a);
void App_send_app_m3bis_(struct App_Instance *_instance, uint8_t r29, uint8_t r28, uint8_t r30, uint8_t r27, uint8_t r26);
void App_send_app_m3bisr4r5r2r3a(struct App_Instance *_instance, uint8_t r4, uint8_t r5, uint8_t r2, uint8_t r3, uint8_t a);
void App_send_diversified_m2(struct App_Instance *_instance, uint8_t r0, uint8_t r1, uint8_t a, uint8_t b, uint8_t c);
void App_send_diversified_m1abcd(struct App_Instance *_instance, uint8_t a, uint8_t b, uint8_t c, uint8_t r16, uint8_t d);
void App_send_diversified_m1e(struct App_Instance *_instance, uint8_t r18, uint8_t r19, uint8_t r20, uint8_t r17, uint8_t e);
void App_send_diversified_m1(struct App_Instance *_instance, uint8_t a, uint8_t b, uint8_t c, uint8_t d, uint8_t e);
void App_send_diversified_m2bisr0r1ab(struct App_Instance *_instance, uint8_t r21, uint8_t r0, uint8_t r1, uint8_t a, uint8_t b);
void App_send_diversified_m2bisc(struct App_Instance *_instance, uint8_t r24, uint8_t r22, uint8_t r23, uint8_t r25, uint8_t c);
void App_send_diversified_m2bis(struct App_Instance *_instance, uint8_t r0, uint8_t r1, uint8_t a, uint8_t b, uint8_t c);
void App_send_diversified_m1bisabcd(struct App_Instance *_instance, uint8_t a, uint8_t r31, uint8_t b, uint8_t c, uint8_t d);
void App_send_diversified_m1bise(struct App_Instance *_instance, uint8_t r32, uint8_t r35, uint8_t r33, uint8_t r34, uint8_t e);
void App_send_diversified_m1bis(struct App_Instance *_instance, uint8_t a, uint8_t b, uint8_t c, uint8_t d, uint8_t e);
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
case APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_STATE:{
_instance->App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_State = APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_S1_STATE;
App_OnEntry(_instance->App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_State, _instance);
break;
}
case APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_STATE:{
_instance->App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_State = APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_S1_STATE;
App_OnEntry(_instance->App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_State, _instance);
break;
}
case APP_STATE:{
_instance->App_State = APP_NULL_WAITFORM1_STATE;
_instance->App_null_generate_m2_from_m2r0r1_and_m2abc_State = APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_STATE;
_instance->App_null_generate_m1_from_m1abcd_and_m1e_State = APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_STATE;
_instance->App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_State = APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_STATE;
_instance->App_null_generate_m1bis_from_m1bisabcd_and_m1bise_State = APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_STATE;
App_OnEntry(_instance->App_State, _instance);
App_OnEntry(_instance->App_null_generate_m2_from_m2r0r1_and_m2abc_State, _instance);
App_OnEntry(_instance->App_null_generate_m1_from_m1abcd_and_m1e_State, _instance);
App_OnEntry(_instance->App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_State, _instance);
App_OnEntry(_instance->App_null_generate_m1bis_from_m1bisabcd_and_m1bise_State, _instance);
break;
}
case APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_STATE:{
_instance->App_null_generate_m1_from_m1abcd_and_m1e_INIT_State = APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_S1_STATE;
App_OnEntry(_instance->App_null_generate_m1_from_m1abcd_and_m1e_INIT_State, _instance);
break;
}
case APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_STATE:{
_instance->App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_State = APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_S1_STATE;
App_OnEntry(_instance->App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_State, _instance);
break;
}
case APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_S2_STATE:{
break;
}
case APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_S2_STATE:{
break;
}
case APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_S1_STATE:{
break;
}
case APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_S2_STATE:{
break;
}
case APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_S4_STATE:{
App_send_diversified_m1bis(_instance, _instance->App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_a_var, _instance->App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_b_var, _instance->App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_c_var, _instance->App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_d_var, _instance->App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_e_var);
break;
}
case APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_S4_STATE:{
App_send_diversified_m2(_instance, _instance->App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_r0_var, _instance->App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_r1_var, _instance->App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_a_var, _instance->App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_b_var, _instance->App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_c_var);
break;
}
case APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_S3_STATE:{
break;
}
case APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_S1_STATE:{
break;
}
case APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_S3_STATE:{
break;
}
case APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_S3_STATE:{
break;
}
case APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_S2_STATE:{
break;
}
case APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_S4_STATE:{
App_send_diversified_m2bis(_instance, _instance->App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_r0_var, _instance->App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_r1_var, _instance->App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_a_var, _instance->App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_b_var, _instance->App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_c_var);
break;
}
case APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_S4_STATE:{
App_send_diversified_m1(_instance, _instance->App_null_generate_m1_from_m1abcd_and_m1e_INIT_a_var, _instance->App_null_generate_m1_from_m1abcd_and_m1e_INIT_b_var, _instance->App_null_generate_m1_from_m1abcd_and_m1e_INIT_c_var, _instance->App_null_generate_m1_from_m1abcd_and_m1e_INIT_d_var, _instance->App_null_generate_m1_from_m1abcd_and_m1e_INIT_e_var);
break;
}
case APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_S1_STATE:{
break;
}
case APP_NULL_WAITFORM2_STATE:{
break;
}
case APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_S1_STATE:{
break;
}
case APP_NULL_WAITFORM1_STATE:{
break;
}
case APP_NULL_SENDACK_STATE:{
Serial.print("#APP: Come get some app!m3(");
Serial.print(_instance->App_id_var);
Serial.print(")!");
Serial.println();
if(f_App_rnd(_instance) < 74) {
if(f_App_rnd(_instance) < 69) {
Serial.print("8");
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.println();
App_send_app_m3_(_instance, f_App_rnd(_instance), f_App_rnd(_instance), f_App_rnd(_instance), f_App_rnd(_instance), f_App_rnd(_instance));
Serial.print("9");
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.print(_instance->App_id_var);
Serial.print(", ");
Serial.println();
App_send_app_m3r4r5r2r3a(_instance, f_App_rnd(_instance), f_App_rnd(_instance), f_App_rnd(_instance), f_App_rnd(_instance), _instance->App_id_var);

} else {
Serial.print("9");
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.print(_instance->App_id_var);
Serial.print(", ");
Serial.println();
App_send_app_m3r4r5r2r3a(_instance, f_App_rnd(_instance), f_App_rnd(_instance), f_App_rnd(_instance), f_App_rnd(_instance), _instance->App_id_var);
Serial.print("8");
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.println();
App_send_app_m3_(_instance, f_App_rnd(_instance), f_App_rnd(_instance), f_App_rnd(_instance), f_App_rnd(_instance), f_App_rnd(_instance));

}

} else {
if(f_App_rnd(_instance) < 248) {
Serial.print("14");
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.println();
App_send_app_m3bis_(_instance, f_App_rnd(_instance), f_App_rnd(_instance), f_App_rnd(_instance), f_App_rnd(_instance), f_App_rnd(_instance));
Serial.print("15");
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.print(_instance->App_id_var);
Serial.print(", ");
Serial.println();
App_send_app_m3bisr4r5r2r3a(_instance, f_App_rnd(_instance), f_App_rnd(_instance), f_App_rnd(_instance), f_App_rnd(_instance), _instance->App_id_var);

} else {
Serial.print("15");
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.print(_instance->App_id_var);
Serial.print(", ");
Serial.println();
App_send_app_m3bisr4r5r2r3a(_instance, f_App_rnd(_instance), f_App_rnd(_instance), f_App_rnd(_instance), f_App_rnd(_instance), _instance->App_id_var);
Serial.print("14");
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.print(f_App_rnd(_instance));
Serial.print(", ");
Serial.println();
App_send_app_m3bis_(_instance, f_App_rnd(_instance), f_App_rnd(_instance), f_App_rnd(_instance), f_App_rnd(_instance), f_App_rnd(_instance));

}

}
break;
}
case APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_S3_STATE:{
break;
}
default: break;
}
}

// On Exit Actions:
void App_OnExit(int state, struct App_Instance *_instance) {
switch(state) {
case APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_STATE:{
App_OnExit(_instance->App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_State, _instance);
break;}
case APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_STATE:{
App_OnExit(_instance->App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_State, _instance);
break;}
case APP_STATE:{
App_OnExit(_instance->App_State, _instance);
App_OnExit(_instance->App_null_generate_m2_from_m2r0r1_and_m2abc_State, _instance);
App_OnExit(_instance->App_null_generate_m1_from_m1abcd_and_m1e_State, _instance);
App_OnExit(_instance->App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_State, _instance);
App_OnExit(_instance->App_null_generate_m1bis_from_m1bisabcd_and_m1bise_State, _instance);
break;}
case APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_STATE:{
App_OnExit(_instance->App_null_generate_m1_from_m1abcd_and_m1e_INIT_State, _instance);
break;}
case APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_STATE:{
App_OnExit(_instance->App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_State, _instance);
break;}
case APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_S2_STATE:{
break;}
case APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_S2_STATE:{
break;}
case APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_S1_STATE:{
break;}
case APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_S2_STATE:{
break;}
case APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_S4_STATE:{
break;}
case APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_S4_STATE:{
break;}
case APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_S3_STATE:{
break;}
case APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_S1_STATE:{
break;}
case APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_S3_STATE:{
break;}
case APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_S3_STATE:{
break;}
case APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_S2_STATE:{
break;}
case APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_S4_STATE:{
break;}
case APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_S4_STATE:{
break;}
case APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_S1_STATE:{
break;}
case APP_NULL_WAITFORM2_STATE:{
break;}
case APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_S1_STATE:{
break;}
case APP_NULL_WAITFORM1_STATE:{
break;}
case APP_NULL_SENDACK_STATE:{
break;}
case APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_S3_STATE:{
break;}
default: break;
}
}

// Event Handlers for incoming messages:
void App_handle_diversified_m2(struct App_Instance *_instance, uint8_t r0, uint8_t r1, uint8_t a, uint8_t b, uint8_t c) {
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
//Region generate_m2_from_m2r0r1_and_m2abc
uint8_t App_null_generate_m2_from_m2r0r1_and_m2abc_State_event_consumed = 0;
//End Region generate_m2_from_m2r0r1_and_m2abc
//Region generate_m1_from_m1abcd_and_m1e
uint8_t App_null_generate_m1_from_m1abcd_and_m1e_State_event_consumed = 0;
//End Region generate_m1_from_m1abcd_and_m1e
//Region generate_m2bis_from_m2bisr0r1ab_and_m2bisc
uint8_t App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_State_event_consumed = 0;
//End Region generate_m2bis_from_m2bisr0r1ab_and_m2bisc
//Region generate_m1bis_from_m1bisabcd_and_m1bise
uint8_t App_null_generate_m1bis_from_m1bisabcd_and_m1bise_State_event_consumed = 0;
//End Region generate_m1bis_from_m1bisabcd_and_m1bise
//End dsregion null
//Session list: 
}
void App_handle_diversified_m1(struct App_Instance *_instance, uint8_t a, uint8_t b, uint8_t c, uint8_t d, uint8_t e) {
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
//Region generate_m2_from_m2r0r1_and_m2abc
uint8_t App_null_generate_m2_from_m2r0r1_and_m2abc_State_event_consumed = 0;
//End Region generate_m2_from_m2r0r1_and_m2abc
//Region generate_m1_from_m1abcd_and_m1e
uint8_t App_null_generate_m1_from_m1abcd_and_m1e_State_event_consumed = 0;
//End Region generate_m1_from_m1abcd_and_m1e
//Region generate_m2bis_from_m2bisr0r1ab_and_m2bisc
uint8_t App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_State_event_consumed = 0;
//End Region generate_m2bis_from_m2bisr0r1ab_and_m2bisc
//Region generate_m1bis_from_m1bisabcd_and_m1bise
uint8_t App_null_generate_m1bis_from_m1bisabcd_and_m1bise_State_event_consumed = 0;
//End Region generate_m1bis_from_m1bisabcd_and_m1bise
//End dsregion null
//Session list: 
}
void App_handle_diversified_m1bis(struct App_Instance *_instance, uint8_t a, uint8_t b, uint8_t c, uint8_t d, uint8_t e) {
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
//Region generate_m2_from_m2r0r1_and_m2abc
uint8_t App_null_generate_m2_from_m2r0r1_and_m2abc_State_event_consumed = 0;
//End Region generate_m2_from_m2r0r1_and_m2abc
//Region generate_m1_from_m1abcd_and_m1e
uint8_t App_null_generate_m1_from_m1abcd_and_m1e_State_event_consumed = 0;
//End Region generate_m1_from_m1abcd_and_m1e
//Region generate_m2bis_from_m2bisr0r1ab_and_m2bisc
uint8_t App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_State_event_consumed = 0;
//End Region generate_m2bis_from_m2bisr0r1ab_and_m2bisc
//Region generate_m1bis_from_m1bisabcd_and_m1bise
uint8_t App_null_generate_m1bis_from_m1bisabcd_and_m1bise_State_event_consumed = 0;
//End Region generate_m1bis_from_m1bisabcd_and_m1bise
//End dsregion null
//Session list: 
}
void App_handle_diversified_m2bis(struct App_Instance *_instance, uint8_t r0, uint8_t r1, uint8_t a, uint8_t b, uint8_t c) {
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
//Region generate_m2_from_m2r0r1_and_m2abc
uint8_t App_null_generate_m2_from_m2r0r1_and_m2abc_State_event_consumed = 0;
//End Region generate_m2_from_m2r0r1_and_m2abc
//Region generate_m1_from_m1abcd_and_m1e
uint8_t App_null_generate_m1_from_m1abcd_and_m1e_State_event_consumed = 0;
//End Region generate_m1_from_m1abcd_and_m1e
//Region generate_m2bis_from_m2bisr0r1ab_and_m2bisc
uint8_t App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_State_event_consumed = 0;
//End Region generate_m2bis_from_m2bisr0r1ab_and_m2bisc
//Region generate_m1bis_from_m1bisabcd_and_m1bise
uint8_t App_null_generate_m1bis_from_m1bisabcd_and_m1bise_State_event_consumed = 0;
//End Region generate_m1bis_from_m1bisabcd_and_m1bise
//End dsregion null
//Session list: 
}
void App_handle_app_m1bise(struct App_Instance *_instance, uint8_t r32, uint8_t r35, uint8_t r33, uint8_t r34, uint8_t e) {
if(!(_instance->active)) return;
//Region null
uint8_t App_State_event_consumed = 0;
//End Region null
//Region generate_m2_from_m2r0r1_and_m2abc
uint8_t App_null_generate_m2_from_m2r0r1_and_m2abc_State_event_consumed = 0;
//End Region generate_m2_from_m2r0r1_and_m2abc
//Region generate_m1_from_m1abcd_and_m1e
uint8_t App_null_generate_m1_from_m1abcd_and_m1e_State_event_consumed = 0;
//End Region generate_m1_from_m1abcd_and_m1e
//Region generate_m2bis_from_m2bisr0r1ab_and_m2bisc
uint8_t App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_State_event_consumed = 0;
//End Region generate_m2bis_from_m2bisr0r1ab_and_m2bisc
//Region generate_m1bis_from_m1bisabcd_and_m1bise
uint8_t App_null_generate_m1bis_from_m1bisabcd_and_m1bise_State_event_consumed = 0;
if (_instance->App_null_generate_m1bis_from_m1bisabcd_and_m1bise_State == APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_STATE) {
//Region INIT
uint8_t App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_State_event_consumed = 0;
if (_instance->App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_State == APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_S1_STATE) {
if (App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_State_event_consumed == 0 && 1) {
App_OnExit(APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_S1_STATE, _instance);
_instance->App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_State = APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_S3_STATE;
_instance->App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_e_var = e;
App_OnEntry(APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_S3_STATE, _instance);
App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_State_event_consumed = 1;
}
}
else if (_instance->App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_State == APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_S2_STATE) {
if (App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_State_event_consumed == 0 && 1) {
App_OnExit(APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_S2_STATE, _instance);
_instance->App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_State = APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_S4_STATE;
_instance->App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_e_var = e;
App_OnEntry(APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_S4_STATE, _instance);
App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_State_event_consumed = 1;
}
}
//End Region INIT
App_null_generate_m1bis_from_m1bisabcd_and_m1bise_State_event_consumed = 0 | App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_State_event_consumed ;
//End dsregion INIT
}
//End Region generate_m1bis_from_m1bisabcd_and_m1bise
//End dsregion null
//Session list: 
}
void App_handle_app_m2bisc(struct App_Instance *_instance, uint8_t r24, uint8_t r22, uint8_t r23, uint8_t r25, uint8_t c) {
if(!(_instance->active)) return;
//Region null
uint8_t App_State_event_consumed = 0;
//End Region null
//Region generate_m2_from_m2r0r1_and_m2abc
uint8_t App_null_generate_m2_from_m2r0r1_and_m2abc_State_event_consumed = 0;
//End Region generate_m2_from_m2r0r1_and_m2abc
//Region generate_m1_from_m1abcd_and_m1e
uint8_t App_null_generate_m1_from_m1abcd_and_m1e_State_event_consumed = 0;
//End Region generate_m1_from_m1abcd_and_m1e
//Region generate_m2bis_from_m2bisr0r1ab_and_m2bisc
uint8_t App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_State_event_consumed = 0;
if (_instance->App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_State == APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_STATE) {
//Region INIT
uint8_t App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_State_event_consumed = 0;
if (_instance->App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_State == APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_S1_STATE) {
if (App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_State_event_consumed == 0 && 1) {
App_OnExit(APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_S1_STATE, _instance);
_instance->App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_State = APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_S3_STATE;
_instance->App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_c_var = c;
App_OnEntry(APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_S3_STATE, _instance);
App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_State_event_consumed = 1;
}
}
else if (_instance->App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_State == APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_S2_STATE) {
if (App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_State_event_consumed == 0 && 1) {
App_OnExit(APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_S2_STATE, _instance);
_instance->App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_State = APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_S4_STATE;
_instance->App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_c_var = c;
App_OnEntry(APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_S4_STATE, _instance);
App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_State_event_consumed = 1;
}
}
//End Region INIT
App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_State_event_consumed = 0 | App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_State_event_consumed ;
//End dsregion INIT
}
//End Region generate_m2bis_from_m2bisr0r1ab_and_m2bisc
//Region generate_m1bis_from_m1bisabcd_and_m1bise
uint8_t App_null_generate_m1bis_from_m1bisabcd_and_m1bise_State_event_consumed = 0;
//End Region generate_m1bis_from_m1bisabcd_and_m1bise
//End dsregion null
//Session list: 
}
void App_handle_app_m1e(struct App_Instance *_instance, uint8_t r18, uint8_t r19, uint8_t r20, uint8_t r17, uint8_t e) {
if(!(_instance->active)) return;
//Region null
uint8_t App_State_event_consumed = 0;
//End Region null
//Region generate_m2_from_m2r0r1_and_m2abc
uint8_t App_null_generate_m2_from_m2r0r1_and_m2abc_State_event_consumed = 0;
//End Region generate_m2_from_m2r0r1_and_m2abc
//Region generate_m1_from_m1abcd_and_m1e
uint8_t App_null_generate_m1_from_m1abcd_and_m1e_State_event_consumed = 0;
if (_instance->App_null_generate_m1_from_m1abcd_and_m1e_State == APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_STATE) {
//Region INIT
uint8_t App_null_generate_m1_from_m1abcd_and_m1e_INIT_State_event_consumed = 0;
if (_instance->App_null_generate_m1_from_m1abcd_and_m1e_INIT_State == APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_S1_STATE) {
if (App_null_generate_m1_from_m1abcd_and_m1e_INIT_State_event_consumed == 0 && 1) {
App_OnExit(APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_S1_STATE, _instance);
_instance->App_null_generate_m1_from_m1abcd_and_m1e_INIT_State = APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_S3_STATE;
_instance->App_null_generate_m1_from_m1abcd_and_m1e_INIT_e_var = e;
App_OnEntry(APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_S3_STATE, _instance);
App_null_generate_m1_from_m1abcd_and_m1e_INIT_State_event_consumed = 1;
}
}
else if (_instance->App_null_generate_m1_from_m1abcd_and_m1e_INIT_State == APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_S2_STATE) {
if (App_null_generate_m1_from_m1abcd_and_m1e_INIT_State_event_consumed == 0 && 1) {
App_OnExit(APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_S2_STATE, _instance);
_instance->App_null_generate_m1_from_m1abcd_and_m1e_INIT_State = APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_S4_STATE;
_instance->App_null_generate_m1_from_m1abcd_and_m1e_INIT_e_var = e;
App_OnEntry(APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_S4_STATE, _instance);
App_null_generate_m1_from_m1abcd_and_m1e_INIT_State_event_consumed = 1;
}
}
//End Region INIT
App_null_generate_m1_from_m1abcd_and_m1e_State_event_consumed = 0 | App_null_generate_m1_from_m1abcd_and_m1e_INIT_State_event_consumed ;
//End dsregion INIT
}
//End Region generate_m1_from_m1abcd_and_m1e
//Region generate_m2bis_from_m2bisr0r1ab_and_m2bisc
uint8_t App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_State_event_consumed = 0;
//End Region generate_m2bis_from_m2bisr0r1ab_and_m2bisc
//Region generate_m1bis_from_m1bisabcd_and_m1bise
uint8_t App_null_generate_m1bis_from_m1bisabcd_and_m1bise_State_event_consumed = 0;
//End Region generate_m1bis_from_m1bisabcd_and_m1bise
//End dsregion null
//Session list: 
}
void App_handle_app_m2r0r1(struct App_Instance *_instance, uint8_t r6, uint8_t r0, uint8_t r7, uint8_t r8, uint8_t r1) {
if(!(_instance->active)) return;
//Region null
uint8_t App_State_event_consumed = 0;
//End Region null
//Region generate_m2_from_m2r0r1_and_m2abc
uint8_t App_null_generate_m2_from_m2r0r1_and_m2abc_State_event_consumed = 0;
if (_instance->App_null_generate_m2_from_m2r0r1_and_m2abc_State == APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_STATE) {
//Region INIT
uint8_t App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_State_event_consumed = 0;
if (_instance->App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_State == APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_S1_STATE) {
if (App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_State_event_consumed == 0 && 1) {
App_OnExit(APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_S1_STATE, _instance);
_instance->App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_State = APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_S2_STATE;
_instance->App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_r0_var = r0;
_instance->App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_r1_var = r1;
App_OnEntry(APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_S2_STATE, _instance);
App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_State_event_consumed = 1;
}
}
else if (_instance->App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_State == APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_S3_STATE) {
if (App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_State_event_consumed == 0 && 1) {
App_OnExit(APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_S3_STATE, _instance);
_instance->App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_State = APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_S4_STATE;
_instance->App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_r0_var = r0;
_instance->App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_r1_var = r1;
App_OnEntry(APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_S4_STATE, _instance);
App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_State_event_consumed = 1;
}
}
//End Region INIT
App_null_generate_m2_from_m2r0r1_and_m2abc_State_event_consumed = 0 | App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_State_event_consumed ;
//End dsregion INIT
}
//End Region generate_m2_from_m2r0r1_and_m2abc
//Region generate_m1_from_m1abcd_and_m1e
uint8_t App_null_generate_m1_from_m1abcd_and_m1e_State_event_consumed = 0;
//End Region generate_m1_from_m1abcd_and_m1e
//Region generate_m2bis_from_m2bisr0r1ab_and_m2bisc
uint8_t App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_State_event_consumed = 0;
//End Region generate_m2bis_from_m2bisr0r1ab_and_m2bisc
//Region generate_m1bis_from_m1bisabcd_and_m1bise
uint8_t App_null_generate_m1bis_from_m1bisabcd_and_m1bise_State_event_consumed = 0;
//End Region generate_m1bis_from_m1bisabcd_and_m1bise
//End dsregion null
//Session list: 
}
void App_handle_app_m1bisabcd(struct App_Instance *_instance, uint8_t a, uint8_t r31, uint8_t b, uint8_t c, uint8_t d) {
if(!(_instance->active)) return;
//Region null
uint8_t App_State_event_consumed = 0;
//End Region null
//Region generate_m2_from_m2r0r1_and_m2abc
uint8_t App_null_generate_m2_from_m2r0r1_and_m2abc_State_event_consumed = 0;
//End Region generate_m2_from_m2r0r1_and_m2abc
//Region generate_m1_from_m1abcd_and_m1e
uint8_t App_null_generate_m1_from_m1abcd_and_m1e_State_event_consumed = 0;
//End Region generate_m1_from_m1abcd_and_m1e
//Region generate_m2bis_from_m2bisr0r1ab_and_m2bisc
uint8_t App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_State_event_consumed = 0;
//End Region generate_m2bis_from_m2bisr0r1ab_and_m2bisc
//Region generate_m1bis_from_m1bisabcd_and_m1bise
uint8_t App_null_generate_m1bis_from_m1bisabcd_and_m1bise_State_event_consumed = 0;
if (_instance->App_null_generate_m1bis_from_m1bisabcd_and_m1bise_State == APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_STATE) {
//Region INIT
uint8_t App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_State_event_consumed = 0;
if (_instance->App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_State == APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_S1_STATE) {
if (App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_State_event_consumed == 0 && 1) {
App_OnExit(APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_S1_STATE, _instance);
_instance->App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_State = APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_S2_STATE;
_instance->App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_a_var = a;
_instance->App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_b_var = b;
_instance->App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_c_var = c;
_instance->App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_d_var = d;
App_OnEntry(APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_S2_STATE, _instance);
App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_State_event_consumed = 1;
}
}
else if (_instance->App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_State == APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_S3_STATE) {
if (App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_State_event_consumed == 0 && 1) {
App_OnExit(APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_S3_STATE, _instance);
_instance->App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_State = APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_S4_STATE;
_instance->App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_a_var = a;
_instance->App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_b_var = b;
_instance->App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_c_var = c;
_instance->App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_d_var = d;
App_OnEntry(APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_S4_STATE, _instance);
App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_State_event_consumed = 1;
}
}
//End Region INIT
App_null_generate_m1bis_from_m1bisabcd_and_m1bise_State_event_consumed = 0 | App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_State_event_consumed ;
//End dsregion INIT
}
//End Region generate_m1bis_from_m1bisabcd_and_m1bise
//End dsregion null
//Session list: 
}
void App_handle_app_m2abc(struct App_Instance *_instance, uint8_t a, uint8_t r10, uint8_t b, uint8_t r9, uint8_t c) {
if(!(_instance->active)) return;
//Region null
uint8_t App_State_event_consumed = 0;
//End Region null
//Region generate_m2_from_m2r0r1_and_m2abc
uint8_t App_null_generate_m2_from_m2r0r1_and_m2abc_State_event_consumed = 0;
if (_instance->App_null_generate_m2_from_m2r0r1_and_m2abc_State == APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_STATE) {
//Region INIT
uint8_t App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_State_event_consumed = 0;
if (_instance->App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_State == APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_S1_STATE) {
if (App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_State_event_consumed == 0 && 1) {
App_OnExit(APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_S1_STATE, _instance);
_instance->App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_State = APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_S3_STATE;
_instance->App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_a_var = a;
_instance->App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_b_var = b;
_instance->App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_c_var = c;
App_OnEntry(APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_S3_STATE, _instance);
App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_State_event_consumed = 1;
}
}
else if (_instance->App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_State == APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_S2_STATE) {
if (App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_State_event_consumed == 0 && 1) {
App_OnExit(APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_S2_STATE, _instance);
_instance->App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_State = APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_S4_STATE;
_instance->App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_a_var = a;
_instance->App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_b_var = b;
_instance->App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_c_var = c;
App_OnEntry(APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_S4_STATE, _instance);
App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_State_event_consumed = 1;
}
}
//End Region INIT
App_null_generate_m2_from_m2r0r1_and_m2abc_State_event_consumed = 0 | App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_State_event_consumed ;
//End dsregion INIT
}
//End Region generate_m2_from_m2r0r1_and_m2abc
//Region generate_m1_from_m1abcd_and_m1e
uint8_t App_null_generate_m1_from_m1abcd_and_m1e_State_event_consumed = 0;
//End Region generate_m1_from_m1abcd_and_m1e
//Region generate_m2bis_from_m2bisr0r1ab_and_m2bisc
uint8_t App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_State_event_consumed = 0;
//End Region generate_m2bis_from_m2bisr0r1ab_and_m2bisc
//Region generate_m1bis_from_m1bisabcd_and_m1bise
uint8_t App_null_generate_m1bis_from_m1bisabcd_and_m1bise_State_event_consumed = 0;
//End Region generate_m1bis_from_m1bisabcd_and_m1bise
//End dsregion null
//Session list: 
}
void App_handle_app_m1abcd(struct App_Instance *_instance, uint8_t a, uint8_t b, uint8_t c, uint8_t r16, uint8_t d) {
if(!(_instance->active)) return;
//Region null
uint8_t App_State_event_consumed = 0;
//End Region null
//Region generate_m2_from_m2r0r1_and_m2abc
uint8_t App_null_generate_m2_from_m2r0r1_and_m2abc_State_event_consumed = 0;
//End Region generate_m2_from_m2r0r1_and_m2abc
//Region generate_m1_from_m1abcd_and_m1e
uint8_t App_null_generate_m1_from_m1abcd_and_m1e_State_event_consumed = 0;
if (_instance->App_null_generate_m1_from_m1abcd_and_m1e_State == APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_STATE) {
//Region INIT
uint8_t App_null_generate_m1_from_m1abcd_and_m1e_INIT_State_event_consumed = 0;
if (_instance->App_null_generate_m1_from_m1abcd_and_m1e_INIT_State == APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_S1_STATE) {
if (App_null_generate_m1_from_m1abcd_and_m1e_INIT_State_event_consumed == 0 && 1) {
App_OnExit(APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_S1_STATE, _instance);
_instance->App_null_generate_m1_from_m1abcd_and_m1e_INIT_State = APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_S2_STATE;
_instance->App_null_generate_m1_from_m1abcd_and_m1e_INIT_a_var = a;
_instance->App_null_generate_m1_from_m1abcd_and_m1e_INIT_b_var = b;
_instance->App_null_generate_m1_from_m1abcd_and_m1e_INIT_c_var = c;
_instance->App_null_generate_m1_from_m1abcd_and_m1e_INIT_d_var = d;
App_OnEntry(APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_S2_STATE, _instance);
App_null_generate_m1_from_m1abcd_and_m1e_INIT_State_event_consumed = 1;
}
}
else if (_instance->App_null_generate_m1_from_m1abcd_and_m1e_INIT_State == APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_S3_STATE) {
if (App_null_generate_m1_from_m1abcd_and_m1e_INIT_State_event_consumed == 0 && 1) {
App_OnExit(APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_S3_STATE, _instance);
_instance->App_null_generate_m1_from_m1abcd_and_m1e_INIT_State = APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_S4_STATE;
_instance->App_null_generate_m1_from_m1abcd_and_m1e_INIT_a_var = a;
_instance->App_null_generate_m1_from_m1abcd_and_m1e_INIT_b_var = b;
_instance->App_null_generate_m1_from_m1abcd_and_m1e_INIT_c_var = c;
_instance->App_null_generate_m1_from_m1abcd_and_m1e_INIT_d_var = d;
App_OnEntry(APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_S4_STATE, _instance);
App_null_generate_m1_from_m1abcd_and_m1e_INIT_State_event_consumed = 1;
}
}
//End Region INIT
App_null_generate_m1_from_m1abcd_and_m1e_State_event_consumed = 0 | App_null_generate_m1_from_m1abcd_and_m1e_INIT_State_event_consumed ;
//End dsregion INIT
}
//End Region generate_m1_from_m1abcd_and_m1e
//Region generate_m2bis_from_m2bisr0r1ab_and_m2bisc
uint8_t App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_State_event_consumed = 0;
//End Region generate_m2bis_from_m2bisr0r1ab_and_m2bisc
//Region generate_m1bis_from_m1bisabcd_and_m1bise
uint8_t App_null_generate_m1bis_from_m1bisabcd_and_m1bise_State_event_consumed = 0;
//End Region generate_m1bis_from_m1bisabcd_and_m1bise
//End dsregion null
//Session list: 
}
void App_handle_app_m2bisr0r1ab(struct App_Instance *_instance, uint8_t r21, uint8_t r0, uint8_t r1, uint8_t a, uint8_t b) {
if(!(_instance->active)) return;
//Region null
uint8_t App_State_event_consumed = 0;
//End Region null
//Region generate_m2_from_m2r0r1_and_m2abc
uint8_t App_null_generate_m2_from_m2r0r1_and_m2abc_State_event_consumed = 0;
//End Region generate_m2_from_m2r0r1_and_m2abc
//Region generate_m1_from_m1abcd_and_m1e
uint8_t App_null_generate_m1_from_m1abcd_and_m1e_State_event_consumed = 0;
//End Region generate_m1_from_m1abcd_and_m1e
//Region generate_m2bis_from_m2bisr0r1ab_and_m2bisc
uint8_t App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_State_event_consumed = 0;
if (_instance->App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_State == APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_STATE) {
//Region INIT
uint8_t App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_State_event_consumed = 0;
if (_instance->App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_State == APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_S1_STATE) {
if (App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_State_event_consumed == 0 && 1) {
App_OnExit(APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_S1_STATE, _instance);
_instance->App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_State = APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_S2_STATE;
_instance->App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_r0_var = r0;
_instance->App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_r1_var = r1;
_instance->App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_a_var = a;
_instance->App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_b_var = b;
App_OnEntry(APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_S2_STATE, _instance);
App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_State_event_consumed = 1;
}
}
else if (_instance->App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_State == APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_S3_STATE) {
if (App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_State_event_consumed == 0 && 1) {
App_OnExit(APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_S3_STATE, _instance);
_instance->App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_State = APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_S4_STATE;
_instance->App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_r0_var = r0;
_instance->App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_r1_var = r1;
_instance->App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_a_var = a;
_instance->App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_b_var = b;
App_OnEntry(APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_S4_STATE, _instance);
App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_State_event_consumed = 1;
}
}
//End Region INIT
App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_State_event_consumed = 0 | App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_State_event_consumed ;
//End dsregion INIT
}
//End Region generate_m2bis_from_m2bisr0r1ab_and_m2bisc
//Region generate_m1bis_from_m1bisabcd_and_m1bise
uint8_t App_null_generate_m1bis_from_m1bisabcd_and_m1bise_State_event_consumed = 0;
//End Region generate_m1bis_from_m1bisabcd_and_m1bise
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
//Region generate_m2_from_m2r0r1_and_m2abc
if (_instance->App_null_generate_m2_from_m2r0r1_and_m2abc_State == APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_STATE) {
//Region INIT
if (_instance->App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_State == APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_S4_STATE) {
if (1) {
App_OnExit(APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_S4_STATE, _instance);
_instance->App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_State = APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_S1_STATE;
App_OnEntry(APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_S1_STATE, _instance);
return 1;
}
}
}
//Region generate_m1_from_m1abcd_and_m1e
if (_instance->App_null_generate_m1_from_m1abcd_and_m1e_State == APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_STATE) {
//Region INIT
if (_instance->App_null_generate_m1_from_m1abcd_and_m1e_INIT_State == APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_S4_STATE) {
if (1) {
App_OnExit(APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_S4_STATE, _instance);
_instance->App_null_generate_m1_from_m1abcd_and_m1e_INIT_State = APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_S1_STATE;
App_OnEntry(APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_S1_STATE, _instance);
return 1;
}
}
}
//Region generate_m2bis_from_m2bisr0r1ab_and_m2bisc
if (_instance->App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_State == APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_STATE) {
//Region INIT
if (_instance->App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_State == APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_S4_STATE) {
if (1) {
App_OnExit(APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_S4_STATE, _instance);
_instance->App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_State = APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_S1_STATE;
App_OnEntry(APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_S1_STATE, _instance);
return 1;
}
}
}
//Region generate_m1bis_from_m1bisabcd_and_m1bise
if (_instance->App_null_generate_m1bis_from_m1bisabcd_and_m1bise_State == APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_STATE) {
//Region INIT
if (_instance->App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_State == APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_S4_STATE) {
if (1) {
App_OnExit(APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_S4_STATE, _instance);
_instance->App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_State = APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_S1_STATE;
App_OnEntry(APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_S1_STATE, _instance);
return 1;
}
}
}
//begin dispatchEmptyToSession
//end dispatchEmptyToSession
return empty_event_consumed;
}

// Observers for outgoing messages:
void (*external_App_send_app_m3__listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void (*App_send_app_m3__listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void register_external_App_send_app_m3__listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
external_App_send_app_m3__listener = _listener;
}
void register_App_send_app_m3__listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
App_send_app_m3__listener = _listener;
}
void App_send_app_m3_(struct App_Instance *_instance, uint8_t r13, uint8_t r15, uint8_t r12, uint8_t r14, uint8_t r11){
if (App_send_app_m3__listener != 0x0) App_send_app_m3__listener(_instance, r13, r15, r12, r14, r11);
if (external_App_send_app_m3__listener != 0x0) external_App_send_app_m3__listener(_instance, r13, r15, r12, r14, r11);
;
}
void (*external_App_send_app_m3r4r5r2r3a_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void (*App_send_app_m3r4r5r2r3a_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void register_external_App_send_app_m3r4r5r2r3a_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
external_App_send_app_m3r4r5r2r3a_listener = _listener;
}
void register_App_send_app_m3r4r5r2r3a_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
App_send_app_m3r4r5r2r3a_listener = _listener;
}
void App_send_app_m3r4r5r2r3a(struct App_Instance *_instance, uint8_t r4, uint8_t r5, uint8_t r2, uint8_t r3, uint8_t a){
if (App_send_app_m3r4r5r2r3a_listener != 0x0) App_send_app_m3r4r5r2r3a_listener(_instance, r4, r5, r2, r3, a);
if (external_App_send_app_m3r4r5r2r3a_listener != 0x0) external_App_send_app_m3r4r5r2r3a_listener(_instance, r4, r5, r2, r3, a);
;
}
void (*external_App_send_app_m3bis__listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void (*App_send_app_m3bis__listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void register_external_App_send_app_m3bis__listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
external_App_send_app_m3bis__listener = _listener;
}
void register_App_send_app_m3bis__listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
App_send_app_m3bis__listener = _listener;
}
void App_send_app_m3bis_(struct App_Instance *_instance, uint8_t r29, uint8_t r28, uint8_t r30, uint8_t r27, uint8_t r26){
if (App_send_app_m3bis__listener != 0x0) App_send_app_m3bis__listener(_instance, r29, r28, r30, r27, r26);
if (external_App_send_app_m3bis__listener != 0x0) external_App_send_app_m3bis__listener(_instance, r29, r28, r30, r27, r26);
;
}
void (*external_App_send_app_m3bisr4r5r2r3a_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void (*App_send_app_m3bisr4r5r2r3a_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void register_external_App_send_app_m3bisr4r5r2r3a_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
external_App_send_app_m3bisr4r5r2r3a_listener = _listener;
}
void register_App_send_app_m3bisr4r5r2r3a_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
App_send_app_m3bisr4r5r2r3a_listener = _listener;
}
void App_send_app_m3bisr4r5r2r3a(struct App_Instance *_instance, uint8_t r4, uint8_t r5, uint8_t r2, uint8_t r3, uint8_t a){
if (App_send_app_m3bisr4r5r2r3a_listener != 0x0) App_send_app_m3bisr4r5r2r3a_listener(_instance, r4, r5, r2, r3, a);
if (external_App_send_app_m3bisr4r5r2r3a_listener != 0x0) external_App_send_app_m3bisr4r5r2r3a_listener(_instance, r4, r5, r2, r3, a);
;
}
void (*external_App_send_diversified_m2_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void (*App_send_diversified_m2_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void register_external_App_send_diversified_m2_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
external_App_send_diversified_m2_listener = _listener;
}
void register_App_send_diversified_m2_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
App_send_diversified_m2_listener = _listener;
}
void App_send_diversified_m2(struct App_Instance *_instance, uint8_t r0, uint8_t r1, uint8_t a, uint8_t b, uint8_t c){
if (App_send_diversified_m2_listener != 0x0) App_send_diversified_m2_listener(_instance, r0, r1, a, b, c);
if (external_App_send_diversified_m2_listener != 0x0) external_App_send_diversified_m2_listener(_instance, r0, r1, a, b, c);
;
}
void (*external_App_send_diversified_m1abcd_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void (*App_send_diversified_m1abcd_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void register_external_App_send_diversified_m1abcd_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
external_App_send_diversified_m1abcd_listener = _listener;
}
void register_App_send_diversified_m1abcd_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
App_send_diversified_m1abcd_listener = _listener;
}
void App_send_diversified_m1abcd(struct App_Instance *_instance, uint8_t a, uint8_t b, uint8_t c, uint8_t r16, uint8_t d){
if (App_send_diversified_m1abcd_listener != 0x0) App_send_diversified_m1abcd_listener(_instance, a, b, c, r16, d);
if (external_App_send_diversified_m1abcd_listener != 0x0) external_App_send_diversified_m1abcd_listener(_instance, a, b, c, r16, d);
;
}
void (*external_App_send_diversified_m1e_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void (*App_send_diversified_m1e_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void register_external_App_send_diversified_m1e_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
external_App_send_diversified_m1e_listener = _listener;
}
void register_App_send_diversified_m1e_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
App_send_diversified_m1e_listener = _listener;
}
void App_send_diversified_m1e(struct App_Instance *_instance, uint8_t r18, uint8_t r19, uint8_t r20, uint8_t r17, uint8_t e){
if (App_send_diversified_m1e_listener != 0x0) App_send_diversified_m1e_listener(_instance, r18, r19, r20, r17, e);
if (external_App_send_diversified_m1e_listener != 0x0) external_App_send_diversified_m1e_listener(_instance, r18, r19, r20, r17, e);
;
}
void (*external_App_send_diversified_m1_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void (*App_send_diversified_m1_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void register_external_App_send_diversified_m1_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
external_App_send_diversified_m1_listener = _listener;
}
void register_App_send_diversified_m1_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
App_send_diversified_m1_listener = _listener;
}
void App_send_diversified_m1(struct App_Instance *_instance, uint8_t a, uint8_t b, uint8_t c, uint8_t d, uint8_t e){
if (App_send_diversified_m1_listener != 0x0) App_send_diversified_m1_listener(_instance, a, b, c, d, e);
if (external_App_send_diversified_m1_listener != 0x0) external_App_send_diversified_m1_listener(_instance, a, b, c, d, e);
;
}
void (*external_App_send_diversified_m2bisr0r1ab_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void (*App_send_diversified_m2bisr0r1ab_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void register_external_App_send_diversified_m2bisr0r1ab_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
external_App_send_diversified_m2bisr0r1ab_listener = _listener;
}
void register_App_send_diversified_m2bisr0r1ab_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
App_send_diversified_m2bisr0r1ab_listener = _listener;
}
void App_send_diversified_m2bisr0r1ab(struct App_Instance *_instance, uint8_t r21, uint8_t r0, uint8_t r1, uint8_t a, uint8_t b){
if (App_send_diversified_m2bisr0r1ab_listener != 0x0) App_send_diversified_m2bisr0r1ab_listener(_instance, r21, r0, r1, a, b);
if (external_App_send_diversified_m2bisr0r1ab_listener != 0x0) external_App_send_diversified_m2bisr0r1ab_listener(_instance, r21, r0, r1, a, b);
;
}
void (*external_App_send_diversified_m2bisc_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void (*App_send_diversified_m2bisc_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void register_external_App_send_diversified_m2bisc_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
external_App_send_diversified_m2bisc_listener = _listener;
}
void register_App_send_diversified_m2bisc_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
App_send_diversified_m2bisc_listener = _listener;
}
void App_send_diversified_m2bisc(struct App_Instance *_instance, uint8_t r24, uint8_t r22, uint8_t r23, uint8_t r25, uint8_t c){
if (App_send_diversified_m2bisc_listener != 0x0) App_send_diversified_m2bisc_listener(_instance, r24, r22, r23, r25, c);
if (external_App_send_diversified_m2bisc_listener != 0x0) external_App_send_diversified_m2bisc_listener(_instance, r24, r22, r23, r25, c);
;
}
void (*external_App_send_diversified_m2bis_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void (*App_send_diversified_m2bis_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void register_external_App_send_diversified_m2bis_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
external_App_send_diversified_m2bis_listener = _listener;
}
void register_App_send_diversified_m2bis_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
App_send_diversified_m2bis_listener = _listener;
}
void App_send_diversified_m2bis(struct App_Instance *_instance, uint8_t r0, uint8_t r1, uint8_t a, uint8_t b, uint8_t c){
if (App_send_diversified_m2bis_listener != 0x0) App_send_diversified_m2bis_listener(_instance, r0, r1, a, b, c);
if (external_App_send_diversified_m2bis_listener != 0x0) external_App_send_diversified_m2bis_listener(_instance, r0, r1, a, b, c);
;
}
void (*external_App_send_diversified_m1bisabcd_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void (*App_send_diversified_m1bisabcd_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void register_external_App_send_diversified_m1bisabcd_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
external_App_send_diversified_m1bisabcd_listener = _listener;
}
void register_App_send_diversified_m1bisabcd_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
App_send_diversified_m1bisabcd_listener = _listener;
}
void App_send_diversified_m1bisabcd(struct App_Instance *_instance, uint8_t a, uint8_t r31, uint8_t b, uint8_t c, uint8_t d){
if (App_send_diversified_m1bisabcd_listener != 0x0) App_send_diversified_m1bisabcd_listener(_instance, a, r31, b, c, d);
if (external_App_send_diversified_m1bisabcd_listener != 0x0) external_App_send_diversified_m1bisabcd_listener(_instance, a, r31, b, c, d);
;
}
void (*external_App_send_diversified_m1bise_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void (*App_send_diversified_m1bise_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void register_external_App_send_diversified_m1bise_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
external_App_send_diversified_m1bise_listener = _listener;
}
void register_App_send_diversified_m1bise_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
App_send_diversified_m1bise_listener = _listener;
}
void App_send_diversified_m1bise(struct App_Instance *_instance, uint8_t r32, uint8_t r35, uint8_t r33, uint8_t r34, uint8_t e){
if (App_send_diversified_m1bise_listener != 0x0) App_send_diversified_m1bise_listener(_instance, r32, r35, r33, r34, e);
if (external_App_send_diversified_m1bise_listener != 0x0) external_App_send_diversified_m1bise_listener(_instance, r32, r35, r33, r34, e);
;
}
void (*external_App_send_diversified_m1bis_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void (*App_send_diversified_m1bis_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void register_external_App_send_diversified_m1bis_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
external_App_send_diversified_m1bis_listener = _listener;
}
void register_App_send_diversified_m1bis_listener(void (*_listener)(struct App_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
App_send_diversified_m1bis_listener = _listener;
}
void App_send_diversified_m1bis(struct App_Instance *_instance, uint8_t a, uint8_t b, uint8_t c, uint8_t d, uint8_t e){
if (App_send_diversified_m1bis_listener != 0x0) App_send_diversified_m1bis_listener(_instance, a, b, c, d, e);
if (external_App_send_diversified_m1bis_listener != 0x0) external_App_send_diversified_m1bis_listener(_instance, a, b, c, d, e);
;
}



/*****************************************************************************
 * Implementation for type : Client
 *****************************************************************************/

// Declaration of prototypes:
//Prototypes: State Machine
void Client_OnExit(int state, struct Client_Instance *_instance);
//Prototypes: Message Sending
void Client_send_app_m2r0r1(struct Client_Instance *_instance, uint8_t r6, uint8_t r0, uint8_t r7, uint8_t r8, uint8_t r1);
void Client_send_app_m2abc(struct Client_Instance *_instance, uint8_t a, uint8_t r10, uint8_t b, uint8_t r9, uint8_t c);
void Client_send_app_m1abcd(struct Client_Instance *_instance, uint8_t a, uint8_t b, uint8_t c, uint8_t r16, uint8_t d);
void Client_send_app_m1e(struct Client_Instance *_instance, uint8_t r18, uint8_t r19, uint8_t r20, uint8_t r17, uint8_t e);
void Client_send_app_m2bisr0r1ab(struct Client_Instance *_instance, uint8_t r21, uint8_t r0, uint8_t r1, uint8_t a, uint8_t b);
void Client_send_app_m2bisc(struct Client_Instance *_instance, uint8_t r24, uint8_t r22, uint8_t r23, uint8_t r25, uint8_t c);
void Client_send_app_m1bisabcd(struct Client_Instance *_instance, uint8_t a, uint8_t r31, uint8_t b, uint8_t c, uint8_t d);
void Client_send_app_m1bise(struct Client_Instance *_instance, uint8_t r32, uint8_t r35, uint8_t r33, uint8_t r34, uint8_t e);
void Client_send_diversified_m3(struct Client_Instance *_instance, uint8_t r4, uint8_t r5, uint8_t r2, uint8_t r3, uint8_t a);
void Client_send_diversified_m3bis_(struct Client_Instance *_instance, uint8_t r29, uint8_t r28, uint8_t r30, uint8_t r27, uint8_t r26);
void Client_send_diversified_m3bisr4r5r2r3a(struct Client_Instance *_instance, uint8_t r4, uint8_t r5, uint8_t r2, uint8_t r3, uint8_t a);
void Client_send_diversified_m3bis(struct Client_Instance *_instance, uint8_t r4, uint8_t r5, uint8_t r2, uint8_t r3, uint8_t a);
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
case CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_STATE:{
_instance->Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_State = CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_S1_STATE;
Client_OnEntry(_instance->Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_State, _instance);
break;
}
case CLIENT_STATE:{
_instance->Client_State = CLIENT_NULL_RUN_STATE;
_instance->Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_State = CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_STATE;
_instance->Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_State = CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_STATE;
_instance->Client__a_var = f_Client_rnd(_instance);
_instance->Client__b_var = f_Client_rnd(_instance);
_instance->Client_start_var = millis();
Client_OnEntry(_instance->Client_State, _instance);
Client_OnEntry(_instance->Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_State, _instance);
Client_OnEntry(_instance->Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_State, _instance);
break;
}
case CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_STATE:{
_instance->Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_State = CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_S1_STATE;
Client_OnEntry(_instance->Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_State, _instance);
break;
}
case CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_S3_STATE:{
break;
}
case CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_S1_STATE:{
break;
}
case CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_S4_STATE:{
Client_send_diversified_m3(_instance, _instance->Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r4_var, _instance->Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r5_var, _instance->Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r2_var, _instance->Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r3_var, _instance->Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_a_var);
break;
}
case CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_S1_STATE:{
break;
}
case CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_S4_STATE:{
Client_send_diversified_m3bis(_instance, _instance->Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r4_var, _instance->Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r5_var, _instance->Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r2_var, _instance->Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r3_var, _instance->Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_a_var);
break;
}
case CLIENT_NULL_ERROR_STATE:{
Serial.print("#CLI: Heh, heh, heh... what a mess!");
Serial.println();
_instance->active = false;
break;
}
case CLIENT_NULL_STOP_STATE:{
Serial.print("#CLI: What are you waitin for? Christmas?");
Serial.println();
;uint16_t duration = millis() - _instance->Client_start_var;
Serial.print("#CLI: took ");
Serial.print(duration);
Serial.print("ms.");
Serial.println();
_instance->active = false;
break;
}
case CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_S3_STATE:{
break;
}
case CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_S2_STATE:{
break;
}
case CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_S2_STATE:{
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
if(f_Client_rnd(_instance) < 211) {
if(f_Client_rnd(_instance) < 62) {
Serial.print("10");
Serial.print(", ");
Serial.print(_instance->Client__a_var);
Serial.print(", ");
Serial.print(_instance->Client__b_var);
Serial.print(", ");
Serial.print(_instance->Client__c_var);
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(_instance->Client__d_var);
Serial.print(", ");
Serial.println();
Client_send_app_m1abcd(_instance, _instance->Client__a_var, _instance->Client__b_var, _instance->Client__c_var, f_Client_rnd(_instance), _instance->Client__d_var);
Serial.print("11");
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(_instance->Client__e_var);
Serial.print(", ");
Serial.println();
Client_send_app_m1e(_instance, f_Client_rnd(_instance), f_Client_rnd(_instance), f_Client_rnd(_instance), f_Client_rnd(_instance), _instance->Client__e_var);

} else {
Serial.print("11");
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(_instance->Client__e_var);
Serial.print(", ");
Serial.println();
Client_send_app_m1e(_instance, f_Client_rnd(_instance), f_Client_rnd(_instance), f_Client_rnd(_instance), f_Client_rnd(_instance), _instance->Client__e_var);
Serial.print("10");
Serial.print(", ");
Serial.print(_instance->Client__a_var);
Serial.print(", ");
Serial.print(_instance->Client__b_var);
Serial.print(", ");
Serial.print(_instance->Client__c_var);
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(_instance->Client__d_var);
Serial.print(", ");
Serial.println();
Client_send_app_m1abcd(_instance, _instance->Client__a_var, _instance->Client__b_var, _instance->Client__c_var, f_Client_rnd(_instance), _instance->Client__d_var);

}

} else {
if(f_Client_rnd(_instance) < 184) {
Serial.print("16");
Serial.print(", ");
Serial.print(_instance->Client__a_var);
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(_instance->Client__b_var);
Serial.print(", ");
Serial.print(_instance->Client__c_var);
Serial.print(", ");
Serial.print(_instance->Client__d_var);
Serial.print(", ");
Serial.println();
Client_send_app_m1bisabcd(_instance, _instance->Client__a_var, f_Client_rnd(_instance), _instance->Client__b_var, _instance->Client__c_var, _instance->Client__d_var);
Serial.print("17");
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(_instance->Client__e_var);
Serial.print(", ");
Serial.println();
Client_send_app_m1bise(_instance, f_Client_rnd(_instance), f_Client_rnd(_instance), f_Client_rnd(_instance), f_Client_rnd(_instance), _instance->Client__e_var);

} else {
Serial.print("17");
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(_instance->Client__e_var);
Serial.print(", ");
Serial.println();
Client_send_app_m1bise(_instance, f_Client_rnd(_instance), f_Client_rnd(_instance), f_Client_rnd(_instance), f_Client_rnd(_instance), _instance->Client__e_var);
Serial.print("16");
Serial.print(", ");
Serial.print(_instance->Client__a_var);
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(_instance->Client__b_var);
Serial.print(", ");
Serial.print(_instance->Client__c_var);
Serial.print(", ");
Serial.print(_instance->Client__d_var);
Serial.print(", ");
Serial.println();
Client_send_app_m1bisabcd(_instance, _instance->Client__a_var, f_Client_rnd(_instance), _instance->Client__b_var, _instance->Client__c_var, _instance->Client__d_var);

}

}
Serial.print("#CLI: Come get some app!m2(");
Serial.print(_instance->Client__a_var);
Serial.print(", ");
Serial.print(_instance->Client__b_var);
Serial.print(", ");
Serial.print(_instance->Client__c_var);
Serial.print(")!");
Serial.println();
if(f_Client_rnd(_instance) < 39) {
if(f_Client_rnd(_instance) < 230) {
Serial.print("6");
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.println();
Client_send_app_m2r0r1(_instance, f_Client_rnd(_instance), f_Client_rnd(_instance), f_Client_rnd(_instance), f_Client_rnd(_instance), f_Client_rnd(_instance));
Serial.print("7");
Serial.print(", ");
Serial.print(_instance->Client__a_var);
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(_instance->Client__b_var);
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(_instance->Client__c_var);
Serial.print(", ");
Serial.println();
Client_send_app_m2abc(_instance, _instance->Client__a_var, f_Client_rnd(_instance), _instance->Client__b_var, f_Client_rnd(_instance), _instance->Client__c_var);

} else {
Serial.print("7");
Serial.print(", ");
Serial.print(_instance->Client__a_var);
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(_instance->Client__b_var);
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(_instance->Client__c_var);
Serial.print(", ");
Serial.println();
Client_send_app_m2abc(_instance, _instance->Client__a_var, f_Client_rnd(_instance), _instance->Client__b_var, f_Client_rnd(_instance), _instance->Client__c_var);
Serial.print("6");
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.println();
Client_send_app_m2r0r1(_instance, f_Client_rnd(_instance), f_Client_rnd(_instance), f_Client_rnd(_instance), f_Client_rnd(_instance), f_Client_rnd(_instance));

}

} else {
if(f_Client_rnd(_instance) < 21) {
Serial.print("12");
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(_instance->Client__a_var);
Serial.print(", ");
Serial.print(_instance->Client__b_var);
Serial.print(", ");
Serial.println();
Client_send_app_m2bisr0r1ab(_instance, f_Client_rnd(_instance), f_Client_rnd(_instance), f_Client_rnd(_instance), _instance->Client__a_var, _instance->Client__b_var);
Serial.print("13");
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(_instance->Client__c_var);
Serial.print(", ");
Serial.println();
Client_send_app_m2bisc(_instance, f_Client_rnd(_instance), f_Client_rnd(_instance), f_Client_rnd(_instance), f_Client_rnd(_instance), _instance->Client__c_var);

} else {
Serial.print("13");
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(_instance->Client__c_var);
Serial.print(", ");
Serial.println();
Client_send_app_m2bisc(_instance, f_Client_rnd(_instance), f_Client_rnd(_instance), f_Client_rnd(_instance), f_Client_rnd(_instance), _instance->Client__c_var);
Serial.print("12");
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(f_Client_rnd(_instance));
Serial.print(", ");
Serial.print(_instance->Client__a_var);
Serial.print(", ");
Serial.print(_instance->Client__b_var);
Serial.print(", ");
Serial.println();
Client_send_app_m2bisr0r1ab(_instance, f_Client_rnd(_instance), f_Client_rnd(_instance), f_Client_rnd(_instance), _instance->Client__a_var, _instance->Client__b_var);

}

}
_instance->Client_counter_var++;
break;
}
default: break;
}
}

// On Exit Actions:
void Client_OnExit(int state, struct Client_Instance *_instance) {
switch(state) {
case CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_STATE:{
Client_OnExit(_instance->Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_State, _instance);
break;}
case CLIENT_STATE:{
Client_OnExit(_instance->Client_State, _instance);
Client_OnExit(_instance->Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_State, _instance);
Client_OnExit(_instance->Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_State, _instance);
break;}
case CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_STATE:{
Client_OnExit(_instance->Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_State, _instance);
break;}
case CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_S3_STATE:{
break;}
case CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_S1_STATE:{
break;}
case CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_S4_STATE:{
break;}
case CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_S1_STATE:{
break;}
case CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_S4_STATE:{
break;}
case CLIENT_NULL_ERROR_STATE:{
break;}
case CLIENT_NULL_STOP_STATE:{
break;}
case CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_S3_STATE:{
break;}
case CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_S2_STATE:{
break;}
case CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_S2_STATE:{
break;}
case CLIENT_NULL_RUN_STATE:{
break;}
default: break;
}
}

// Event Handlers for incoming messages:
void Client_handle_app_m3r4r5r2r3a(struct Client_Instance *_instance, uint8_t r4, uint8_t r5, uint8_t r2, uint8_t r3, uint8_t a) {
if(!(_instance->active)) return;
//Region null
uint8_t Client_State_event_consumed = 0;
//End Region null
//Region generate_m3_from_m3__and_m3r4r5r2r3a
uint8_t Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_State_event_consumed = 0;
if (_instance->Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_State == CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_STATE) {
//Region INIT
uint8_t Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_State_event_consumed = 0;
if (_instance->Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_State == CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_S1_STATE) {
if (Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_State_event_consumed == 0 && 1) {
Client_OnExit(CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_S1_STATE, _instance);
_instance->Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_State = CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_S3_STATE;
_instance->Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r4_var = r4;
_instance->Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r5_var = r5;
_instance->Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r2_var = r2;
_instance->Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r3_var = r3;
_instance->Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_a_var = a;
Client_OnEntry(CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_S3_STATE, _instance);
Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_State_event_consumed = 1;
}
}
else if (_instance->Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_State == CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_S2_STATE) {
if (Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_State_event_consumed == 0 && 1) {
Client_OnExit(CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_S2_STATE, _instance);
_instance->Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_State = CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_S4_STATE;
_instance->Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r4_var = r4;
_instance->Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r5_var = r5;
_instance->Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r2_var = r2;
_instance->Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r3_var = r3;
_instance->Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_a_var = a;
Client_OnEntry(CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_S4_STATE, _instance);
Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_State_event_consumed = 1;
}
}
//End Region INIT
Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_State_event_consumed = 0 | Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_State_event_consumed ;
//End dsregion INIT
}
//End Region generate_m3_from_m3__and_m3r4r5r2r3a
//Region generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a
uint8_t Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_State_event_consumed = 0;
//End Region generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a
//End dsregion null
//Session list: 
}
void Client_handle_app_m3bis_(struct Client_Instance *_instance, uint8_t r29, uint8_t r28, uint8_t r30, uint8_t r27, uint8_t r26) {
if(!(_instance->active)) return;
//Region null
uint8_t Client_State_event_consumed = 0;
//End Region null
//Region generate_m3_from_m3__and_m3r4r5r2r3a
uint8_t Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_State_event_consumed = 0;
//End Region generate_m3_from_m3__and_m3r4r5r2r3a
//Region generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a
uint8_t Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_State_event_consumed = 0;
if (_instance->Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_State == CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_STATE) {
//Region INIT
uint8_t Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_State_event_consumed = 0;
if (_instance->Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_State == CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_S1_STATE) {
if (Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_State_event_consumed == 0 && 1) {
Client_OnExit(CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_S1_STATE, _instance);
_instance->Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_State = CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_S2_STATE;
Client_OnEntry(CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_S2_STATE, _instance);
Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_State_event_consumed = 1;
}
}
else if (_instance->Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_State == CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_S3_STATE) {
if (Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_State_event_consumed == 0 && 1) {
Client_OnExit(CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_S3_STATE, _instance);
_instance->Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_State = CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_S4_STATE;
Client_OnEntry(CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_S4_STATE, _instance);
Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_State_event_consumed = 1;
}
}
//End Region INIT
Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_State_event_consumed = 0 | Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_State_event_consumed ;
//End dsregion INIT
}
//End Region generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a
//End dsregion null
//Session list: 
}
void Client_handle_app_m3_(struct Client_Instance *_instance, uint8_t r13, uint8_t r15, uint8_t r12, uint8_t r14, uint8_t r11) {
if(!(_instance->active)) return;
//Region null
uint8_t Client_State_event_consumed = 0;
//End Region null
//Region generate_m3_from_m3__and_m3r4r5r2r3a
uint8_t Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_State_event_consumed = 0;
if (_instance->Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_State == CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_STATE) {
//Region INIT
uint8_t Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_State_event_consumed = 0;
if (_instance->Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_State == CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_S1_STATE) {
if (Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_State_event_consumed == 0 && 1) {
Client_OnExit(CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_S1_STATE, _instance);
_instance->Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_State = CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_S2_STATE;
Client_OnEntry(CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_S2_STATE, _instance);
Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_State_event_consumed = 1;
}
}
else if (_instance->Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_State == CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_S3_STATE) {
if (Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_State_event_consumed == 0 && 1) {
Client_OnExit(CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_S3_STATE, _instance);
_instance->Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_State = CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_S4_STATE;
Client_OnEntry(CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_S4_STATE, _instance);
Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_State_event_consumed = 1;
}
}
//End Region INIT
Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_State_event_consumed = 0 | Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_State_event_consumed ;
//End dsregion INIT
}
//End Region generate_m3_from_m3__and_m3r4r5r2r3a
//Region generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a
uint8_t Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_State_event_consumed = 0;
//End Region generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a
//End dsregion null
//Session list: 
}
void Client_handle_app_m3bisr4r5r2r3a(struct Client_Instance *_instance, uint8_t r4, uint8_t r5, uint8_t r2, uint8_t r3, uint8_t a) {
if(!(_instance->active)) return;
//Region null
uint8_t Client_State_event_consumed = 0;
//End Region null
//Region generate_m3_from_m3__and_m3r4r5r2r3a
uint8_t Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_State_event_consumed = 0;
//End Region generate_m3_from_m3__and_m3r4r5r2r3a
//Region generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a
uint8_t Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_State_event_consumed = 0;
if (_instance->Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_State == CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_STATE) {
//Region INIT
uint8_t Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_State_event_consumed = 0;
if (_instance->Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_State == CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_S1_STATE) {
if (Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_State_event_consumed == 0 && 1) {
Client_OnExit(CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_S1_STATE, _instance);
_instance->Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_State = CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_S3_STATE;
_instance->Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r4_var = r4;
_instance->Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r5_var = r5;
_instance->Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r2_var = r2;
_instance->Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r3_var = r3;
_instance->Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_a_var = a;
Client_OnEntry(CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_S3_STATE, _instance);
Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_State_event_consumed = 1;
}
}
else if (_instance->Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_State == CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_S2_STATE) {
if (Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_State_event_consumed == 0 && 1) {
Client_OnExit(CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_S2_STATE, _instance);
_instance->Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_State = CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_S4_STATE;
_instance->Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r4_var = r4;
_instance->Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r5_var = r5;
_instance->Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r2_var = r2;
_instance->Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r3_var = r3;
_instance->Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_a_var = a;
Client_OnEntry(CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_S4_STATE, _instance);
Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_State_event_consumed = 1;
}
}
//End Region INIT
Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_State_event_consumed = 0 | Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_State_event_consumed ;
//End dsregion INIT
}
//End Region generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a
//End dsregion null
//Session list: 
}
void Client_handle_diversified_m3bis(struct Client_Instance *_instance, uint8_t r4, uint8_t r5, uint8_t r2, uint8_t r3, uint8_t a) {
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
//Region generate_m3_from_m3__and_m3r4r5r2r3a
uint8_t Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_State_event_consumed = 0;
//End Region generate_m3_from_m3__and_m3r4r5r2r3a
//Region generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a
uint8_t Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_State_event_consumed = 0;
//End Region generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a
//End dsregion null
//Session list: 
}
void Client_handle_diversified_m3(struct Client_Instance *_instance, uint8_t r4, uint8_t r5, uint8_t r2, uint8_t r3, uint8_t a) {
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
//Region generate_m3_from_m3__and_m3r4r5r2r3a
uint8_t Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_State_event_consumed = 0;
//End Region generate_m3_from_m3__and_m3r4r5r2r3a
//Region generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a
uint8_t Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_State_event_consumed = 0;
//End Region generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a
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
//Region generate_m3_from_m3__and_m3r4r5r2r3a
if (_instance->Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_State == CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_STATE) {
//Region INIT
if (_instance->Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_State == CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_S4_STATE) {
if (1) {
Client_OnExit(CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_S4_STATE, _instance);
_instance->Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_State = CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_S1_STATE;
Client_OnEntry(CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_S1_STATE, _instance);
return 1;
}
}
}
//Region generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a
if (_instance->Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_State == CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_STATE) {
//Region INIT
if (_instance->Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_State == CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_S4_STATE) {
if (1) {
Client_OnExit(CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_S4_STATE, _instance);
_instance->Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_State = CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_S1_STATE;
Client_OnEntry(CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_S1_STATE, _instance);
return 1;
}
}
}
//begin dispatchEmptyToSession
//end dispatchEmptyToSession
return empty_event_consumed;
}

// Observers for outgoing messages:
void (*external_Client_send_app_m2r0r1_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void (*Client_send_app_m2r0r1_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void register_external_Client_send_app_m2r0r1_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
external_Client_send_app_m2r0r1_listener = _listener;
}
void register_Client_send_app_m2r0r1_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
Client_send_app_m2r0r1_listener = _listener;
}
void Client_send_app_m2r0r1(struct Client_Instance *_instance, uint8_t r6, uint8_t r0, uint8_t r7, uint8_t r8, uint8_t r1){
if (Client_send_app_m2r0r1_listener != 0x0) Client_send_app_m2r0r1_listener(_instance, r6, r0, r7, r8, r1);
if (external_Client_send_app_m2r0r1_listener != 0x0) external_Client_send_app_m2r0r1_listener(_instance, r6, r0, r7, r8, r1);
;
}
void (*external_Client_send_app_m2abc_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void (*Client_send_app_m2abc_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void register_external_Client_send_app_m2abc_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
external_Client_send_app_m2abc_listener = _listener;
}
void register_Client_send_app_m2abc_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
Client_send_app_m2abc_listener = _listener;
}
void Client_send_app_m2abc(struct Client_Instance *_instance, uint8_t a, uint8_t r10, uint8_t b, uint8_t r9, uint8_t c){
if (Client_send_app_m2abc_listener != 0x0) Client_send_app_m2abc_listener(_instance, a, r10, b, r9, c);
if (external_Client_send_app_m2abc_listener != 0x0) external_Client_send_app_m2abc_listener(_instance, a, r10, b, r9, c);
;
}
void (*external_Client_send_app_m1abcd_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void (*Client_send_app_m1abcd_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void register_external_Client_send_app_m1abcd_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
external_Client_send_app_m1abcd_listener = _listener;
}
void register_Client_send_app_m1abcd_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
Client_send_app_m1abcd_listener = _listener;
}
void Client_send_app_m1abcd(struct Client_Instance *_instance, uint8_t a, uint8_t b, uint8_t c, uint8_t r16, uint8_t d){
if (Client_send_app_m1abcd_listener != 0x0) Client_send_app_m1abcd_listener(_instance, a, b, c, r16, d);
if (external_Client_send_app_m1abcd_listener != 0x0) external_Client_send_app_m1abcd_listener(_instance, a, b, c, r16, d);
;
}
void (*external_Client_send_app_m1e_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void (*Client_send_app_m1e_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void register_external_Client_send_app_m1e_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
external_Client_send_app_m1e_listener = _listener;
}
void register_Client_send_app_m1e_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
Client_send_app_m1e_listener = _listener;
}
void Client_send_app_m1e(struct Client_Instance *_instance, uint8_t r18, uint8_t r19, uint8_t r20, uint8_t r17, uint8_t e){
if (Client_send_app_m1e_listener != 0x0) Client_send_app_m1e_listener(_instance, r18, r19, r20, r17, e);
if (external_Client_send_app_m1e_listener != 0x0) external_Client_send_app_m1e_listener(_instance, r18, r19, r20, r17, e);
;
}
void (*external_Client_send_app_m2bisr0r1ab_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void (*Client_send_app_m2bisr0r1ab_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void register_external_Client_send_app_m2bisr0r1ab_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
external_Client_send_app_m2bisr0r1ab_listener = _listener;
}
void register_Client_send_app_m2bisr0r1ab_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
Client_send_app_m2bisr0r1ab_listener = _listener;
}
void Client_send_app_m2bisr0r1ab(struct Client_Instance *_instance, uint8_t r21, uint8_t r0, uint8_t r1, uint8_t a, uint8_t b){
if (Client_send_app_m2bisr0r1ab_listener != 0x0) Client_send_app_m2bisr0r1ab_listener(_instance, r21, r0, r1, a, b);
if (external_Client_send_app_m2bisr0r1ab_listener != 0x0) external_Client_send_app_m2bisr0r1ab_listener(_instance, r21, r0, r1, a, b);
;
}
void (*external_Client_send_app_m2bisc_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void (*Client_send_app_m2bisc_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void register_external_Client_send_app_m2bisc_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
external_Client_send_app_m2bisc_listener = _listener;
}
void register_Client_send_app_m2bisc_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
Client_send_app_m2bisc_listener = _listener;
}
void Client_send_app_m2bisc(struct Client_Instance *_instance, uint8_t r24, uint8_t r22, uint8_t r23, uint8_t r25, uint8_t c){
if (Client_send_app_m2bisc_listener != 0x0) Client_send_app_m2bisc_listener(_instance, r24, r22, r23, r25, c);
if (external_Client_send_app_m2bisc_listener != 0x0) external_Client_send_app_m2bisc_listener(_instance, r24, r22, r23, r25, c);
;
}
void (*external_Client_send_app_m1bisabcd_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void (*Client_send_app_m1bisabcd_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void register_external_Client_send_app_m1bisabcd_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
external_Client_send_app_m1bisabcd_listener = _listener;
}
void register_Client_send_app_m1bisabcd_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
Client_send_app_m1bisabcd_listener = _listener;
}
void Client_send_app_m1bisabcd(struct Client_Instance *_instance, uint8_t a, uint8_t r31, uint8_t b, uint8_t c, uint8_t d){
if (Client_send_app_m1bisabcd_listener != 0x0) Client_send_app_m1bisabcd_listener(_instance, a, r31, b, c, d);
if (external_Client_send_app_m1bisabcd_listener != 0x0) external_Client_send_app_m1bisabcd_listener(_instance, a, r31, b, c, d);
;
}
void (*external_Client_send_app_m1bise_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void (*Client_send_app_m1bise_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void register_external_Client_send_app_m1bise_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
external_Client_send_app_m1bise_listener = _listener;
}
void register_Client_send_app_m1bise_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
Client_send_app_m1bise_listener = _listener;
}
void Client_send_app_m1bise(struct Client_Instance *_instance, uint8_t r32, uint8_t r35, uint8_t r33, uint8_t r34, uint8_t e){
if (Client_send_app_m1bise_listener != 0x0) Client_send_app_m1bise_listener(_instance, r32, r35, r33, r34, e);
if (external_Client_send_app_m1bise_listener != 0x0) external_Client_send_app_m1bise_listener(_instance, r32, r35, r33, r34, e);
;
}
void (*external_Client_send_diversified_m3_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void (*Client_send_diversified_m3_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void register_external_Client_send_diversified_m3_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
external_Client_send_diversified_m3_listener = _listener;
}
void register_Client_send_diversified_m3_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
Client_send_diversified_m3_listener = _listener;
}
void Client_send_diversified_m3(struct Client_Instance *_instance, uint8_t r4, uint8_t r5, uint8_t r2, uint8_t r3, uint8_t a){
if (Client_send_diversified_m3_listener != 0x0) Client_send_diversified_m3_listener(_instance, r4, r5, r2, r3, a);
if (external_Client_send_diversified_m3_listener != 0x0) external_Client_send_diversified_m3_listener(_instance, r4, r5, r2, r3, a);
;
}
void (*external_Client_send_diversified_m3bis__listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void (*Client_send_diversified_m3bis__listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void register_external_Client_send_diversified_m3bis__listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
external_Client_send_diversified_m3bis__listener = _listener;
}
void register_Client_send_diversified_m3bis__listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
Client_send_diversified_m3bis__listener = _listener;
}
void Client_send_diversified_m3bis_(struct Client_Instance *_instance, uint8_t r29, uint8_t r28, uint8_t r30, uint8_t r27, uint8_t r26){
if (Client_send_diversified_m3bis__listener != 0x0) Client_send_diversified_m3bis__listener(_instance, r29, r28, r30, r27, r26);
if (external_Client_send_diversified_m3bis__listener != 0x0) external_Client_send_diversified_m3bis__listener(_instance, r29, r28, r30, r27, r26);
;
}
void (*external_Client_send_diversified_m3bisr4r5r2r3a_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void (*Client_send_diversified_m3bisr4r5r2r3a_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void register_external_Client_send_diversified_m3bisr4r5r2r3a_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
external_Client_send_diversified_m3bisr4r5r2r3a_listener = _listener;
}
void register_Client_send_diversified_m3bisr4r5r2r3a_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
Client_send_diversified_m3bisr4r5r2r3a_listener = _listener;
}
void Client_send_diversified_m3bisr4r5r2r3a(struct Client_Instance *_instance, uint8_t r4, uint8_t r5, uint8_t r2, uint8_t r3, uint8_t a){
if (Client_send_diversified_m3bisr4r5r2r3a_listener != 0x0) Client_send_diversified_m3bisr4r5r2r3a_listener(_instance, r4, r5, r2, r3, a);
if (external_Client_send_diversified_m3bisr4r5r2r3a_listener != 0x0) external_Client_send_diversified_m3bisr4r5r2r3a_listener(_instance, r4, r5, r2, r3, a);
;
}
void (*external_Client_send_diversified_m3bis_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void (*Client_send_diversified_m3bis_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)= 0x0;
void register_external_Client_send_diversified_m3bis_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
external_Client_send_diversified_m3bis_listener = _listener;
}
void register_Client_send_diversified_m3bis_listener(void (*_listener)(struct Client_Instance *, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t)){
Client_send_diversified_m3bis_listener = _listener;
}
void Client_send_diversified_m3bis(struct Client_Instance *_instance, uint8_t r4, uint8_t r5, uint8_t r2, uint8_t r3, uint8_t a){
if (Client_send_diversified_m3bis_listener != 0x0) Client_send_diversified_m3bis_listener(_instance, r4, r5, r2, r3, a);
if (external_Client_send_diversified_m3bis_listener != 0x0) external_Client_send_diversified_m3bis_listener(_instance, r4, r5, r2, r3, a);
;
}






/*****************************************************************************
 * Definitions for configuration : test
 *****************************************************************************/

//Declaration of instance variables
//Instance app
// Variables for the properties of the instance
struct App_Instance app_var;
// Variables for the sessions of the instance
//Instance client1
// Variables for the properties of the instance
struct Client_Instance client1_var;
// Variables for the sessions of the instance


// Enqueue of messages App::app::m3r4r5r2r3a
void enqueue_App_send_app_m3r4r5r2r3a(struct App_Instance *_instance, uint8_t r4, uint8_t r5, uint8_t r2, uint8_t r3, uint8_t a){
if ( fifo_byte_available() > 9 ) {

_fifo_enqueue( (9 >> 8) & 0xFF );
_fifo_enqueue( 9 & 0xFF );

// ID of the source port of the instance
_fifo_enqueue( (_instance->id_app >> 8) & 0xFF );
_fifo_enqueue( _instance->id_app & 0xFF );

// parameter r4
union u_r4_t {
uint8_t p;
byte bytebuffer[1];
} u_r4;
u_r4.p = r4;
_fifo_enqueue(u_r4.bytebuffer[0] & 0xFF );

// parameter r5
union u_r5_t {
uint8_t p;
byte bytebuffer[1];
} u_r5;
u_r5.p = r5;
_fifo_enqueue(u_r5.bytebuffer[0] & 0xFF );

// parameter r2
union u_r2_t {
uint8_t p;
byte bytebuffer[1];
} u_r2;
u_r2.p = r2;
_fifo_enqueue(u_r2.bytebuffer[0] & 0xFF );

// parameter r3
union u_r3_t {
uint8_t p;
byte bytebuffer[1];
} u_r3;
u_r3.p = r3;
_fifo_enqueue(u_r3.bytebuffer[0] & 0xFF );

// parameter a
union u_a_t {
uint8_t p;
byte bytebuffer[1];
} u_a;
u_a.p = a;
_fifo_enqueue(u_a.bytebuffer[0] & 0xFF );
}
}
// Enqueue of messages App::app::m3bis_
void enqueue_App_send_app_m3bis_(struct App_Instance *_instance, uint8_t r29, uint8_t r28, uint8_t r30, uint8_t r27, uint8_t r26){
if ( fifo_byte_available() > 9 ) {

_fifo_enqueue( (14 >> 8) & 0xFF );
_fifo_enqueue( 14 & 0xFF );

// ID of the source port of the instance
_fifo_enqueue( (_instance->id_app >> 8) & 0xFF );
_fifo_enqueue( _instance->id_app & 0xFF );

// parameter r29
union u_r29_t {
uint8_t p;
byte bytebuffer[1];
} u_r29;
u_r29.p = r29;
_fifo_enqueue(u_r29.bytebuffer[0] & 0xFF );

// parameter r28
union u_r28_t {
uint8_t p;
byte bytebuffer[1];
} u_r28;
u_r28.p = r28;
_fifo_enqueue(u_r28.bytebuffer[0] & 0xFF );

// parameter r30
union u_r30_t {
uint8_t p;
byte bytebuffer[1];
} u_r30;
u_r30.p = r30;
_fifo_enqueue(u_r30.bytebuffer[0] & 0xFF );

// parameter r27
union u_r27_t {
uint8_t p;
byte bytebuffer[1];
} u_r27;
u_r27.p = r27;
_fifo_enqueue(u_r27.bytebuffer[0] & 0xFF );

// parameter r26
union u_r26_t {
uint8_t p;
byte bytebuffer[1];
} u_r26;
u_r26.p = r26;
_fifo_enqueue(u_r26.bytebuffer[0] & 0xFF );
}
}
// Enqueue of messages App::app::m3_
void enqueue_App_send_app_m3_(struct App_Instance *_instance, uint8_t r13, uint8_t r15, uint8_t r12, uint8_t r14, uint8_t r11){
if ( fifo_byte_available() > 9 ) {

_fifo_enqueue( (8 >> 8) & 0xFF );
_fifo_enqueue( 8 & 0xFF );

// ID of the source port of the instance
_fifo_enqueue( (_instance->id_app >> 8) & 0xFF );
_fifo_enqueue( _instance->id_app & 0xFF );

// parameter r13
union u_r13_t {
uint8_t p;
byte bytebuffer[1];
} u_r13;
u_r13.p = r13;
_fifo_enqueue(u_r13.bytebuffer[0] & 0xFF );

// parameter r15
union u_r15_t {
uint8_t p;
byte bytebuffer[1];
} u_r15;
u_r15.p = r15;
_fifo_enqueue(u_r15.bytebuffer[0] & 0xFF );

// parameter r12
union u_r12_t {
uint8_t p;
byte bytebuffer[1];
} u_r12;
u_r12.p = r12;
_fifo_enqueue(u_r12.bytebuffer[0] & 0xFF );

// parameter r14
union u_r14_t {
uint8_t p;
byte bytebuffer[1];
} u_r14;
u_r14.p = r14;
_fifo_enqueue(u_r14.bytebuffer[0] & 0xFF );

// parameter r11
union u_r11_t {
uint8_t p;
byte bytebuffer[1];
} u_r11;
u_r11.p = r11;
_fifo_enqueue(u_r11.bytebuffer[0] & 0xFF );
}
}
// Enqueue of messages App::app::m3bisr4r5r2r3a
void enqueue_App_send_app_m3bisr4r5r2r3a(struct App_Instance *_instance, uint8_t r4, uint8_t r5, uint8_t r2, uint8_t r3, uint8_t a){
if ( fifo_byte_available() > 9 ) {

_fifo_enqueue( (15 >> 8) & 0xFF );
_fifo_enqueue( 15 & 0xFF );

// ID of the source port of the instance
_fifo_enqueue( (_instance->id_app >> 8) & 0xFF );
_fifo_enqueue( _instance->id_app & 0xFF );

// parameter r4
union u_r4_t {
uint8_t p;
byte bytebuffer[1];
} u_r4;
u_r4.p = r4;
_fifo_enqueue(u_r4.bytebuffer[0] & 0xFF );

// parameter r5
union u_r5_t {
uint8_t p;
byte bytebuffer[1];
} u_r5;
u_r5.p = r5;
_fifo_enqueue(u_r5.bytebuffer[0] & 0xFF );

// parameter r2
union u_r2_t {
uint8_t p;
byte bytebuffer[1];
} u_r2;
u_r2.p = r2;
_fifo_enqueue(u_r2.bytebuffer[0] & 0xFF );

// parameter r3
union u_r3_t {
uint8_t p;
byte bytebuffer[1];
} u_r3;
u_r3.p = r3;
_fifo_enqueue(u_r3.bytebuffer[0] & 0xFF );

// parameter a
union u_a_t {
uint8_t p;
byte bytebuffer[1];
} u_a;
u_a.p = a;
_fifo_enqueue(u_a.bytebuffer[0] & 0xFF );
}
}
// Enqueue of messages App::diversified::m1bise
void enqueue_App_send_diversified_m1bise(struct App_Instance *_instance, uint8_t r32, uint8_t r35, uint8_t r33, uint8_t r34, uint8_t e){
if ( fifo_byte_available() > 9 ) {

_fifo_enqueue( (17 >> 8) & 0xFF );
_fifo_enqueue( 17 & 0xFF );

// ID of the source port of the instance
_fifo_enqueue( (_instance->id_diversified >> 8) & 0xFF );
_fifo_enqueue( _instance->id_diversified & 0xFF );

// parameter r32
union u_r32_t {
uint8_t p;
byte bytebuffer[1];
} u_r32;
u_r32.p = r32;
_fifo_enqueue(u_r32.bytebuffer[0] & 0xFF );

// parameter r35
union u_r35_t {
uint8_t p;
byte bytebuffer[1];
} u_r35;
u_r35.p = r35;
_fifo_enqueue(u_r35.bytebuffer[0] & 0xFF );

// parameter r33
union u_r33_t {
uint8_t p;
byte bytebuffer[1];
} u_r33;
u_r33.p = r33;
_fifo_enqueue(u_r33.bytebuffer[0] & 0xFF );

// parameter r34
union u_r34_t {
uint8_t p;
byte bytebuffer[1];
} u_r34;
u_r34.p = r34;
_fifo_enqueue(u_r34.bytebuffer[0] & 0xFF );

// parameter e
union u_e_t {
uint8_t p;
byte bytebuffer[1];
} u_e;
u_e.p = e;
_fifo_enqueue(u_e.bytebuffer[0] & 0xFF );
}
}
// Enqueue of messages App::diversified::m2bisc
void enqueue_App_send_diversified_m2bisc(struct App_Instance *_instance, uint8_t r24, uint8_t r22, uint8_t r23, uint8_t r25, uint8_t c){
if ( fifo_byte_available() > 9 ) {

_fifo_enqueue( (13 >> 8) & 0xFF );
_fifo_enqueue( 13 & 0xFF );

// ID of the source port of the instance
_fifo_enqueue( (_instance->id_diversified >> 8) & 0xFF );
_fifo_enqueue( _instance->id_diversified & 0xFF );

// parameter r24
union u_r24_t {
uint8_t p;
byte bytebuffer[1];
} u_r24;
u_r24.p = r24;
_fifo_enqueue(u_r24.bytebuffer[0] & 0xFF );

// parameter r22
union u_r22_t {
uint8_t p;
byte bytebuffer[1];
} u_r22;
u_r22.p = r22;
_fifo_enqueue(u_r22.bytebuffer[0] & 0xFF );

// parameter r23
union u_r23_t {
uint8_t p;
byte bytebuffer[1];
} u_r23;
u_r23.p = r23;
_fifo_enqueue(u_r23.bytebuffer[0] & 0xFF );

// parameter r25
union u_r25_t {
uint8_t p;
byte bytebuffer[1];
} u_r25;
u_r25.p = r25;
_fifo_enqueue(u_r25.bytebuffer[0] & 0xFF );

// parameter c
union u_c_t {
uint8_t p;
byte bytebuffer[1];
} u_c;
u_c.p = c;
_fifo_enqueue(u_c.bytebuffer[0] & 0xFF );
}
}
// Enqueue of messages App::diversified::m1e
void enqueue_App_send_diversified_m1e(struct App_Instance *_instance, uint8_t r18, uint8_t r19, uint8_t r20, uint8_t r17, uint8_t e){
if ( fifo_byte_available() > 9 ) {

_fifo_enqueue( (11 >> 8) & 0xFF );
_fifo_enqueue( 11 & 0xFF );

// ID of the source port of the instance
_fifo_enqueue( (_instance->id_diversified >> 8) & 0xFF );
_fifo_enqueue( _instance->id_diversified & 0xFF );

// parameter r18
union u_r18_t {
uint8_t p;
byte bytebuffer[1];
} u_r18;
u_r18.p = r18;
_fifo_enqueue(u_r18.bytebuffer[0] & 0xFF );

// parameter r19
union u_r19_t {
uint8_t p;
byte bytebuffer[1];
} u_r19;
u_r19.p = r19;
_fifo_enqueue(u_r19.bytebuffer[0] & 0xFF );

// parameter r20
union u_r20_t {
uint8_t p;
byte bytebuffer[1];
} u_r20;
u_r20.p = r20;
_fifo_enqueue(u_r20.bytebuffer[0] & 0xFF );

// parameter r17
union u_r17_t {
uint8_t p;
byte bytebuffer[1];
} u_r17;
u_r17.p = r17;
_fifo_enqueue(u_r17.bytebuffer[0] & 0xFF );

// parameter e
union u_e_t {
uint8_t p;
byte bytebuffer[1];
} u_e;
u_e.p = e;
_fifo_enqueue(u_e.bytebuffer[0] & 0xFF );
}
}
// Enqueue of messages App::diversified::m2
void enqueue_App_send_diversified_m2(struct App_Instance *_instance, uint8_t r0, uint8_t r1, uint8_t a, uint8_t b, uint8_t c){
if ( fifo_byte_available() > 9 ) {

_fifo_enqueue( (0 >> 8) & 0xFF );
_fifo_enqueue( 0 & 0xFF );

// ID of the source port of the instance
_fifo_enqueue( (_instance->id_diversified >> 8) & 0xFF );
_fifo_enqueue( _instance->id_diversified & 0xFF );

// parameter r0
union u_r0_t {
uint8_t p;
byte bytebuffer[1];
} u_r0;
u_r0.p = r0;
_fifo_enqueue(u_r0.bytebuffer[0] & 0xFF );

// parameter r1
union u_r1_t {
uint8_t p;
byte bytebuffer[1];
} u_r1;
u_r1.p = r1;
_fifo_enqueue(u_r1.bytebuffer[0] & 0xFF );

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
// Enqueue of messages App::diversified::m1
void enqueue_App_send_diversified_m1(struct App_Instance *_instance, uint8_t a, uint8_t b, uint8_t c, uint8_t d, uint8_t e){
if ( fifo_byte_available() > 9 ) {

_fifo_enqueue( (2 >> 8) & 0xFF );
_fifo_enqueue( 2 & 0xFF );

// ID of the source port of the instance
_fifo_enqueue( (_instance->id_diversified >> 8) & 0xFF );
_fifo_enqueue( _instance->id_diversified & 0xFF );

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
// Enqueue of messages App::diversified::m1bisabcd
void enqueue_App_send_diversified_m1bisabcd(struct App_Instance *_instance, uint8_t a, uint8_t r31, uint8_t b, uint8_t c, uint8_t d){
if ( fifo_byte_available() > 9 ) {

_fifo_enqueue( (16 >> 8) & 0xFF );
_fifo_enqueue( 16 & 0xFF );

// ID of the source port of the instance
_fifo_enqueue( (_instance->id_diversified >> 8) & 0xFF );
_fifo_enqueue( _instance->id_diversified & 0xFF );

// parameter a
union u_a_t {
uint8_t p;
byte bytebuffer[1];
} u_a;
u_a.p = a;
_fifo_enqueue(u_a.bytebuffer[0] & 0xFF );

// parameter r31
union u_r31_t {
uint8_t p;
byte bytebuffer[1];
} u_r31;
u_r31.p = r31;
_fifo_enqueue(u_r31.bytebuffer[0] & 0xFF );

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
}
}
// Enqueue of messages App::diversified::m1bis
void enqueue_App_send_diversified_m1bis(struct App_Instance *_instance, uint8_t a, uint8_t b, uint8_t c, uint8_t d, uint8_t e){
if ( fifo_byte_available() > 9 ) {

_fifo_enqueue( (5 >> 8) & 0xFF );
_fifo_enqueue( 5 & 0xFF );

// ID of the source port of the instance
_fifo_enqueue( (_instance->id_diversified >> 8) & 0xFF );
_fifo_enqueue( _instance->id_diversified & 0xFF );

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
// Enqueue of messages App::diversified::m1abcd
void enqueue_App_send_diversified_m1abcd(struct App_Instance *_instance, uint8_t a, uint8_t b, uint8_t c, uint8_t r16, uint8_t d){
if ( fifo_byte_available() > 9 ) {

_fifo_enqueue( (10 >> 8) & 0xFF );
_fifo_enqueue( 10 & 0xFF );

// ID of the source port of the instance
_fifo_enqueue( (_instance->id_diversified >> 8) & 0xFF );
_fifo_enqueue( _instance->id_diversified & 0xFF );

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

// parameter r16
union u_r16_t {
uint8_t p;
byte bytebuffer[1];
} u_r16;
u_r16.p = r16;
_fifo_enqueue(u_r16.bytebuffer[0] & 0xFF );

// parameter d
union u_d_t {
uint8_t p;
byte bytebuffer[1];
} u_d;
u_d.p = d;
_fifo_enqueue(u_d.bytebuffer[0] & 0xFF );
}
}
// Enqueue of messages App::diversified::m2bisr0r1ab
void enqueue_App_send_diversified_m2bisr0r1ab(struct App_Instance *_instance, uint8_t r21, uint8_t r0, uint8_t r1, uint8_t a, uint8_t b){
if ( fifo_byte_available() > 9 ) {

_fifo_enqueue( (12 >> 8) & 0xFF );
_fifo_enqueue( 12 & 0xFF );

// ID of the source port of the instance
_fifo_enqueue( (_instance->id_diversified >> 8) & 0xFF );
_fifo_enqueue( _instance->id_diversified & 0xFF );

// parameter r21
union u_r21_t {
uint8_t p;
byte bytebuffer[1];
} u_r21;
u_r21.p = r21;
_fifo_enqueue(u_r21.bytebuffer[0] & 0xFF );

// parameter r0
union u_r0_t {
uint8_t p;
byte bytebuffer[1];
} u_r0;
u_r0.p = r0;
_fifo_enqueue(u_r0.bytebuffer[0] & 0xFF );

// parameter r1
union u_r1_t {
uint8_t p;
byte bytebuffer[1];
} u_r1;
u_r1.p = r1;
_fifo_enqueue(u_r1.bytebuffer[0] & 0xFF );

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
}
}
// Enqueue of messages App::diversified::m2bis
void enqueue_App_send_diversified_m2bis(struct App_Instance *_instance, uint8_t r0, uint8_t r1, uint8_t a, uint8_t b, uint8_t c){
if ( fifo_byte_available() > 9 ) {

_fifo_enqueue( (3 >> 8) & 0xFF );
_fifo_enqueue( 3 & 0xFF );

// ID of the source port of the instance
_fifo_enqueue( (_instance->id_diversified >> 8) & 0xFF );
_fifo_enqueue( _instance->id_diversified & 0xFF );

// parameter r0
union u_r0_t {
uint8_t p;
byte bytebuffer[1];
} u_r0;
u_r0.p = r0;
_fifo_enqueue(u_r0.bytebuffer[0] & 0xFF );

// parameter r1
union u_r1_t {
uint8_t p;
byte bytebuffer[1];
} u_r1;
u_r1.p = r1;
_fifo_enqueue(u_r1.bytebuffer[0] & 0xFF );

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
// Enqueue of messages Client::app::m1bise
void enqueue_Client_send_app_m1bise(struct Client_Instance *_instance, uint8_t r32, uint8_t r35, uint8_t r33, uint8_t r34, uint8_t e){
if ( fifo_byte_available() > 9 ) {

_fifo_enqueue( (17 >> 8) & 0xFF );
_fifo_enqueue( 17 & 0xFF );

// ID of the source port of the instance
_fifo_enqueue( (_instance->id_app >> 8) & 0xFF );
_fifo_enqueue( _instance->id_app & 0xFF );

// parameter r32
union u_r32_t {
uint8_t p;
byte bytebuffer[1];
} u_r32;
u_r32.p = r32;
_fifo_enqueue(u_r32.bytebuffer[0] & 0xFF );

// parameter r35
union u_r35_t {
uint8_t p;
byte bytebuffer[1];
} u_r35;
u_r35.p = r35;
_fifo_enqueue(u_r35.bytebuffer[0] & 0xFF );

// parameter r33
union u_r33_t {
uint8_t p;
byte bytebuffer[1];
} u_r33;
u_r33.p = r33;
_fifo_enqueue(u_r33.bytebuffer[0] & 0xFF );

// parameter r34
union u_r34_t {
uint8_t p;
byte bytebuffer[1];
} u_r34;
u_r34.p = r34;
_fifo_enqueue(u_r34.bytebuffer[0] & 0xFF );

// parameter e
union u_e_t {
uint8_t p;
byte bytebuffer[1];
} u_e;
u_e.p = e;
_fifo_enqueue(u_e.bytebuffer[0] & 0xFF );
}
}
// Enqueue of messages Client::app::m2bisc
void enqueue_Client_send_app_m2bisc(struct Client_Instance *_instance, uint8_t r24, uint8_t r22, uint8_t r23, uint8_t r25, uint8_t c){
if ( fifo_byte_available() > 9 ) {

_fifo_enqueue( (13 >> 8) & 0xFF );
_fifo_enqueue( 13 & 0xFF );

// ID of the source port of the instance
_fifo_enqueue( (_instance->id_app >> 8) & 0xFF );
_fifo_enqueue( _instance->id_app & 0xFF );

// parameter r24
union u_r24_t {
uint8_t p;
byte bytebuffer[1];
} u_r24;
u_r24.p = r24;
_fifo_enqueue(u_r24.bytebuffer[0] & 0xFF );

// parameter r22
union u_r22_t {
uint8_t p;
byte bytebuffer[1];
} u_r22;
u_r22.p = r22;
_fifo_enqueue(u_r22.bytebuffer[0] & 0xFF );

// parameter r23
union u_r23_t {
uint8_t p;
byte bytebuffer[1];
} u_r23;
u_r23.p = r23;
_fifo_enqueue(u_r23.bytebuffer[0] & 0xFF );

// parameter r25
union u_r25_t {
uint8_t p;
byte bytebuffer[1];
} u_r25;
u_r25.p = r25;
_fifo_enqueue(u_r25.bytebuffer[0] & 0xFF );

// parameter c
union u_c_t {
uint8_t p;
byte bytebuffer[1];
} u_c;
u_c.p = c;
_fifo_enqueue(u_c.bytebuffer[0] & 0xFF );
}
}
// Enqueue of messages Client::app::m1e
void enqueue_Client_send_app_m1e(struct Client_Instance *_instance, uint8_t r18, uint8_t r19, uint8_t r20, uint8_t r17, uint8_t e){
if ( fifo_byte_available() > 9 ) {

_fifo_enqueue( (11 >> 8) & 0xFF );
_fifo_enqueue( 11 & 0xFF );

// ID of the source port of the instance
_fifo_enqueue( (_instance->id_app >> 8) & 0xFF );
_fifo_enqueue( _instance->id_app & 0xFF );

// parameter r18
union u_r18_t {
uint8_t p;
byte bytebuffer[1];
} u_r18;
u_r18.p = r18;
_fifo_enqueue(u_r18.bytebuffer[0] & 0xFF );

// parameter r19
union u_r19_t {
uint8_t p;
byte bytebuffer[1];
} u_r19;
u_r19.p = r19;
_fifo_enqueue(u_r19.bytebuffer[0] & 0xFF );

// parameter r20
union u_r20_t {
uint8_t p;
byte bytebuffer[1];
} u_r20;
u_r20.p = r20;
_fifo_enqueue(u_r20.bytebuffer[0] & 0xFF );

// parameter r17
union u_r17_t {
uint8_t p;
byte bytebuffer[1];
} u_r17;
u_r17.p = r17;
_fifo_enqueue(u_r17.bytebuffer[0] & 0xFF );

// parameter e
union u_e_t {
uint8_t p;
byte bytebuffer[1];
} u_e;
u_e.p = e;
_fifo_enqueue(u_e.bytebuffer[0] & 0xFF );
}
}
// Enqueue of messages Client::app::m2r0r1
void enqueue_Client_send_app_m2r0r1(struct Client_Instance *_instance, uint8_t r6, uint8_t r0, uint8_t r7, uint8_t r8, uint8_t r1){
if ( fifo_byte_available() > 9 ) {

_fifo_enqueue( (6 >> 8) & 0xFF );
_fifo_enqueue( 6 & 0xFF );

// ID of the source port of the instance
_fifo_enqueue( (_instance->id_app >> 8) & 0xFF );
_fifo_enqueue( _instance->id_app & 0xFF );

// parameter r6
union u_r6_t {
uint8_t p;
byte bytebuffer[1];
} u_r6;
u_r6.p = r6;
_fifo_enqueue(u_r6.bytebuffer[0] & 0xFF );

// parameter r0
union u_r0_t {
uint8_t p;
byte bytebuffer[1];
} u_r0;
u_r0.p = r0;
_fifo_enqueue(u_r0.bytebuffer[0] & 0xFF );

// parameter r7
union u_r7_t {
uint8_t p;
byte bytebuffer[1];
} u_r7;
u_r7.p = r7;
_fifo_enqueue(u_r7.bytebuffer[0] & 0xFF );

// parameter r8
union u_r8_t {
uint8_t p;
byte bytebuffer[1];
} u_r8;
u_r8.p = r8;
_fifo_enqueue(u_r8.bytebuffer[0] & 0xFF );

// parameter r1
union u_r1_t {
uint8_t p;
byte bytebuffer[1];
} u_r1;
u_r1.p = r1;
_fifo_enqueue(u_r1.bytebuffer[0] & 0xFF );
}
}
// Enqueue of messages Client::app::m1bisabcd
void enqueue_Client_send_app_m1bisabcd(struct Client_Instance *_instance, uint8_t a, uint8_t r31, uint8_t b, uint8_t c, uint8_t d){
if ( fifo_byte_available() > 9 ) {

_fifo_enqueue( (16 >> 8) & 0xFF );
_fifo_enqueue( 16 & 0xFF );

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

// parameter r31
union u_r31_t {
uint8_t p;
byte bytebuffer[1];
} u_r31;
u_r31.p = r31;
_fifo_enqueue(u_r31.bytebuffer[0] & 0xFF );

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
}
}
// Enqueue of messages Client::app::m2abc
void enqueue_Client_send_app_m2abc(struct Client_Instance *_instance, uint8_t a, uint8_t r10, uint8_t b, uint8_t r9, uint8_t c){
if ( fifo_byte_available() > 9 ) {

_fifo_enqueue( (7 >> 8) & 0xFF );
_fifo_enqueue( 7 & 0xFF );

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

// parameter r10
union u_r10_t {
uint8_t p;
byte bytebuffer[1];
} u_r10;
u_r10.p = r10;
_fifo_enqueue(u_r10.bytebuffer[0] & 0xFF );

// parameter b
union u_b_t {
uint8_t p;
byte bytebuffer[1];
} u_b;
u_b.p = b;
_fifo_enqueue(u_b.bytebuffer[0] & 0xFF );

// parameter r9
union u_r9_t {
uint8_t p;
byte bytebuffer[1];
} u_r9;
u_r9.p = r9;
_fifo_enqueue(u_r9.bytebuffer[0] & 0xFF );

// parameter c
union u_c_t {
uint8_t p;
byte bytebuffer[1];
} u_c;
u_c.p = c;
_fifo_enqueue(u_c.bytebuffer[0] & 0xFF );
}
}
// Enqueue of messages Client::app::m1abcd
void enqueue_Client_send_app_m1abcd(struct Client_Instance *_instance, uint8_t a, uint8_t b, uint8_t c, uint8_t r16, uint8_t d){
if ( fifo_byte_available() > 9 ) {

_fifo_enqueue( (10 >> 8) & 0xFF );
_fifo_enqueue( 10 & 0xFF );

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

// parameter r16
union u_r16_t {
uint8_t p;
byte bytebuffer[1];
} u_r16;
u_r16.p = r16;
_fifo_enqueue(u_r16.bytebuffer[0] & 0xFF );

// parameter d
union u_d_t {
uint8_t p;
byte bytebuffer[1];
} u_d;
u_d.p = d;
_fifo_enqueue(u_d.bytebuffer[0] & 0xFF );
}
}
// Enqueue of messages Client::app::m2bisr0r1ab
void enqueue_Client_send_app_m2bisr0r1ab(struct Client_Instance *_instance, uint8_t r21, uint8_t r0, uint8_t r1, uint8_t a, uint8_t b){
if ( fifo_byte_available() > 9 ) {

_fifo_enqueue( (12 >> 8) & 0xFF );
_fifo_enqueue( 12 & 0xFF );

// ID of the source port of the instance
_fifo_enqueue( (_instance->id_app >> 8) & 0xFF );
_fifo_enqueue( _instance->id_app & 0xFF );

// parameter r21
union u_r21_t {
uint8_t p;
byte bytebuffer[1];
} u_r21;
u_r21.p = r21;
_fifo_enqueue(u_r21.bytebuffer[0] & 0xFF );

// parameter r0
union u_r0_t {
uint8_t p;
byte bytebuffer[1];
} u_r0;
u_r0.p = r0;
_fifo_enqueue(u_r0.bytebuffer[0] & 0xFF );

// parameter r1
union u_r1_t {
uint8_t p;
byte bytebuffer[1];
} u_r1;
u_r1.p = r1;
_fifo_enqueue(u_r1.bytebuffer[0] & 0xFF );

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
}
}
// Enqueue of messages Client::diversified::m3bis_
void enqueue_Client_send_diversified_m3bis_(struct Client_Instance *_instance, uint8_t r29, uint8_t r28, uint8_t r30, uint8_t r27, uint8_t r26){
if ( fifo_byte_available() > 9 ) {

_fifo_enqueue( (14 >> 8) & 0xFF );
_fifo_enqueue( 14 & 0xFF );

// ID of the source port of the instance
_fifo_enqueue( (_instance->id_diversified >> 8) & 0xFF );
_fifo_enqueue( _instance->id_diversified & 0xFF );

// parameter r29
union u_r29_t {
uint8_t p;
byte bytebuffer[1];
} u_r29;
u_r29.p = r29;
_fifo_enqueue(u_r29.bytebuffer[0] & 0xFF );

// parameter r28
union u_r28_t {
uint8_t p;
byte bytebuffer[1];
} u_r28;
u_r28.p = r28;
_fifo_enqueue(u_r28.bytebuffer[0] & 0xFF );

// parameter r30
union u_r30_t {
uint8_t p;
byte bytebuffer[1];
} u_r30;
u_r30.p = r30;
_fifo_enqueue(u_r30.bytebuffer[0] & 0xFF );

// parameter r27
union u_r27_t {
uint8_t p;
byte bytebuffer[1];
} u_r27;
u_r27.p = r27;
_fifo_enqueue(u_r27.bytebuffer[0] & 0xFF );

// parameter r26
union u_r26_t {
uint8_t p;
byte bytebuffer[1];
} u_r26;
u_r26.p = r26;
_fifo_enqueue(u_r26.bytebuffer[0] & 0xFF );
}
}
// Enqueue of messages Client::diversified::m3bis
void enqueue_Client_send_diversified_m3bis(struct Client_Instance *_instance, uint8_t r4, uint8_t r5, uint8_t r2, uint8_t r3, uint8_t a){
if ( fifo_byte_available() > 9 ) {

_fifo_enqueue( (4 >> 8) & 0xFF );
_fifo_enqueue( 4 & 0xFF );

// ID of the source port of the instance
_fifo_enqueue( (_instance->id_diversified >> 8) & 0xFF );
_fifo_enqueue( _instance->id_diversified & 0xFF );

// parameter r4
union u_r4_t {
uint8_t p;
byte bytebuffer[1];
} u_r4;
u_r4.p = r4;
_fifo_enqueue(u_r4.bytebuffer[0] & 0xFF );

// parameter r5
union u_r5_t {
uint8_t p;
byte bytebuffer[1];
} u_r5;
u_r5.p = r5;
_fifo_enqueue(u_r5.bytebuffer[0] & 0xFF );

// parameter r2
union u_r2_t {
uint8_t p;
byte bytebuffer[1];
} u_r2;
u_r2.p = r2;
_fifo_enqueue(u_r2.bytebuffer[0] & 0xFF );

// parameter r3
union u_r3_t {
uint8_t p;
byte bytebuffer[1];
} u_r3;
u_r3.p = r3;
_fifo_enqueue(u_r3.bytebuffer[0] & 0xFF );

// parameter a
union u_a_t {
uint8_t p;
byte bytebuffer[1];
} u_a;
u_a.p = a;
_fifo_enqueue(u_a.bytebuffer[0] & 0xFF );
}
}
// Enqueue of messages Client::diversified::m3
void enqueue_Client_send_diversified_m3(struct Client_Instance *_instance, uint8_t r4, uint8_t r5, uint8_t r2, uint8_t r3, uint8_t a){
if ( fifo_byte_available() > 9 ) {

_fifo_enqueue( (1 >> 8) & 0xFF );
_fifo_enqueue( 1 & 0xFF );

// ID of the source port of the instance
_fifo_enqueue( (_instance->id_diversified >> 8) & 0xFF );
_fifo_enqueue( _instance->id_diversified & 0xFF );

// parameter r4
union u_r4_t {
uint8_t p;
byte bytebuffer[1];
} u_r4;
u_r4.p = r4;
_fifo_enqueue(u_r4.bytebuffer[0] & 0xFF );

// parameter r5
union u_r5_t {
uint8_t p;
byte bytebuffer[1];
} u_r5;
u_r5.p = r5;
_fifo_enqueue(u_r5.bytebuffer[0] & 0xFF );

// parameter r2
union u_r2_t {
uint8_t p;
byte bytebuffer[1];
} u_r2;
u_r2.p = r2;
_fifo_enqueue(u_r2.bytebuffer[0] & 0xFF );

// parameter r3
union u_r3_t {
uint8_t p;
byte bytebuffer[1];
} u_r3;
u_r3.p = r3;
_fifo_enqueue(u_r3.bytebuffer[0] & 0xFF );

// parameter a
union u_a_t {
uint8_t p;
byte bytebuffer[1];
} u_a;
u_a.p = a;
_fifo_enqueue(u_a.bytebuffer[0] & 0xFF );
}
}
// Enqueue of messages Client::diversified::m3bisr4r5r2r3a
void enqueue_Client_send_diversified_m3bisr4r5r2r3a(struct Client_Instance *_instance, uint8_t r4, uint8_t r5, uint8_t r2, uint8_t r3, uint8_t a){
if ( fifo_byte_available() > 9 ) {

_fifo_enqueue( (15 >> 8) & 0xFF );
_fifo_enqueue( 15 & 0xFF );

// ID of the source port of the instance
_fifo_enqueue( (_instance->id_diversified >> 8) & 0xFF );
_fifo_enqueue( _instance->id_diversified & 0xFF );

// parameter r4
union u_r4_t {
uint8_t p;
byte bytebuffer[1];
} u_r4;
u_r4.p = r4;
_fifo_enqueue(u_r4.bytebuffer[0] & 0xFF );

// parameter r5
union u_r5_t {
uint8_t p;
byte bytebuffer[1];
} u_r5;
u_r5.p = r5;
_fifo_enqueue(u_r5.bytebuffer[0] & 0xFF );

// parameter r2
union u_r2_t {
uint8_t p;
byte bytebuffer[1];
} u_r2;
u_r2.p = r2;
_fifo_enqueue(u_r2.bytebuffer[0] & 0xFF );

// parameter r3
union u_r3_t {
uint8_t p;
byte bytebuffer[1];
} u_r3;
u_r3.p = r3;
_fifo_enqueue(u_r3.bytebuffer[0] & 0xFF );

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
void dispatch_m1bise(uint16_t sender, uint8_t param_r32, uint8_t param_r35, uint8_t param_r33, uint8_t param_r34, uint8_t param_e) {
if (sender == app_var.id_diversified) {

}
if (sender == client1_var.id_app) {
App_handle_app_m1bise(&app_var, param_r32, param_r35, param_r33, param_r34, param_e);

}
if (sender == client1_var.id_diversified) {

}

}


//New dispatcher for messages
void dispatch_m1e(uint16_t sender, uint8_t param_r18, uint8_t param_r19, uint8_t param_r20, uint8_t param_r17, uint8_t param_e) {
if (sender == app_var.id_diversified) {

}
if (sender == client1_var.id_app) {
App_handle_app_m1e(&app_var, param_r18, param_r19, param_r20, param_r17, param_e);

}
if (sender == client1_var.id_diversified) {

}

}


//New dispatcher for messages
void dispatch_m2r0r1(uint16_t sender, uint8_t param_r6, uint8_t param_r0, uint8_t param_r7, uint8_t param_r8, uint8_t param_r1) {
if (sender == app_var.id_diversified) {

}
if (sender == client1_var.id_app) {
App_handle_app_m2r0r1(&app_var, param_r6, param_r0, param_r7, param_r8, param_r1);

}
if (sender == client1_var.id_diversified) {

}

}


//New dispatcher for messages
void dispatch_m3_(uint16_t sender, uint8_t param_r13, uint8_t param_r15, uint8_t param_r12, uint8_t param_r14, uint8_t param_r11) {
if (sender == app_var.id_diversified) {

}
if (sender == app_var.id_app) {
Client_handle_app_m3_(&client1_var, param_r13, param_r15, param_r12, param_r14, param_r11);

}
if (sender == client1_var.id_diversified) {

}

}


//New dispatcher for messages
void dispatch_m3bis(uint16_t sender, uint8_t param_r4, uint8_t param_r5, uint8_t param_r2, uint8_t param_r3, uint8_t param_a) {
if (sender == app_var.id_diversified) {

}
if (sender == client1_var.id_diversified) {
Client_handle_diversified_m3bis(&client1_var, param_r4, param_r5, param_r2, param_r3, param_a);

}

}


//New dispatcher for messages
void dispatch_m3(uint16_t sender, uint8_t param_r4, uint8_t param_r5, uint8_t param_r2, uint8_t param_r3, uint8_t param_a) {
if (sender == app_var.id_diversified) {

}
if (sender == client1_var.id_diversified) {
Client_handle_diversified_m3(&client1_var, param_r4, param_r5, param_r2, param_r3, param_a);

}

}


//New dispatcher for messages
void dispatch_m1bis(uint16_t sender, uint8_t param_a, uint8_t param_b, uint8_t param_c, uint8_t param_d, uint8_t param_e) {
if (sender == app_var.id_diversified) {
App_handle_diversified_m1bis(&app_var, param_a, param_b, param_c, param_d, param_e);

}
if (sender == client1_var.id_diversified) {

}

}


//New dispatcher for messages
void dispatch_m2abc(uint16_t sender, uint8_t param_a, uint8_t param_r10, uint8_t param_b, uint8_t param_r9, uint8_t param_c) {
if (sender == app_var.id_diversified) {

}
if (sender == client1_var.id_app) {
App_handle_app_m2abc(&app_var, param_a, param_r10, param_b, param_r9, param_c);

}
if (sender == client1_var.id_diversified) {

}

}


//New dispatcher for messages
void dispatch_m1abcd(uint16_t sender, uint8_t param_a, uint8_t param_b, uint8_t param_c, uint8_t param_r16, uint8_t param_d) {
if (sender == app_var.id_diversified) {

}
if (sender == client1_var.id_app) {
App_handle_app_m1abcd(&app_var, param_a, param_b, param_c, param_r16, param_d);

}
if (sender == client1_var.id_diversified) {

}

}


//New dispatcher for messages
void dispatch_m2bis(uint16_t sender, uint8_t param_r0, uint8_t param_r1, uint8_t param_a, uint8_t param_b, uint8_t param_c) {
if (sender == app_var.id_diversified) {
App_handle_diversified_m2bis(&app_var, param_r0, param_r1, param_a, param_b, param_c);

}
if (sender == client1_var.id_diversified) {

}

}


//New dispatcher for messages
void dispatch_m2bisc(uint16_t sender, uint8_t param_r24, uint8_t param_r22, uint8_t param_r23, uint8_t param_r25, uint8_t param_c) {
if (sender == app_var.id_diversified) {

}
if (sender == client1_var.id_app) {
App_handle_app_m2bisc(&app_var, param_r24, param_r22, param_r23, param_r25, param_c);

}
if (sender == client1_var.id_diversified) {

}

}


//New dispatcher for messages
void dispatch_m3r4r5r2r3a(uint16_t sender, uint8_t param_r4, uint8_t param_r5, uint8_t param_r2, uint8_t param_r3, uint8_t param_a) {
if (sender == app_var.id_diversified) {

}
if (sender == app_var.id_app) {
Client_handle_app_m3r4r5r2r3a(&client1_var, param_r4, param_r5, param_r2, param_r3, param_a);

}
if (sender == client1_var.id_diversified) {

}

}


//New dispatcher for messages
void dispatch_m3bis_(uint16_t sender, uint8_t param_r29, uint8_t param_r28, uint8_t param_r30, uint8_t param_r27, uint8_t param_r26) {
if (sender == app_var.id_diversified) {

}
if (sender == app_var.id_app) {
Client_handle_app_m3bis_(&client1_var, param_r29, param_r28, param_r30, param_r27, param_r26);

}
if (sender == client1_var.id_diversified) {

}

}


//New dispatcher for messages
void dispatch_m2(uint16_t sender, uint8_t param_r0, uint8_t param_r1, uint8_t param_a, uint8_t param_b, uint8_t param_c) {
if (sender == app_var.id_diversified) {
App_handle_diversified_m2(&app_var, param_r0, param_r1, param_a, param_b, param_c);

}
if (sender == client1_var.id_diversified) {

}

}


//New dispatcher for messages
void dispatch_m1(uint16_t sender, uint8_t param_a, uint8_t param_b, uint8_t param_c, uint8_t param_d, uint8_t param_e) {
if (sender == app_var.id_diversified) {
App_handle_diversified_m1(&app_var, param_a, param_b, param_c, param_d, param_e);

}
if (sender == client1_var.id_diversified) {

}

}


//New dispatcher for messages
void dispatch_m1bisabcd(uint16_t sender, uint8_t param_a, uint8_t param_r31, uint8_t param_b, uint8_t param_c, uint8_t param_d) {
if (sender == app_var.id_diversified) {

}
if (sender == client1_var.id_app) {
App_handle_app_m1bisabcd(&app_var, param_a, param_r31, param_b, param_c, param_d);

}
if (sender == client1_var.id_diversified) {

}

}


//New dispatcher for messages
void dispatch_m3bisr4r5r2r3a(uint16_t sender, uint8_t param_r4, uint8_t param_r5, uint8_t param_r2, uint8_t param_r3, uint8_t param_a) {
if (sender == app_var.id_diversified) {

}
if (sender == app_var.id_app) {
Client_handle_app_m3bisr4r5r2r3a(&client1_var, param_r4, param_r5, param_r2, param_r3, param_a);

}
if (sender == client1_var.id_diversified) {

}

}


//New dispatcher for messages
void dispatch_m2bisr0r1ab(uint16_t sender, uint8_t param_r21, uint8_t param_r0, uint8_t param_r1, uint8_t param_a, uint8_t param_b) {
if (sender == app_var.id_diversified) {

}
if (sender == client1_var.id_app) {
App_handle_app_m2bisr0r1ab(&app_var, param_r21, param_r0, param_r1, param_a, param_b);

}
if (sender == client1_var.id_diversified) {

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
case 17:{
byte mbuf[9 - 2];
while (mbufi < (9 - 2)) mbuf[mbufi++] = fifo_dequeue();
uint8_t mbufi_m1bise = 2;
union u_m1bise_r32_t {
uint8_t p;
byte bytebuffer[1];
} u_m1bise_r32;
u_m1bise_r32.bytebuffer[0] = mbuf[mbufi_m1bise + 0];
mbufi_m1bise += 1;
union u_m1bise_r35_t {
uint8_t p;
byte bytebuffer[1];
} u_m1bise_r35;
u_m1bise_r35.bytebuffer[0] = mbuf[mbufi_m1bise + 0];
mbufi_m1bise += 1;
union u_m1bise_r33_t {
uint8_t p;
byte bytebuffer[1];
} u_m1bise_r33;
u_m1bise_r33.bytebuffer[0] = mbuf[mbufi_m1bise + 0];
mbufi_m1bise += 1;
union u_m1bise_r34_t {
uint8_t p;
byte bytebuffer[1];
} u_m1bise_r34;
u_m1bise_r34.bytebuffer[0] = mbuf[mbufi_m1bise + 0];
mbufi_m1bise += 1;
union u_m1bise_e_t {
uint8_t p;
byte bytebuffer[1];
} u_m1bise_e;
u_m1bise_e.bytebuffer[0] = mbuf[mbufi_m1bise + 0];
mbufi_m1bise += 1;
dispatch_m1bise((mbuf[0] << 8) + mbuf[1] /* instance port*/,
 u_m1bise_r32.p /* r32 */ ,
 u_m1bise_r35.p /* r35 */ ,
 u_m1bise_r33.p /* r33 */ ,
 u_m1bise_r34.p /* r34 */ ,
 u_m1bise_e.p /* e */ );
break;
}
case 11:{
byte mbuf[9 - 2];
while (mbufi < (9 - 2)) mbuf[mbufi++] = fifo_dequeue();
uint8_t mbufi_m1e = 2;
union u_m1e_r18_t {
uint8_t p;
byte bytebuffer[1];
} u_m1e_r18;
u_m1e_r18.bytebuffer[0] = mbuf[mbufi_m1e + 0];
mbufi_m1e += 1;
union u_m1e_r19_t {
uint8_t p;
byte bytebuffer[1];
} u_m1e_r19;
u_m1e_r19.bytebuffer[0] = mbuf[mbufi_m1e + 0];
mbufi_m1e += 1;
union u_m1e_r20_t {
uint8_t p;
byte bytebuffer[1];
} u_m1e_r20;
u_m1e_r20.bytebuffer[0] = mbuf[mbufi_m1e + 0];
mbufi_m1e += 1;
union u_m1e_r17_t {
uint8_t p;
byte bytebuffer[1];
} u_m1e_r17;
u_m1e_r17.bytebuffer[0] = mbuf[mbufi_m1e + 0];
mbufi_m1e += 1;
union u_m1e_e_t {
uint8_t p;
byte bytebuffer[1];
} u_m1e_e;
u_m1e_e.bytebuffer[0] = mbuf[mbufi_m1e + 0];
mbufi_m1e += 1;
dispatch_m1e((mbuf[0] << 8) + mbuf[1] /* instance port*/,
 u_m1e_r18.p /* r18 */ ,
 u_m1e_r19.p /* r19 */ ,
 u_m1e_r20.p /* r20 */ ,
 u_m1e_r17.p /* r17 */ ,
 u_m1e_e.p /* e */ );
break;
}
case 8:{
byte mbuf[9 - 2];
while (mbufi < (9 - 2)) mbuf[mbufi++] = fifo_dequeue();
uint8_t mbufi_m3_ = 2;
union u_m3__r13_t {
uint8_t p;
byte bytebuffer[1];
} u_m3__r13;
u_m3__r13.bytebuffer[0] = mbuf[mbufi_m3_ + 0];
mbufi_m3_ += 1;
union u_m3__r15_t {
uint8_t p;
byte bytebuffer[1];
} u_m3__r15;
u_m3__r15.bytebuffer[0] = mbuf[mbufi_m3_ + 0];
mbufi_m3_ += 1;
union u_m3__r12_t {
uint8_t p;
byte bytebuffer[1];
} u_m3__r12;
u_m3__r12.bytebuffer[0] = mbuf[mbufi_m3_ + 0];
mbufi_m3_ += 1;
union u_m3__r14_t {
uint8_t p;
byte bytebuffer[1];
} u_m3__r14;
u_m3__r14.bytebuffer[0] = mbuf[mbufi_m3_ + 0];
mbufi_m3_ += 1;
union u_m3__r11_t {
uint8_t p;
byte bytebuffer[1];
} u_m3__r11;
u_m3__r11.bytebuffer[0] = mbuf[mbufi_m3_ + 0];
mbufi_m3_ += 1;
dispatch_m3_((mbuf[0] << 8) + mbuf[1] /* instance port*/,
 u_m3__r13.p /* r13 */ ,
 u_m3__r15.p /* r15 */ ,
 u_m3__r12.p /* r12 */ ,
 u_m3__r14.p /* r14 */ ,
 u_m3__r11.p /* r11 */ );
break;
}
case 6:{
byte mbuf[9 - 2];
while (mbufi < (9 - 2)) mbuf[mbufi++] = fifo_dequeue();
uint8_t mbufi_m2r0r1 = 2;
union u_m2r0r1_r6_t {
uint8_t p;
byte bytebuffer[1];
} u_m2r0r1_r6;
u_m2r0r1_r6.bytebuffer[0] = mbuf[mbufi_m2r0r1 + 0];
mbufi_m2r0r1 += 1;
union u_m2r0r1_r0_t {
uint8_t p;
byte bytebuffer[1];
} u_m2r0r1_r0;
u_m2r0r1_r0.bytebuffer[0] = mbuf[mbufi_m2r0r1 + 0];
mbufi_m2r0r1 += 1;
union u_m2r0r1_r7_t {
uint8_t p;
byte bytebuffer[1];
} u_m2r0r1_r7;
u_m2r0r1_r7.bytebuffer[0] = mbuf[mbufi_m2r0r1 + 0];
mbufi_m2r0r1 += 1;
union u_m2r0r1_r8_t {
uint8_t p;
byte bytebuffer[1];
} u_m2r0r1_r8;
u_m2r0r1_r8.bytebuffer[0] = mbuf[mbufi_m2r0r1 + 0];
mbufi_m2r0r1 += 1;
union u_m2r0r1_r1_t {
uint8_t p;
byte bytebuffer[1];
} u_m2r0r1_r1;
u_m2r0r1_r1.bytebuffer[0] = mbuf[mbufi_m2r0r1 + 0];
mbufi_m2r0r1 += 1;
dispatch_m2r0r1((mbuf[0] << 8) + mbuf[1] /* instance port*/,
 u_m2r0r1_r6.p /* r6 */ ,
 u_m2r0r1_r0.p /* r0 */ ,
 u_m2r0r1_r7.p /* r7 */ ,
 u_m2r0r1_r8.p /* r8 */ ,
 u_m2r0r1_r1.p /* r1 */ );
break;
}
case 4:{
byte mbuf[9 - 2];
while (mbufi < (9 - 2)) mbuf[mbufi++] = fifo_dequeue();
uint8_t mbufi_m3bis = 2;
union u_m3bis_r4_t {
uint8_t p;
byte bytebuffer[1];
} u_m3bis_r4;
u_m3bis_r4.bytebuffer[0] = mbuf[mbufi_m3bis + 0];
mbufi_m3bis += 1;
union u_m3bis_r5_t {
uint8_t p;
byte bytebuffer[1];
} u_m3bis_r5;
u_m3bis_r5.bytebuffer[0] = mbuf[mbufi_m3bis + 0];
mbufi_m3bis += 1;
union u_m3bis_r2_t {
uint8_t p;
byte bytebuffer[1];
} u_m3bis_r2;
u_m3bis_r2.bytebuffer[0] = mbuf[mbufi_m3bis + 0];
mbufi_m3bis += 1;
union u_m3bis_r3_t {
uint8_t p;
byte bytebuffer[1];
} u_m3bis_r3;
u_m3bis_r3.bytebuffer[0] = mbuf[mbufi_m3bis + 0];
mbufi_m3bis += 1;
union u_m3bis_a_t {
uint8_t p;
byte bytebuffer[1];
} u_m3bis_a;
u_m3bis_a.bytebuffer[0] = mbuf[mbufi_m3bis + 0];
mbufi_m3bis += 1;
dispatch_m3bis((mbuf[0] << 8) + mbuf[1] /* instance port*/,
 u_m3bis_r4.p /* r4 */ ,
 u_m3bis_r5.p /* r5 */ ,
 u_m3bis_r2.p /* r2 */ ,
 u_m3bis_r3.p /* r3 */ ,
 u_m3bis_a.p /* a */ );
break;
}
case 5:{
byte mbuf[9 - 2];
while (mbufi < (9 - 2)) mbuf[mbufi++] = fifo_dequeue();
uint8_t mbufi_m1bis = 2;
union u_m1bis_a_t {
uint8_t p;
byte bytebuffer[1];
} u_m1bis_a;
u_m1bis_a.bytebuffer[0] = mbuf[mbufi_m1bis + 0];
mbufi_m1bis += 1;
union u_m1bis_b_t {
uint8_t p;
byte bytebuffer[1];
} u_m1bis_b;
u_m1bis_b.bytebuffer[0] = mbuf[mbufi_m1bis + 0];
mbufi_m1bis += 1;
union u_m1bis_c_t {
uint8_t p;
byte bytebuffer[1];
} u_m1bis_c;
u_m1bis_c.bytebuffer[0] = mbuf[mbufi_m1bis + 0];
mbufi_m1bis += 1;
union u_m1bis_d_t {
uint8_t p;
byte bytebuffer[1];
} u_m1bis_d;
u_m1bis_d.bytebuffer[0] = mbuf[mbufi_m1bis + 0];
mbufi_m1bis += 1;
union u_m1bis_e_t {
uint8_t p;
byte bytebuffer[1];
} u_m1bis_e;
u_m1bis_e.bytebuffer[0] = mbuf[mbufi_m1bis + 0];
mbufi_m1bis += 1;
dispatch_m1bis((mbuf[0] << 8) + mbuf[1] /* instance port*/,
 u_m1bis_a.p /* a */ ,
 u_m1bis_b.p /* b */ ,
 u_m1bis_c.p /* c */ ,
 u_m1bis_d.p /* d */ ,
 u_m1bis_e.p /* e */ );
break;
}
case 7:{
byte mbuf[9 - 2];
while (mbufi < (9 - 2)) mbuf[mbufi++] = fifo_dequeue();
uint8_t mbufi_m2abc = 2;
union u_m2abc_a_t {
uint8_t p;
byte bytebuffer[1];
} u_m2abc_a;
u_m2abc_a.bytebuffer[0] = mbuf[mbufi_m2abc + 0];
mbufi_m2abc += 1;
union u_m2abc_r10_t {
uint8_t p;
byte bytebuffer[1];
} u_m2abc_r10;
u_m2abc_r10.bytebuffer[0] = mbuf[mbufi_m2abc + 0];
mbufi_m2abc += 1;
union u_m2abc_b_t {
uint8_t p;
byte bytebuffer[1];
} u_m2abc_b;
u_m2abc_b.bytebuffer[0] = mbuf[mbufi_m2abc + 0];
mbufi_m2abc += 1;
union u_m2abc_r9_t {
uint8_t p;
byte bytebuffer[1];
} u_m2abc_r9;
u_m2abc_r9.bytebuffer[0] = mbuf[mbufi_m2abc + 0];
mbufi_m2abc += 1;
union u_m2abc_c_t {
uint8_t p;
byte bytebuffer[1];
} u_m2abc_c;
u_m2abc_c.bytebuffer[0] = mbuf[mbufi_m2abc + 0];
mbufi_m2abc += 1;
dispatch_m2abc((mbuf[0] << 8) + mbuf[1] /* instance port*/,
 u_m2abc_a.p /* a */ ,
 u_m2abc_r10.p /* r10 */ ,
 u_m2abc_b.p /* b */ ,
 u_m2abc_r9.p /* r9 */ ,
 u_m2abc_c.p /* c */ );
break;
}
case 1:{
byte mbuf[9 - 2];
while (mbufi < (9 - 2)) mbuf[mbufi++] = fifo_dequeue();
uint8_t mbufi_m3 = 2;
union u_m3_r4_t {
uint8_t p;
byte bytebuffer[1];
} u_m3_r4;
u_m3_r4.bytebuffer[0] = mbuf[mbufi_m3 + 0];
mbufi_m3 += 1;
union u_m3_r5_t {
uint8_t p;
byte bytebuffer[1];
} u_m3_r5;
u_m3_r5.bytebuffer[0] = mbuf[mbufi_m3 + 0];
mbufi_m3 += 1;
union u_m3_r2_t {
uint8_t p;
byte bytebuffer[1];
} u_m3_r2;
u_m3_r2.bytebuffer[0] = mbuf[mbufi_m3 + 0];
mbufi_m3 += 1;
union u_m3_r3_t {
uint8_t p;
byte bytebuffer[1];
} u_m3_r3;
u_m3_r3.bytebuffer[0] = mbuf[mbufi_m3 + 0];
mbufi_m3 += 1;
union u_m3_a_t {
uint8_t p;
byte bytebuffer[1];
} u_m3_a;
u_m3_a.bytebuffer[0] = mbuf[mbufi_m3 + 0];
mbufi_m3 += 1;
dispatch_m3((mbuf[0] << 8) + mbuf[1] /* instance port*/,
 u_m3_r4.p /* r4 */ ,
 u_m3_r5.p /* r5 */ ,
 u_m3_r2.p /* r2 */ ,
 u_m3_r3.p /* r3 */ ,
 u_m3_a.p /* a */ );
break;
}
case 10:{
byte mbuf[9 - 2];
while (mbufi < (9 - 2)) mbuf[mbufi++] = fifo_dequeue();
uint8_t mbufi_m1abcd = 2;
union u_m1abcd_a_t {
uint8_t p;
byte bytebuffer[1];
} u_m1abcd_a;
u_m1abcd_a.bytebuffer[0] = mbuf[mbufi_m1abcd + 0];
mbufi_m1abcd += 1;
union u_m1abcd_b_t {
uint8_t p;
byte bytebuffer[1];
} u_m1abcd_b;
u_m1abcd_b.bytebuffer[0] = mbuf[mbufi_m1abcd + 0];
mbufi_m1abcd += 1;
union u_m1abcd_c_t {
uint8_t p;
byte bytebuffer[1];
} u_m1abcd_c;
u_m1abcd_c.bytebuffer[0] = mbuf[mbufi_m1abcd + 0];
mbufi_m1abcd += 1;
union u_m1abcd_r16_t {
uint8_t p;
byte bytebuffer[1];
} u_m1abcd_r16;
u_m1abcd_r16.bytebuffer[0] = mbuf[mbufi_m1abcd + 0];
mbufi_m1abcd += 1;
union u_m1abcd_d_t {
uint8_t p;
byte bytebuffer[1];
} u_m1abcd_d;
u_m1abcd_d.bytebuffer[0] = mbuf[mbufi_m1abcd + 0];
mbufi_m1abcd += 1;
dispatch_m1abcd((mbuf[0] << 8) + mbuf[1] /* instance port*/,
 u_m1abcd_a.p /* a */ ,
 u_m1abcd_b.p /* b */ ,
 u_m1abcd_c.p /* c */ ,
 u_m1abcd_r16.p /* r16 */ ,
 u_m1abcd_d.p /* d */ );
break;
}
case 3:{
byte mbuf[9 - 2];
while (mbufi < (9 - 2)) mbuf[mbufi++] = fifo_dequeue();
uint8_t mbufi_m2bis = 2;
union u_m2bis_r0_t {
uint8_t p;
byte bytebuffer[1];
} u_m2bis_r0;
u_m2bis_r0.bytebuffer[0] = mbuf[mbufi_m2bis + 0];
mbufi_m2bis += 1;
union u_m2bis_r1_t {
uint8_t p;
byte bytebuffer[1];
} u_m2bis_r1;
u_m2bis_r1.bytebuffer[0] = mbuf[mbufi_m2bis + 0];
mbufi_m2bis += 1;
union u_m2bis_a_t {
uint8_t p;
byte bytebuffer[1];
} u_m2bis_a;
u_m2bis_a.bytebuffer[0] = mbuf[mbufi_m2bis + 0];
mbufi_m2bis += 1;
union u_m2bis_b_t {
uint8_t p;
byte bytebuffer[1];
} u_m2bis_b;
u_m2bis_b.bytebuffer[0] = mbuf[mbufi_m2bis + 0];
mbufi_m2bis += 1;
union u_m2bis_c_t {
uint8_t p;
byte bytebuffer[1];
} u_m2bis_c;
u_m2bis_c.bytebuffer[0] = mbuf[mbufi_m2bis + 0];
mbufi_m2bis += 1;
dispatch_m2bis((mbuf[0] << 8) + mbuf[1] /* instance port*/,
 u_m2bis_r0.p /* r0 */ ,
 u_m2bis_r1.p /* r1 */ ,
 u_m2bis_a.p /* a */ ,
 u_m2bis_b.p /* b */ ,
 u_m2bis_c.p /* c */ );
break;
}
case 13:{
byte mbuf[9 - 2];
while (mbufi < (9 - 2)) mbuf[mbufi++] = fifo_dequeue();
uint8_t mbufi_m2bisc = 2;
union u_m2bisc_r24_t {
uint8_t p;
byte bytebuffer[1];
} u_m2bisc_r24;
u_m2bisc_r24.bytebuffer[0] = mbuf[mbufi_m2bisc + 0];
mbufi_m2bisc += 1;
union u_m2bisc_r22_t {
uint8_t p;
byte bytebuffer[1];
} u_m2bisc_r22;
u_m2bisc_r22.bytebuffer[0] = mbuf[mbufi_m2bisc + 0];
mbufi_m2bisc += 1;
union u_m2bisc_r23_t {
uint8_t p;
byte bytebuffer[1];
} u_m2bisc_r23;
u_m2bisc_r23.bytebuffer[0] = mbuf[mbufi_m2bisc + 0];
mbufi_m2bisc += 1;
union u_m2bisc_r25_t {
uint8_t p;
byte bytebuffer[1];
} u_m2bisc_r25;
u_m2bisc_r25.bytebuffer[0] = mbuf[mbufi_m2bisc + 0];
mbufi_m2bisc += 1;
union u_m2bisc_c_t {
uint8_t p;
byte bytebuffer[1];
} u_m2bisc_c;
u_m2bisc_c.bytebuffer[0] = mbuf[mbufi_m2bisc + 0];
mbufi_m2bisc += 1;
dispatch_m2bisc((mbuf[0] << 8) + mbuf[1] /* instance port*/,
 u_m2bisc_r24.p /* r24 */ ,
 u_m2bisc_r22.p /* r22 */ ,
 u_m2bisc_r23.p /* r23 */ ,
 u_m2bisc_r25.p /* r25 */ ,
 u_m2bisc_c.p /* c */ );
break;
}
case 9:{
byte mbuf[9 - 2];
while (mbufi < (9 - 2)) mbuf[mbufi++] = fifo_dequeue();
uint8_t mbufi_m3r4r5r2r3a = 2;
union u_m3r4r5r2r3a_r4_t {
uint8_t p;
byte bytebuffer[1];
} u_m3r4r5r2r3a_r4;
u_m3r4r5r2r3a_r4.bytebuffer[0] = mbuf[mbufi_m3r4r5r2r3a + 0];
mbufi_m3r4r5r2r3a += 1;
union u_m3r4r5r2r3a_r5_t {
uint8_t p;
byte bytebuffer[1];
} u_m3r4r5r2r3a_r5;
u_m3r4r5r2r3a_r5.bytebuffer[0] = mbuf[mbufi_m3r4r5r2r3a + 0];
mbufi_m3r4r5r2r3a += 1;
union u_m3r4r5r2r3a_r2_t {
uint8_t p;
byte bytebuffer[1];
} u_m3r4r5r2r3a_r2;
u_m3r4r5r2r3a_r2.bytebuffer[0] = mbuf[mbufi_m3r4r5r2r3a + 0];
mbufi_m3r4r5r2r3a += 1;
union u_m3r4r5r2r3a_r3_t {
uint8_t p;
byte bytebuffer[1];
} u_m3r4r5r2r3a_r3;
u_m3r4r5r2r3a_r3.bytebuffer[0] = mbuf[mbufi_m3r4r5r2r3a + 0];
mbufi_m3r4r5r2r3a += 1;
union u_m3r4r5r2r3a_a_t {
uint8_t p;
byte bytebuffer[1];
} u_m3r4r5r2r3a_a;
u_m3r4r5r2r3a_a.bytebuffer[0] = mbuf[mbufi_m3r4r5r2r3a + 0];
mbufi_m3r4r5r2r3a += 1;
dispatch_m3r4r5r2r3a((mbuf[0] << 8) + mbuf[1] /* instance port*/,
 u_m3r4r5r2r3a_r4.p /* r4 */ ,
 u_m3r4r5r2r3a_r5.p /* r5 */ ,
 u_m3r4r5r2r3a_r2.p /* r2 */ ,
 u_m3r4r5r2r3a_r3.p /* r3 */ ,
 u_m3r4r5r2r3a_a.p /* a */ );
break;
}
case 14:{
byte mbuf[9 - 2];
while (mbufi < (9 - 2)) mbuf[mbufi++] = fifo_dequeue();
uint8_t mbufi_m3bis_ = 2;
union u_m3bis__r29_t {
uint8_t p;
byte bytebuffer[1];
} u_m3bis__r29;
u_m3bis__r29.bytebuffer[0] = mbuf[mbufi_m3bis_ + 0];
mbufi_m3bis_ += 1;
union u_m3bis__r28_t {
uint8_t p;
byte bytebuffer[1];
} u_m3bis__r28;
u_m3bis__r28.bytebuffer[0] = mbuf[mbufi_m3bis_ + 0];
mbufi_m3bis_ += 1;
union u_m3bis__r30_t {
uint8_t p;
byte bytebuffer[1];
} u_m3bis__r30;
u_m3bis__r30.bytebuffer[0] = mbuf[mbufi_m3bis_ + 0];
mbufi_m3bis_ += 1;
union u_m3bis__r27_t {
uint8_t p;
byte bytebuffer[1];
} u_m3bis__r27;
u_m3bis__r27.bytebuffer[0] = mbuf[mbufi_m3bis_ + 0];
mbufi_m3bis_ += 1;
union u_m3bis__r26_t {
uint8_t p;
byte bytebuffer[1];
} u_m3bis__r26;
u_m3bis__r26.bytebuffer[0] = mbuf[mbufi_m3bis_ + 0];
mbufi_m3bis_ += 1;
dispatch_m3bis_((mbuf[0] << 8) + mbuf[1] /* instance port*/,
 u_m3bis__r29.p /* r29 */ ,
 u_m3bis__r28.p /* r28 */ ,
 u_m3bis__r30.p /* r30 */ ,
 u_m3bis__r27.p /* r27 */ ,
 u_m3bis__r26.p /* r26 */ );
break;
}
case 0:{
byte mbuf[9 - 2];
while (mbufi < (9 - 2)) mbuf[mbufi++] = fifo_dequeue();
uint8_t mbufi_m2 = 2;
union u_m2_r0_t {
uint8_t p;
byte bytebuffer[1];
} u_m2_r0;
u_m2_r0.bytebuffer[0] = mbuf[mbufi_m2 + 0];
mbufi_m2 += 1;
union u_m2_r1_t {
uint8_t p;
byte bytebuffer[1];
} u_m2_r1;
u_m2_r1.bytebuffer[0] = mbuf[mbufi_m2 + 0];
mbufi_m2 += 1;
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
 u_m2_r0.p /* r0 */ ,
 u_m2_r1.p /* r1 */ ,
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
case 16:{
byte mbuf[9 - 2];
while (mbufi < (9 - 2)) mbuf[mbufi++] = fifo_dequeue();
uint8_t mbufi_m1bisabcd = 2;
union u_m1bisabcd_a_t {
uint8_t p;
byte bytebuffer[1];
} u_m1bisabcd_a;
u_m1bisabcd_a.bytebuffer[0] = mbuf[mbufi_m1bisabcd + 0];
mbufi_m1bisabcd += 1;
union u_m1bisabcd_r31_t {
uint8_t p;
byte bytebuffer[1];
} u_m1bisabcd_r31;
u_m1bisabcd_r31.bytebuffer[0] = mbuf[mbufi_m1bisabcd + 0];
mbufi_m1bisabcd += 1;
union u_m1bisabcd_b_t {
uint8_t p;
byte bytebuffer[1];
} u_m1bisabcd_b;
u_m1bisabcd_b.bytebuffer[0] = mbuf[mbufi_m1bisabcd + 0];
mbufi_m1bisabcd += 1;
union u_m1bisabcd_c_t {
uint8_t p;
byte bytebuffer[1];
} u_m1bisabcd_c;
u_m1bisabcd_c.bytebuffer[0] = mbuf[mbufi_m1bisabcd + 0];
mbufi_m1bisabcd += 1;
union u_m1bisabcd_d_t {
uint8_t p;
byte bytebuffer[1];
} u_m1bisabcd_d;
u_m1bisabcd_d.bytebuffer[0] = mbuf[mbufi_m1bisabcd + 0];
mbufi_m1bisabcd += 1;
dispatch_m1bisabcd((mbuf[0] << 8) + mbuf[1] /* instance port*/,
 u_m1bisabcd_a.p /* a */ ,
 u_m1bisabcd_r31.p /* r31 */ ,
 u_m1bisabcd_b.p /* b */ ,
 u_m1bisabcd_c.p /* c */ ,
 u_m1bisabcd_d.p /* d */ );
break;
}
case 15:{
byte mbuf[9 - 2];
while (mbufi < (9 - 2)) mbuf[mbufi++] = fifo_dequeue();
uint8_t mbufi_m3bisr4r5r2r3a = 2;
union u_m3bisr4r5r2r3a_r4_t {
uint8_t p;
byte bytebuffer[1];
} u_m3bisr4r5r2r3a_r4;
u_m3bisr4r5r2r3a_r4.bytebuffer[0] = mbuf[mbufi_m3bisr4r5r2r3a + 0];
mbufi_m3bisr4r5r2r3a += 1;
union u_m3bisr4r5r2r3a_r5_t {
uint8_t p;
byte bytebuffer[1];
} u_m3bisr4r5r2r3a_r5;
u_m3bisr4r5r2r3a_r5.bytebuffer[0] = mbuf[mbufi_m3bisr4r5r2r3a + 0];
mbufi_m3bisr4r5r2r3a += 1;
union u_m3bisr4r5r2r3a_r2_t {
uint8_t p;
byte bytebuffer[1];
} u_m3bisr4r5r2r3a_r2;
u_m3bisr4r5r2r3a_r2.bytebuffer[0] = mbuf[mbufi_m3bisr4r5r2r3a + 0];
mbufi_m3bisr4r5r2r3a += 1;
union u_m3bisr4r5r2r3a_r3_t {
uint8_t p;
byte bytebuffer[1];
} u_m3bisr4r5r2r3a_r3;
u_m3bisr4r5r2r3a_r3.bytebuffer[0] = mbuf[mbufi_m3bisr4r5r2r3a + 0];
mbufi_m3bisr4r5r2r3a += 1;
union u_m3bisr4r5r2r3a_a_t {
uint8_t p;
byte bytebuffer[1];
} u_m3bisr4r5r2r3a_a;
u_m3bisr4r5r2r3a_a.bytebuffer[0] = mbuf[mbufi_m3bisr4r5r2r3a + 0];
mbufi_m3bisr4r5r2r3a += 1;
dispatch_m3bisr4r5r2r3a((mbuf[0] << 8) + mbuf[1] /* instance port*/,
 u_m3bisr4r5r2r3a_r4.p /* r4 */ ,
 u_m3bisr4r5r2r3a_r5.p /* r5 */ ,
 u_m3bisr4r5r2r3a_r2.p /* r2 */ ,
 u_m3bisr4r5r2r3a_r3.p /* r3 */ ,
 u_m3bisr4r5r2r3a_a.p /* a */ );
break;
}
case 12:{
byte mbuf[9 - 2];
while (mbufi < (9 - 2)) mbuf[mbufi++] = fifo_dequeue();
uint8_t mbufi_m2bisr0r1ab = 2;
union u_m2bisr0r1ab_r21_t {
uint8_t p;
byte bytebuffer[1];
} u_m2bisr0r1ab_r21;
u_m2bisr0r1ab_r21.bytebuffer[0] = mbuf[mbufi_m2bisr0r1ab + 0];
mbufi_m2bisr0r1ab += 1;
union u_m2bisr0r1ab_r0_t {
uint8_t p;
byte bytebuffer[1];
} u_m2bisr0r1ab_r0;
u_m2bisr0r1ab_r0.bytebuffer[0] = mbuf[mbufi_m2bisr0r1ab + 0];
mbufi_m2bisr0r1ab += 1;
union u_m2bisr0r1ab_r1_t {
uint8_t p;
byte bytebuffer[1];
} u_m2bisr0r1ab_r1;
u_m2bisr0r1ab_r1.bytebuffer[0] = mbuf[mbufi_m2bisr0r1ab + 0];
mbufi_m2bisr0r1ab += 1;
union u_m2bisr0r1ab_a_t {
uint8_t p;
byte bytebuffer[1];
} u_m2bisr0r1ab_a;
u_m2bisr0r1ab_a.bytebuffer[0] = mbuf[mbufi_m2bisr0r1ab + 0];
mbufi_m2bisr0r1ab += 1;
union u_m2bisr0r1ab_b_t {
uint8_t p;
byte bytebuffer[1];
} u_m2bisr0r1ab_b;
u_m2bisr0r1ab_b.bytebuffer[0] = mbuf[mbufi_m2bisr0r1ab + 0];
mbufi_m2bisr0r1ab += 1;
dispatch_m2bisr0r1ab((mbuf[0] << 8) + mbuf[1] /* instance port*/,
 u_m2bisr0r1ab_r21.p /* r21 */ ,
 u_m2bisr0r1ab_r0.p /* r0 */ ,
 u_m2bisr0r1ab_r1.p /* r1 */ ,
 u_m2bisr0r1ab_a.p /* a */ ,
 u_m2bisr0r1ab_b.p /* b */ );
break;
}
}
return 1;
}


//external Message enqueue

void initialize_configuration_test() {
// Initialize connectors
register_App_send_app_m3__listener(&enqueue_App_send_app_m3_);
register_App_send_app_m3r4r5r2r3a_listener(&enqueue_App_send_app_m3r4r5r2r3a);
register_App_send_app_m3bis__listener(&enqueue_App_send_app_m3bis_);
register_App_send_app_m3bisr4r5r2r3a_listener(&enqueue_App_send_app_m3bisr4r5r2r3a);
register_App_send_diversified_m2_listener(&enqueue_App_send_diversified_m2);
register_App_send_diversified_m1abcd_listener(&enqueue_App_send_diversified_m1abcd);
register_App_send_diversified_m1e_listener(&enqueue_App_send_diversified_m1e);
register_App_send_diversified_m1_listener(&enqueue_App_send_diversified_m1);
register_App_send_diversified_m2bisr0r1ab_listener(&enqueue_App_send_diversified_m2bisr0r1ab);
register_App_send_diversified_m2bisc_listener(&enqueue_App_send_diversified_m2bisc);
register_App_send_diversified_m2bis_listener(&enqueue_App_send_diversified_m2bis);
register_App_send_diversified_m1bisabcd_listener(&enqueue_App_send_diversified_m1bisabcd);
register_App_send_diversified_m1bise_listener(&enqueue_App_send_diversified_m1bise);
register_App_send_diversified_m1bis_listener(&enqueue_App_send_diversified_m1bis);
register_Client_send_app_m2r0r1_listener(&enqueue_Client_send_app_m2r0r1);
register_Client_send_app_m2abc_listener(&enqueue_Client_send_app_m2abc);
register_Client_send_app_m1abcd_listener(&enqueue_Client_send_app_m1abcd);
register_Client_send_app_m1e_listener(&enqueue_Client_send_app_m1e);
register_Client_send_app_m2bisr0r1ab_listener(&enqueue_Client_send_app_m2bisr0r1ab);
register_Client_send_app_m2bisc_listener(&enqueue_Client_send_app_m2bisc);
register_Client_send_app_m1bisabcd_listener(&enqueue_Client_send_app_m1bisabcd);
register_Client_send_app_m1bise_listener(&enqueue_Client_send_app_m1bise);
register_Client_send_diversified_m3_listener(&enqueue_Client_send_diversified_m3);
register_Client_send_diversified_m3bis__listener(&enqueue_Client_send_diversified_m3bis_);
register_Client_send_diversified_m3bisr4r5r2r3a_listener(&enqueue_Client_send_diversified_m3bisr4r5r2r3a);
register_Client_send_diversified_m3bis_listener(&enqueue_Client_send_diversified_m3bis);


// Network Initialization
// End Network Initialization

// Init the ID, state variables and properties for instance app
app_var.active = true;
app_var.id_app = add_instance( (void*) &app_var);
app_var.id_diversified = add_instance( (void*) &app_var);
app_var.App_State = APP_NULL_WAITFORM1_STATE;
app_var.App_null_generate_m2_from_m2r0r1_and_m2abc_State = APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_STATE;
app_var.App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_State = APP_NULL_GENERATE_M2_FROM_M2R0R1_AND_M2ABC_INIT_S1_STATE;
app_var.App_null_generate_m1_from_m1abcd_and_m1e_State = APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_STATE;
app_var.App_null_generate_m1_from_m1abcd_and_m1e_INIT_State = APP_NULL_GENERATE_M1_FROM_M1ABCD_AND_M1E_INIT_S1_STATE;
app_var.App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_State = APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_STATE;
app_var.App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_State = APP_NULL_GENERATE_M2BIS_FROM_M2BISR0R1AB_AND_M2BISC_INIT_S1_STATE;
app_var.App_null_generate_m1bis_from_m1bisabcd_and_m1bise_State = APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_STATE;
app_var.App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_State = APP_NULL_GENERATE_M1BIS_FROM_M1BISABCD_AND_M1BISE_INIT_S1_STATE;

App_OnEntry(APP_STATE, &app_var);
// Init the ID, state variables and properties for instance client1
client1_var.active = true;
client1_var.id_app = add_instance( (void*) &client1_var);
client1_var.id_diversified = add_instance( (void*) &client1_var);
client1_var.Client_State = CLIENT_NULL_RUN_STATE;
client1_var.Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_State = CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_STATE;
client1_var.Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_State = CLIENT_NULL_GENERATE_M3_FROM_M3__AND_M3R4R5R2R3A_INIT_S1_STATE;
client1_var.Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_State = CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_STATE;
client1_var.Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_State = CLIENT_NULL_GENERATE_M3BIS_FROM_M3BIS__AND_M3BISR4R5R2R3A_INIT_S1_STATE;
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
emptyEventConsumed += App_handle_empty_event(&app_var);
emptyEventConsumed += Client_handle_empty_event(&client1_var);
}

    processMessageQueue();
}
