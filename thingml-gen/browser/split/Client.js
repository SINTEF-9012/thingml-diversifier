'use strict';

StateJS.internalTransitionsTriggerCompletion = true;


/*
 * Definition for type : Client
 */

function Client(name, root, Client__b_var, Client__d_var, Client__a_var, Client_counter_var, Client__c_var, Client__e_var, debug) {
	this.name = name;
	this.root = (root === null)? this : root;
	this.debug = debug;
	this.ready = false;
	this.bus = (root === null)? new EventEmitter() : this.root.bus;
	
	/*Attributes*/
	this.Client__b_var = Client__b_var;
	this.debug_Client__b_var = Client__b_var;
	this.Client__d_var = Client__d_var;
	this.debug_Client__d_var = Client__d_var;
	this.Client__a_var = Client__a_var;
	this.debug_Client__a_var = Client__a_var;
	this.Client_counter_var = Client_counter_var;
	this.debug_Client_counter_var = Client_counter_var;
	this.Client_null_generate_m3_from_m3__and_m3a_INIT_a_var;
	this.Client__c_var = Client__c_var;
	this.debug_Client__c_var = Client__c_var;
	this.Client__e_var = Client__e_var;
	this.debug_Client__e_var = Client__e_var;
	
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
	});
	let _initial_Client = new StateJS.PseudoState('_initial', this._statemachine, StateJS.PseudoStateKind.Initial);
	let Client_null_generate_m3_from_m3__and_m3a_reg = new StateJS.Region('generate_m3_from_m3__and_m3a', this._statemachine);
	let _initial_Client_null_generate_m3_from_m3__and_m3a_reg = new StateJS.PseudoState('_initial', Client_null_generate_m3_from_m3__and_m3a_reg, StateJS.PseudoStateKind.Initial);
	let Client_null_generate_m3_from_m3__and_m3a_INIT = new StateJS.State('INIT', Client_null_generate_m3_from_m3__and_m3a_reg);
	let Client_null_generate_m3_from_m3__and_m3a_INIT_S1 = new StateJS.State('S1', Client_null_generate_m3_from_m3__and_m3a_INIT);
	let Client_null_generate_m3_from_m3__and_m3a_INIT_S2 = new StateJS.State('S2', Client_null_generate_m3_from_m3__and_m3a_INIT);
	let Client_null_generate_m3_from_m3__and_m3a_INIT_S3 = new StateJS.State('S3', Client_null_generate_m3_from_m3__and_m3a_INIT);
	let Client_null_generate_m3_from_m3__and_m3a_INIT_S4 = new StateJS.State('S4', Client_null_generate_m3_from_m3__and_m3a_INIT).entry(() => {
		setTimeout(() => this.bus.emit('diversified?m3', this.Client_null_generate_m3_from_m3__and_m3a_INIT_a_var), 0);
	});
	let _initial_Client_null_generate_m3_from_m3__and_m3a_INIT = new StateJS.PseudoState('_initial', Client_null_generate_m3_from_m3__and_m3a_INIT, StateJS.PseudoStateKind.Initial);
	_initial_Client_null_generate_m3_from_m3__and_m3a_INIT.to(Client_null_generate_m3_from_m3__and_m3a_INIT_S1);
	_initial_Client_null_generate_m3_from_m3__and_m3a_reg.to(Client_null_generate_m3_from_m3__and_m3a_INIT);
	let Client_null_RUN = new StateJS.State('RUN', this._statemachine).entry(() => {
		this.Client__c_var = this.rnd() % 100;
		this.bus.emit('_c=', this.Client__c_var);
		this.Client__d_var = this.rnd() % 50;
		this.bus.emit('_d=', this.Client__d_var);
		this.Client__e_var = this.rnd() % 25;
		this.bus.emit('_e=', this.Client__e_var);
		console.log(''+'#CLI: Come get some app!m1('+this.Client__a_var+', '+this.Client__b_var+', '+this.Client__c_var+', '+this.Client__d_var+', '+this.Client__e_var+')!');
		if(this.rnd() < 209) {
		console.log(''+'3'+', '+this.Client__a_var+', ');
		setTimeout(() => this.bus.emit('app?m1a', this.Client__a_var), 0);
		console.log(''+'4'+', '+this.Client__b_var+', '+this.Client__c_var+', '+this.Client__d_var+', '+this.Client__e_var+', ');
		setTimeout(() => this.bus.emit('app?m1bcde', this.Client__b_var, this.Client__c_var, this.Client__d_var, this.Client__e_var), 0);
		
		} else {
		console.log(''+'4'+', '+this.Client__b_var+', '+this.Client__c_var+', '+this.Client__d_var+', '+this.Client__e_var+', ');
		setTimeout(() => this.bus.emit('app?m1bcde', this.Client__b_var, this.Client__c_var, this.Client__d_var, this.Client__e_var), 0);
		console.log(''+'3'+', '+this.Client__a_var+', ');
		setTimeout(() => this.bus.emit('app?m1a', this.Client__a_var), 0);
		
		}
		console.log(''+'#CLI: Come get some app!m2('+this.Client__a_var+', '+this.Client__b_var+', '+this.Client__c_var+')!');
		if(this.rnd() < 112) {
		console.log(''+'5'+', '+this.Client__a_var+', '+this.Client__b_var+', ');
		setTimeout(() => this.bus.emit('app?m2a', this.Client__b_var), 0);
		console.log(''+'6'+', '+this.Client__c_var+', ');
		setTimeout(() => this.bus.emit('app?m2bc', this.Client__b_var, this.Client__c_var), 0);
		
		} else {
		console.log(''+'6'+', '+this.Client__c_var+', ');
		setTimeout(() => this.bus.emit('app?m2bc', this.Client__b_var, this.Client__c_var), 0);
		console.log(''+'5'+', '+this.Client__a_var+', '+this.Client__b_var+', ');
		setTimeout(() => this.bus.emit('app?m2a', this.Client__a_var), 0);
		
		}
		this.Client_counter_var++;
	});
	let Client_null_STOP = new StateJS.FinalState('STOP', this._statemachine).entry(() => {
		console.log(''+'#CLI: What are you waitin for? Christmas?');
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
	Client_null_generate_m3_from_m3__and_m3a_INIT_S4.to(Client_null_generate_m3_from_m3__and_m3a_INIT_S1);
	Client_null_RUN.to(Client_null_RUN).when((m3) => {
		return m3._port === 'diversified' && m3._msg === 'm3' && (m3.a === this.Client__a_var);
	}).effect((m3) => {
		console.log(''+'#CLI: Ooh, I needed that app?m3('+m3.a+')!');
		console.log(''+'#CLI: We meet again, Doctor Jones!');
	});
	Client_null_RUN.to(Client_null_ERROR).when((m3) => {
		return m3._port === 'diversified' && m3._msg === 'm3' && (m3.a != this.Client__a_var);
	}).effect((m3) => {
		console.log(''+'#CLI: Ooh, I needed that app?m3('+m3.a+')!');
		console.log(''+'#CLI: Damn, you re ugly.');
	});
	Client_null_generate_m3_from_m3__and_m3a_INIT_S1.to(Client_null_generate_m3_from_m3__and_m3a_INIT_S3).when((m3a) => {
		return m3a._port === 'app' && m3a._msg === 'm3a';
	}).effect((m3a) => {
		this.Client_null_generate_m3_from_m3__and_m3a_INIT_a_var = m3a.a;
	});
	Client_null_generate_m3_from_m3__and_m3a_INIT_S2.to(Client_null_generate_m3_from_m3__and_m3a_INIT_S4).when((m3a) => {
		return m3a._port === 'app' && m3a._msg === 'm3a';
	}).effect((m3a) => {
		this.Client_null_generate_m3_from_m3__and_m3a_INIT_a_var = m3a.a;
	});
	Client_null_generate_m3_from_m3__and_m3a_INIT_S1.to(Client_null_generate_m3_from_m3__and_m3a_INIT_S2).when((m3_) => {
		return m3_._port === 'app' && m3_._msg === 'm3_';
	});
	Client_null_generate_m3_from_m3__and_m3a_INIT_S3.to(Client_null_generate_m3_from_m3__and_m3a_INIT_S4).when((m3_) => {
		return m3_._port === 'app' && m3_._msg === 'm3_';
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

Client.prototype.receivem3_Onapp = function() {
	this._receive({_port:"app", _msg:"m3_"});
}

Client.prototype.receivem3aOnapp = function(a) {
	this._receive({_port:"app", _msg:"m3a", a:a});
}

Client.prototype.receivem3Ondiversified = function(a) {
	this._receive({_port:"diversified", _msg:"m3", a:a});
}

Client.prototype.toString = function() {
	let result = 'instance ' + this.name + ':' + this.constructor.name + '\n';
	result += '\n\t_b = ' + this.Client__b_var;
	result += '\n\t_d = ' + this.Client__d_var;
	result += '\n\t_a = ' + this.Client__a_var;
	result += '\n\tcounter = ' + this.Client_counter_var;
	result += '\n\ta = ' + this.Client_null_generate_m3_from_m3__and_m3a_INIT_a_var;
	result += '\n\t_c = ' + this.Client__c_var;
	result += '\n\t_e = ' + this.Client__e_var;
	result += '';
	return result;
}

