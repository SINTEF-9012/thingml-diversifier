'use strict';

StateJS.internalTransitionsTriggerCompletion = true;


/*
 * Definition for type : Client
 */

function Client(name, root, Client__b_var, Client__d_var, Client__e_var, Client__c_var, Client_start_var, Client__a_var, Client_counter_var, debug) {
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
	this.Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r3_var;
	this.Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r5_var;
	this.Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r2_var;
	this.Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_a_var;
	this.Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r3_var;
	this.Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_a_var;
	this.Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r4_var;
	this.Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r5_var;
	this.Client__e_var = Client__e_var;
	this.debug_Client__e_var = Client__e_var;
	this.Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r4_var;
	this.Client__c_var = Client__c_var;
	this.debug_Client__c_var = Client__c_var;
	this.Client_start_var = Client_start_var;
	this.debug_Client_start_var = Client_start_var;
	this.Client__a_var = Client__a_var;
	this.debug_Client__a_var = Client__a_var;
	this.Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r2_var;
	this.Client_counter_var = Client_counter_var;
	this.debug_Client_counter_var = Client_counter_var;
	
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
	let Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_reg = new StateJS.Region('generate_m3_from_m3__and_m3r4r5r2r3a', this._statemachine);
	let _initial_Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_reg = new StateJS.PseudoState('_initial', Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_reg, StateJS.PseudoStateKind.Initial);
	let Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT = new StateJS.State('INIT', Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_reg);
	let Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_S1 = new StateJS.State('S1', Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT);
	let Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_S2 = new StateJS.State('S2', Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT);
	let Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_S3 = new StateJS.State('S3', Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT);
	let Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_S4 = new StateJS.State('S4', Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT).entry(() => {
		setTimeout(() => this.bus.emit('diversified?m3', this.Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r4_var, this.Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r5_var, this.Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r2_var, this.Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r3_var, this.Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_a_var), 0);
	});
	let _initial_Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT = new StateJS.PseudoState('_initial', Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT, StateJS.PseudoStateKind.Initial);
	_initial_Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT.to(Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_S1);
	_initial_Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_reg.to(Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT);
	let Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_reg = new StateJS.Region('generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a', this._statemachine);
	let _initial_Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_reg = new StateJS.PseudoState('_initial', Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_reg, StateJS.PseudoStateKind.Initial);
	let Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT = new StateJS.State('INIT', Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_reg);
	let Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_S1 = new StateJS.State('S1', Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT);
	let Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_S2 = new StateJS.State('S2', Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT);
	let Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_S3 = new StateJS.State('S3', Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT);
	let Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_S4 = new StateJS.State('S4', Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT).entry(() => {
		setTimeout(() => this.bus.emit('diversified?m3bis', this.Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r4_var, this.Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r5_var, this.Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r2_var, this.Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r3_var, this.Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_a_var), 0);
	});
	let _initial_Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT = new StateJS.PseudoState('_initial', Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT, StateJS.PseudoStateKind.Initial);
	_initial_Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT.to(Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_S1);
	_initial_Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_reg.to(Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT);
	let Client_null_RUN = new StateJS.State('RUN', this._statemachine).entry(() => {
		this.Client__c_var = this.rnd() % 100;
		this.bus.emit('_c=', this.Client__c_var);
		this.Client__d_var = this.rnd() % 50;
		this.bus.emit('_d=', this.Client__d_var);
		this.Client__e_var = this.rnd() % 25;
		this.bus.emit('_e=', this.Client__e_var);
		console.log(''+'#CLI: Come get some app!m1('+this.Client__a_var+', '+this.Client__b_var+', '+this.Client__c_var+', '+this.Client__d_var+', '+this.Client__e_var+')!');
		if(this.rnd() < 211) {
		if(this.rnd() < 62) {
		console.log(''+'10'+', '+this.Client__a_var+', '+this.Client__b_var+', '+this.Client__c_var+', '+this.rnd()+', '+this.Client__d_var+', ');
		setTimeout(() => this.bus.emit('app?m1abcd', this.Client__a_var, this.Client__b_var, this.Client__c_var, this.rnd(), this.Client__d_var), 0);
		console.log(''+'11'+', '+this.rnd()+', '+this.rnd()+', '+this.rnd()+', '+this.rnd()+', '+this.Client__e_var+', ');
		setTimeout(() => this.bus.emit('app?m1e', this.rnd(), this.rnd(), this.rnd(), this.rnd(), this.Client__e_var), 0);
		
		} else {
		console.log(''+'11'+', '+this.rnd()+', '+this.rnd()+', '+this.rnd()+', '+this.rnd()+', '+this.Client__e_var+', ');
		setTimeout(() => this.bus.emit('app?m1e', this.rnd(), this.rnd(), this.rnd(), this.rnd(), this.Client__e_var), 0);
		console.log(''+'10'+', '+this.Client__a_var+', '+this.Client__b_var+', '+this.Client__c_var+', '+this.rnd()+', '+this.Client__d_var+', ');
		setTimeout(() => this.bus.emit('app?m1abcd', this.Client__a_var, this.Client__b_var, this.Client__c_var, this.rnd(), this.Client__d_var), 0);
		
		}
		
		} else {
		if(this.rnd() < 184) {
		console.log(''+'16'+', '+this.Client__a_var+', '+this.rnd()+', '+this.Client__b_var+', '+this.Client__c_var+', '+this.Client__d_var+', ');
		setTimeout(() => this.bus.emit('app?m1bisabcd', this.Client__a_var, this.rnd(), this.Client__b_var, this.Client__c_var, this.Client__d_var), 0);
		console.log(''+'17'+', '+this.rnd()+', '+this.rnd()+', '+this.rnd()+', '+this.rnd()+', '+this.Client__e_var+', ');
		setTimeout(() => this.bus.emit('app?m1bise', this.rnd(), this.rnd(), this.rnd(), this.rnd(), this.Client__e_var), 0);
		
		} else {
		console.log(''+'17'+', '+this.rnd()+', '+this.rnd()+', '+this.rnd()+', '+this.rnd()+', '+this.Client__e_var+', ');
		setTimeout(() => this.bus.emit('app?m1bise', this.rnd(), this.rnd(), this.rnd(), this.rnd(), this.Client__e_var), 0);
		console.log(''+'16'+', '+this.Client__a_var+', '+this.rnd()+', '+this.Client__b_var+', '+this.Client__c_var+', '+this.Client__d_var+', ');
		setTimeout(() => this.bus.emit('app?m1bisabcd', this.Client__a_var, this.rnd(), this.Client__b_var, this.Client__c_var, this.Client__d_var), 0);
		
		}
		
		}
		console.log(''+'#CLI: Come get some app!m2('+this.Client__a_var+', '+this.Client__b_var+', '+this.Client__c_var+')!');
		if(this.rnd() < 39) {
		if(this.rnd() < 230) {
		console.log(''+'6'+', '+this.rnd()+', '+this.rnd()+', '+this.rnd()+', '+this.rnd()+', '+this.rnd()+', ');
		setTimeout(() => this.bus.emit('app?m2r0r1', this.rnd(), this.rnd(), this.rnd(), this.rnd(), this.rnd()), 0);
		console.log(''+'7'+', '+this.Client__a_var+', '+this.rnd()+', '+this.Client__b_var+', '+this.rnd()+', '+this.Client__c_var+', ');
		setTimeout(() => this.bus.emit('app?m2abc', this.Client__a_var, this.rnd(), this.Client__b_var, this.rnd(), this.Client__c_var), 0);
		
		} else {
		console.log(''+'7'+', '+this.Client__a_var+', '+this.rnd()+', '+this.Client__b_var+', '+this.rnd()+', '+this.Client__c_var+', ');
		setTimeout(() => this.bus.emit('app?m2abc', this.Client__a_var, this.rnd(), this.Client__b_var, this.rnd(), this.Client__c_var), 0);
		console.log(''+'6'+', '+this.rnd()+', '+this.rnd()+', '+this.rnd()+', '+this.rnd()+', '+this.rnd()+', ');
		setTimeout(() => this.bus.emit('app?m2r0r1', this.rnd(), this.rnd(), this.rnd(), this.rnd(), this.rnd()), 0);
		
		}
		
		} else {
		if(this.rnd() < 21) {
		console.log(''+'12'+', '+this.rnd()+', '+this.rnd()+', '+this.rnd()+', '+this.Client__a_var+', '+this.Client__b_var+', ');
		setTimeout(() => this.bus.emit('app?m2bisr0r1ab', this.rnd(), this.rnd(), this.rnd(), this.Client__a_var, this.Client__b_var), 0);
		console.log(''+'13'+', '+this.rnd()+', '+this.rnd()+', '+this.rnd()+', '+this.rnd()+', '+this.Client__c_var+', ');
		setTimeout(() => this.bus.emit('app?m2bisc', this.rnd(), this.rnd(), this.rnd(), this.rnd(), this.Client__c_var), 0);
		
		} else {
		console.log(''+'13'+', '+this.rnd()+', '+this.rnd()+', '+this.rnd()+', '+this.rnd()+', '+this.Client__c_var+', ');
		setTimeout(() => this.bus.emit('app?m2bisc', this.rnd(), this.rnd(), this.rnd(), this.rnd(), this.Client__c_var), 0);
		console.log(''+'12'+', '+this.rnd()+', '+this.rnd()+', '+this.rnd()+', '+this.Client__a_var+', '+this.Client__b_var+', ');
		setTimeout(() => this.bus.emit('app?m2bisr0r1ab', this.rnd(), this.rnd(), this.rnd(), this.Client__a_var, this.Client__b_var), 0);
		
		}
		
		}
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
	Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_S4.to(Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_S1);
	Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_S4.to(Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_S1);
	Client_null_RUN.to(Client_null_STOP).when(() => {
		return (this.Client_counter_var === 100);
	});
	Client_null_RUN.to(Client_null_RUN).when((m3bis) => {
		return m3bis._port === 'diversified' && m3bis._msg === 'm3bis' && (m3bis.a === this.Client__a_var);
	}).effect((m3bis) => {
		console.log(''+'#CLI: Ooh, I needed that app?m3('+m3bis.a+')!');
		console.log(''+'#CLI: We meet again, Doctor Jones!');
	});
	Client_null_RUN.to(Client_null_ERROR).when((m3bis) => {
		return m3bis._port === 'diversified' && m3bis._msg === 'm3bis' && (m3bis.a != this.Client__a_var);
	}).effect((m3bis) => {
		console.log(''+'#CLI: Ooh, I needed that app?m3('+m3bis.a+')!');
		console.log(''+'#CLI: Damn, you re ugly.');
	});
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
	Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_S3.to(Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_S4).when((m3_) => {
		return m3_._port === 'app' && m3_._msg === 'm3_';
	});
	Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_S1.to(Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_S2).when((m3_) => {
		return m3_._port === 'app' && m3_._msg === 'm3_';
	});
	Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_S1.to(Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_S3).when((m3r4r5r2r3a) => {
		return m3r4r5r2r3a._port === 'app' && m3r4r5r2r3a._msg === 'm3r4r5r2r3a';
	}).effect((m3r4r5r2r3a) => {
		this.Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r4_var = m3r4r5r2r3a.r4;
		this.Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r5_var = m3r4r5r2r3a.r5;
		this.Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r2_var = m3r4r5r2r3a.r2;
		this.Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r3_var = m3r4r5r2r3a.r3;
		this.Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_a_var = m3r4r5r2r3a.a;
	});
	Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_S2.to(Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_S4).when((m3r4r5r2r3a) => {
		return m3r4r5r2r3a._port === 'app' && m3r4r5r2r3a._msg === 'm3r4r5r2r3a';
	}).effect((m3r4r5r2r3a) => {
		this.Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r4_var = m3r4r5r2r3a.r4;
		this.Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r5_var = m3r4r5r2r3a.r5;
		this.Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r2_var = m3r4r5r2r3a.r2;
		this.Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r3_var = m3r4r5r2r3a.r3;
		this.Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_a_var = m3r4r5r2r3a.a;
	});
	Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_S1.to(Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_S3).when((m3bisr4r5r2r3a) => {
		return m3bisr4r5r2r3a._port === 'app' && m3bisr4r5r2r3a._msg === 'm3bisr4r5r2r3a';
	}).effect((m3bisr4r5r2r3a) => {
		this.Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r4_var = m3bisr4r5r2r3a.r4;
		this.Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r5_var = m3bisr4r5r2r3a.r5;
		this.Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r2_var = m3bisr4r5r2r3a.r2;
		this.Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r3_var = m3bisr4r5r2r3a.r3;
		this.Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_a_var = m3bisr4r5r2r3a.a;
	});
	Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_S2.to(Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_S4).when((m3bisr4r5r2r3a) => {
		return m3bisr4r5r2r3a._port === 'app' && m3bisr4r5r2r3a._msg === 'm3bisr4r5r2r3a';
	}).effect((m3bisr4r5r2r3a) => {
		this.Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r4_var = m3bisr4r5r2r3a.r4;
		this.Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r5_var = m3bisr4r5r2r3a.r5;
		this.Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r2_var = m3bisr4r5r2r3a.r2;
		this.Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r3_var = m3bisr4r5r2r3a.r3;
		this.Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_a_var = m3bisr4r5r2r3a.a;
	});
	Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_S1.to(Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_S2).when((m3bis_) => {
		return m3bis_._port === 'app' && m3bis_._msg === 'm3bis_';
	});
	Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_S3.to(Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_S4).when((m3bis_) => {
		return m3bis_._port === 'app' && m3bis_._msg === 'm3bis_';
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

Client.prototype.receivem3_Onapp = function(r13, r15, r12, r14, r11) {
	this._receive({_port:"app", _msg:"m3_", r13:r13, r15:r15, r12:r12, r14:r14, r11:r11});
}

Client.prototype.receivem3r4r5r2r3aOnapp = function(r4, r5, r2, r3, a) {
	this._receive({_port:"app", _msg:"m3r4r5r2r3a", r4:r4, r5:r5, r2:r2, r3:r3, a:a});
}

Client.prototype.receivem3bis_Onapp = function(r29, r28, r30, r27, r26) {
	this._receive({_port:"app", _msg:"m3bis_", r29:r29, r28:r28, r30:r30, r27:r27, r26:r26});
}

Client.prototype.receivem3bisr4r5r2r3aOnapp = function(r4, r5, r2, r3, a) {
	this._receive({_port:"app", _msg:"m3bisr4r5r2r3a", r4:r4, r5:r5, r2:r2, r3:r3, a:a});
}

Client.prototype.receivem3Ondiversified = function(r4, r5, r2, r3, a) {
	this._receive({_port:"diversified", _msg:"m3", r4:r4, r5:r5, r2:r2, r3:r3, a:a});
}

Client.prototype.receivem3bis_Ondiversified = function(r29, r28, r30, r27, r26) {
	this._receive({_port:"diversified", _msg:"m3bis_", r29:r29, r28:r28, r30:r30, r27:r27, r26:r26});
}

Client.prototype.receivem3bisr4r5r2r3aOndiversified = function(r4, r5, r2, r3, a) {
	this._receive({_port:"diversified", _msg:"m3bisr4r5r2r3a", r4:r4, r5:r5, r2:r2, r3:r3, a:a});
}

Client.prototype.receivem3bisOndiversified = function(r4, r5, r2, r3, a) {
	this._receive({_port:"diversified", _msg:"m3bis", r4:r4, r5:r5, r2:r2, r3:r3, a:a});
}

Client.prototype.toString = function() {
	let result = 'instance ' + this.name + ':' + this.constructor.name + '\n';
	result += '\n\t_b = ' + this.Client__b_var;
	result += '\n\t_d = ' + this.Client__d_var;
	result += '\n\tr3 = ' + this.Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r3_var;
	result += '\n\tr5 = ' + this.Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r5_var;
	result += '\n\tr2 = ' + this.Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r2_var;
	result += '\n\ta = ' + this.Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_a_var;
	result += '\n\tr3 = ' + this.Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r3_var;
	result += '\n\ta = ' + this.Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_a_var;
	result += '\n\tr4 = ' + this.Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r4_var;
	result += '\n\tr5 = ' + this.Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r5_var;
	result += '\n\t_e = ' + this.Client__e_var;
	result += '\n\tr4 = ' + this.Client_null_generate_m3_from_m3__and_m3r4r5r2r3a_INIT_r4_var;
	result += '\n\t_c = ' + this.Client__c_var;
	result += '\n\tstart = ' + this.Client_start_var;
	result += '\n\t_a = ' + this.Client__a_var;
	result += '\n\tr2 = ' + this.Client_null_generate_m3bis_from_m3bis__and_m3bisr4r5r2r3a_INIT_r2_var;
	result += '\n\tcounter = ' + this.Client_counter_var;
	result += '';
	return result;
}

