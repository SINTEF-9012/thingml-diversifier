'use strict';

const StateJS = require('state.js');
const EventEmitter = require('events').EventEmitter;

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
		process.stdout.write(''+'#APP: Come get some app!m3(');
		process.stdout.write(''+this.App_id_var);
		process.stdout.write(''+')!');
		process.stderr.write('\n');
		process.stdout.write(''+'0');
		process.stdout.write(''+', ');
		process.stdout.write(''+this.App_id_var);
		process.stdout.write(''+', ');
		process.stdout.write(''+this.rnd());
		process.stdout.write(''+', ');
		process.stdout.write(''+this.rnd());
		process.stdout.write(''+', ');
		process.stdout.write(''+this.rnd());
		process.stdout.write(''+', ');
		process.stdout.write(''+this.rnd());
		process.stdout.write(''+', ');
		process.stderr.write('\n');
		setImmediate(() => this.bus.emit('app?m3', this.App_id_var, this.rnd(), this.rnd(), this.rnd(), this.rnd()));
	});
	_initial_App.to(App_null_WaitForM1);
	App_null_SendAck.to(App_null_WaitForM1);
	App_null_WaitForM1.to(App_null_WaitForM2).when((m1) => {
		return m1._port === 'app' && m1._msg === 'm1';
	}).effect((m1) => {
		this.App_id_var = m1.a;
		this.bus.emit('id=', this.App_id_var);
		process.stdout.write(''+'#APP: Ooh, I needed that app?m1(');
		process.stdout.write(''+m1.a);
		process.stdout.write(''+', ');
		process.stdout.write(''+m1.b);
		process.stdout.write(''+', ');
		process.stdout.write(''+m1.c);
		process.stdout.write(''+', ');
		process.stdout.write(''+m1.d);
		process.stdout.write(''+', ');
		process.stdout.write(''+m1.e);
		process.stdout.write(''+')!');
		process.stderr.write('\n');
	});
	App_null_WaitForM2.to(App_null_SendAck).when((m2) => {
		return m2._port === 'app' && m2._msg === 'm2';
	}).effect((m2) => {
		process.stdout.write(''+'#APP: Ooh, I needed that app?m2(');
		process.stdout.write(''+m2.a);
		process.stdout.write(''+', ');
		process.stdout.write(''+m2.b);
		process.stdout.write(''+', ');
		process.stdout.write(''+m2.c);
		process.stdout.write(''+')!');
		process.stderr.write('\n');
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

App.prototype.receivem1Onapp = function(e, d, c, a, b) {
	this._receive({_port:"app", _msg:"m1", e:e, d:d, c:c, a:a, b:b});
}

App.prototype.receivem2Onapp = function(c, b, r0, a, r1) {
	this._receive({_port:"app", _msg:"m2", c:c, b:b, r0:r0, a:a, r1:r1});
}

App.prototype.toString = function() {
	let result = 'instance ' + this.name + ':' + this.constructor.name + '\n';
	result += '\n\tid = ' + this.App_id_var;
	result += '';
	return result;
}

module.exports = App;