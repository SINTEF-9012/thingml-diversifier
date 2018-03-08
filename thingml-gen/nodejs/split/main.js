'use strict';

const Client = require('./Client');
const App = require('./App');
/*$REQUIRE_PLUGINS$*/


var client1_counter = 0;
var client1__b = null;
var client1__e = null;
var client1__c = null;
var client1__d = null;
var client1__a = null;
/*$CONFIGURATION client1$*/
const client1 = new Client('client1', null, client1_counter, client1__b, client1__e, client1__c, client1__d, client1__a, false);
var app_id = null;
/*$CONFIGURATION app$*/
const app = new App('app', null, app_id, false);
/*$PLUGINS$*/
/*Connecting internal ports...*/
client1.bus.on('diversified?m3', (a) => client1.receivem3Ondiversified(a));
app.bus.on('diversified?m1', (a, b, c, d, e) => app.receivem1Ondiversified(a, b, c, d, e));
app.bus.on('diversified?m2a', (a) => app.receivem2aOndiversified(a));
app.bus.on('diversified?m2bc', (b, c) => app.receivem2bcOndiversified(b, c));
app.bus.on('diversified?m2', (a, b, c) => app.receivem2Ondiversified(a, b, c));
/*Connecting ports...*/
app.bus.on('app?m3_', () => client1.receivem3_Onapp());
app.bus.on('app?m3a', (a) => client1.receivem3aOnapp(a));
client1.bus.on('app?m1a', (a) => app.receivem1aOnapp(a));
client1.bus.on('app?m1bcde', (b, c, d, e) => app.receivem1bcdeOnapp(b, c, d, e));
client1.bus.on('app?m2a', (a) => app.receivem2aOnapp(a));
client1.bus.on('app?m2bc', (b, c) => app.receivem2bcOnapp(b, c));
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
