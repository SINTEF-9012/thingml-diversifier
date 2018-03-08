'use strict';

var RunThingMLConfiguration = function() {
	/*$REQUIRE_PLUGINS$*/
	
	var app_id = null;
	const app = new App('app', null, app_id, false);
	var client1__b = null;
	var client1__d = null;
	var client1__a = null;
	var client1_counter = 0;
	var client1__c = null;
	var client1__e = null;
	const client1 = new Client('client1', null, client1__b, client1__d, client1__a, client1_counter, client1__c, client1__e, false);
	
	/*Connecting internal ports...*/
	app.bus.on('diversified?m1', (a, b, c, d, e) => app.receivem1Ondiversified(a, b, c, d, e));
	app.bus.on('diversified?m2a', (a) => app.receivem2aOndiversified(a));
	app.bus.on('diversified?m2bc', (b, c) => app.receivem2bcOndiversified(b, c));
	app.bus.on('diversified?m2', (a, b, c) => app.receivem2Ondiversified(a, b, c));
	client1.bus.on('diversified?m3', (a) => client1.receivem3Ondiversified(a));
	/*Connecting ports...*/
	app.bus.on('app?m3_', () => client1.receivem3_Onapp());
	app.bus.on('app?m3a', (a) => client1.receivem3aOnapp(a));
	client1.bus.on('app?m1a', (a) => app.receivem1aOnapp(a));
	client1.bus.on('app?m1bcde', (b, c, d, e) => app.receivem1bcdeOnapp(b, c, d, e));
	client1.bus.on('app?m2a', (a) => app.receivem2aOnapp(a));
	client1.bus.on('app?m2bc', (b, c) => app.receivem2bcOnapp(b, c));
	
	app._init();
	client1._init();
	
	/*$PLUGINS_END$*/
}

window.addEventListener('DOMContentLoaded', function(){
	RunThingMLConfiguration();
});

