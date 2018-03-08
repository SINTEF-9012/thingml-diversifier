'use strict';

StateJS.internalTransitionsTriggerCompletion = true;


/*
 * Definition for type : App
 */

function App(name, root, App_id_var, debug) {
	this.name = name;
	this.root = (root === null)? this : root;
	this.debug = debug;
	this.ready = false;
	this.bus = (root === null)? new EventEmitter() : this.root.bus;
	
	/*Attributes*/
	this.App_id_var = App_id_var;
	this.debug_App_id_var = App_id_var;
	
	this.build(name);
}

App.prototype.build = function(session) {
	/*State machine (states and regions)*/
	/*Building root component*/
	this._statemachine = new StateJS.StateMachine('default');
	let _initial_App = new StateJS.PseudoState('_initial', this._statemachine, StateJS.PseudoStateKind.Initial);
	let App_null_WaitForM1 = new StateJS.State('WaitForM1', this._statemachine);
	let App_null_WaitForM2 = new StateJS.State('WaitForM2', this._statemachine);
	let App_null_SendAck = new StateJS.State('SendAck', this._statemachine).entry(() => {
		console.log(''+'#APP: Come get some app!m3('+this.App_id_var+')!');
		setTimeout(() => this.bus.emit('app?m3', this.App_id_var), 0);
	});
	_initial_App.to(App_null_WaitForM1);
	App_null_SendAck.to(App_null_WaitForM1);
	App_null_WaitForM1.to(App_null_WaitForM2).when((m1) => {
		return m1._port === 'app' && m1._msg === 'm1';
	}).effect((m1) => {
		this.App_id_var = m1.a;
		this.bus.emit('id=', this.App_id_var);
		console.log(''+'#APP: Ooh, I needed that app?m1('+m1.a+', '+m1.b+', '+m1.c+', '+m1.d+', '+m1.e+')!');
	});
	App_null_WaitForM2.to(App_null_SendAck).when((m2) => {
		return m2._port === 'app' && m2._msg === 'm2';
	}).effect((m2) => {
		console.log(''+'#APP: Ooh, I needed that app?m2('+m2.a+', '+m2.b+', '+m2.c+')!');
	});
}
App.prototype.rnd = function() {
	return Math.floor(Math.random() * Math.floor(256));
}

App.prototype._stop = function() {
	this.root = null;
	this.ready = false;
}

App.prototype._delete = function() {
	this._statemachine = null;
	this._null_instance = null;
	this.bus.removeAllListeners();
}

App.prototype._init = function() {
	this._null_instance = new StateJS.StateMachineInstance("null_instance");
	StateJS.initialise(this._statemachine, this._null_instance);
	this.ready = true;
}

App.prototype._receive = function(msg) {
	/*msg = {_port:myPort, _msg:myMessage, paramN=paramN, ...}*/
	if (this.ready) {
		StateJS.evaluate(this._statemachine, this._null_instance, msg);
	} else {
		setTimeout(()=>this._receive(msg),0);
	}
}

App.prototype.receivem1Onapp = function(a, b, c, d, e) {
	this._receive({_port:"app", _msg:"m1", a:a, b:b, c:c, d:d, e:e});
}

App.prototype.receivem2Onapp = function(a, b, c) {
	this._receive({_port:"app", _msg:"m2", a:a, b:b, c:c});
}

App.prototype.toString = function() {
	let result = 'instance ' + this.name + ':' + this.constructor.name + '\n';
	result += '\n\tid = ' + this.App_id_var;
	result += '';
	return result;
}

