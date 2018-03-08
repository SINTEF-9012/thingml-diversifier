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
	this.App_null_generate_m1_from_m1abcd_and_m1e_INIT_b_var;
	this.App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_b_var;
	this.App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_b_var;
	this.App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_c_var;
	this.App_null_generate_m1_from_m1abcd_and_m1e_INIT_c_var;
	this.App_null_generate_m1_from_m1abcd_and_m1e_INIT_d_var;
	this.App_null_generate_m1_from_m1abcd_and_m1e_INIT_e_var;
	this.App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_a_var;
	this.App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_c_var;
	this.App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_r0_var;
	this.App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_r1_var;
	this.App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_a_var;
	this.App_null_generate_m1_from_m1abcd_and_m1e_INIT_a_var;
	this.App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_d_var;
	this.App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_e_var;
	this.App_id_var = App_id_var;
	this.debug_App_id_var = App_id_var;
	this.App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_c_var;
	this.App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_r1_var;
	this.App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_a_var;
	this.App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_b_var;
	this.App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_r0_var;
	
	this.build(name);
}

App.prototype.build = function(session) {
	/*State machine (states and regions)*/
	/*Building root component*/
	this._statemachine = new StateJS.StateMachine('default');
	let _initial_App = new StateJS.PseudoState('_initial', this._statemachine, StateJS.PseudoStateKind.Initial);
	let App_null_generate_m2_from_m2r0r1_and_m2abc_reg = new StateJS.Region('generate_m2_from_m2r0r1_and_m2abc', this._statemachine);
	let _initial_App_null_generate_m2_from_m2r0r1_and_m2abc_reg = new StateJS.PseudoState('_initial', App_null_generate_m2_from_m2r0r1_and_m2abc_reg, StateJS.PseudoStateKind.Initial);
	let App_null_generate_m2_from_m2r0r1_and_m2abc_INIT = new StateJS.State('INIT', App_null_generate_m2_from_m2r0r1_and_m2abc_reg);
	let App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_S1 = new StateJS.State('S1', App_null_generate_m2_from_m2r0r1_and_m2abc_INIT);
	let App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_S2 = new StateJS.State('S2', App_null_generate_m2_from_m2r0r1_and_m2abc_INIT);
	let App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_S3 = new StateJS.State('S3', App_null_generate_m2_from_m2r0r1_and_m2abc_INIT);
	let App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_S4 = new StateJS.State('S4', App_null_generate_m2_from_m2r0r1_and_m2abc_INIT).entry(() => {
		setImmediate(() => this.bus.emit('diversified?m2', this.App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_r0_var, this.App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_r1_var, this.App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_a_var, this.App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_b_var, this.App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_c_var));
	});
	let _initial_App_null_generate_m2_from_m2r0r1_and_m2abc_INIT = new StateJS.PseudoState('_initial', App_null_generate_m2_from_m2r0r1_and_m2abc_INIT, StateJS.PseudoStateKind.Initial);
	_initial_App_null_generate_m2_from_m2r0r1_and_m2abc_INIT.to(App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_S1);
	_initial_App_null_generate_m2_from_m2r0r1_and_m2abc_reg.to(App_null_generate_m2_from_m2r0r1_and_m2abc_INIT);
	let App_null_generate_m1_from_m1abcd_and_m1e_reg = new StateJS.Region('generate_m1_from_m1abcd_and_m1e', this._statemachine);
	let _initial_App_null_generate_m1_from_m1abcd_and_m1e_reg = new StateJS.PseudoState('_initial', App_null_generate_m1_from_m1abcd_and_m1e_reg, StateJS.PseudoStateKind.Initial);
	let App_null_generate_m1_from_m1abcd_and_m1e_INIT = new StateJS.State('INIT', App_null_generate_m1_from_m1abcd_and_m1e_reg);
	let App_null_generate_m1_from_m1abcd_and_m1e_INIT_S1 = new StateJS.State('S1', App_null_generate_m1_from_m1abcd_and_m1e_INIT);
	let App_null_generate_m1_from_m1abcd_and_m1e_INIT_S2 = new StateJS.State('S2', App_null_generate_m1_from_m1abcd_and_m1e_INIT);
	let App_null_generate_m1_from_m1abcd_and_m1e_INIT_S3 = new StateJS.State('S3', App_null_generate_m1_from_m1abcd_and_m1e_INIT);
	let App_null_generate_m1_from_m1abcd_and_m1e_INIT_S4 = new StateJS.State('S4', App_null_generate_m1_from_m1abcd_and_m1e_INIT).entry(() => {
		setImmediate(() => this.bus.emit('diversified?m1', this.App_null_generate_m1_from_m1abcd_and_m1e_INIT_a_var, this.App_null_generate_m1_from_m1abcd_and_m1e_INIT_b_var, this.App_null_generate_m1_from_m1abcd_and_m1e_INIT_c_var, this.App_null_generate_m1_from_m1abcd_and_m1e_INIT_d_var, this.App_null_generate_m1_from_m1abcd_and_m1e_INIT_e_var));
	});
	let _initial_App_null_generate_m1_from_m1abcd_and_m1e_INIT = new StateJS.PseudoState('_initial', App_null_generate_m1_from_m1abcd_and_m1e_INIT, StateJS.PseudoStateKind.Initial);
	_initial_App_null_generate_m1_from_m1abcd_and_m1e_INIT.to(App_null_generate_m1_from_m1abcd_and_m1e_INIT_S1);
	_initial_App_null_generate_m1_from_m1abcd_and_m1e_reg.to(App_null_generate_m1_from_m1abcd_and_m1e_INIT);
	let App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_reg = new StateJS.Region('generate_m2bis_from_m2bisr0r1ab_and_m2bisc', this._statemachine);
	let _initial_App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_reg = new StateJS.PseudoState('_initial', App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_reg, StateJS.PseudoStateKind.Initial);
	let App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT = new StateJS.State('INIT', App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_reg);
	let App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_S1 = new StateJS.State('S1', App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT);
	let App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_S2 = new StateJS.State('S2', App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT);
	let App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_S3 = new StateJS.State('S3', App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT);
	let App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_S4 = new StateJS.State('S4', App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT).entry(() => {
		setImmediate(() => this.bus.emit('diversified?m2bis', this.App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_r0_var, this.App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_r1_var, this.App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_a_var, this.App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_b_var, this.App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_c_var));
	});
	let _initial_App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT = new StateJS.PseudoState('_initial', App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT, StateJS.PseudoStateKind.Initial);
	_initial_App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT.to(App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_S1);
	_initial_App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_reg.to(App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT);
	let App_null_generate_m1bis_from_m1bisabcd_and_m1bise_reg = new StateJS.Region('generate_m1bis_from_m1bisabcd_and_m1bise', this._statemachine);
	let _initial_App_null_generate_m1bis_from_m1bisabcd_and_m1bise_reg = new StateJS.PseudoState('_initial', App_null_generate_m1bis_from_m1bisabcd_and_m1bise_reg, StateJS.PseudoStateKind.Initial);
	let App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT = new StateJS.State('INIT', App_null_generate_m1bis_from_m1bisabcd_and_m1bise_reg);
	let App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_S1 = new StateJS.State('S1', App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT);
	let App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_S2 = new StateJS.State('S2', App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT);
	let App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_S3 = new StateJS.State('S3', App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT);
	let App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_S4 = new StateJS.State('S4', App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT).entry(() => {
		setImmediate(() => this.bus.emit('diversified?m1bis', this.App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_a_var, this.App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_b_var, this.App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_c_var, this.App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_d_var, this.App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_e_var));
	});
	let _initial_App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT = new StateJS.PseudoState('_initial', App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT, StateJS.PseudoStateKind.Initial);
	_initial_App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT.to(App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_S1);
	_initial_App_null_generate_m1bis_from_m1bisabcd_and_m1bise_reg.to(App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT);
	let App_null_WaitForM1 = new StateJS.State('WaitForM1', this._statemachine);
	let App_null_WaitForM2 = new StateJS.State('WaitForM2', this._statemachine);
	let App_null_SendAck = new StateJS.State('SendAck', this._statemachine).entry(() => {
		process.stdout.write(''+'#APP: Come get some app!m3(');
		process.stdout.write(''+this.App_id_var);
		process.stdout.write(''+')!');
		process.stderr.write('\n');
		if(this.rnd() < 74) {
		if(this.rnd() < 69) {
		process.stdout.write(''+'8');
		process.stdout.write(''+', ');
		process.stdout.write(''+this.rnd());
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
		setImmediate(() => this.bus.emit('app?m3_', this.rnd(), this.rnd(), this.rnd(), this.rnd(), this.rnd()));
		process.stdout.write(''+'9');
		process.stdout.write(''+', ');
		process.stdout.write(''+this.rnd());
		process.stdout.write(''+', ');
		process.stdout.write(''+this.rnd());
		process.stdout.write(''+', ');
		process.stdout.write(''+this.rnd());
		process.stdout.write(''+', ');
		process.stdout.write(''+this.rnd());
		process.stdout.write(''+', ');
		process.stdout.write(''+this.App_id_var);
		process.stdout.write(''+', ');
		process.stderr.write('\n');
		setImmediate(() => this.bus.emit('app?m3r4r5r2r3a', this.rnd(), this.rnd(), this.rnd(), this.rnd(), this.App_id_var));
		
		} else {
		process.stdout.write(''+'9');
		process.stdout.write(''+', ');
		process.stdout.write(''+this.rnd());
		process.stdout.write(''+', ');
		process.stdout.write(''+this.rnd());
		process.stdout.write(''+', ');
		process.stdout.write(''+this.rnd());
		process.stdout.write(''+', ');
		process.stdout.write(''+this.rnd());
		process.stdout.write(''+', ');
		process.stdout.write(''+this.App_id_var);
		process.stdout.write(''+', ');
		process.stderr.write('\n');
		setImmediate(() => this.bus.emit('app?m3r4r5r2r3a', this.rnd(), this.rnd(), this.rnd(), this.rnd(), this.App_id_var));
		process.stdout.write(''+'8');
		process.stdout.write(''+', ');
		process.stdout.write(''+this.rnd());
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
		setImmediate(() => this.bus.emit('app?m3_', this.rnd(), this.rnd(), this.rnd(), this.rnd(), this.rnd()));
		
		}
		
		} else {
		if(this.rnd() < 248) {
		process.stdout.write(''+'14');
		process.stdout.write(''+', ');
		process.stdout.write(''+this.rnd());
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
		setImmediate(() => this.bus.emit('app?m3bis_', this.rnd(), this.rnd(), this.rnd(), this.rnd(), this.rnd()));
		process.stdout.write(''+'15');
		process.stdout.write(''+', ');
		process.stdout.write(''+this.rnd());
		process.stdout.write(''+', ');
		process.stdout.write(''+this.rnd());
		process.stdout.write(''+', ');
		process.stdout.write(''+this.rnd());
		process.stdout.write(''+', ');
		process.stdout.write(''+this.rnd());
		process.stdout.write(''+', ');
		process.stdout.write(''+this.App_id_var);
		process.stdout.write(''+', ');
		process.stderr.write('\n');
		setImmediate(() => this.bus.emit('app?m3bisr4r5r2r3a', this.rnd(), this.rnd(), this.rnd(), this.rnd(), this.App_id_var));
		
		} else {
		process.stdout.write(''+'15');
		process.stdout.write(''+', ');
		process.stdout.write(''+this.rnd());
		process.stdout.write(''+', ');
		process.stdout.write(''+this.rnd());
		process.stdout.write(''+', ');
		process.stdout.write(''+this.rnd());
		process.stdout.write(''+', ');
		process.stdout.write(''+this.rnd());
		process.stdout.write(''+', ');
		process.stdout.write(''+this.App_id_var);
		process.stdout.write(''+', ');
		process.stderr.write('\n');
		setImmediate(() => this.bus.emit('app?m3bisr4r5r2r3a', this.rnd(), this.rnd(), this.rnd(), this.rnd(), this.App_id_var));
		process.stdout.write(''+'14');
		process.stdout.write(''+', ');
		process.stdout.write(''+this.rnd());
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
		setImmediate(() => this.bus.emit('app?m3bis_', this.rnd(), this.rnd(), this.rnd(), this.rnd(), this.rnd()));
		
		}
		
		}
	});
	_initial_App.to(App_null_WaitForM1);
	App_null_generate_m1_from_m1abcd_and_m1e_INIT_S4.to(App_null_generate_m1_from_m1abcd_and_m1e_INIT_S1);
	App_null_SendAck.to(App_null_WaitForM1);
	App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_S4.to(App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_S1);
	App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_S4.to(App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_S1);
	App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_S4.to(App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_S1);
	App_null_generate_m1_from_m1abcd_and_m1e_INIT_S1.to(App_null_generate_m1_from_m1abcd_and_m1e_INIT_S3).when((m1e) => {
		return m1e._port === 'app' && m1e._msg === 'm1e';
	}).effect((m1e) => {
		this.App_null_generate_m1_from_m1abcd_and_m1e_INIT_e_var = m1e.e;
	});
	App_null_generate_m1_from_m1abcd_and_m1e_INIT_S2.to(App_null_generate_m1_from_m1abcd_and_m1e_INIT_S4).when((m1e) => {
		return m1e._port === 'app' && m1e._msg === 'm1e';
	}).effect((m1e) => {
		this.App_null_generate_m1_from_m1abcd_and_m1e_INIT_e_var = m1e.e;
	});
	App_null_generate_m1_from_m1abcd_and_m1e_INIT_S1.to(App_null_generate_m1_from_m1abcd_and_m1e_INIT_S2).when((m1abcd) => {
		return m1abcd._port === 'app' && m1abcd._msg === 'm1abcd';
	}).effect((m1abcd) => {
		this.App_null_generate_m1_from_m1abcd_and_m1e_INIT_a_var = m1abcd.a;
		this.App_null_generate_m1_from_m1abcd_and_m1e_INIT_b_var = m1abcd.b;
		this.App_null_generate_m1_from_m1abcd_and_m1e_INIT_c_var = m1abcd.c;
		this.App_null_generate_m1_from_m1abcd_and_m1e_INIT_d_var = m1abcd.d;
	});
	App_null_generate_m1_from_m1abcd_and_m1e_INIT_S3.to(App_null_generate_m1_from_m1abcd_and_m1e_INIT_S4).when((m1abcd) => {
		return m1abcd._port === 'app' && m1abcd._msg === 'm1abcd';
	}).effect((m1abcd) => {
		this.App_null_generate_m1_from_m1abcd_and_m1e_INIT_a_var = m1abcd.a;
		this.App_null_generate_m1_from_m1abcd_and_m1e_INIT_b_var = m1abcd.b;
		this.App_null_generate_m1_from_m1abcd_and_m1e_INIT_c_var = m1abcd.c;
		this.App_null_generate_m1_from_m1abcd_and_m1e_INIT_d_var = m1abcd.d;
	});
	App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_S1.to(App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_S2).when((m1bisabcd) => {
		return m1bisabcd._port === 'app' && m1bisabcd._msg === 'm1bisabcd';
	}).effect((m1bisabcd) => {
		this.App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_a_var = m1bisabcd.a;
		this.App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_b_var = m1bisabcd.b;
		this.App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_c_var = m1bisabcd.c;
		this.App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_d_var = m1bisabcd.d;
	});
	App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_S3.to(App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_S4).when((m1bisabcd) => {
		return m1bisabcd._port === 'app' && m1bisabcd._msg === 'm1bisabcd';
	}).effect((m1bisabcd) => {
		this.App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_a_var = m1bisabcd.a;
		this.App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_b_var = m1bisabcd.b;
		this.App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_c_var = m1bisabcd.c;
		this.App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_d_var = m1bisabcd.d;
	});
	App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_S1.to(App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_S2).when((m2bisr0r1ab) => {
		return m2bisr0r1ab._port === 'app' && m2bisr0r1ab._msg === 'm2bisr0r1ab';
	}).effect((m2bisr0r1ab) => {
		this.App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_r0_var = m2bisr0r1ab.r0;
		this.App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_r1_var = m2bisr0r1ab.r1;
		this.App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_a_var = m2bisr0r1ab.a;
		this.App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_b_var = m2bisr0r1ab.b;
	});
	App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_S3.to(App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_S4).when((m2bisr0r1ab) => {
		return m2bisr0r1ab._port === 'app' && m2bisr0r1ab._msg === 'm2bisr0r1ab';
	}).effect((m2bisr0r1ab) => {
		this.App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_r0_var = m2bisr0r1ab.r0;
		this.App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_r1_var = m2bisr0r1ab.r1;
		this.App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_a_var = m2bisr0r1ab.a;
		this.App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_b_var = m2bisr0r1ab.b;
	});
	App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_S2.to(App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_S4).when((m2bisc) => {
		return m2bisc._port === 'app' && m2bisc._msg === 'm2bisc';
	}).effect((m2bisc) => {
		this.App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_c_var = m2bisc.c;
	});
	App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_S1.to(App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_S3).when((m2bisc) => {
		return m2bisc._port === 'app' && m2bisc._msg === 'm2bisc';
	}).effect((m2bisc) => {
		this.App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_c_var = m2bisc.c;
	});
	App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_S2.to(App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_S4).when((m2abc) => {
		return m2abc._port === 'app' && m2abc._msg === 'm2abc';
	}).effect((m2abc) => {
		this.App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_a_var = m2abc.a;
		this.App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_b_var = m2abc.b;
		this.App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_c_var = m2abc.c;
	});
	App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_S1.to(App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_S3).when((m2abc) => {
		return m2abc._port === 'app' && m2abc._msg === 'm2abc';
	}).effect((m2abc) => {
		this.App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_a_var = m2abc.a;
		this.App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_b_var = m2abc.b;
		this.App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_c_var = m2abc.c;
	});
	App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_S3.to(App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_S4).when((m2r0r1) => {
		return m2r0r1._port === 'app' && m2r0r1._msg === 'm2r0r1';
	}).effect((m2r0r1) => {
		this.App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_r0_var = m2r0r1.r0;
		this.App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_r1_var = m2r0r1.r1;
	});
	App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_S1.to(App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_S2).when((m2r0r1) => {
		return m2r0r1._port === 'app' && m2r0r1._msg === 'm2r0r1';
	}).effect((m2r0r1) => {
		this.App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_r0_var = m2r0r1.r0;
		this.App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_r1_var = m2r0r1.r1;
	});
	App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_S1.to(App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_S3).when((m1bise) => {
		return m1bise._port === 'app' && m1bise._msg === 'm1bise';
	}).effect((m1bise) => {
		this.App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_e_var = m1bise.e;
	});
	App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_S2.to(App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_S4).when((m1bise) => {
		return m1bise._port === 'app' && m1bise._msg === 'm1bise';
	}).effect((m1bise) => {
		this.App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_e_var = m1bise.e;
	});
	App_null_WaitForM2.to(App_null_SendAck).when((m2bis) => {
		return m2bis._port === 'diversified' && m2bis._msg === 'm2bis';
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
	App_null_WaitForM2.to(App_null_SendAck).when((m2) => {
		return m2._port === 'diversified' && m2._msg === 'm2';
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
	App_null_WaitForM1.to(App_null_WaitForM2).when((m1) => {
		return m1._port === 'diversified' && m1._msg === 'm1';
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
	App_null_WaitForM1.to(App_null_WaitForM2).when((m1bis) => {
		return m1bis._port === 'diversified' && m1bis._msg === 'm1bis';
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

App.prototype.receivem2r0r1Onapp = function(r6, r0, r7, r8, r1) {
	this._receive({_port:"app", _msg:"m2r0r1", r6:r6, r0:r0, r7:r7, r8:r8, r1:r1});
}

App.prototype.receivem2abcOnapp = function(a, r10, b, r9, c) {
	this._receive({_port:"app", _msg:"m2abc", a:a, r10:r10, b:b, r9:r9, c:c});
}

App.prototype.receivem1abcdOnapp = function(a, b, c, r16, d) {
	this._receive({_port:"app", _msg:"m1abcd", a:a, b:b, c:c, r16:r16, d:d});
}

App.prototype.receivem1eOnapp = function(r18, r19, r20, r17, e) {
	this._receive({_port:"app", _msg:"m1e", r18:r18, r19:r19, r20:r20, r17:r17, e:e});
}

App.prototype.receivem2bisr0r1abOnapp = function(r21, r0, r1, a, b) {
	this._receive({_port:"app", _msg:"m2bisr0r1ab", r21:r21, r0:r0, r1:r1, a:a, b:b});
}

App.prototype.receivem2biscOnapp = function(r24, r22, r23, r25, c) {
	this._receive({_port:"app", _msg:"m2bisc", r24:r24, r22:r22, r23:r23, r25:r25, c:c});
}

App.prototype.receivem1bisabcdOnapp = function(a, r31, b, c, d) {
	this._receive({_port:"app", _msg:"m1bisabcd", a:a, r31:r31, b:b, c:c, d:d});
}

App.prototype.receivem1biseOnapp = function(r32, r35, r33, r34, e) {
	this._receive({_port:"app", _msg:"m1bise", r32:r32, r35:r35, r33:r33, r34:r34, e:e});
}

App.prototype.receivem2Ondiversified = function(r0, r1, a, b, c) {
	this._receive({_port:"diversified", _msg:"m2", r0:r0, r1:r1, a:a, b:b, c:c});
}

App.prototype.receivem1abcdOndiversified = function(a, b, c, r16, d) {
	this._receive({_port:"diversified", _msg:"m1abcd", a:a, b:b, c:c, r16:r16, d:d});
}

App.prototype.receivem1eOndiversified = function(r18, r19, r20, r17, e) {
	this._receive({_port:"diversified", _msg:"m1e", r18:r18, r19:r19, r20:r20, r17:r17, e:e});
}

App.prototype.receivem1Ondiversified = function(a, b, c, d, e) {
	this._receive({_port:"diversified", _msg:"m1", a:a, b:b, c:c, d:d, e:e});
}

App.prototype.receivem2bisr0r1abOndiversified = function(r21, r0, r1, a, b) {
	this._receive({_port:"diversified", _msg:"m2bisr0r1ab", r21:r21, r0:r0, r1:r1, a:a, b:b});
}

App.prototype.receivem2biscOndiversified = function(r24, r22, r23, r25, c) {
	this._receive({_port:"diversified", _msg:"m2bisc", r24:r24, r22:r22, r23:r23, r25:r25, c:c});
}

App.prototype.receivem2bisOndiversified = function(r0, r1, a, b, c) {
	this._receive({_port:"diversified", _msg:"m2bis", r0:r0, r1:r1, a:a, b:b, c:c});
}

App.prototype.receivem1bisabcdOndiversified = function(a, r31, b, c, d) {
	this._receive({_port:"diversified", _msg:"m1bisabcd", a:a, r31:r31, b:b, c:c, d:d});
}

App.prototype.receivem1biseOndiversified = function(r32, r35, r33, r34, e) {
	this._receive({_port:"diversified", _msg:"m1bise", r32:r32, r35:r35, r33:r33, r34:r34, e:e});
}

App.prototype.receivem1bisOndiversified = function(a, b, c, d, e) {
	this._receive({_port:"diversified", _msg:"m1bis", a:a, b:b, c:c, d:d, e:e});
}

App.prototype.toString = function() {
	let result = 'instance ' + this.name + ':' + this.constructor.name + '\n';
	result += '\n\tb = ' + this.App_null_generate_m1_from_m1abcd_and_m1e_INIT_b_var;
	result += '\n\tb = ' + this.App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_b_var;
	result += '\n\tb = ' + this.App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_b_var;
	result += '\n\tc = ' + this.App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_c_var;
	result += '\n\tc = ' + this.App_null_generate_m1_from_m1abcd_and_m1e_INIT_c_var;
	result += '\n\td = ' + this.App_null_generate_m1_from_m1abcd_and_m1e_INIT_d_var;
	result += '\n\te = ' + this.App_null_generate_m1_from_m1abcd_and_m1e_INIT_e_var;
	result += '\n\ta = ' + this.App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_a_var;
	result += '\n\tc = ' + this.App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_c_var;
	result += '\n\tr0 = ' + this.App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_r0_var;
	result += '\n\tr1 = ' + this.App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_r1_var;
	result += '\n\ta = ' + this.App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_a_var;
	result += '\n\ta = ' + this.App_null_generate_m1_from_m1abcd_and_m1e_INIT_a_var;
	result += '\n\td = ' + this.App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_d_var;
	result += '\n\te = ' + this.App_null_generate_m1bis_from_m1bisabcd_and_m1bise_INIT_e_var;
	result += '\n\tid = ' + this.App_id_var;
	result += '\n\tc = ' + this.App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_c_var;
	result += '\n\tr1 = ' + this.App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_r1_var;
	result += '\n\ta = ' + this.App_null_generate_m2_from_m2r0r1_and_m2abc_INIT_a_var;
	result += '\n\tb = ' + this.App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_b_var;
	result += '\n\tr0 = ' + this.App_null_generate_m2bis_from_m2bisr0r1ab_and_m2bisc_INIT_r0_var;
	result += '';
	return result;
}

module.exports = App;
