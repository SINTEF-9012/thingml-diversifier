'use strict';

const App = require('./App');
const Client = require('./Client');
/*$REQUIRE_PLUGINS$*/


var app_id = null;
/*$CONFIGURATION app$*/
const app = new App('app', null, app_id, false);
var client1_start = null;
var client1__d = null;
var client1__b = null;
var client1__c = null;
var client1__a = null;
var client1_counter = 0;
var client1__e = null;
/*$CONFIGURATION client1$*/
const client1 = new Client('client1', null, client1_start, client1__d, client1__b, client1__c, client1__a, client1_counter, client1__e, false);
/*$PLUGINS$*/
/*Connecting internal ports...*/
/*Connecting ports...*/
app.bus.on('app?m3', (a) => client1.receivem3Onapp(a));
client1.bus.on('app?m1', (a, b, c, d, e) => app.receivem1Onapp(a, b, c, d, e));
client1.bus.on('app?m2', (a, b, c) => app.receivem2Onapp(a, b, c));
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
