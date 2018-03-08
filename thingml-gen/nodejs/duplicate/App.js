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
		if(this.rnd() < 129) {
		process.stdout.write(''+'2');
		process.stdout.write(''+', ');
		process.stdout.write(''+this.App_id_var);
		process.stdout.write(''+', ');
		process.stderr.write('\n');
		setImmediate(() => this.bus.emit('app?m3', this.App_id_var));
		
		} else {
		process.stdout.write(''+'5');
		process.stdout.write(''+', ');
		process.stdout.write(''+this.App_id_var);
		process.stdout.write(''+', ');
		process.stderr.write('\n');
		setImmediate(() => this.bus.emit('app?m3bis', this.App_id_var));
		
		}
	});
	_initial_App.to(App_null_WaitForM1);
	App_null_SendAck.to(App_null_WaitForM1);
	App_null_WaitForM1.to(App_null_WaitForM2).when((m1bis) => {
		return m1bis._port === 'app' && m1bis._msg === 'm1bis';
	}).effect((m1bis) => {
		this.App_id_var = m1bis.a;
		this.bus.emit('id=', this.App_id_var);
		process.stdout.write(''+'#APP: Ooh, I needed that app?m1(');
		process.stdout.write(''+m1bis.a);
		process.stdout.write(''+', ');
		process.stdout.write(''+m1bis.b);
		process.stdout.write(''+', ');
		process.stdout.write(''+m1bis.c);
		process.stdout.write(''+', ');
		process.stdout.write(''+m1bis.d);
		process.stdout.write(''+', ');
		process.stdout.write(''+m1bis.e);
		process.stdout.write(''+')!');
		process.stderr.write('\n');
	});
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
	App_null_WaitForM2.to(App_null_SendAck).when((m2bis) => {
		return m2bis._port === 'app' && m2bis._msg === 'm2bis';
	}).effect((m2bis) => {
		process.stdout.write(''+'#APP: Ooh, I needed that app?m2(');
		process.stdout.write(''+m2bis.a);
		process.stdout.write(''+', ');
		process.stdout.write(''+m2bis.b);
		process.stdout.write(''+', ');
		process.stdout.write(''+m2bis.c);
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

App.prototype.receivem1Onapp = function(a, b, c, d, e) {
	this._receive({_port:"app", _msg:"m1", a:a, b:b, c:c, d:d, e:e});
}

App.prototype.receivem2Onapp = function(a, b, c) {
	this._receive({_port:"app", _msg:"m2", a:a, b:b, c:c});
}

App.prototype.receivem2bisOnapp = function(a, b, c) {
	this._receive({_port:"app", _msg:"m2bis", a:a, b:b, c:c});
}

App.prototype.receivem1bisOnapp = function(a, b, c, d, e) {
	this._receive({_port:"app", _msg:"m1bis", a:a, b:b, c:c, d:d, e:e});
}

App.prototype.toString = function() {
	let result = 'instance ' + this.name + ':' + this.constructor.name + '\n';
	result += '\n\tid = ' + this.App_id_var;
	result += '';
	return result;
}

module.exports = App;
