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
	this.App_null_generate_m1_from_m1a_and_m1bcde_INIT_d_var;
	this.App_null_generate_m2_from_m2ab_and_m2c_INIT_a_var;
	this.App_null_generate_m2_from_m2ab_and_m2c_INIT_b_var;
	this.App_null_generate_m1_from_m1a_and_m1bcde_INIT_b_var;
	this.App_null_generate_m1_from_m1a_and_m1bcde_INIT_c_var;
	this.App_null_generate_m1_from_m1a_and_m1bcde_INIT_a_var;
	this.App_null_generate_m2_from_m2ab_and_m2c_INIT_c_var;
	this.App_id_var = App_id_var;
	this.debug_App_id_var = App_id_var;
	this.App_null_generate_m1_from_m1a_and_m1bcde_INIT_e_var;
	
	this.build(name);
}

App.prototype.build = function(session) {
	/*State machine (states and regions)*/
	/*Building root component*/
	this._statemachine = new StateJS.StateMachine('default');
	let _initial_App = new StateJS.PseudoState('_initial', this._statemachine, StateJS.PseudoStateKind.Initial);
	let App_null_generate_m1_from_m1a_and_m1bcde_reg = new StateJS.Region('generate_m1_from_m1a_and_m1bcde', this._statemachine);
	let _initial_App_null_generate_m1_from_m1a_and_m1bcde_reg = new StateJS.PseudoState('_initial', App_null_generate_m1_from_m1a_and_m1bcde_reg, StateJS.PseudoStateKind.Initial);
	let App_null_generate_m1_from_m1a_and_m1bcde_INIT = new StateJS.State('INIT', App_null_generate_m1_from_m1a_and_m1bcde_reg);
	let App_null_generate_m1_from_m1a_and_m1bcde_INIT_S1 = new StateJS.State('S1', App_null_generate_m1_from_m1a_and_m1bcde_INIT);
	let App_null_generate_m1_from_m1a_and_m1bcde_INIT_S2 = new StateJS.State('S2', App_null_generate_m1_from_m1a_and_m1bcde_INIT);
	let App_null_generate_m1_from_m1a_and_m1bcde_INIT_S3 = new StateJS.State('S3', App_null_generate_m1_from_m1a_and_m1bcde_INIT);
	let App_null_generate_m1_from_m1a_and_m1bcde_INIT_S4 = new StateJS.State('S4', App_null_generate_m1_from_m1a_and_m1bcde_INIT).entry(() => {
		setTimeout(() => this.bus.emit('diversified?m1', this.App_null_generate_m1_from_m1a_and_m1bcde_INIT_a_var, this.App_null_generate_m1_from_m1a_and_m1bcde_INIT_b_var, this.App_null_generate_m1_from_m1a_and_m1bcde_INIT_c_var, this.App_null_generate_m1_from_m1a_and_m1bcde_INIT_d_var, this.App_null_generate_m1_from_m1a_and_m1bcde_INIT_e_var), 0);
	});
	let _initial_App_null_generate_m1_from_m1a_and_m1bcde_INIT = new StateJS.PseudoState('_initial', App_null_generate_m1_from_m1a_and_m1bcde_INIT, StateJS.PseudoStateKind.Initial);
	_initial_App_null_generate_m1_from_m1a_and_m1bcde_INIT.to(App_null_generate_m1_from_m1a_and_m1bcde_INIT_S1);
	_initial_App_null_generate_m1_from_m1a_and_m1bcde_reg.to(App_null_generate_m1_from_m1a_and_m1bcde_INIT);
	let App_null_generate_m2_from_m2ab_and_m2c_reg = new StateJS.Region('generate_m2_from_m2ab_and_m2c', this._statemachine);
	let _initial_App_null_generate_m2_from_m2ab_and_m2c_reg = new StateJS.PseudoState('_initial', App_null_generate_m2_from_m2ab_and_m2c_reg, StateJS.PseudoStateKind.Initial);
	let App_null_generate_m2_from_m2ab_and_m2c_INIT = new StateJS.State('INIT', App_null_generate_m2_from_m2ab_and_m2c_reg);
	let App_null_generate_m2_from_m2ab_and_m2c_INIT_S1 = new StateJS.State('S1', App_null_generate_m2_from_m2ab_and_m2c_INIT);
	let App_null_generate_m2_from_m2ab_and_m2c_INIT_S2 = new StateJS.State('S2', App_null_generate_m2_from_m2ab_and_m2c_INIT);
	let App_null_generate_m2_from_m2ab_and_m2c_INIT_S3 = new StateJS.State('S3', App_null_generate_m2_from_m2ab_and_m2c_INIT);
	let App_null_generate_m2_from_m2ab_and_m2c_INIT_S4 = new StateJS.State('S4', App_null_generate_m2_from_m2ab_and_m2c_INIT).entry(() => {
		setTimeout(() => this.bus.emit('diversified?m2', this.App_null_generate_m2_from_m2ab_and_m2c_INIT_a_var, this.App_null_generate_m2_from_m2ab_and_m2c_INIT_b_var, this.App_null_generate_m2_from_m2ab_and_m2c_INIT_c_var), 0);
	});
	let _initial_App_null_generate_m2_from_m2ab_and_m2c_INIT = new StateJS.PseudoState('_initial', App_null_generate_m2_from_m2ab_and_m2c_INIT, StateJS.PseudoStateKind.Initial);
	_initial_App_null_generate_m2_from_m2ab_and_m2c_INIT.to(App_null_generate_m2_from_m2ab_and_m2c_INIT_S1);
	_initial_App_null_generate_m2_from_m2ab_and_m2c_reg.to(App_null_generate_m2_from_m2ab_and_m2c_INIT);
	let App_null_WaitForM1 = new StateJS.State('WaitForM1', this._statemachine);
	let App_null_WaitForM2 = new StateJS.State('WaitForM2', this._statemachine);
	let App_null_SendAck = new StateJS.State('SendAck', this._statemachine).entry(() => {
		console.log(''+'#APP: Come get some app!m3('+this.App_id_var+')!');
		if(this.rnd() < 57) {
		console.log(''+'7'+', ');
		setTimeout(() => this.bus.emit('app?m3_'), 0);
		console.log(''+'8'+', '+this.App_id_var+', ');
		setTimeout(() => this.bus.emit('app?m3a', this.App_id_var), 0);
		
		} else {
		console.log(''+'8'+', '+this.App_id_var+', ');
		setTimeout(() => this.bus.emit('app?m3a', this.App_id_var), 0);
		console.log(''+'7'+', ');
		setTimeout(() => this.bus.emit('app?m3_'), 0);
		
		}
	});
	_initial_App.to(App_null_WaitForM1);
	App_null_SendAck.to(App_null_WaitForM1);
	App_null_generate_m2_from_m2ab_and_m2c_INIT_S4.to(App_null_generate_m2_from_m2ab_and_m2c_INIT_S1);
	App_null_generate_m1_from_m1a_and_m1bcde_INIT_S4.to(App_null_generate_m1_from_m1a_and_m1bcde_INIT_S1);
	App_null_generate_m1_from_m1a_and_m1bcde_INIT_S1.to(App_null_generate_m1_from_m1a_and_m1bcde_INIT_S2).when((m1a) => {
		return m1a._port === 'app' && m1a._msg === 'm1a';
	}).effect((m1a) => {
		this.App_null_generate_m1_from_m1a_and_m1bcde_INIT_a_var = m1a.a;
	});
	App_null_generate_m1_from_m1a_and_m1bcde_INIT_S3.to(App_null_generate_m1_from_m1a_and_m1bcde_INIT_S4).when((m1a) => {
		return m1a._port === 'app' && m1a._msg === 'm1a';
	}).effect((m1a) => {
		this.App_null_generate_m1_from_m1a_and_m1bcde_INIT_a_var = m1a.a;
	});
	App_null_generate_m2_from_m2ab_and_m2c_INIT_S1.to(App_null_generate_m2_from_m2ab_and_m2c_INIT_S3).when((m2bc) => {
		return m2bc._port === 'app' && m2bc._msg === 'm2bc';
	}).effect((m2bc) => {
		this.App_null_generate_m2_from_m2ab_and_m2c_INIT_b_var = m2bc.b;
		this.App_null_generate_m2_from_m2ab_and_m2c_INIT_c_var = m2bc.c;
	});
	App_null_generate_m2_from_m2ab_and_m2c_INIT_S2.to(App_null_generate_m2_from_m2ab_and_m2c_INIT_S4).when((m2bc) => {
		return m2bc._port === 'app' && m2bc._msg === 'm2bc';
	}).effect((m2bc) => {
		this.App_null_generate_m2_from_m2ab_and_m2c_INIT_b_var = m2bc.b;
		this.App_null_generate_m2_from_m2ab_and_m2c_INIT_c_var = m2bc.c;
	});
	App_null_generate_m2_from_m2ab_and_m2c_INIT_S1.to(App_null_generate_m2_from_m2ab_and_m2c_INIT_S2).when((m2a) => {
		return m2a._port === 'app' && m2a._msg === 'm2a';
	}).effect((m2a) => {
		this.App_null_generate_m2_from_m2ab_and_m2c_INIT_a_var = m2a.a;
	});
	App_null_generate_m2_from_m2ab_and_m2c_INIT_S3.to(App_null_generate_m2_from_m2ab_and_m2c_INIT_S4).when((m2a) => {
		return m2a._port === 'app' && m2a._msg === 'm2a';
	}).effect((m2a) => {
		this.App_null_generate_m2_from_m2ab_and_m2c_INIT_a_var = m2a.a;
	});
	App_null_generate_m1_from_m1a_and_m1bcde_INIT_S2.to(App_null_generate_m1_from_m1a_and_m1bcde_INIT_S4).when((m1bcde) => {
		return m1bcde._port === 'app' && m1bcde._msg === 'm1bcde';
	}).effect((m1bcde) => {
		this.App_null_generate_m1_from_m1a_and_m1bcde_INIT_b_var = m1bcde.b;
		this.App_null_generate_m1_from_m1a_and_m1bcde_INIT_c_var = m1bcde.c;
		this.App_null_generate_m1_from_m1a_and_m1bcde_INIT_d_var = m1bcde.d;
		this.App_null_generate_m1_from_m1a_and_m1bcde_INIT_e_var = m1bcde.e;
	});
	App_null_generate_m1_from_m1a_and_m1bcde_INIT_S1.to(App_null_generate_m1_from_m1a_and_m1bcde_INIT_S3).when((m1bcde) => {
		return m1bcde._port === 'app' && m1bcde._msg === 'm1bcde';
	}).effect((m1bcde) => {
		this.App_null_generate_m1_from_m1a_and_m1bcde_INIT_b_var = m1bcde.b;
		this.App_null_generate_m1_from_m1a_and_m1bcde_INIT_c_var = m1bcde.c;
		this.App_null_generate_m1_from_m1a_and_m1bcde_INIT_d_var = m1bcde.d;
		this.App_null_generate_m1_from_m1a_and_m1bcde_INIT_e_var = m1bcde.e;
	});
	App_null_WaitForM1.to(App_null_WaitForM2).when((m1) => {
		return m1._port === 'diversified' && m1._msg === 'm1';
	}).effect((m1) => {
		this.App_id_var = m1.a;
		this.bus.emit('id=', this.App_id_var);
		console.log(''+'#APP: Ooh, I needed that app?m1('+m1.a+', '+m1.b+', '+m1.c+', '+m1.d+', '+m1.e+')!');
	});
	App_null_WaitForM2.to(App_null_SendAck).when((m2) => {
		return m2._port === 'diversified' && m2._msg === 'm2';
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

App.prototype.receivem1aOnapp = function(a) {
	this._receive({_port:"app", _msg:"m1a", a:a});
}

App.prototype.receivem1bcdeOnapp = function(b, c, d, e) {
	this._receive({_port:"app", _msg:"m1bcde", b:b, c:c, d:d, e:e});
}

App.prototype.receivem2aOnapp = function(a) {
	this._receive({_port:"app", _msg:"m2a", a:a});
}

App.prototype.receivem2bcOnapp = function(b, c) {
	this._receive({_port:"app", _msg:"m2bc", b:b, c:c});
}

App.prototype.receivem1Ondiversified = function(a, b, c, d, e) {
	this._receive({_port:"diversified", _msg:"m1", a:a, b:b, c:c, d:d, e:e});
}

App.prototype.receivem2aOndiversified = function(a) {
	this._receive({_port:"diversified", _msg:"m2a", a:a});
}

App.prototype.receivem2bcOndiversified = function(b, c) {
	this._receive({_port:"diversified", _msg:"m2bc", b:b, c:c});
}

App.prototype.receivem2Ondiversified = function(a, b, c) {
	this._receive({_port:"diversified", _msg:"m2", a:a, b:b, c:c});
}

App.prototype.toString = function() {
	let result = 'instance ' + this.name + ':' + this.constructor.name + '\n';
	result += '\n\td = ' + this.App_null_generate_m1_from_m1a_and_m1bcde_INIT_d_var;
	result += '\n\ta = ' + this.App_null_generate_m2_from_m2ab_and_m2c_INIT_a_var;
	result += '\n\tb = ' + this.App_null_generate_m2_from_m2ab_and_m2c_INIT_b_var;
	result += '\n\tb = ' + this.App_null_generate_m1_from_m1a_and_m1bcde_INIT_b_var;
	result += '\n\tc = ' + this.App_null_generate_m1_from_m1a_and_m1bcde_INIT_c_var;
	result += '\n\ta = ' + this.App_null_generate_m1_from_m1a_and_m1bcde_INIT_a_var;
	result += '\n\tc = ' + this.App_null_generate_m2_from_m2ab_and_m2c_INIT_c_var;
	result += '\n\tid = ' + this.App_id_var;
	result += '\n\te = ' + this.App_null_generate_m1_from_m1a_and_m1bcde_INIT_e_var;
	result += '';
	return result;
}

