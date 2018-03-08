'use strict';

const App = require('./App');
const Client = require('./Client');
/*$REQUIRE_PLUGINS$*/


var app_id = null;
/*$CONFIGURATION app$*/
const app = new App('app', null, app_id, false);
var client1__b = null;
var client1__c = null;
var client1__d = null;
var client1__a = null;
var client1__e = null;
var client1_counter = 0;
/*$CONFIGURATION client1$*/
const client1 = new Client('client1', null, client1__b, client1__c, client1__d, client1__a, client1__e, client1_counter, false);
/*$PLUGINS$*/
/*Connecting internal ports...*/
/*Connecting ports...*/
app.bus.on('app?m3', (a, r3, r5, r2, r4) => client1.receivem3Onapp(a, r3, r5, r2, r4));
client1.bus.on('app?m1', (e, d, c, a, b) => app.receivem1Onapp(e, d, c, a, b));
client1.bus.on('app?m2', (c, b, r0, a, r1) => app.receivem2Onapp(c, b, r0, a, r1));
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
