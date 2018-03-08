'use strict';

StateJS.internalTransitionsTriggerCompletion = true;


/*
 * Definition for type : Client
 */

function Client(name, root, Client__c_var, Client__e_var, Client__b_var, Client_counter_var, Client_start_var, Client__d_var, Client__a_var, debug) {
	this.name = name;
	this.root = (root === null)? this : root;
	this.debug = debug;
	this.ready = false;
	this.bus = (root === null)? new EventEmitter() : this.root.bus;
	
	/*Attributes*/
	this.Client__c_var = Client__c_var;
	this.debug_Client__c_var = Client__c_var;
	this.Client__e_var = Client__e_var;
	this.debug_Client__e_var = Client__e_var;
	this.Client__b_var = Client__b_var;
	this.debug_Client__b_var = Client__b_var;
	this.Client_counter_var = Client_counter_var;
	this.debug_Client_counter_var = Client_counter_var;
	this.Client_start_var = Client_start_var;
	this.debug_Client_start_var = Client_start_var;
	this.Client__d_var = Client__d_var;
	this.debug_Client__d_var = Client__d_var;
	this.Client__a_var = Client__a_var;
	this.debug_Client__a_var = Client__a_var;
	
	this.build(name);
}

Client.prototype.build = function(session) {
	/*State machine (states and regions)*/
	/*Building root component*/
	this._statemachine = new StateJS.StateMachine('default').entry(() => {
		this.Client__a_var = this.rnd();
		this.bus.emit('_a=', this.Client__a_var);
		this.Client__b_var = this.rnd();
		this.bus.emit('_b=', this.Client__b_var);
		this.Client_start_var = new Date();
		this.bus.emit('start=', this.Client_start_var);
	});
	let _initial_Client = new StateJS.PseudoState('_initial', this._statemachine, StateJS.PseudoStateKind.Initial);
	let Client_null_RUN = new StateJS.State('RUN', this._statemachine).entry(() => {
		this.Client__c_var = this.rnd() % 100;
		this.bus.emit('_c=', this.Client__c_var);
		this.Client__d_var = this.rnd() % 50;
		this.bus.emit('_d=', this.Client__d_var);
		this.Client__e_var = this.rnd() % 25;
		this.bus.emit('_e=', this.Client__e_var);
		console.log(''+'#CLI: Come get some app!m1('+this.Client__a_var+', '+this.Client__b_var+', '+this.Client__c_var+', '+this.Client__d_var+', '+this.Client__e_var+')!');
		setTimeout(() => this.bus.emit('app?m1', this.Client__a_var, this.Client__b_var, this.Client__c_var, this.Client__d_var, this.Client__e_var), 0);
		console.log(''+'#CLI: Come get some app!m2('+this.Client__a_var+', '+this.Client__b_var+', '+this.Client__c_var+')!');
		setTimeout(() => this.bus.emit('app?m2', this.Client__a_var, this.Client__b_var, this.Client__c_var), 0);
		this.Client_counter_var++;
	});
	let Client_null_STOP = new StateJS.FinalState('STOP', this._statemachine).entry(() => {
		console.log(''+'#CLI: What are you waitin for? Christmas?');
		let duration_var = new Date() - this.Client_start_var;
		console.log(''+'#CLI: took '+duration_var+'ms.');
		setImmediate(()=>this._stop());
	});
	let Client_null_ERROR = new StateJS.FinalState('ERROR', this._statemachine).entry(() => {
		console.log(''+'#CLI: Heh, heh, heh... what a mess!');
		setImmediate(()=>this._stop());
	});
	_initial_Client.to(Client_null_RUN);
	Client_null_RUN.to(Client_null_STOP).when(() => {
		return (this.Client_counter_var === 100);
	});
	Client_null_RUN.to(Client_null_RUN).when((m3) => {
		return m3._port === 'app' && m3._msg === 'm3' && (m3.a === this.Client__a_var);
	}).effect((m3) => {
		console.log(''+'#CLI: Ooh, I needed that app?m3('+m3.a+')!');
		console.log(''+'#CLI: We meet again, Doctor Jones!');
	});
	Client_null_RUN.to(Client_null_ERROR).when((m3) => {
		return m3._port === 'app' && m3._msg === 'm3' && (m3.a != this.Client__a_var);
	}).effect((m3) => {
		console.log(''+'#CLI: Ooh, I needed that app?m3('+m3.a+')!');
		console.log(''+'#CLI: Damn, you re ugly.');
	});
}
Client.prototype.rnd = function() {
	return Math.floor(Math.random() * Math.floor(256));
}

Client.prototype._stop = function() {
	this.root = null;
	this.ready = false;
}

Client.prototype._delete = function() {
	this._statemachine = null;
	this._null_instance = null;
	this.bus.removeAllListeners();
}

Client.prototype._init = function() {
	this._null_instance = new StateJS.StateMachineInstance("null_instance");
	StateJS.initialise(this._statemachine, this._null_instance);
	this.ready = true;
}

Client.prototype._receive = function(msg) {
	/*msg = {_port:myPort, _msg:myMessage, paramN=paramN, ...}*/
	if (this.ready) {
		StateJS.evaluate(this._statemachine, this._null_instance, msg);
	} else {
		setTimeout(()=>this._receive(msg),0);
	}
}

Client.prototype.receivem3Onapp = function(a) {
	this._receive({_port:"app", _msg:"m3", a:a});
}

Client.prototype.toString = function() {
	let result = 'instance ' + this.name + ':' + this.constructor.name + '\n';
	result += '\n\t_c = ' + this.Client__c_var;
	result += '\n\t_e = ' + this.Client__e_var;
	result += '\n\t_b = ' + this.Client__b_var;
	result += '\n\tcounter = ' + this.Client_counter_var;
	result += '\n\tstart = ' + this.Client_start_var;
	result += '\n\t_d = ' + this.Client__d_var;
	result += '\n\t_a = ' + this.Client__a_var;
	result += '';
	return result;
}

