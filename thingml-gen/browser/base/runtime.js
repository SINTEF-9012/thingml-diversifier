'use strict';

var RunThingMLConfiguration = function() {
	/*$REQUIRE_PLUGINS$*/
	
	var client1__c = null;
	var client1__e = null;
	var client1__b = null;
	var client1_counter = 0;
	var client1_start = null;
	var client1__d = null;
	var client1__a = null;
	const client1 = new Client('client1', null, client1__c, client1__e, client1__b, client1_counter, client1_start, client1__d, client1__a, false);
	var app_id = null;
	const app = new App('app', null, app_id, false);
	
	/*Connecting internal ports...*/
	/*Connecting ports...*/
	app.bus.on('app?m3', (a) => client1.receivem3Onapp(a));
	client1.bus.on('app?m1', (a, b, c, d, e) => app.receivem1Onapp(a, b, c, d, e));
	client1.bus.on('app?m2', (a, b, c) => app.receivem2Onapp(a, b, c));
	
	app._init();
	client1._init();
	
	/*$PLUGINS_END$*/
}

window.addEventListener('DOMContentLoaded', function(){
	RunThingMLConfiguration();
});

