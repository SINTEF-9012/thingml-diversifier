'use strict';

const App = require('./App');
const Client = require('./Client');
/*$REQUIRE_PLUGINS$*/


var app_id = null;
/*$CONFIGURATION app$*/
const app = new App('app', null, app_id, false);
var client1_counter = 0;
var client1_start = null;
var client1__b = null;
var client1__a = null;
var client1__d = null;
var client1__e = null;
var client1__c = null;
/*$CONFIGURATION client1$*/
const client1 = new Client('client1', null, client1_counter, client1_start, client1__b, client1__a, client1__d, client1__e, client1__c, false);
/*$PLUGINS$*/
/*Connecting internal ports...*/
app.bus.on('diversified?m2', (r0, r1, a, b, c) => app.receivem2Ondiversified(r0, r1, a, b, c));
app.bus.on('diversified?m1abcd', (a, b, c, r16, d) => app.receivem1abcdOndiversified(a, b, c, r16, d));
app.bus.on('diversified?m1e', (r18, r19, r20, r17, e) => app.receivem1eOndiversified(r18, r19, r20, r17, e));
app.bus.on('diversified?m1', (a, b, c, d, e) => app.receivem1Ondiversified(a, b, c, d, e));
app.bus.on('diversified?m2bisr0r1ab', (r21, r0, r1, a, b) => app.receivem2bisr0r1abOndiversified(r21, r0, r1, a, b));
app.bus.on('diversified?m2bisc', (r24, r22, r23, r25, c) => app.receivem2biscOndiversified(r24, r22, r23, r25, c));
app.bus.on('diversified?m2bis', (r0, r1, a, b, c) => app.receivem2bisOndiversified(r0, r1, a, b, c));
app.bus.on('diversified?m1bisabcd', (a, r31, b, c, d) => app.receivem1bisabcdOndiversified(a, r31, b, c, d));
app.bus.on('diversified?m1bise', (r32, r35, r33, r34, e) => app.receivem1biseOndiversified(r32, r35, r33, r34, e));
app.bus.on('diversified?m1bis', (a, b, c, d, e) => app.receivem1bisOndiversified(a, b, c, d, e));
client1.bus.on('diversified?m3', (r4, r5, r2, r3, a) => client1.receivem3Ondiversified(r4, r5, r2, r3, a));
client1.bus.on('diversified?m3bis_', (r29, r28, r30, r27, r26) => client1.receivem3bis_Ondiversified(r29, r28, r30, r27, r26));
client1.bus.on('diversified?m3bisr4r5r2r3a', (r4, r5, r2, r3, a) => client1.receivem3bisr4r5r2r3aOndiversified(r4, r5, r2, r3, a));
client1.bus.on('diversified?m3bis', (r4, r5, r2, r3, a) => client1.receivem3bisOndiversified(r4, r5, r2, r3, a));
/*Connecting ports...*/
app.bus.on('app?m3_', (r13, r15, r12, r14, r11) => client1.receivem3_Onapp(r13, r15, r12, r14, r11));
app.bus.on('app?m3r4r5r2r3a', (r4, r5, r2, r3, a) => client1.receivem3r4r5r2r3aOnapp(r4, r5, r2, r3, a));
app.bus.on('app?m3bis_', (r29, r28, r30, r27, r26) => client1.receivem3bis_Onapp(r29, r28, r30, r27, r26));
app.bus.on('app?m3bisr4r5r2r3a', (r4, r5, r2, r3, a) => client1.receivem3bisr4r5r2r3aOnapp(r4, r5, r2, r3, a));
client1.bus.on('app?m2r0r1', (r6, r0, r7, r8, r1) => app.receivem2r0r1Onapp(r6, r0, r7, r8, r1));
client1.bus.on('app?m2abc', (a, r10, b, r9, c) => app.receivem2abcOnapp(a, r10, b, r9, c));
client1.bus.on('app?m1abcd', (a, b, c, r16, d) => app.receivem1abcdOnapp(a, b, c, r16, d));
client1.bus.on('app?m1e', (r18, r19, r20, r17, e) => app.receivem1eOnapp(r18, r19, r20, r17, e));
client1.bus.on('app?m2bisr0r1ab', (r21, r0, r1, a, b) => app.receivem2bisr0r1abOnapp(r21, r0, r1, a, b));
client1.bus.on('app?m2bisc', (r24, r22, r23, r25, c) => app.receivem2biscOnapp(r24, r22, r23, r25, c));
client1.bus.on('app?m1bisabcd', (a, r31, b, c, d) => app.receivem1bisabcdOnapp(a, r31, b, c, d));
client1.bus.on('app?m1bise', (r32, r35, r33, r34, e) => app.receivem1biseOnapp(r32, r35, r33, r34, e));
/*$PLUGINS_CONNECTORS$*/
app._init();
client1._init();
/*$PLUGINS_END$*/

/*terminate all things on SIGINT (e.g. CTRL+C)*/
process.on('SIGINT', function() {
	client1._stop();
	client1._delete();
	app._stop();
	app._delete();
	/*$STOP_PLUGINS$*/
	setTimeout(() => {
		process.exit();
	}, 1000);
});
